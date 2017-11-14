package com.example.dangkhoa.qrcodefirebase;

import android.content.ContextWrapper;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.dangkhoa.qrcodefirebase.Fragments.HomeFragment;
import com.example.dangkhoa.qrcodefirebase.Realm.QRCode;
import com.example.dangkhoa.qrcodefirebase.Receivers.Network;
import com.example.dangkhoa.qrcodefirebase.Utils.Firebase;
import com.example.dangkhoa.qrcodefirebase.Utils.Services;
import com.pixplicity.easyprefs.library.Prefs;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity {
    Network changeReceiver = new Network();
    RealmConfiguration configuration;
    Realm realm;
    public static QRCode qrCode;

    int CAMERA_ID = 0;

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

    public void getNetworkStatus() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION);

        changeReceiver.onReceive(getApplicationContext(), getIntent());
        registerReceiver(changeReceiver, intentFilter);
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

    @Override
    protected void onResume() {
        super.onResume();
        getNetworkStatus();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        backFunction();
    }
}
