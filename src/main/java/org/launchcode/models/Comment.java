package org.launchcode.models;

import javax.persistence.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Comment {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min = 1, max = 10000, message = "Comment text cannot be empty or more than 10,000 characters.")
    private String text;

    @ManyToOne
    private User author;

    private final String timeStamp;

    @ManyToOne
    private Post post;               // This field describes which post the comment was made on

    @ManyToOne
    private Discussion discussion;   // This field describes which discussion the comment was made on


    public Comment() {
        this.timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
    }

    public Comment(String text, User author) {
        this();
        this.text = text;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Post getPost() {
        return post;
    }

    public String getTimeStamp() {
        return timeStamp;
    }
}
