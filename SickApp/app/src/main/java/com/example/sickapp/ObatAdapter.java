package com.example.sickapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

        holder.tvnama.setText(obat.nama);
        holder.tvgejala.setText(obat.gejala);

        holder.card.setOnClickListener(new View.OnClickListener() {
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
        TextView tvnama, tvgejala;
        ImageView ivimage;
        CardView card;
        public ListViewHolder(@NonNull final View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.card);
            tvnama = itemView.findViewById(R.id.tvnama);
            tvgejala = itemView.findViewById(R.id.tvgejala);
            ivimage = itemView.findViewById(R.id.ivimage);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(Obat obat);
    }
}
