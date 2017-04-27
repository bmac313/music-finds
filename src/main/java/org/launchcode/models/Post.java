package org.launchcode.models;

import javax.persistence.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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

    private final String timeStamp;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    private List<Comment> comments = new ArrayList<>();


    public Post () {
        this.timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public Post(String title, String imgUrl, String location, String author) {
        this();
        this.title = title;
        this.imgUrl = imgUrl;
        this.location = location;
        this.author = author;
    }

    public int getId() {
        return id;
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

    public String getTimeStamp() {
        return timeStamp;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }
}
