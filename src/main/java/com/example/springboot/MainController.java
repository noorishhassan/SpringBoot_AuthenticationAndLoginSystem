/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.springboot;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author noorishhassan
 */

@RestController
public class MainController {
    
    @Autowired
    UserService userService;
    
    @RequestMapping("/")
    public String greeting (){
        return "Welcome to my SpringBoot Assignment.";
    }
    
    @RequestMapping("/logout")
    public String logout (){
        if (SpringbootApplication.sessionId == null)
            return "You are not logged in.";
        else {
            SpringbootApplication.sessionId = null;
            return "You have been logged out.";
        }
    }
    
    @GetMapping("/view-my-profile")
    public List<String> getUserProfile(){
        
        if (SpringbootApplication.sessionId==null){
            List<String> list = new ArrayList<>();
            list.add("Login to view your profile.");
            return list;
        }
        else{
            List<UserModel> userList = userService.getUserDetails();
            List<String> list = new ArrayList<>();

            for (UserModel userDetails : userList){
                if (userDetails.getEmail().equals(SpringbootApplication.sessionId)){
                list.add(userDetails.name);
                list.add(userDetails.getEmail());
                list.add(userDetails.getPassword());
                list.add(userDetails.getGender());
                list.add(userDetails.getDateOfBirth());
                }
            }

            return list;
        }
    }
    
    @PostMapping("/signup")
    public String createUser(@RequestBody List<String> list){
        
        String message = userService.createUser(list);
        return message;
    }
    
    @PostMapping("/update-profile")
    public String updateUserProfile(@RequestBody List<String> list){
        
        if (SpringbootApplication.sessionId == null)
            return "Log in to edit your profile.";
        else {
            String message = userService.updateUserProfile(list);
            return message;
        }
    }
    
    @PostMapping("/change-password")
    public String changeUserPassword(@RequestBody List<String> list){
        
        if (SpringbootApplication.sessionId == null)
            return "Log in to change your password.";
        else {
            String message = userService.changeUserPassword(list);
            return message;
        }
    }
    
    @PostMapping("/delete-my-account")
    public String deleteUserAccount(){
        
        if (SpringbootApplication.sessionId == null)
            return "Log in to delete your account.";
        else {
            userService.deleteUserAccount();
            return "Your account has been deleted.";
        }
    }
    
    @PostMapping("/login")
    public String loginUser(@RequestBody List<String> list){
        
        if (userService.loginUser(list)){
            SpringbootApplication.sessionId = list.get(0);
           return "Welcome back " + list.get(0) + ". You are logged in.";
        }
        else
            return "Incorrect email or password.";
    }
}
