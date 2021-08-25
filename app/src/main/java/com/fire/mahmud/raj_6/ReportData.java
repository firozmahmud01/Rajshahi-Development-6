package com.fire.mahmud.raj_6;

public class ReportData {
    String name,uid,email,address1,address2,address3,complain_name,zip,phone_number,complain,picture,attachment,key;

    public ReportData(){

    }

    public ReportData(String name, String uid, String email,
                      String address1, String address2,
                      String address3, String complain_name,
                      String zip, String phone_number,
                      String complain, String picture,
                      String attachment, String key) {
        this.name = name;
        this.uid = uid;
        this.email = email;
        this.address1 = address1;
        this.address2 = address2;
        this.address3 = address3;
        this.complain_name = complain_name;
        this.zip = zip;
        this.phone_number = phone_number;
        this.complain = complain;
        this.picture = picture;
        this.attachment = attachment;
        this.key = key;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getComplain_name() {
        return complain_name;
    }

    public void setComplain_name(String complain_name) {
        this.complain_name = complain_name;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getComplain() {
        return complain;
    }

    public void setComplain(String complain) {
        this.complain = complain;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
