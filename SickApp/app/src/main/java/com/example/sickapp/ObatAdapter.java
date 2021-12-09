package com.example.sickapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ObatAdapter extends RecyclerView.Adapter<ObatAdapter.ListViewHolder>{
    private ArrayList<Obat> obats;

    public ObatAdapter(ArrayList<Obat> obats) {
        this.obats = obats;
    }

    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_obat,parent,false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        Obat obat = obats.get(position);

        String u = "";
        String[] umur = obat.untuk_umur.split(",");
        if (umur[0].equals("<")){
            u = "Dibawah " + umur[1];
        }
        else if (umur[0].equals(">")){
            u = "Diatas " + umur[1];
        }
        else if (umur[0].equals("-")){
            u = "Antara " + umur[1] + " dan " + umur[2];
        }
        else{
            u = umur[0];
        }

        holder.tvnamaobat.setText(obat.nama);
        holder.tvumurobat.setText("Untuk umur : " + u);
        holder.tvpenyakit.setText("Untuk mengatasi : " + obat.untuk_penyakit);

        holder.cardobat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickCallback.onItemClicked(obat);
            }
        });
    }

    @Override
    public int getItemCount() {
        return obats.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        TextView tvnamaobat, tvumurobat, tvpenyakit;
        CardView cardobat;
        public ListViewHolder(@NonNull final View itemView) {
            super(itemView);
            cardobat = itemView.findViewById(R.id.cardobat);
            tvnamaobat = itemView.findViewById(R.id.tvnamaobat);
            tvumurobat = itemView.findViewById(R.id.tvumurobat);
            tvpenyakit = itemView.findViewById(R.id.tvpenyakit);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(Obat obat);
    }
}
