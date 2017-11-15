package com.example.dangkhoa.qrcodefirebase;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.dangkhoa.qrcodefirebase.Fragments.HomeFragment;
import com.example.dangkhoa.qrcodefirebase.Models.QRModel;
import com.example.dangkhoa.qrcodefirebase.Realm.QRCode;
import com.example.dangkhoa.qrcodefirebase.Utils.Firebase;
import com.example.dangkhoa.qrcodefirebase.Utils.Services;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity {
    RealmConfiguration configuration;
    Realm realm;
    public static QRCode qrCode;

    class BroadcastNetwork extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                HomeFragment.txtStatus.setText("Connected");
                uploadDataOffline();
            } else {
                HomeFragment.txtStatus.setText("Disconnected");
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
    }

    private void initialize() {
        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();

        Realm.init(this);
        configuration = new RealmConfiguration.Builder().name("qr_codes.realm").build();
        realm = Realm.getInstance(configuration);
        qrCode = new QRCode(realm);

        Firebase.initializeFirebase(this);

        Services.Navigate(getSupportFragmentManager(), new HomeFragment(),"HOME", null, Services.NO_ANIMATION);
    }

    public void backFunction() {
        String CURRENT_TAG = getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount()-1).getName();
        Log.i("TAG", CURRENT_TAG);

        if (CURRENT_TAG.equals("CAMERA")) {
            for(Fragment fragment : getSupportFragmentManager().getFragments()){
                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            }
            Services.Navigate(getSupportFragmentManager(), new HomeFragment(),"HOME", null, Services.NO_ANIMATION);
        } else {
            getSupportFragmentManager().popBackStack();
        }
    }

    public static void uploadDataOffline() {
        final ArrayList<QRModel> qrModels = QRCode.getData();
        if (qrModels.size() > 0) {
            for (int i = 0; i < qrModels.size(); i++) {
                Log.i("MODEL " + i, qrModels.get(i).getId() + " | " + qrModels.get(i).getPrice() + " | " + qrModels.get(i).getTimestamp());
                final int finalI = i;
                Firebase.createQRCode(qrModels.get(i), new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.i("DATA", dataSnapshot.toString());
                        QRCode.deleteData(qrModels.get(finalI));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        BroadcastNetwork broadcastNetwork = new BroadcastNetwork();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        filter.addAction("android.net.wifi.WIFI_STATE_CHANGED");

        registerReceiver(broadcastNetwork, filter);
    }

    @Override
    public void onBackPressed() {
        backFunction();
    }
}
