package org.launchcode.models;

public class Comment {

    //TODO: add date/time field
    private String text;
    private String author;

    public Comment() {}

    public Comment(String text, String author) {
        this.text = text;
        this.author = author;
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
}
