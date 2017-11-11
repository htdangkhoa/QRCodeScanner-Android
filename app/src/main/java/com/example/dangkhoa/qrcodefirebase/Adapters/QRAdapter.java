package com.example.dangkhoa.qrcodefirebase.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dangkhoa.qrcodefirebase.Models.QRModel;
import com.example.dangkhoa.qrcodefirebase.R;
import com.example.dangkhoa.qrcodefirebase.Utils.Services;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;

/**
 * Created by dangkhoa on 11/12/17.
 */

public class QRAdapter extends RecyclerView.Adapter<QRAdapter.ViewHolder> {
    ArrayList<QRModel> qrModels;

    public QRAdapter(ArrayList<QRModel> qrModels) {
        this.qrModels = qrModels;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.model_qr, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textViewList.get(0).setText("Id:\t\t\t\t\t\t" + qrModels.get(position).getId());
        holder.textViewList.get(1).setText("Detail:\t\t" + qrModels.get(position).getDetail());
        holder.textViewList.get(2).setText("Time:\t\t" + Services.getTime(qrModels.get(position).getTimestamp()));
        holder.textViewList.get(3).setText(String.valueOf(qrModels.get(position).getPrice()));
    }

    @Override
    public int getItemCount() {
        return qrModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindViews({R.id.txtId, R.id.txtDetail, R.id.txtTime, R.id.txtPrice}) List<TextView> textViewList;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
