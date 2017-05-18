package org.launchcode.controllers;

import org.launchcode.models.Comment;
import org.launchcode.models.Post;
import org.launchcode.models.User;
import org.launchcode.models.data.CommentDao;
import org.launchcode.models.data.PostDao;
import org.launchcode.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "posts")
public class PostController {

    @Autowired
    private PostDao postDao;

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "false") boolean justLoggedIn,
                        @RequestParam(defaultValue = "false") boolean justSignedUp) {

        if (justLoggedIn) {
            model.addAttribute("alertClass", "alert alert-success");
            model.addAttribute("alert", "Logged in successfully!");
        }

        if (justSignedUp) {
            model.addAttribute("alertClass", "alert alert-success");
            model.addAttribute("alert", "Signed up successfully!");
        }

        model.addAttribute("posts", postDao.findAll(new PageRequest(page, 5, Sort.Direction.DESC, "timeStamp")));
        model.addAttribute("title", "Latest Finds - MusicFinds");

        model.addAttribute("findsActiveStatus", "active");

        return "posts/index";
    }

    @RequestMapping(value = "newpost", method = RequestMethod.GET)
    public String showNewPostForm(Model model,
                                  @CookieValue("id") String userIdCookie,
                                  @CookieValue("password") String passwordCookie) {

        if (userIdCookie.equals("") || passwordCookie.equals("")) {
            return "redirect:/login?loginPrompt=true";
        }

        model.addAttribute("title", "Share a Find - MusicFinds");
        model.addAttribute("header", "Share a Find");
        model.addAttribute(new Post());

        model.addAttribute("findsActiveStatus", "active");

        return "posts/new-post";
    }

    @RequestMapping(value = "newpost", method = RequestMethod.POST)
    public String handleNewPostSubmission(@Valid @ModelAttribute Post postToAdd,
                                          Errors errors,
                                          @CookieValue("id") String userIdCookie, Model model) {

        if (errors.hasErrors()) {

            model.addAttribute("title", "Share a Find - MusicFinds");
            model.addAttribute("header", "Share a Find");

            model.addAttribute("findsActiveStatus", "active");

            return "posts/new-post";
        }

        int userId = Integer.parseInt(userIdCookie);
        User author = userDao.findOne(userId);

        author.addSubmissionToAccount(postToAdd);
        postToAdd.setAuthor(author);
        postDao.save(postToAdd);

        return "redirect:";
    }

    @RequestMapping(value = "viewpost/{id}", method = RequestMethod.GET)
    public String viewPost(@PathVariable(value = "id") int id,
                           @CookieValue("id") String userIdCookie, Model model) {

        Post post = postDao.findOne(id);

        model.addAttribute("title", "View Post - MusicFinds");
        model.addAttribute("post", post);
        model.addAttribute(new Comment());
        model.addAttribute("comments", post.getComments());

        model.addAttribute("findsActiveStatus", "active");

        return "posts/view-post";
    }

    @RequestMapping(value = "viewpost/{id}", method = RequestMethod.POST)
    public String addCommentToPost(@ModelAttribute @Valid Comment comment,
                                   Errors errors,
                                   @PathVariable(value = "id") int id,
                                   Model model) {

        Post post = postDao.findOne(id);

        if (errors.hasErrors()) {

            model.addAttribute("title", post.getTitle() + " - MusicFinds");
            model.addAttribute("post", post);
            model.addAttribute(new Comment());
            model.addAttribute("comments", post.getComments());

            model.addAttribute("findsActiveStatus", "active");

            return "posts/view-post";
        }

        post.addComment(comment);
        postDao.save(post);

        model.addAttribute("title", "View Post - MusicFinds");
        model.addAttribute("post", post);
        model.addAttribute(new Comment());
        model.addAttribute("comments", post.getComments());

        model.addAttribute("findsActiveStatus", "active");

        return "posts/view-post";
    }

}
