package com.example.absensiapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.absensiapp.R;
import com.example.absensiapp.model.GroupTanggalModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GroupTanggalAdapter extends RecyclerView.Adapter<GroupTanggalAdapter.ViewHolder> {

    private Context context;

    private List<GroupTanggalModel> mData;

    public GroupTanggalAdapter(Context context) {
        this.context = context;
    }

    public List<GroupTanggalModel> getmData() {
        return mData;
    }

    public void setmData(List<GroupTanggalModel> mData) {
        this.mData = mData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemRow = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.single_view_group_tgl,viewGroup,false);

        return new ViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.textViewTanggalAbsen.setText(getmData().get(i).getTanggal());
    }

    @Override
    public int getItemCount() {
        return getmData().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tanggal_absen)
        TextView textViewTanggalAbsen;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
