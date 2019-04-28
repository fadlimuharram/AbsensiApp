package com.example.absensiapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.absensiapp.R;
import com.example.absensiapp.model.Absensi;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AbsensiAdapter extends RecyclerView.Adapter<AbsensiAdapter.ViewHolder> {

    private Context context;

    private List<Absensi> mData;

    public AbsensiAdapter(Context context) {
        this.context = context;
    }

    public List<Absensi> getmData() {
        return mData;
    }

    public void setmData(List<Absensi> mData) {
        this.mData = mData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemRow = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.single_view_absensi, viewGroup, false);
        return new ViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.textViewNama.setText(getmData().get(i).getNama());
        viewHolder.textViewJam.setText(getmData().get(i).getJam());
        viewHolder.textViewNpm.setText(getmData().get(i).getNpm());
        viewHolder.textViewMapel.setText(getmData().get(i).getMatapelajaran());
    }

    @Override
    public int getItemCount() {
        return getmData().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.absen_npm)
        TextView textViewNpm;

        @BindView(R.id.absen_nama)
        TextView textViewNama;

        @BindView(R.id.absen_jam)
        TextView textViewJam;

        @BindView(R.id.absen_mapel)
        TextView textViewMapel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
