package movil.reclamos.com.pe.reclamos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import movil.reclamos.com.pe.reclamos.beans.Parametro;
import movil.reclamos.com.pe.reclamos.database.ReclamosDBHelper;

public class ConfigActivity extends AppCompatActivity {

    private static final String _TAG_ = "CONFIG";
    private ReclamosDBHelper dbHelper;
    private EditText mTxtIp;
    Parametro ipActual;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        dbHelper = new ReclamosDBHelper( this );
        mTxtIp = (EditText) findViewById(R.id.txtIp);
        Log.d(_TAG_, "onCreate" );
        Parametro ip = dbHelper.getParametro( ReclamosDBHelper.PARAMETRO_IP );
        if( ip != null){
            Log.d(_TAG_, "ip encontrada" );
            ipActual = ip;
            mTxtIp.setText( ip.getValor() );
        }else{
            Log.d(_TAG_, "ip no encontrada" );
        }
    }

    public void grabarConfiguracion(View v){
        Log.d(_TAG_, "grabando configuracion" );

        Parametro ip = new Parametro();
        ip.setCodigo( ReclamosDBHelper.PARAMETRO_IP );
        ip.setValor( mTxtIp.getText().toString() );
        ip.setEstado( 1 );

        if( ipActual!=null ){
            Log.d(_TAG_, " modificar ip " );
            Log.d(_TAG_, " ip ID = "  + ipActual.getId());
            ip.setId( ipActual.getId());
            dbHelper.updateReclamo( ip );
        }else{
            Log.d(_TAG_, "nueva ip" );
            dbHelper.saveReclamo( ip );
        }

        Intent intent = null;
        intent = new Intent(getApplicationContext(), LoginActivity.class);
        //Con esto se elimina esta pantalla para evitar el mostrarla al dar clic en el boton Volver
        finish();
    }

}
