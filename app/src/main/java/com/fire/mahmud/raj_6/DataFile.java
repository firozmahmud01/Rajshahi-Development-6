package com.fire.mahmud.raj_6;

public class DataFile {
    private String title;
    private String details,picurl,uniqkey,folder,subfolder;
    public DataFile(){

    }
    public DataFile(String title,String details,String picurl,String uniqkey,String folder,String subfolder){
        this.title=title;
        this.uniqkey=uniqkey;
        this.picurl=picurl;
        this.details=details;
        this.folder=folder;
        this.subfolder=subfolder;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getSubfolder() {
        return subfolder;
    }

    public void setSubfolder(String subfolder) {
        this.subfolder = subfolder;
    }

    public String getUniqkey() {
        return uniqkey;
    }

    public void setUniqkey(String uniqkey) {
        this.uniqkey = uniqkey;
    }

    public String getPicurl() {
        return picurl;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
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
