package com.example.dangkhoa.qrcodefirebase.Models;

import com.example.dangkhoa.qrcodefirebase.Utils.Services;

import java.util.UUID;

import io.realm.RealmObject;

/**
 * Created by dangkhoa on 11/10/17.
 */

public class QRModel extends RealmObject {
    String id;
    String detail;
    long price;
    String timestamp;

    public QRModel() {
    }

    public QRModel(String detail, long price) {
        this.id = UUID.randomUUID().toString();
        this.detail = detail;
        this.price = price;
        this.timestamp = Services.GenerateTimestamp();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
