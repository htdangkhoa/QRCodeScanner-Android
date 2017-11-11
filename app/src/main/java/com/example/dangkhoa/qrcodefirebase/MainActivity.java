package com.example.dangkhoa.qrcodefirebase;

import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.dangkhoa.qrcodefirebase.Fragments.HomeFragment;
import com.example.dangkhoa.qrcodefirebase.Realm.QRCode;
import com.example.dangkhoa.qrcodefirebase.Receivers.Network;
import com.example.dangkhoa.qrcodefirebase.Utils.Firebase;
import com.example.dangkhoa.qrcodefirebase.Utils.Services;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity {
    Network changeReceiver = new Network();
    RealmConfiguration configuration;
    Realm realm;
    public static QRCode qrCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
    }

    private void initialize() {
        Realm.init(this);
        configuration = new RealmConfiguration.Builder().name("qr_codes.realm").build();
        realm = Realm.getInstance(configuration);
        qrCode = new QRCode(realm);

        Firebase.initializeFirebase(this);

        Services.Navigate(getSupportFragmentManager(), new HomeFragment(),"HOME", null, Services.NO_ANIMATION);
    }

    public void getNetworkStatus() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION);

        changeReceiver.onReceive(getApplicationContext(), getIntent());
        registerReceiver(changeReceiver, intentFilter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getNetworkStatus();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(changeReceiver);
    }
}
