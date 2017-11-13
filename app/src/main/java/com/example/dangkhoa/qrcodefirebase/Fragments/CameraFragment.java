package com.example.dangkhoa.qrcodefirebase.Fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;

import com.example.dangkhoa.qrcodefirebase.R;
import com.example.dangkhoa.qrcodefirebase.Utils.Services;
import com.google.zxing.Result;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by dangkhoa on 11/13/17.
 */

public class CameraFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        CameraFrameFragment.zXingScannerView = new ZXingScannerView(getActivity());

        return CameraFrameFragment.zXingScannerView;
    }
}
