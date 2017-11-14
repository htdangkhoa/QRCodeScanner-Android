package com.example.dangkhoa.qrcodefirebase.Fragments;

import android.content.DialogInterface;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.example.dangkhoa.qrcodefirebase.R;
import com.example.dangkhoa.qrcodefirebase.Utils.Services;
import com.google.zxing.Result;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.dm7.barcodescanner.core.CameraUtils;
import me.dm7.barcodescanner.core.CameraWrapper;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by dangkhoa on 11/11/17.
 */

public class CameraFrameFragment extends Fragment implements ZXingScannerView.ResultHandler {
    public static ZXingScannerView zXingScannerView;
    int cameraId = 0;
    @OnClick(R.id.btnSwitch) public void onSwitch() {
        if (cameraId == 1) {
            Bundle bundle = new Bundle();
            bundle.putInt("CAMERA_ID", 0);
            Services.Navigate(getFragmentManager(), new CameraFrameFragment(), "CAMERA", bundle, Services.NO_ANIMATION);
        } else {
            Bundle bundle = new Bundle();
            bundle.putInt("CAMERA_ID", 1);
            Services.Navigate(getFragmentManager(), new CameraFrameFragment(), "CAMERA", bundle, Services.NO_ANIMATION);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera, container, false);

        initialize(view);

        return view;
    }

    private void initialize(View view) {
        Bundle bundle = getArguments();
        cameraId = bundle.getInt("CAMERA_ID", 0);
        ButterKnife.bind(this, view);
    }

    private void openCamera() {
        if (cameraId != 0) zXingScannerView.setAspectTolerance(.5f);

        zXingScannerView.startCamera(cameraId);
        zXingScannerView.setResultHandler(this);
    }

    private void stopCamera() {
        zXingScannerView.stopCameraPreview();
        zXingScannerView.stopCamera();
    }

    private void resumeCamera() {
        zXingScannerView.resumeCameraPreview(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        openCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopCamera();
    }

    @Override
    public void handleResult(Result result) {
        Log.v("CONTENT", result.toString());

        try {
            JSONObject object = new JSONObject(result.toString());

            Bundle bundle = new Bundle();
            bundle.putLong("PRICE", object.getLong("price"));
            bundle.putString("DETAIL", object.getString("detail"));
            Services.Navigate(getFragmentManager(), new DetailedFragment(), "DETAILED", bundle, Services.FROM_RIGHT_TO_LEFT);
        } catch (JSONException e) {
            Services.ShowDialog(getContext(), "Error", "The data structure is incorrect. Please try again.", null, null, "OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    resumeCamera();
                }
            });
        }
    }
}
