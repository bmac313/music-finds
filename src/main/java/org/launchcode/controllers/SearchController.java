package org.launchcode.controllers;

import org.launchcode.models.Post;
import org.launchcode.models.data.PostDao;
import org.launchcode.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "search")
public class SearchController {

    @Autowired
    private PostDao postDao;

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "")
    public String index(@RequestParam String searchTerm,
                        @RequestParam(defaultValue = "1") int page,
                        @CookieValue("id") String userIdCookie,
                        @CookieValue("password") String passwordCookie,
                        Model model) {

        PageRequest pageRequest = new PageRequest(page-1, 5, Sort.Direction.DESC, "timeStamp");
        int pages = postDao.findAll(pageRequest).getTotalPages();

        if ((page-1) <= 0) {
            model.addAttribute("visibilityPrev", "hidden");
        }

        if ((page) >= pages) {
            model.addAttribute("visibilityNext", "hidden");
        }

        List<Post> searchResults = new ArrayList<>();
        Iterable<Post> posts = postDao.findAll(pageRequest);

        model = setNavItemVisibility(model, userIdCookie, passwordCookie);

        for (Post post : posts) {

            List<String> fields = post.getStandardSearchFields();

            for (String field : fields) {
                if (field.toLowerCase().contains(searchTerm.toLowerCase())) {
                    searchResults.add(post);
                    break;
                }
            }
        }

        model.addAttribute("title", "Search Results - MusicFinds");
        model.addAttribute("searchTerm", searchTerm);
        model.addAttribute("results", searchResults);
        model.addAttribute("page", page);

        return "search/search-results";
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
