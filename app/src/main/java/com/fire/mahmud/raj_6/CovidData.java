package com.fire.mahmud.raj_6;

public class CovidData {
    private String name,fathersname,number,zilla,upozilla,post,wordno;
    boolean[]checked;
    public CovidData(){

    }

    public CovidData(String name, String fathersname, String number, String zilla, String upozilla, String post, String wordno, boolean[] checked) {
        this.name = name;
        this.fathersname = fathersname;
        this.number = number;
        this.zilla = zilla;
        this.upozilla = upozilla;
        this.post = post;
        this.wordno = wordno;
        this.checked = checked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFathersname() {
        return fathersname;
    }

    public void setFathersname(String fathersname) {
        this.fathersname = fathersname;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getZilla() {
        return zilla;
    }

    public void setZilla(String zilla) {
        this.zilla = zilla;
    }

    public String getUpozilla() {
        return upozilla;
    }

    public void setUpozilla(String upozilla) {
        this.upozilla = upozilla;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getWordno() {
        return wordno;
    }

    public void setWordno(String wordno) {
        this.wordno = wordno;
    }

    public boolean[] getChecked() {
        return checked;
    }

    public void setChecked(boolean[] checked) {
        this.checked = checked;
    }
}
