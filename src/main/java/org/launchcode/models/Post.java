package org.launchcode.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Post extends UserSubmission{

    @NotNull
    @Size(min = 1, max = 255, message = "Please provide a location for your Find (255 characters or less).")
    private String location;

    @NotNull
    @Size(min = 1, max = 255, message = "Please provide an address for your Find (255 characters or less).")
    private String address;

    @NotNull
    @Size(min = 1, max = 255, message = "Please enter a city.")
    private String city;

    @NotNull
    @Size(min = 1, message = "Please select a state.")
    private String state;

    @NotNull
    @Size(min = 1, max = 255, message = "Please provide a link to your image (255 characters or less).")
    private String imgUrl;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    private List<Comment> comments;

    public Post() {}

    public Post(String location, String address, String city, String state, String imgUrl) {
        super();
        this.location = location;
        this.address = address;
        this.city = city;
        this.state = state;
        this.imgUrl = imgUrl;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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