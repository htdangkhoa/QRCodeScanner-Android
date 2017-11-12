package com.example.dangkhoa.qrcodefirebase.Fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dangkhoa.qrcodefirebase.Utils.Services;
import com.google.zxing.Result;

import org.json.JSONException;
import org.json.JSONObject;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by dangkhoa on 11/11/17.
 */

public class CameraFragment extends Fragment implements ZXingScannerView.ResultHandler {
    ZXingScannerView zXingScannerView;
    boolean resume = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        zXingScannerView = new ZXingScannerView(getActivity());

        return zXingScannerView;
    }

    public void openCamera() {
        zXingScannerView.startCamera(1);
        zXingScannerView.setResultHandler(this);
    }

    public void stopCamera() {
        zXingScannerView.stopCamera();
    }

    public void resumeCamera() {
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
