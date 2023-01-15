package com.adem.common.model;

import java.io.Serializable;

public class Address implements Serializable {
    private static final long serialVersionUID = -2209048502791432889L;
    private String street;
    private String city;
    private String district;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
}
