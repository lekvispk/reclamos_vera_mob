package movil.reclamos.com.pe.reclamos;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import movil.reclamos.com.pe.reclamos.adapters.SpinAdapter;
import movil.reclamos.com.pe.reclamos.beans.ItemCombo;
import movil.reclamos.com.pe.reclamos.beans.Parametro;
import movil.reclamos.com.pe.reclamos.database.ReclamosDBHelper;
import movil.reclamos.com.pe.reclamos.rest.beans.Cliente;
import movil.reclamos.com.pe.reclamos.rest.beans.Factura;
import movil.reclamos.com.pe.reclamos.rest.beans.Reclamo;
import movil.reclamos.com.pe.reclamos.rest.beans.RespuestaRest;
import movil.reclamos.com.pe.reclamos.rest.beans.Usuario;

public class ReclamosActivity extends AppCompatActivity {

    private static final String _TAG_ = "LOGIN";
    private HttpRequestRegistraReclamoTask registroTask = null;
    private ReclamosDBHelper dbHelper;
    private EditText txtRuc, txtRepresentante, txtEmail, txtFecha, txtAsunto, txtObservacion;
    private Spinner cbFacturas;
    private SpinAdapter adapterFacturas;
    private List<ItemCombo> facturas;
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

        facturas = new ArrayList<ItemCombo>();

        usuarioActivo = dbHelper.getUsuario();
        Log.v(_TAG_, "usuarioActivo = " + usuarioActivo);
        txtRuc = (EditText) findViewById(R.id.txtRuc);
        txtRepresentante = (EditText) findViewById(R.id.txtRepresentante);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        cbFacturas = (Spinner)findViewById(R.id.cbFactura);
        txtAsunto = (EditText) findViewById(R.id.txtAsunto);
        txtObservacion = (EditText) findViewById(R.id.txtObservaciones);

        adapterFacturas = new SpinAdapter(this, android.R.layout.simple_spinner_item, facturas);

        btnGrabar = (Button) findViewById(R.id.btnGrabar);

        if(usuarioActivo != null){
            Log.v(_TAG_, "usuario encontrado");
            txtRuc.setText( usuarioActivo.getRuc() );
            txtRepresentante.setText( usuarioActivo.getRepresentante() );
            txtEmail.setText( usuarioActivo.getCorreo() );

            HttpRequestBuscarFacturasTask registroTask = new HttpRequestBuscarFacturasTask();
            registroTask.execute( usuarioActivo.getIdCliente()+"" );

        }

        cbFacturas.setAdapter(adapterFacturas);
        cbFacturas.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                        int position = cbFacturas.getSelectedItemPosition();
                        //Toast.makeText(getApplicationContext(),"seleccionaste "+ proveedores.get(position).getNombre(), Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                }
        );

    }

    public void buscarCliente( View view ){
        //HttpRequestBuscarFacturasTask registroTask = new HttpRequestBuscarFacturasTask();
        //registroTask.execute( txtRuc.getText().toString() );
    }

    public void grabarReclamo( View view){

        if(  cbFacturas.getSelectedItem()==null ){
            Toast.makeText(getApplicationContext(),"No hay una factura seleccionada." , Toast.LENGTH_LONG).show();
            return;
        }

        registroTask = new HttpRequestRegistraReclamoTask();
        registroTask.execute(
                        txtObservacion.getText().toString() ,
                        txtAsunto.getText().toString(),
                        usuarioActivo.getIdCliente().toString(),
                        cbFacturas.getSelectedItem()==null?"":((ItemCombo)cbFacturas.getSelectedItem()).getNombre(),
                        cbFacturas.getSelectedItem()==null? "":((ItemCombo)cbFacturas.getSelectedItem()).getCodigo()+""
                    );

    }

    public class HttpRequestRegistraReclamoTask extends AsyncTask<String, Void, RespuestaRest> {

        private static final String _TAG_ = "HttpLic";
        private String url = "http://"+ ip +"/reclamos/rest/clientes/reclamos/";
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
            reclamo.setFactura( new Factura());
            reclamo.setMensaje( params[0] );
            reclamo.setAsunto( params[1] );
            reclamo.setIdCliente( Integer.valueOf(params[2]) );
            reclamo.getFactura().setNumero( params[3] );
            reclamo.getFactura().setIdFactura( Integer.valueOf( params[4] ) );

            reclamo.setEstado( 1 );

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

    public class HttpRequestBuscarFacturasTask extends AsyncTask<String, Void, Factura[]> {

        private static final String _TAG_ = "HttpLic";
        private String url = "http://"+ ip +"/reclamos/rest/clientes/{ID}/facturas";
        private String errMsg = "";

        public Factura[] buscarFacturas(String idCliente){
            Log.v(_TAG_, "buscarFacturas");
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            Hashtable<String, String> variables = new Hashtable<String, String>();
            variables.put("ID", idCliente);
            Factura[] response = restTemplate.getForObject(url , Factura[].class , variables);
            Log.v(_TAG_, "response " + response );
            return response;
        }

        @Override
        protected Factura[] doInBackground(String... params) {
            Factura[] rpta = null;

            try {
                rpta = buscarFacturas( params[0] );
            }catch(HttpClientErrorException e){
                errMsg = "Error de conexion: " + e.getMessage();
                Log.e(_TAG_, "doInBackground-" + e.getMessage(), e);
            }catch (Exception e) {
                errMsg = "Error generico: " + e.getMessage();
                Log.e(_TAG_, "doInBackground-" + e.getMessage(), e);
            }
            return rpta;
        }

        protected void onProgressUpdate(Factura[] respuesta){
            Log.d(_TAG_ , "onProgressUpdate INI" );

            Log.d(_TAG_ , "onProgressUpdate FIN" );
        }

        protected void onPostExecute(Factura[] respuesta) {
            Log.d(_TAG_ , "onPostExecute INI" );
            //showProgress(false);
            registroTask = null;
            if( respuesta != null ){

                adapterFacturas.clear();
                for (Factura fact : respuesta) {
                    adapterFacturas.insert(  new ItemCombo( fact.getIdFactura() , fact.getNumero() , fact.getMonto()+"" ) , adapterFacturas.getCount() );
                }
                adapterFacturas.notifyDataSetChanged();

            }else{
                Toast.makeText(getApplicationContext(), "Cliente no existe.", Toast.LENGTH_SHORT).show();
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
