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
import java.nio.channels.Pipe;

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
    public String index(Model model, @RequestParam(defaultValue = "0") int page) {

        model.addAttribute("title", "Discussions - MusicFinds");
        model.addAttribute("discussions", discussionDao.findAll(new PageRequest(page, 5, Sort.Direction.DESC, "timeStamp")));

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
                                           @CookieValue("id") String userIdCookie, Model model) {

        if (errors.hasErrors()) {
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
    public String viewDiscussion(@PathVariable(value = "id") int id, Model model) {

        Discussion discussion = discussionDao.findOne(id);

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
                                         Model model) {

        Discussion discussion = discussionDao.findOne(id);

        if (errors.hasErrors()) {

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

}
