package ibanez.brian.myapplication;

import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by alumno on 08/09/2016.
 */
public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private List<Persona> personas;

    public MyAdapter(List<Persona> personas){

        this.personas = personas;
        
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layaout, parent, false);
        MyViewHolder mvh = new MyViewHolder(v);
        return mvh;

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Persona p = this.personas.get(position);
        holder.tvNombre.setText(p.getNombre());
        holder.tvApellido.setText(p.getApellido());

    }

    @Override
    public int getItemCount() {
        return personas.size();
    }
}
