package org.erikasv.automaticcallresponses;

import java.io.Serializable;

class Profile implements Serializable{
    private long id;
    private String name;
    private String sms;
    private boolean active;

    Profile(long id, String name, String sms, boolean active) {
        this.id = id;
        this.name = name;
        this.sms = sms;
        this.active = active;
    }

    Profile(String name, String sms, boolean active) {
        this.id = -1;
        this.name = name;
        this.sms = sms;
        this.active = active;
    }

    @Override
    public String toString() {
        return name + ": " + sms;
    }

    long getId() {
        return id;
    }

    void setId(long id) {
        this.id = id;
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    String getSms() {
        return sms;
    }

    void setSms(String sms) {
        this.sms = sms;
    }

    boolean isActive() {
        return active;
    }

    void setActive(boolean active) {
        this.active = active;
    }
}