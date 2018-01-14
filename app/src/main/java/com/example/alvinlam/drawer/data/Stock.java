package com.example.alvinlam.drawer.data;

/**
 * Created by Alvin Lam on 11/5/2017.
 */

public class Stock {
    // private variables
    int _id;
    String _name;
    double _price;
    double _netChange;
    byte[] _image;

    // Empty constructor
    public Stock() {

    }

    // constructor
    public Stock(int keyId, String name, double price, double netChange, byte[] image) {
        this._id = keyId;
        this._name = name;
        this._price = price;
        this._netChange = netChange;
        this._image = image;

    }
    public Stock(String name, double price, double netChange, byte[] image) {
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

    public double getPrice() {
        return this._price;
    }

    public void setPrice(double price) {
        this._price = price;
    }

    public double getNetChange() {
        return this._netChange;
    }

    public void setNetChange(double netChange) {
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
