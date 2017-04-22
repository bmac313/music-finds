package org.launchcode.controllers;

import org.launchcode.models.Post;
import org.launchcode.models.data.PostDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class IndexController {

    @Autowired
    private PostDao postDao;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model) {

        model.addAttribute("posts", postDao.findAll());
        model.addAttribute("title", "Latest Finds - MusicFinds");

        return "index";
    }

    @RequestMapping(value = "newpost", method = RequestMethod.GET)
    public String showNewPostForm(Model model) {

        model.addAttribute("title", "Share a Find - MusicFinds");
        model.addAttribute("header", "Share a Find");
        model.addAttribute(new Post());

        return "new-post";
    }

    @RequestMapping(value = "newpost", method = RequestMethod.POST)
    public String handleNewPostSubmission(@Valid @ModelAttribute Post postToAdd,
                                          Errors errors, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Share a Find - MusicFinds");
            model.addAttribute("header", "Share a Find");
            return "new-post";
        }

        postDao.save(postToAdd);

        return "redirect:";
    }

}
