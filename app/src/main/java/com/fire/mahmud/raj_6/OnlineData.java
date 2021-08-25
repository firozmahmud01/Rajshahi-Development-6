package com.fire.mahmud.raj_6;

import android.net.Uri;

public class OnlineData {
    private String title,details;
    private Uri picuri;
    public OnlineData(String title,String details,Uri picuri){
        this.title=title;
        this.picuri=picuri;
        this.details=details;
    }

    public Uri getPicuri() {
        return picuri;
    }

    public void setPicuri(Uri picuri) {
        this.picuri = picuri;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
