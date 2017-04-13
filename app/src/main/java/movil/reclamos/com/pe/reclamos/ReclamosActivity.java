package movil.reclamos.com.pe.reclamos;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Hashtable;

import movil.reclamos.com.pe.reclamos.beans.Parametro;
import movil.reclamos.com.pe.reclamos.database.ReclamosDBHelper;
import movil.reclamos.com.pe.reclamos.rest.beans.Factura;
import movil.reclamos.com.pe.reclamos.rest.beans.Reclamo;
import movil.reclamos.com.pe.reclamos.rest.beans.RespuestaRest;
import movil.reclamos.com.pe.reclamos.rest.beans.Usuario;

public class ReclamosActivity extends AppCompatActivity {

    private static final String _TAG_ = "LOGIN";
    private HttpRequestRegistraReclamoTask registroTask = null;
    private ReclamosDBHelper dbHelper;
    private EditText txtRuc, txtRepresentante, txtEmail, txtFactura, txtFecha, txtAsunto, txtObservacion;
    private Button btnGrabar;
    private String ip;
    private movil.reclamos.com.pe.reclamos.beans.Usuario usuarioActivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reclamos);

        dbHelper = new ReclamosDBHelper( this );

        Parametro tmpip = dbHelper.getParametro( ReclamosDBHelper.PARAMETRO_IP  );
        if( tmpip != null){
            this.ip = tmpip.getValor() ;
        }
        usuarioActivo = dbHelper.getUsuario();

        txtRuc = (EditText) findViewById(R.id.txtRuc);
        txtRepresentante = (EditText) findViewById(R.id.txtRepresentante);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtFactura = (EditText) findViewById(R.id.txtFactura);
        txtAsunto = (EditText) findViewById(R.id.txtAsunto);
        txtObservacion = (EditText) findViewById(R.id.txtObservaciones);

        btnGrabar = (Button) findViewById(R.id.btnGrabar);

    }

    public void grabarReclamo( View view){

        registroTask = new HttpRequestRegistraReclamoTask();
        registroTask.execute(
                        txtObservacion.getText().toString() ,
                        txtAsunto.getText().toString(),
                        usuarioActivo.getIdCliente().toString(),
                        txtFactura.getText().toString()
                    );
    }

    public class HttpRequestRegistraReclamoTask extends AsyncTask<String, Void, RespuestaRest> {

        private static final String _TAG_ = "HttpLic";
        private String url = "http://"+ ip +":8080/reclamos/rest/clientes/reclamos/";
        private String errMsg = "";

        public RespuestaRest enviarDatosDeRclamo(Reclamo reclamo){
            Log.v(_TAG_, "enviarDatosDeRclamo ");
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
            RespuestaRest response = restTemplate.postForObject(url, reclamo , RespuestaRest.class );
            return response;
        }

        @Override
        protected RespuestaRest doInBackground(String... params) {
            RespuestaRest rpta = null;

            Reclamo reclamo = new Reclamo();
            reclamo.setMensaje( params[0] );
            reclamo.setAsunto( params[1] );
            reclamo.setIdCliente( Integer.valueOf(params[2]) );
            reclamo.setFactura( new Factura());
            reclamo.getFactura().setIdFactura(1);
            reclamo.getFactura().setNumero( params[3] );

            try {
                rpta = enviarDatosDeRclamo( reclamo );
            }catch(HttpClientErrorException e){
                errMsg = "Error de conexion: " + e.getMessage();
                Log.e(_TAG_, "doInBackground-" + e.getMessage(), e);
            }catch (Exception e) {
                errMsg = "Error generico: " + e.getMessage();
                Log.e(_TAG_, "doInBackground-" + e.getMessage(), e);
            }
            return rpta;
        }

        protected void onProgressUpdate(RespuestaRest respuesta){
            Log.d(_TAG_ , "onProgressUpdate INI" );

            Log.d(_TAG_ , "onProgressUpdate FIN" );
        }

        protected void onPostExecute(RespuestaRest respuesta) {
            Log.d(_TAG_ , "onPostExecute INI" );
            //showProgress(false);
            registroTask = null;
            if( respuesta.getCodigo() == 1 ){

                Log.v("auth", "reclamo registrado");
                Toast.makeText(getApplicationContext(), "reclamo registrado", Toast.LENGTH_SHORT).show();
                Intent inten2t = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(inten2t);

            }else{
                String mensaje = "No se pudo grabar el reclamo" ;
                if(!errMsg.equals("")) mensaje = errMsg;
                Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
            }
            Log.d(_TAG_ , "onPostExecute FIN" );
        }

        @Override
        protected void onCancelled() {
            Log.d(_TAG_ , "onCancelled INI" );
            registroTask = null;
            Log.d(_TAG_ , "onCancelled FIN" );
        }

    }
}
