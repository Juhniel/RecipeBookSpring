package com.juhnkim.Controller;

import com.juhnkim.Model.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/profile")
    public String handleProfileForm(@AuthenticationPrincipal CurrentUser currentUser,
                                    @RequestParam("profile-pic") MultipartFile file,
                                    @RequestParam("name") String name,
                                    @RequestParam("linkedin") String linkedin,
                                    @RequestParam("facebook") String facebook,
                                    @RequestParam("instagram") String instagram) throws IOException {

        User user = currentUser.getUser();
        UserProfile userProfile = user.getProfile();

        if (userProfile == null) {
            userProfile = new UserProfile();
            userProfile.setUser(user);
            user.setProfile(userProfile);
        }

        // Set user profile fields
        userProfile.setName(name);
        userProfile.setLinkedin(linkedin);
        userProfile.setFacebook(facebook);
        userProfile.setInstagram(instagram);

        // Handle profile picture upload
        if (!file.isEmpty()) {
            userProfile.setImage(file.getBytes());
        }

        userRepository.save(user);

        return "redirect:/profile";
    }
}