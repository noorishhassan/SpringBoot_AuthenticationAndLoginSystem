/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.springboot;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 *
 * @author noorishhassan
 */

@Service
public class UserService {
    
    @Autowired
    UserRepository userRepository;
    
    public List<UserModel> getUserDetails(){
       
        List<UserModel> userDetails = userRepository.findAll();
        return userDetails;
    }
    
    public Integer assignUserId(){
        
        List<UserModel> userDetails = userRepository.findAll();
        
        Integer id = 1;
        
        for (UserModel u : userDetails)
            id = u.getId();
        id++;
        return id;
    }
    
    String createUser(List<String> user){

        if (! (findUserEmail(user.get(1)) == null))
            return "Invalid email.";
        else if (! (user.get(2).equals(user.get(3))))
            return "Passwords don't match.";
        else if (user.get(2).length() < 8)
            return "Password must be have atleast 8 characters.";
        else{
            userRepository.save(new UserModel(assignUserId(), user.get(0), user.get(1), user.get(2), user.get(4), user.get(5)));
            SpringbootApplication.sessionId = user.get(1);
            return "Welcome " + user.get(0) + " . We have created your profile.";
        }
    }

    boolean loginUser(List<String> list) {
        
        List<UserModel> userDetails = userRepository.findAll();
        
        for (UserModel u : userDetails){
            if (u.getEmail().equals(list.get(0))){
                if (u.getPassword().equals(list.get(1))){
                    return true;
                }
            }
        }
        return false;
        
    }

    Integer findUserId(String email){
        
        List<UserModel> userDetails = userRepository.findAll();
        
        for (UserModel u : userDetails){
            if (u.getEmail().equals(email)){
                return u.getId();
            }
        }
        return 0;
    }
    
    String findUserName(String email){
        
        List<UserModel> userDetails = userRepository.findAll();
        
        for (UserModel u : userDetails){
            if (u.getEmail().equals(email)){
                return u.getName();
            }
        }
        return null;
    }
    
    String findUserEmail(String email){
        
        List<UserModel> userDetails = userRepository.findAll();
        
        for (UserModel u : userDetails){
            if (u.getEmail().equals(email)){
                return u.getEmail();
            }
        }
        return null;
    }
    
    String findUserPassword(String email){
        
        List<UserModel> userDetails = userRepository.findAll();
        
        for (UserModel u : userDetails){
            if (u.getEmail().equals(email)){
                return u.getPassword();
            }
        }
        return null;
    }
    
    String findUserGender(String email){
        
        List<UserModel> userDetails = userRepository.findAll();
        
        for (UserModel u : userDetails){
            if (u.getEmail().equals(email)){
                return u.getGender();
            }
        }
        return null;
    }
    
    String findUserDateOfBirth(String email){
        
        List<UserModel> userDetails = userRepository.findAll();
        
        for (UserModel u : userDetails){
            if (u.getEmail().equals(email)){
                return u.getDateOfBirth();
            }
        }
        return null;
    }
    
    UserModel findUser(String email){
        
        List<UserModel> userDetails = userRepository.findAll();
        
        for (UserModel u : userDetails){
            if (u.getEmail().equals(email)){
                return u;
            }
        }
        return null;
    }
    
    String updateUserProfile(List<String> user) { //index 0 -> name, index 1 -> email, index 2 -> gender, index 3 -> dateOfBirth
        
        if (! (findUserEmail(user.get(1)) == null))
            return "Invalid email";
        else {
            userRepository.save(new UserModel(findUserId(SpringbootApplication.sessionId), user.get(0), user.get(1), findUserPassword(SpringbootApplication.sessionId), user.get(2), user.get(3)));
            SpringbootApplication.sessionId = user.get(1);
            return "Your profile has been updated.";
        }
    }

    String changeUserPassword(List<String> list) {
        
        if (findUserPassword(SpringbootApplication.sessionId).equals(list.get(0))){
            if (list.get(1).equals(list.get(2))){
                userRepository.save(new UserModel(findUserId(SpringbootApplication.sessionId), findUserName(SpringbootApplication.sessionId), SpringbootApplication.sessionId, list.get(1), findUserGender(SpringbootApplication.sessionId), findUserDateOfBirth(SpringbootApplication.sessionId)));
                return "Your Password has been changed";
            }
            else
            {
                return "Passwords don't match.";
            }
        }
        else
            return "Current password was incorrect";
    }

    void deleteUserAccount() {
        SpringbootApplication.sessionId = null;
        userRepository.delete(findUser(SpringbootApplication.sessionId));
    }
}
