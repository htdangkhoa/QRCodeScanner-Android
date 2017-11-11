package com.example.dangkhoa.qrcodefirebase.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dangkhoa.qrcodefirebase.Models.QRModel;
import com.example.dangkhoa.qrcodefirebase.R;
import com.example.dangkhoa.qrcodefirebase.Realm.QRCode;
import com.example.dangkhoa.qrcodefirebase.Utils.Firebase;
import com.example.dangkhoa.qrcodefirebase.Utils.Network;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by dangkhoa on 11/11/17.
 */

public class DetailedFragment extends Fragment {
    @BindViews({R.id.txtPrice, R.id.txtDetail}) List<TextView> textViews;
    @OnClick(R.id.btnUpload) public void onUpload() {
        if (detail != null && !detail.isEmpty() && price > -1) {
            QRModel qrModel = new QRModel(detail, price);

            boolean isOnline = Network.getConnectivityStatusString(getContext());
            if (isOnline) {
                final ProgressDialog dialog = ProgressDialog.show(getActivity(), "","Uploading...", true);
                Firebase.createQRCode(qrModel, new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        dialog.hide();
                        getFragmentManager().popBackStack();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            } else {
                QRCode.addData(qrModel);
                getFragmentManager().popBackStack();
            }
        }
    }

    long price = -1;
    String detail = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detailed, container, false);

        initialize(view);

        return view;
    }

    private void initialize(View view) {
        ButterKnife.bind(this, view);

        Bundle bundle = getArguments();

        if (bundle != null) {
            price = bundle.getLong("PRICE");
            detail = bundle.getString("DETAIL");

            textViews.get(0).setText(String.valueOf(price));

            if (detail != null) {
                textViews.get(1).setText(detail);
            }
        }
    }
}
