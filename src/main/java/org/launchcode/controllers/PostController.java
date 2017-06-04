package org.launchcode.controllers;

import org.launchcode.models.Comment;
import org.launchcode.models.Post;
import org.launchcode.models.User;
import org.launchcode.models.data.CommentDao;
import org.launchcode.models.data.PostDao;
import org.launchcode.models.data.UserDao;
import org.launchcode.models.enums.States;
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
                        @RequestParam(defaultValue = "1") int page,
                        @RequestParam(defaultValue = "false") boolean justLoggedIn,
                        @RequestParam(defaultValue = "false") boolean justSignedUp,
                        @CookieValue("id") String userIdCookie,
                        @CookieValue("password") String passwordCookie) {

        PageRequest pageRequest = new PageRequest(page-1, 5, Sort.Direction.DESC, "timeStamp");
        int pages = postDao.findAll(pageRequest).getTotalPages();

        model = setNavItemVisibility(model, userIdCookie, passwordCookie);

        if (justLoggedIn) {
            model.addAttribute("alertClass", "alert alert-success");
            model.addAttribute("alert", "Logged in successfully!");
        }

        if (justSignedUp) {
            model.addAttribute("alertClass", "alert alert-success");
            model.addAttribute("alert", "Signed up successfully!");
        }

        if ((page-1) <= 0) {
            model.addAttribute("visibilityPrev", "hidden");
        }

        if ((page) >= pages) {
            model.addAttribute("visibilityNext", "hidden");
        }

        model.addAttribute("posts", postDao.findAll(pageRequest));
        model.addAttribute("title", "Latest Finds - MusicFinds");

        model.addAttribute("page", page);
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

        model = setNavItemVisibility(model, userIdCookie, passwordCookie);

        model.addAttribute("title", "Share a Find - MusicFinds");
        model.addAttribute("header", "Share a Find");
        model.addAttribute("states", States.values());
        model.addAttribute(new Post());

        model.addAttribute("findsActiveStatus", "active");

        return "posts/new-post";
    }

    @RequestMapping(value = "newpost", method = RequestMethod.POST)
    public String handleNewPostSubmission(@Valid @ModelAttribute Post postToAdd,
                                          Errors errors,
                                          @CookieValue("id") String userIdCookie,
                                          @CookieValue("password") String passwordCookie,
                                          Model model) {

        if (errors.hasErrors()) {

            model = setNavItemVisibility(model, userIdCookie, passwordCookie);

            model.addAttribute("title", "Share a Find - MusicFinds");
            model.addAttribute("header", "Share a Find");
            model.addAttribute("states", States.values());

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
                           @CookieValue("id") String userIdCookie,
                           @CookieValue("password") String passwordCookie,
                           Model model) {

        Post post = postDao.findOne(id);
        model = setNavItemVisibility(model, userIdCookie, passwordCookie);

        // If the user is not logged in, hide the comment box.
        if (userIdCookie.equals("") && passwordCookie.equals("")) {
            model.addAttribute("visibilityComments", "hidden");
        }

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
                                   @CookieValue("id") String userIdCookie,
                                   @CookieValue("password") String passwordCookie,
                                   Model model) {

        Post post = postDao.findOne(id);

        if (errors.hasErrors()) {

            model = setNavItemVisibility(model, userIdCookie, passwordCookie);

            model.addAttribute("title", post.getTitle() + " - MusicFinds");
            model.addAttribute("post", post);
            model.addAttribute(new Comment());
            model.addAttribute("comments", post.getComments());

            model.addAttribute("findsActiveStatus", "active");

            return "posts/view-post";
        }

        model = setNavItemVisibility(model, userIdCookie, passwordCookie);

        int userId = Integer.parseInt(userIdCookie);
        User author = userDao.findOne(userId);

        comment.setAuthor(author);
        post.addComment(comment);

        postDao.save(post);

        model.addAttribute("title", "View Post - MusicFinds");
        model.addAttribute("post", post);
        model.addAttribute(new Comment());
        model.addAttribute("comments", post.getComments());

        model.addAttribute("findsActiveStatus", "active");

        return "posts/view-post";
    }

    private Model setNavItemVisibility(Model model, String userIdCookie, String passwordCookie) {

        // If the user is logged in, add their username to the model, and hide the Log In nav item.
        if (!userIdCookie.isEmpty() && !passwordCookie.isEmpty()) {
            int userId = Integer.parseInt(userIdCookie);
            String username = userDao.findOne(userId).getUsername();
            model.addAttribute("loggedInUser", username);
            model.addAttribute("visibilityLogIn", "hidden");
        } else { // If not, hide the username display and Log Out nav item.
            model.addAttribute("visibilityUsernameDisplay", "hidden");
            model.addAttribute("visibilityLogOut", "hidden");
        }

        return model;
    }

}
