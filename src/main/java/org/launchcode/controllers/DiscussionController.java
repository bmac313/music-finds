package org.launchcode.controllers;

import org.launchcode.models.Comment;
import org.launchcode.models.Discussion;
import org.launchcode.models.User;
import org.launchcode.models.data.CommentDao;
import org.launchcode.models.data.DiscussionDao;
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
@RequestMapping(value = "discussions")
public class DiscussionController {

    @Autowired
    private DiscussionDao discussionDao;

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(@CookieValue("id") String userIdCookie,
                        @CookieValue("password") String passwordCookie,
                        Model model, @RequestParam(defaultValue = "1") int page) {

        PageRequest pageRequest = new PageRequest(page-1, 5, Sort.Direction.DESC, "timeStamp");
        int pages = discussionDao.findAll(pageRequest).getTotalPages();

        model = setNavItemVisibility(model, userIdCookie, passwordCookie);

        if ((page-1) <= 0) {
            model.addAttribute("visibilityPrev", "hidden");
        }

        if ((page) >= pages) {
            model.addAttribute("visibilityNext", "hidden");
        }

        model.addAttribute("title", "Discussions - MusicFinds");
        model.addAttribute("discussions", discussionDao.findAll(pageRequest));
        model.addAttribute("page", page);

        model.addAttribute("findsActiveStatus", "inactive");
        model.addAttribute("discActiveStatus", "active");
        model.addAttribute("loginActiveStatus", "inactive");
        model.addAttribute("signupActiveStatus", "inactive");

        return "discussions/index";

    }

    @RequestMapping(value = "newtopic", method = RequestMethod.GET)
    public String showNewDiscussionForm(@CookieValue("id") String userIdCookie,
                                        @CookieValue("password") String passwordCookie,
                                        Model model) {

        if (userIdCookie.equals("") || passwordCookie.equals("")) {
            return "redirect:/login?loginPrompt=true";
        }

        model = setNavItemVisibility(model, userIdCookie, passwordCookie);

        model.addAttribute("title", "New Discussion Topic - MusicFinds");
        model.addAttribute("header", "New Discussion Topic");
        model.addAttribute(new Discussion());

        model.addAttribute("findsActiveStatus", "inactive");
        model.addAttribute("discActiveStatus", "active");
        model.addAttribute("loginActiveStatus", "inactive");
        model.addAttribute("signupActiveStatus", "inactive");

        return "discussions/new-discussion";
    }

    @RequestMapping(value = "newtopic", method = RequestMethod.POST)
    public String processNewDiscussionForm(@ModelAttribute @Valid Discussion newDiscussion,
                                           Errors errors,
                                           @CookieValue("id") String userIdCookie,
                                           @CookieValue("password") String passwordCookie,
                                           Model model) {

        if (errors.hasErrors()) {
            model = setNavItemVisibility(model, userIdCookie, passwordCookie);

            model.addAttribute("title", "New Discussion Topic - MusicFinds");
            model.addAttribute("header", "New Discussion Topic");

            model.addAttribute("findsActiveStatus", "inactive");
            model.addAttribute("discActiveStatus", "active");
            model.addAttribute("loginActiveStatus", "inactive");
            model.addAttribute("signupActiveStatus", "inactive");

            return "discussions/new-discussion";
        }

        int userId = Integer.parseInt(userIdCookie);
        User author = userDao.findOne(userId);

        author.addSubmissionToAccount(newDiscussion);
        newDiscussion.setAuthor(author);
        discussionDao.save(newDiscussion);

        return "redirect:";

    }

    @RequestMapping(value = "viewtopic/{id}", method = RequestMethod.GET)
    public String viewDiscussion(@PathVariable(value = "id") int id,
                                 @CookieValue("id") String userIdCookie,
                                 @CookieValue("password") String passwordCookie,
                                 Model model) {

        Discussion discussion = discussionDao.findOne(id);

        model = setNavItemVisibility(model, userIdCookie, passwordCookie);

        model.addAttribute("title", discussion.getTitle() + " - MusicFinds");
        model.addAttribute("discussion", discussion);
        model.addAttribute(new Comment());
        model.addAttribute("comments", discussion.getComments());

        model.addAttribute("findsActiveStatus", "inactive");
        model.addAttribute("discActiveStatus", "active");
        model.addAttribute("loginActiveStatus", "inactive");
        model.addAttribute("signupActiveStatus", "inactive");

        return "discussions/view-discussion";
    }

    @RequestMapping(value = "viewtopic/{id}", method = RequestMethod.POST)
    public String addCommentToDiscussion(@Valid @ModelAttribute Comment comment,
                                         Errors errors,
                                         @PathVariable(value = "id") int id,
                                         @CookieValue("id") String userIdCookie,
                                         @CookieValue("password") String passwordCookie,
                                         Model model) {

        Discussion discussion = discussionDao.findOne(id);

        if (errors.hasErrors()) {

            model = setNavItemVisibility(model, userIdCookie, passwordCookie);

            model.addAttribute("title", discussion.getTitle() + " - MusicFinds");
            model.addAttribute("discussion", discussion);
            model.addAttribute(new Comment());
            model.addAttribute("comments", discussion.getComments());

            model.addAttribute("findsActiveStatus", "inactive");
            model.addAttribute("discActiveStatus", "active");
            model.addAttribute("loginActiveStatus", "inactive");
            model.addAttribute("signupActiveStatus", "inactive");

            return "discussions/view-discussion";
        }

        model = setNavItemVisibility(model, userIdCookie, passwordCookie);

        discussion.addComment(comment);
        discussionDao.save(discussion);

        model.addAttribute("title", discussion.getTitle() + " - MusicFinds");
        model.addAttribute("discussion", discussion);
        model.addAttribute(new Comment());
        model.addAttribute("comments", discussion.getComments());

        model.addAttribute("findsActiveStatus", "inactive");
        model.addAttribute("discActiveStatus", "active");
        model.addAttribute("loginActiveStatus", "inactive");
        model.addAttribute("signupActiveStatus", "inactive");

        return "discussions/view-discussion";

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
