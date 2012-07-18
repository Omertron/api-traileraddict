package com.moviejukebox.traileraddictapi.model;

public class Trailer {
    /*
     * Properties
     */
    private String title;
    private String link;
    private String publishDate;
    private int trailerId;
    private String embed;

    //<editor-fold defaultstate="collapsed" desc="Getter methods">
    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public int getTrailerId() {
        return trailerId;
    }

    public String getEmbed() {
        return embed;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Setter methods">
    public void setTitle(String title) {
        this.title = title;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public void setTrailerId(int trailerId) {
        this.trailerId = trailerId;
    }

    public void setEmbed(String embed) {
        this.embed = embed;
    }
    //</editor-fold>

}
