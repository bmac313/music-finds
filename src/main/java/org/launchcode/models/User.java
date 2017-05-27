package org.launchcode.models;

import javax.persistence.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min = 1, message = "Username cannot be empty.")
    private String username;

    @NotNull
    @Size(min = 1, message = "Password cannot be empty.")
    private String password;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<UserSubmission> submissions;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<Comment> comments;


    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<UserSubmission> getSubmissions() {
        return submissions;
    }

    public void setSubmissions(List<UserSubmission> submissions) {
        this.submissions = submissions;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void addSubmissionToAccount(UserSubmission submission) {
        this.submissions.add(submission);
    }

    public void removeSubmissionFromAccount(UserSubmission submission) {
        this.submissions.remove(submission);
    }

    public void addCommentToAccount(Comment comment) {
        this.comments.add(comment);
    }

    public void removeCommentFromAccount(Comment comment) {
        this.comments.remove(comment);
    }
}
