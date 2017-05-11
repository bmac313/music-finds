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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

//TODO: add confirmation messages informing the user of a successful signup / login
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
    public String logInUser(@ModelAttribute(name = "login") @Valid LoginForm loginForm,
                            Errors errors, Model model, HttpServletResponse response) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Log In - MusicFinds");
            model.addAttribute("header", "Log in to your account");
            model.addAttribute("wrongLoginInfoError", "");
            return "users/login";
        }

        int userId = 0;

        for (User user : userDao.findAll()) {

            // If the username and password entered match a user in the database, create cookies for that user.
            if (loginForm.getUsername().equals(user.getUsername())) {
                if (loginForm.getPassword().equals(user.getPassword())) {
                    userId = user.getId();

                    Cookie sessionCookie = new Cookie("loggedIn", "true");
                    Cookie userNameCookie = new Cookie("username", loginForm.getUsername());
                    Cookie userPwCookie = new Cookie("password", loginForm.getPassword());
                    Cookie userIdCookie = new Cookie("id", Integer.toString(userId));

                    response.addCookie(sessionCookie);
                    response.addCookie(userNameCookie);
                    response.addCookie(userPwCookie);
                    response.addCookie(userIdCookie);

                    return "redirect:";
                }
            }
        }

        //If the username or password does not match the database, create an error and return to the login form.

        model.addAttribute("title", "Log In - MusicFinds");
        model.addAttribute("header", "Log in to your account");
        model.addAttribute("wrongLoginInfoError", "Incorrect username or password.");
        return "users/login";
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

        userDao.save(newUser);

        return "redirect:";
    }
}
