package org.launchcode.models;

public class Post {

    private String title;
    private String imgUrl;
    private String location;
    private String author;

    public Post () {}

    public Post(String title, String imgUrl, String location, String author) {
        this.title = title;
        this.imgUrl = imgUrl;
        this.location = location;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
