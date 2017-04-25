package org.launchcode.models;

import javax.persistence.Id;

import javax.persistence.GeneratedValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

//TODO: Add Date/Time field

@Entity
public class Post {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min = 1, max = 100, message = "Post title must be between 1 and 100 characters.")
    private String title;

    @NotNull
    @Size(min = 1, message = "Please provide a link to your image.")
    private String imgUrl;

    @NotNull
    @Size(min = 1, message = "Please provide a location for your Find.")
    private String location;

    @NotNull
    @Size(min = 1, message = "Please provide a name.")
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

    public int getId() {
        return id;
    }
}
