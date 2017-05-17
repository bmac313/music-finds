package org.launchcode.controllers;

import org.launchcode.models.User;
import org.launchcode.models.data.UserDao;
import org.launchcode.models.forms.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String showLoginForm(@RequestParam(defaultValue = "false") boolean loggedOut, Model model) {
        LoginForm loginForm = new LoginForm();

        if (loggedOut) {
            model.addAttribute("logOutConfirm", "You have been logged out successfully!");
        }

        model.addAttribute("title", "Log In - MusicFinds");
        model.addAttribute("header", "Log in to your account");
        model.addAttribute("login", loginForm);

        model.addAttribute("loginActiveStatus", "active");

        return "users/login";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String logInUser(@ModelAttribute(name = "login") @Valid LoginForm loginForm,
                            Errors errors, Model model, HttpServletResponse response) {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Log In - MusicFinds");
            model.addAttribute("header", "Log in to your account");

            model.addAttribute("loginActiveStatus", "active");

            return "users/login";
        }

        int userId;

        for (User user : userDao.findAll()) {

            // If the username and password entered match a user in the database, create cookies for that user.
            if (loginForm.getUsername().equals(user.getUsername())) {
                if (loginForm.getPassword().equals(user.getPassword())) {
                    userId = user.getId();

                    Cookie loggedInCookie = new Cookie("loggedIn", "true");
                    Cookie userIdCookie = new Cookie("id", Integer.toString(userId));
                    Cookie passwordCookie = new Cookie("password", user.getPassword());

                    response.addCookie(loggedInCookie);
                    response.addCookie(userIdCookie);
                    response.addCookie(passwordCookie);

                    return "redirect:/posts?justLoggedIn=true";
                }
            }
        }

        //If the username or password does not match the database, create an error and return to the login form.

        model.addAttribute("title", "Log In - MusicFinds");
        model.addAttribute("header", "Log in to your account");
        model.addAttribute("wrongLoginInfoError", "Incorrect username or password.");

        model.addAttribute("loginActiveStatus", "active");

        return "users/login";
    }

    @RequestMapping(value = "signup", method = RequestMethod.GET)
    public String showSignupForm(Model model) {
        model.addAttribute("title", "Sign Up - MusicFinds");
        model.addAttribute("header", "Create an account");
        model.addAttribute(new User());

        model.addAttribute("signupActiveStatus", "active");

        return "users/signup";
    }

    @RequestMapping(value = "signup", method = RequestMethod.POST)
    public String signupUser(@ModelAttribute @Valid User newUser,
                             Errors errors, Model model) {

        //TODO: Handle password confirmation

        if (errors.hasErrors()) {
            model.addAttribute("title", "Sign Up - MusicFinds");
            model.addAttribute("header", "Create an account");

            model.addAttribute("signupActiveStatus", "active");

            return "users/signup";
        }

        userDao.save(newUser);

        return "redirect:/posts?justSignedUp=true";
    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logOutUser(HttpServletRequest request, HttpServletResponse response) {

        Cookie[] cookies = request.getCookies();

        Cookie loggedInCookie = new Cookie("loggedIn", null);
        Cookie userIdCookie = new Cookie("id", null);

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("loggedIn")) {
                response.addCookie(loggedInCookie);
            }
            if (cookie.getName().equals("id")) {
                response.addCookie(userIdCookie);
            }
        }

        return "redirect:/login?loggedOut=true";
    }
}
