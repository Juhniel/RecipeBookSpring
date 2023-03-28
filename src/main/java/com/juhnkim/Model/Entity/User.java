package com.juhnkim.Model.Entity;

import com.juhnkim.Model.Entity.Recipe;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String username;

    private String userFirstName;
    private String userLastName;
    private String userPassword;
    private String userEmail;

    @Column(name = "user_profile_img")
    @Lob
    private byte[] userProfileImg;

    @ManyToMany
    @JoinTable(
            name = "userfavouriterecipes",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "recipe_id")
    )
    private List<Recipe> favouriteRecipe = new ArrayList<>();

    public User() {

    }

    public User(String username, String userFirstName, String userLastName, String userPassword, String userEmail) {
        this.username = username;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userPassword = userPassword;
        this.userEmail = userEmail;
    }


//    public String setHashedPassword(String password) {
//        // Hash the password using BCrypt
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String hashedPassword = passwordEncoder.encode(password);
//
//        // Set the hashed password as the new password
//        return hashedPassword;
//    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public byte[] getUserProfileImg() {
        return userProfileImg;
    }

    public void setUserProfileImg(byte[] userProfileImg) {
        this.userProfileImg = userProfileImg;
    }

    public List<Recipe> getFavouriteRecipe() {
        return favouriteRecipe;
    }

    public void setFavouriteRecipe(List<Recipe> favouriteRecipe) {
        this.favouriteRecipe = favouriteRecipe;
    }
}
