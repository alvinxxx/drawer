package com.example.alvinlam.drawer.data;

/**
 * Created by Alvin Lam on 11/5/2017.
 */

public class Stock {
    // private variables
    int _id;
    String _name;
    Double _price;
    Double _netChange;
    byte[] _image;

    // Empty constructor
    public Stock() {

    }

    // constructor
    public Stock(int keyId, String name, Double price, Double netChange, byte[] image) {
        this._id = keyId;
        this._name = name;
        this._price = price;
        this._netChange = netChange;
        this._image = image;

    }
    public Stock(String name, Double price, Double netChange, byte[] image) {
        this._name = name;
        this._price = price;
        this._netChange = netChange;
        this._image = image;

    }
    public Stock(int keyId) {
        this._id = keyId;

    }

    // getting ID
    public int getID() {
        return this._id;
    }

    // setting id
    public void setID(int keyId) {
        this._id = keyId;
    }

    // getting name
    public String getName() {
        return this._name;
    }

    // setting name
    public void setName(String name) {
        this._name = name;
    }

    public Double getPrice() {
        return this._price;
    }

    public void setPrice(Double price) {
        this._price = price;
    }

    public Double getNetChange() {
        return this._netChange;
    }

    public void setNetChange(Double netChange) {
        this._netChange = netChange;
    }

    // getting phone number
    public byte[] getImage() {
        return this._image;
    }

    // setting phone number
    public void setImage(byte[] image) {
        this._image = image;
    }
}
