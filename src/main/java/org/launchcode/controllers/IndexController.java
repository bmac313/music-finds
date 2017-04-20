package org.launchcode.controllers;

import org.launchcode.models.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;

@Controller
public class IndexController {

    private ArrayList<Post> posts;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model) {

        model.addAttribute("title", "Latest Finds - MusicFinds");
        model.addAttribute("posts", posts);

        return "index";
    }

    @RequestMapping(value = "newpost", method = RequestMethod.GET)
    public String showNewPostForm(Model model) {

        model.addAttribute("title", "Share a Find - MusicFinds");

        return "new-post";
    }

}
