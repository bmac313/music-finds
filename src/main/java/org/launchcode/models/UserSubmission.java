package org.launchcode.models;

import javax.persistence.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class UserSubmission {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min = 1, max = 100, message = "Post title must be between 1 and 100 characters.")
    private String title;

    @NotNull
    @Size(min = 1, message = "Please provide a name.")
    private String author;

    private final String timeStamp;


    public UserSubmission () {
        this.timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
    }

    public UserSubmission(String title, String author) {
        this();
        this.title = title;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

}
