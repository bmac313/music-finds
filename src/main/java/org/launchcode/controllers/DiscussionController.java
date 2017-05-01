package org.launchcode.controllers;

import org.launchcode.models.Comment;
import org.launchcode.models.Discussion;
import org.launchcode.models.data.CommentDao;
import org.launchcode.models.data.DiscussionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "discussions")
public class DiscussionController {

    @Autowired
    private DiscussionDao discussionDao;

    @Autowired
    private CommentDao commentDao;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model) {

        model.addAttribute("title", "Discussions - MusicFinds");
        model.addAttribute("discussions", discussionDao.findAll(new Sort(Sort.Direction.DESC, "timeStamp")));

        return "discussions/index";

    }

    @RequestMapping(value = "newtopic", method = RequestMethod.GET)
    public String showNewDiscussionForm(Model model) {

        model.addAttribute("title", "New Discussion Topic - MusicFinds");
        model.addAttribute("header", "New Discussion Topic");
        model.addAttribute(new Discussion());

        return "discussions/new-discussion";
    }

    @RequestMapping(value = "newtopic", method = RequestMethod.POST)
    public String processNewDiscussionForm(@Valid @ModelAttribute Discussion newDiscussion,
                                           Errors errors, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "New Discussion Topic - MusicFinds");
            model.addAttribute("header", "New Discussion Topic");
            return "discussions/new-discussion";
        }

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
        return "discussions/view-discussion";
    }

    @RequestMapping(value = "viewtopic/{id}", method = RequestMethod.POST)
    public String addCommentToDiscussion(@Valid @ModelAttribute Comment comment,
                                         Errors errors,
                                         @PathVariable(value = "id") int id,
                                         Model model) {

        if (errors.hasErrors()) {
            Discussion discussion = discussionDao.findOne(id);

            model.addAttribute("title", discussion.getTitle() + " - MusicFinds");
            model.addAttribute("discussion", discussion);
            model.addAttribute(new Comment());
            model.addAttribute("comments", discussion.getComments());
            return "discussions/view-discussion";
        }

        Discussion discussion = discussionDao.findOne(id);

        discussion.addComment(comment);
        discussionDao.save(discussion);

        model.addAttribute("title", discussion.getTitle() + " - MusicFinds");
        model.addAttribute("discussion", discussion);
        model.addAttribute(new Comment());
        model.addAttribute("comments", discussion.getComments());

        return "discussions/view-discussion";

    }

}
