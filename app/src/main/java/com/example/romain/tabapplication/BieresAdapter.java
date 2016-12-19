package com.example.romain.tabapplication;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;


public class BieresAdapter extends RecyclerView.Adapter<BieresAdapter.BiereHolder> {

    JSONArray bieres;
    public BieresAdapter(JSONArray Json){
        this.bieres = Json;
    }

    @Override
    public BiereHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_second,null,false);
        BiereHolder bh = new BiereHolder(v);
        return bh;
    }

    @Override
    public void onBindViewHolder(BiereHolder holder, int position) {
        try {
            bieres.getJSONObject(position).getString(String.valueOf(holder.name));
            holder.name.setText("bieres");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return  bieres.length();
    }

    public class BiereHolder extends RecyclerView.ViewHolder {
        public TextView name ;


        public BiereHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.rv_biere_element_name);

        }
    }

    void setNewBiere (JSONArray Json){
        bieres = Json;
        notifyDataSetChanged();

    }

}
