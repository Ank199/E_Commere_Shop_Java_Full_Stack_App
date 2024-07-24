package com.Contact.Management.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.Contact.Management.Models.User;
import com.Contact.Management.Repository.UserRepository;
import com.Contact.Management.global.GlobalData;


import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("title", "Home - Smart Contact Manager");
       
        return "home";
    }

    @GetMapping("/about")
    public String About(Model model) {
        model.addAttribute("title", "about - Smart Contact Manager");
        return "About";
    }

    @GetMapping("/signup")
    public String Resistration(Model model) {
        model.addAttribute("title", "signup - E-Commerce Shop");
        model.addAttribute("user", new User());
        return "signup";
    }

    @RequestMapping(value = "/do_resister", method = RequestMethod.POST)
    public String registerUser(@ModelAttribute("user") User user,
            @RequestParam(value = "agreement", defaultValue = "false") boolean agreement,
            Model model,
            HttpSession session) {

        try {
            if (!agreement) {
                throw new Exception("You have not agreed to terms and conditions.");
            }

            user.setRole("ROLE_USER"); // Prefix with ROLE_

            user.setEnable(true);
            user.setImageUrl("default.png");
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            userRepository.save(user); // Save user to the database

            model.addAttribute("user", new User()); // Reset the user object for the form

            return "signup"; // Redirect to the signup page or another page upon successful registration

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("user", user); // Populate the form with user data for correction

            return "signup"; // Stay on the signup page to correct errors
        }
    }

    @RequestMapping("/login")
     public String CustomeLogin(Model model) {
        model.addAttribute("title", "login page");
        GlobalData.cart.clear();
        return "/login";
    }
    @GetMapping("/loginfail")
    public String FailLogin() {
        return "/loginfail";
    }
    





  


  
}
