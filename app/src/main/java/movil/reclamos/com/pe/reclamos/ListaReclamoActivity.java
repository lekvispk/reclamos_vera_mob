package movil.reclamos.com.pe.reclamos;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import movil.reclamos.com.pe.reclamos.adapters.DividerItemDecoration;
import movil.reclamos.com.pe.reclamos.adapters.ReclamosAdapter;
import movil.reclamos.com.pe.reclamos.adapters.RecyclerTouchListener;
import movil.reclamos.com.pe.reclamos.beans.Parametro;
import movil.reclamos.com.pe.reclamos.beans.Usuario;
import movil.reclamos.com.pe.reclamos.database.ReclamosDBHelper;
import movil.reclamos.com.pe.reclamos.rest.beans.Reclamo;

public class ListaReclamoActivity extends AppCompatActivity {

    private static final String _TAG_ = "RECLAMOS";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ReclamosDBHelper dbHelper;
    private List<Reclamo> reclamos = new ArrayList<Reclamo>();
    private String ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_reclamo);
        dbHelper = new ReclamosDBHelper( this );

        Parametro tmpip = dbHelper.getParametro( ReclamosDBHelper.PARAMETRO_IP  );
        if( tmpip != null){
            this.ip = tmpip.getValor() ;
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_reclamos);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mAdapter = new ReclamosAdapter( reclamos );
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {

                Intent intent = new Intent(getApplicationContext(), DetalleReclamoActivity.class);
                Reclamo rec =  reclamos.get( position );

                Log.v(_TAG_, "Reclamo 1 : " + rec.toString() );

                Bundle bundle = new Bundle();
                bundle.putParcelable("Reclamo" , rec );
                bundle.putParcelable("factura" , rec.getFactura() );
                intent.putExtras(bundle);

                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        getData();

    }

    public void getData(){
        Log.v(_TAG_, "getData INI");
        //showProgress(true);

        Usuario user = dbHelper.getUsuario();
        HttpRequestLicenciasInternTask http = new HttpRequestLicenciasInternTask();
        http.execute( user.getIdCliente()+"" );
        Log.v(_TAG_, "getData FIN");
    }

    public class HttpRequestLicenciasInternTask extends AsyncTask<String, Void, Reclamo[]> {

        private static final String _TAG_ = "HttpLic";
        private String url = "http://"+ ip +":8080/reclamos/rest/clientes/{ID_USUARIO}/reclamos";
        private String errMsg = "";

        public Reclamo[] obtenerReclamos(String idUsuario){
            Log.v(_TAG_, "obtenerReclamos ");
            Log.v(_TAG_, "url="+url);

            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            Hashtable<String, String> variables = new Hashtable<String, String>();
            variables.put("ID_USUARIO", idUsuario);

            return restTemplate.getForObject(url, Reclamo[].class, variables);
        }

        @Override
        protected Reclamo[] doInBackground(String... params) {
            Reclamo[] lista = null;
            try {
                lista = obtenerReclamos(params[0]);
            }catch(HttpClientErrorException e){
                errMsg = "Error de conexion: " + e.getMessage();
                Log.e(_TAG_, e.getMessage(), e);
            }catch (Exception e) {
                errMsg = "Error generico: " + e.getMessage();
                Log.e(_TAG_, e.getMessage(), e);
            }
            return lista;
        }

        protected void onProgressUpdate(Reclamo[] lista){
            Log.d(_TAG_ , "onProgressUpdate INI" );
            //showProgress(false);
            if( !errMsg.equals("")){
                Toast.makeText(getApplicationContext(), "Error: " +errMsg, Toast.LENGTH_SHORT).show();
            }
            Log.d(_TAG_ , "onProgressUpdate FIN" );
        }

        protected void onPostExecute(Reclamo[] lista) {
             if( lista != null){
                for(Reclamo obj : lista){
                    reclamos.add( obj );
                }
                mAdapter.notifyDataSetChanged();
            }else{
                String mensaje = "No se pudo obtener la lista de reclamos" ;
                if(!errMsg.equals("")) mensaje = errMsg;
                Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_SHORT).show();
            }
        }

    }

}
