package org.launchcode.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Discussion extends UserSubmission {

    @NotNull
    @Size(min = 1, max = 10000, message = "The post body must be between 1 and 10,000 characters.")
    private String postBody;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "discussion_id")
    private List<Comment> comments;

    public Discussion() {}

    public Discussion(String postBody) {
        super();
        this.postBody = postBody;
    }

    public String getPostBody() {
        return postBody;
    }

    public void setPostBody(String postBody) {
        this.postBody = postBody;
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
