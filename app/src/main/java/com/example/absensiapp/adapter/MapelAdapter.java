package com.example.absensiapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.absensiapp.R;
import com.example.absensiapp.model.MapelModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapelAdapter extends RecyclerView.Adapter<MapelAdapter.ViewHolder> {

    private Context context;
    private List<MapelModel> mData;

    public MapelAdapter(Context context) {
        this.context = context;
    }

    public List<MapelModel> getmData() {
        return mData;
    }

    public void setmData(List<MapelModel> mData) {
        this.mData = mData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemRow = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.single_view_mapel, viewGroup, false);
        return new ViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.textViewMapel.setText(getmData().get(i).getNama());
        viewHolder.textViewHari.setText(getmData().get(i).getHari());
        viewHolder.textViewJam.setText(getmData().get(i).getJam());
        viewHolder.textViewKelas.setText(getmData().get(i).getKelas());
    }

    @Override
    public int getItemCount() {
        return getmData().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.mapel_nama)
        TextView textViewMapel;

        @BindView(R.id.mapel_hari)
        TextView textViewHari;

        @BindView(R.id.mapel_jam)
        TextView textViewJam;

        @BindView(R.id.mapel_kelas)
        TextView textViewKelas;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
