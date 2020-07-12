package com.leandoer.logic.Domain;

public class Password {
    private String login;
    private String password;
    private String resourceUrl;
    private String desc;
    private String notes;

    public String getResourceUrl() {
        return resourceUrl;
    }

    public String getLogin() {
        return login;
    }

    public Password(String login, String password, String resourceUrl, String desc, String notes) {
        this.login = login;
        this.password = password;
        this.resourceUrl = resourceUrl;
        this.desc = desc;
        this.notes = notes;
    }

    public String getPassword() {
        return password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDesc() {
        return desc;
    }

    public String getNotes() {
        return notes;
    }

    public String toCSV(){
        return this.login+","+this.password+","+this.resourceUrl+","+this.desc+","+this.notes;
    }
    @Override
    public String toString() {
        return this.desc+"; "+this.resourceUrl;
    }



}
