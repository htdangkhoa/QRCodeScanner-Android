package com.example.dangkhoa.qrcodefirebase.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import com.example.dangkhoa.qrcodefirebase.Fragments.HomeFragment;
import com.example.dangkhoa.qrcodefirebase.Models.QRModel;
import com.example.dangkhoa.qrcodefirebase.Realm.QRCode;
import com.example.dangkhoa.qrcodefirebase.Utils.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by dangkhoa on 11/10/17.
 */

public class Network extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean IsConnected = com.example.dangkhoa.qrcodefirebase.Utils.Network.getConnectivityStatusString(context);
                Log.i("STATUS", String.valueOf(IsConnected));

                if (IsConnected) {
                    HomeFragment.txtStatus.setText("Network: On");
                    final ArrayList<QRModel> qrModels = QRCode.getData();
                    if (qrModels.size() > 0) {
                        for (int i = 0; i < qrModels.size(); i++) {
                            Log.i("MODEL " + i, qrModels.get(i).getId() + " | " + qrModels.get(i).getPrice() + " | " + qrModels.get(i).getTimestamp());
                            final int finalI = i;
                            Firebase.createQRCode(qrModels.get(i), new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    QRCode.deleteData(qrModels.get(finalI));
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                    }
                } else {
                    HomeFragment.txtStatus.setText("Network: Off");
                }
            }
        }, 3000);
    }
}
