package com.lebartodev.mercuryapp2.model;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

/**
 * Created by Александр on 12.11.2016.
 */

public class ContactModel extends SugarRecord {
    private String name;
    @Unique
    private String contactid;
    private String email;
    private String phone;

    public String getContactid() {
        return contactid;
    }

    public void setContactid(String contactid) {
        this.contactid = contactid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ContactModel(String name, String contactid, String email, String phone) {
        this.name = name;
        this.contactid = contactid;
        this.email = email;
        this.phone = phone;
    }

    public ContactModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
