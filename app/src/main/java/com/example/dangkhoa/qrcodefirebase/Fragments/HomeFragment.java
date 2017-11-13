package com.example.dangkhoa.qrcodefirebase.Fragments;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dangkhoa.qrcodefirebase.R;
import com.example.dangkhoa.qrcodefirebase.Utils.Network;
import com.example.dangkhoa.qrcodefirebase.Utils.Services;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by dangkhoa on 11/10/17.
 */

public class HomeFragment extends Fragment {
    int PERMISSION_CAMERA = 11111;

    private Unbinder unbinder;

    public static TextView txtStatus;
    @OnClick({R.id.btnCamera, R.id.btnView}) public void goToCamera(View view) {
        int id = view.getId();

        switch (id) {
            case R.id.btnCamera: {
                goToCamera();
                break;
            }
            case R.id.btnView: {
                Services.Navigate(getFragmentManager(), new CmsFragment(), "CMS", null, Services.FROM_RIGHT_TO_LEFT);
                break;
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initialize(view);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initialize(View view) {
        txtStatus = (TextView) view.findViewById(R.id.txtStatus);
        unbinder = ButterKnife.bind(this, view);

        boolean IsConnected = Network.getConnectivityStatusString(getContext());
        if (IsConnected) {
            txtStatus.setText("Network: On");
        } else {
            txtStatus.setText("Network: Off");
        }
    }

    private void goToCamera() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestCameraPermission();
            return;
        }

        Services.Navigate(getFragmentManager(), new CameraFrameFragment(), "CAMERA", null, Services.FROM_BOTTOM_TO_TOP);
    }

    private void requestCameraPermission() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
            Services.ShowDialog(getContext(), "", getContext().getResources().getString(R.string.rationale_camera), "CANCEL", null, "OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, PERMISSION_CAMERA);
                }
            });
        } else {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, PERMISSION_CAMERA);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_CAMERA) {
            if (grantResults.length != 1 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {

            } else {
                goToCamera();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
