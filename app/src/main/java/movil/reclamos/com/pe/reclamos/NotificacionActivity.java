package movil.reclamos.com.pe.reclamos;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Hashtable;

import movil.reclamos.com.pe.reclamos.beans.Parametro;
import movil.reclamos.com.pe.reclamos.beans.Usuario;
import movil.reclamos.com.pe.reclamos.database.ReclamosDBHelper;
import movil.reclamos.com.pe.reclamos.rest.beans.Notificacion;
import movil.reclamos.com.pe.reclamos.rest.beans.Reclamo;

public class NotificacionActivity extends AppCompatActivity {

    private static final String _TAG_ = "NOTIFICA";
    private ReclamosDBHelper dbHelper;
    private String ip;
    private TextView lblAsunto,lblFecha,lblNotificacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacion);

        lblAsunto = (TextView) findViewById(R.id.lblAsunto);
        lblFecha = (TextView) findViewById(R.id.lblFecha);
        lblNotificacion = (TextView) findViewById(R.id.lblNotificacion);

        dbHelper = new ReclamosDBHelper( this );
        Parametro tmpip = dbHelper.getParametro( ReclamosDBHelper.PARAMETRO_IP  );
        if( tmpip != null){
            this.ip = tmpip.getValor() ;
        }

        getData();
    }

    public void getData(){
        Log.v(_TAG_, "getData INI");
        //showProgress(true);
        Usuario user = dbHelper.getUsuario();
        NotificacionActivity.HttpRequestNotificacionesInternTask http = new NotificacionActivity.HttpRequestNotificacionesInternTask();
        http.execute( user.getIdCliente()+"" );
        Log.v(_TAG_, "getData FIN");
    }

    public class HttpRequestNotificacionesInternTask extends AsyncTask<String, Void, Notificacion> {

        private static final String _TAG_ = "HttpLic";
        private String url = "http://"+ ip +":8080/reclamos/rest/clientes/{ID_USUARIO}/notificaciones";
        private String errMsg = "";

        public Notificacion obtenerNotificacion(String idUsuario){
            Log.v(_TAG_, "obtenerNotificacion ");
            Log.v(_TAG_, "url="+url);

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            Hashtable<String, String> variables = new Hashtable<String, String>();
            variables.put("ID_USUARIO", idUsuario);

            return restTemplate.getForObject(url, Notificacion.class, variables);
        }

        @Override
        protected Notificacion doInBackground(String... params) {
            Notificacion notificacion = null;
            try {
                notificacion = obtenerNotificacion(params[0]);
            }catch(HttpClientErrorException e){
                errMsg = "Error de conexion: " + e.getMessage();
                Log.e(_TAG_, e.getMessage(), e);
            }catch (Exception e) {
                errMsg = "Error generico: " + e.getMessage();
                Log.e(_TAG_, e.getMessage(), e);
            }
            return notificacion;
        }

        protected void onProgressUpdate(Notificacion notif){
            Log.d(_TAG_ , "onProgressUpdate INI" );
            //showProgress(false);
            if( !errMsg.equals("")){
                Toast.makeText(getApplicationContext(), "Error: " +errMsg, Toast.LENGTH_SHORT).show();
            }
            Log.d(_TAG_ , "onProgressUpdate FIN" );
        }

        protected void onPostExecute(Notificacion notificacion) {
            if( notificacion != null && notificacion.getIdNotificacion() != null){

                    lblFecha.setText( notificacion.getCreatedAt()+ "" );
                    lblAsunto.setText( notificacion.getAsunto() + "" );
                    lblNotificacion.setText( notificacion.getNotificacion() + "" );

            }else{
                String mensaje = "No se pudo obtener la notificacion" ;
                lblFecha.setText(  "No se pudo obtener la notificacion" );
                lblAsunto.setText( "" );
                lblNotificacion.setText( "" );
                if(!errMsg.equals("")) mensaje = errMsg;
                Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
            }
        }

    }
}
