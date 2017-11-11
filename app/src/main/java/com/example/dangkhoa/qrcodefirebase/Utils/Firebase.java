package com.example.dangkhoa.qrcodefirebase.Utils;

import android.content.Context;

import com.example.dangkhoa.qrcodefirebase.Models.QRModel;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by dangkhoa on 11/10/17.
 */

public class Firebase {
    static FirebaseDatabase database;
    static DatabaseReference reference;

    public static void initializeFirebase(Context context) {
        FirebaseApp.initializeApp(context);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
    }

    public static void createQRCode(QRModel qrModel, ValueEventListener valueEventListener) {
        String key = reference.push().getKey();

        reference.child("qr_codes").child(key).child("id").setValue(qrModel.getId());
        reference.child("qr_codes").child(key).child("detail").setValue(qrModel.getDetail());
        reference.child("qr_codes").child(key).child("price").setValue(qrModel.getPrice());
        reference.child("qr_codes").child(key).child("timestamp").setValue(qrModel.getTimestamp());

        if (valueEventListener != null) {
            reference.child("qr_codes").child(key).addValueEventListener(valueEventListener);
        }
    }
}
