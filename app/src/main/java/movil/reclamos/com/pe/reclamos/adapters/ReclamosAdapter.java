package movil.reclamos.com.pe.reclamos.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

import movil.reclamos.com.pe.reclamos.R;
import movil.reclamos.com.pe.reclamos.rest.beans.Reclamo;

public class ReclamosAdapter extends RecyclerView.Adapter<ReclamosAdapter.MyViewHolder> {

    private List<Reclamo> moviesList;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView codigo, factura, fecha, estado;

        public MyViewHolder(View view) {
            super(view);
            codigo = (TextView) view.findViewById(R.id.txtCodigoReclamo);
            factura = (TextView) view.findViewById(R.id.txtFactura);
            fecha = (TextView) view.findViewById(R.id.txtFechaEnvio);
            estado = (TextView) view.findViewById(R.id.txtEstadoReclamo);
        }
    }

    public ReclamosAdapter(List<Reclamo> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_reclamos, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Reclamo obj = moviesList.get(position);
        holder.codigo.setText( obj.getIdReclamo().toString() );
        holder.factura.setText( obj.getFactura().getNumero() );
        holder.fecha.setText( obj.getCreatedAt() );
        holder.estado.setText( obj.getEstado().toString() );

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}


