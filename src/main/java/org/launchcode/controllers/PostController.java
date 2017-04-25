package org.launchcode.controllers;

import org.launchcode.models.Comment;
import org.launchcode.models.data.CommentDao;
import org.launchcode.models.data.PostDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class PostController {

    @Autowired
    private PostDao postDao;

    @Autowired
    private CommentDao commentDao;

    @RequestMapping(value = "/posts/{id}", method = RequestMethod.GET)
    public String viewPost(@PathVariable(value = "id") int id, Model model) {

        //TODO: Add post comments to the model (do this on the POST method as well)
        model.addAttribute("title", "View Post - MusicFinds");
        model.addAttribute("post", postDao.findOne(id));

        return "posts/view-post";
    }

    @RequestMapping(value = "/posts/{id}", method = RequestMethod.POST)
    public String addCommentToPost(@Valid @ModelAttribute Comment comment,
                                   @PathVariable(value = "id") int id,
                                   Errors errors, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "View Post - MusicFinds");
            model.addAttribute("post", postDao.findOne(id));
            return "posts/view-post";
        }

        commentDao.save(comment);

        model.addAttribute("title", "View Post - MusicFinds");
        model.addAttribute("post", postDao.findOne(id));

        return "posts/view-post";
    }

}
