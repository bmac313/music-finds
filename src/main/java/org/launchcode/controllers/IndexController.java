package org.launchcode.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class IndexController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(HttpServletResponse response) {

        Cookie userIdCookie = new Cookie("id", "");
        Cookie passwordCookie = new Cookie("password", "");

        response.addCookie(userIdCookie);
        response.addCookie(passwordCookie);

        return "redirect:posts";
    }

}
