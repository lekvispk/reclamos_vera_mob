package movil.reclamos.com.pe.reclamos.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;
import movil.reclamos.com.pe.reclamos.beans.ItemCombo;

public class SpinAdapter extends ArrayAdapter<ItemCombo> {

    private Context context;
    private List<ItemCombo> values;

    public SpinAdapter(Context context, int textViewResourceId, List<ItemCombo> values) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values = values;
    }

    public int getCount(){
       return values.size();
    }

    public ItemCombo getItem(int position){
       return values.get(position);
    }

    public long getItemId(int position){
       return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        label.setText( values.get(position).getNombre() );
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView,ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        label.setText(values.get(position).getNombre());
        return label;
    }
}