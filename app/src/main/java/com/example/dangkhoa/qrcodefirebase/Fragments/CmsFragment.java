package com.example.dangkhoa.qrcodefirebase.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dangkhoa.qrcodefirebase.Adapters.QRAdapter;
import com.example.dangkhoa.qrcodefirebase.Models.QRModel;
import com.example.dangkhoa.qrcodefirebase.R;
import com.example.dangkhoa.qrcodefirebase.Realm.QRCode;
import com.example.dangkhoa.qrcodefirebase.SuperClass.DividerItemDecoration;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dangkhoa on 11/12/17.
 */

public class CmsFragment extends Fragment {
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.txtNotify) TextView txtNotify;

    ArrayList<QRModel> qrModels= new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cms, container, false);

        initialize(view);

        return view;
    }

    private void initialize(View view) {
        ButterKnife.bind(this, view);

        qrModels.addAll(QRCode.getData());
        if (qrModels.size() > 0) {
            recyclerView.setVisibility(View.VISIBLE);
            txtNotify.setVisibility(View.GONE);
            setupRecyclerView(this.recyclerView);
        } else {
            recyclerView.setVisibility(View.GONE);
            txtNotify.setVisibility(View.VISIBLE);
        }
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        DividerItemDecoration decoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(decoration);

        QRAdapter adapter = new QRAdapter(qrModels);
        recyclerView.setAdapter(adapter);
    }

}
