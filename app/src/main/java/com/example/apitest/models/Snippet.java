package com.example.apitest.models;

/**
 * Created by gordonwallace on 07/04/2017.
 */

public class Snippet {

    private String created;
    private String title;
    private String code;
    private String lineos;
    private String language;
    private String style;
    private String owner;

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLineos() {
        return lineos;
    }

    public void setLineos(String lineos) {
        this.lineos = lineos;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
