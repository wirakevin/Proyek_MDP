package com.example.sickapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DiseaseAdapter extends RecyclerView.Adapter<DiseaseAdapter.ListViewHolder>{
    private ArrayList<Disease> diseases;

    public DiseaseAdapter(ArrayList<Disease> diseases) {
        this.diseases = diseases;
    }

    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_disease,parent,false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        Disease disease = diseases.get(position);

        holder.tvnama.setText(disease.nama);
        holder.tvgejala.setText("Gejala : " + disease.gejala);

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickCallback.onItemClicked(disease);
            }
        });
    }

    @Override
    public int getItemCount() {
        return diseases.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        TextView tvnama, tvgejala;
        CardView card;
        public ListViewHolder(@NonNull final View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.card);
            tvnama = itemView.findViewById(R.id.tvnamaobat);
            tvgejala = itemView.findViewById(R.id.tvgejala);
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(Disease disease);
    }
}
