package com.adem.common.model;

import java.io.Serializable;
import java.util.List;

public class School implements Serializable {
    private static final long serialVersionUID = 1412821239509663958L;
    private long schoolId;
    private String name;
    private List<Address> addressList;

    public long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(long schoolId) {
        this.schoolId = schoolId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }
}
