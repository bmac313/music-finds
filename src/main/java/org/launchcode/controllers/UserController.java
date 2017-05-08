package org.launchcode.controllers;

import org.launchcode.models.User;
import org.launchcode.models.data.UserDao;
import org.launchcode.models.forms.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String showLoginForm(Model model) {
        LoginForm loginForm = new LoginForm();

        model.addAttribute("title", "Log In - MusicFinds");
        model.addAttribute("header", "Log in to your account");
        model.addAttribute("login", loginForm);
        return "users/login";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String signInUser(@ModelAttribute(name = "login") @Valid LoginForm loginForm,
                             Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Log In - MusicFinds");
            model.addAttribute("header", "Log in to your account");
            return "users/login";
        }

        return "redirect:";
    }

    @RequestMapping(value = "signup", method = RequestMethod.GET)
    public String showSignupForm(Model model) {
        model.addAttribute("title", "Sign Up - MusicFinds");
        model.addAttribute("header", "Create an account");
        model.addAttribute(new User());
        return "users/signup";
    }

    @RequestMapping(value = "signup", method = RequestMethod.POST)
    public String signupUser(@ModelAttribute @Valid User newUser,
                             Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Sign Up - MusicFinds");
            model.addAttribute("header", "Create an account");
            return "users/signup";
        }

        return "redirect:";
    }
}
