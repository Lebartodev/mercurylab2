package com.lebartodev.mercuryapp2.model;

/**
 * Created by Александр on 15.11.2016.
 */

public class PhoneModel {
    private String content;
    private int type;

    public PhoneModel(String content, int type) {
        this.content = content;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
