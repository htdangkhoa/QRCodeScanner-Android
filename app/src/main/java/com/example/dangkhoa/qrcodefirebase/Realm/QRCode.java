package com.example.dangkhoa.qrcodefirebase.Realm;

import com.example.dangkhoa.qrcodefirebase.Models.QRModel;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by dangkhoa on 11/10/17.
 */

public class QRCode {
    private static Realm realm;

    public QRCode(Realm realm) {
        this.realm = realm;
    }

    public static void addData(final QRModel qrModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                QRModel q = realm.createObject(QRModel.class);
                q.setId(qrModel.getId());
                q.setDetail(qrModel.getDetail());
                q.setPrice(qrModel.getPrice());
                q.setTimestamp(qrModel.getTimestamp());
            }
        });
    }

    public static void deleteData(QRModel qrModel) {
        RealmQuery<QRModel> query = realm.where(QRModel.class);
        QRModel q = query.equalTo("id", qrModel.getId()).findFirst();
        if (q != null) {
            realm.beginTransaction();
            q.deleteFromRealm();
            realm.commitTransaction();
        }
    }

    public static ArrayList<QRModel> getData() {
        ArrayList<QRModel> qrModels = new ArrayList<>();
        RealmQuery<QRModel> query = realm.where(QRModel.class);
        RealmResults<QRModel> results = query.findAll();
        qrModels.addAll(results);
        return qrModels;
    }
}
