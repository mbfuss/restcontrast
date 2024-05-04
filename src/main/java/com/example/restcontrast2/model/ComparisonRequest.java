package com.example.restcontrast2.model;

public class ComparisonRequest {

    private String oldVersion;
    private String newVersion;

    public ComparisonRequest() {
    }

    public String getOldVersion() {
        return oldVersion;
    }

    public void setOldVersion(String oldVersion) {
        this.oldVersion = oldVersion;
    }

    public String getNewVersion() {
        return newVersion;
    }

    public void setNewVersion(String newVersion) {
        this.newVersion = newVersion;
    }
}

