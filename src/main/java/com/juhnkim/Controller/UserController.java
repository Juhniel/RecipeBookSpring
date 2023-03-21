package com.juhnkim.Controller;

import com.juhnkim.Model.Recipe;
import com.juhnkim.Model.User;
import com.juhnkim.Model.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/myProfile")
    public String myProfile(Model model, HttpSession session) {
        // retrieve the logged-in user's information from the session
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        List<Recipe> favouriteRecipes = loggedInUser.getFavouriteRecipe();
        // add the user's information to the model
        model.addAttribute("favouriteRecipes", favouriteRecipes);

        // return the view for the profile page
        return "../templates/html/myProfile";
    }

    @PostMapping("/myProfile/update")
    public String updateMyProfile(@RequestParam("name") String name,
                                  @RequestParam("profile-pic") MultipartFile profilePic,
                                  HttpSession session) throws IOException {
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser == null) {
            return "redirect:/login";
        }

        if (name != null && !name.trim().isEmpty()) {
            loggedInUser.setUsername(name.trim());
        }

        if (!profilePic.isEmpty()) {
            loggedInUser.setUserProfileImg(profilePic.getBytes());
        }

        userService.saveUser(loggedInUser);
        return "redirect:/myProfile";
    }
}
