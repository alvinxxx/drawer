package com.example.alvinlam.drawer.data;

/**
 * Created by Alvin Lam on 11/5/2017.
 */

public class StockAlert {
    // private variables
    int _id;
    String _name;
    double _current;
    String _condition;
    double _expect;
    double _distance;
    byte[] _image;

    // Empty constructor
    public StockAlert() {

    }


    // constructor
    public StockAlert(int keyId, String name, double current, String condition, double expect, double distance, byte[] image) {
        this._id = keyId;
        this._name = name;
        this._current = current;
        this._condition = condition;
        this._expect = expect;
        this._distance = distance;
        this._image = image;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public double get_current() {
        return _current;
    }

    public void set_current(double _current) {
        this._current = _current;
    }

    public String get_condition() {
        return _condition;
    }

    public void set_condition(String _condition) {
        this._condition = _condition;
    }

    public double get_expect() {
        return _expect;
    }

    public void set_expect(double _expect) {
        this._expect = _expect;
    }

    public double get_distance() {
        return _distance;
    }

    public void set_distance(double _distance) {
        this._distance = _distance;
    }

    public byte[] get_image() {
        return _image;
    }

    public void set_image(byte[] _image) {
        this._image = _image;
    }

    public StockAlert(String name, double current, String condition, double expect, double distance, byte[] image) {
        this._name = name;
        this._current = current;
        this._condition = condition;
        this._expect = expect;
        this._distance = distance;
        this._image = image;
    }

    public StockAlert(int keyId) {
        this._id = keyId;

    }


}
