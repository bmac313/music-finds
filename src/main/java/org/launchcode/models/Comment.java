package org.launchcode.models;

import javax.persistence.Entity;
import javax.persistence.Id;

import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;
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
    @Size(min = 1, message = "The comment body cannot be empty.")
    private String text;

    @NotNull
    @Size(min = 1, message = "The author box cannot be empty.")
    private String author;

    private final String timeStamp;

    @ManyToOne
    private Post post;              // This field describes which post the comment was made on


    public Comment() {
        this.timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
    }

    public Comment(String text, String author) {
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Post getPost() {
        return post;
    }

    public String getTimeStamp() {
        return timeStamp;
    }
}
