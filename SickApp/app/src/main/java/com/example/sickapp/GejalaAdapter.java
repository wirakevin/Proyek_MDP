package com.example.sickapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GejalaAdapter extends RecyclerView.Adapter<GejalaAdapter.ListViewHolder>{
    private ArrayList<Gejala> gejalas;
    private ArrayList<String> gejalapenyakit;

    public GejalaAdapter(ArrayList<Gejala> gejalas, ArrayList<String> gejalapenyakit) {
        this.gejalas = gejalas;
        this.gejalapenyakit = gejalapenyakit;
    }

    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gejala,parent,false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        Gejala gejala = gejalas.get(position);

        holder.cbgejala.setText(gejala.nama);

        for (String g:gejalapenyakit) {
            if (g.toUpperCase().equals(gejala.nama.toUpperCase())){
                holder.cbgejala.setChecked(true);
            }
        }

        holder.cbgejala.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                onItemClickCallback.onItemClicked(gejala);
            }
        });
    }

    @Override
    public int getItemCount() {
        return gejalas.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        CheckBox cbgejala;
        public ListViewHolder(@NonNull final View itemView) {
            super(itemView);
            cbgejala = itemView.findViewById(R.id.cbgejala);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(Gejala gejala);
    }
}
