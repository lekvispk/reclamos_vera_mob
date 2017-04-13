package movil.reclamos.com.pe.reclamos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import movil.reclamos.com.pe.reclamos.database.ReclamosDBHelper;
import movil.reclamos.com.pe.reclamos.rest.beans.Factura;
import movil.reclamos.com.pe.reclamos.rest.beans.Reclamo;

public class DetalleReclamoActivity extends AppCompatActivity {

    private static final String _TAG_ = "DETALLE";
    private TextView lblAsunto,lblFactura,lblFecha,lblPrioridad,lblMensaje,lblVencimiento;
    Reclamo reclamo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_reclamo);

        lblAsunto = (TextView) findViewById(R.id.lblAsuntoContent);
        lblFactura = (TextView) findViewById(R.id.lblFacturaContent);
        lblFecha = (TextView) findViewById(R.id.lblFechaContent);
        lblPrioridad = (TextView) findViewById(R.id.lblPrioridadContent);
        lblMensaje = (TextView) findViewById(R.id.lblMensajeContent);
        lblVencimiento = (TextView) findViewById(R.id.lblVencimientoContent);

        Bundle bundle = getIntent().getExtras();
        reclamo = bundle.getParcelable("Reclamo");
        reclamo.setFactura( (Factura) bundle.getParcelable("factura") ) ;
        //reclamo = (Reclamo)getIntent().getSerializableExtra("Reclamo");

        Log.v(_TAG_, "Reclamo 2: " + reclamo.toString() );
        if( reclamo != null ){

            lblAsunto.setText( reclamo.getAsunto() + "" );
            if( reclamo.getFactura() != null){
                lblFactura.setText( reclamo.getFactura().getNumero() );
            }
            lblFecha.setText( reclamo.getFecReclamo()  + "" );
            lblPrioridad.setText( reclamo.getPrioridad() + "" );
            lblVencimiento.setText( reclamo.getVencimiento()  + "" );
            lblMensaje.setText( reclamo.getMensaje() + "" );

        }

    }
}
