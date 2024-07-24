package com.Contact.Management.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.Contact.Management.Models.User;
import com.Contact.Management.Repository.UserRepository;
import com.Contact.Management.global.GlobalData;
import com.Contact.Management.services.CategoryService;
import com.Contact.Management.services.ProductService;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserRepository userRepository;

    @ModelAttribute
    public void addCommonData(Model model, Principal principal) {
        if (principal != null) {
            String userName = principal.getName();
            User user = userRepository.getUserByUsername(userName);
            if (user != null) {
                model.addAttribute("user", user);
            } else {
                model.addAttribute("user", new User());
            }
        }
    }

    @GetMapping("/index")
    public String home(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login"; // Redirect to login if the user is not authenticated
        }
        return "Shop/index";
    }

    @GetMapping("/shop")
    public String shop(Model model) {
        model.addAttribute("categories", categoryService.getAllCategory());
        model.addAttribute("products", productService.getAllProduct());
        model.addAttribute("cartCount", GlobalData.cart.size());
        return "Shop/shop";
    }

    @GetMapping("/shop/category/{id}")
    public String shopByCategory(Model model, @PathVariable int id) {
        model.addAttribute("categories", categoryService.getAllCategory());
        model.addAttribute("products", productService.getAllProductByCategoryId(id));
        return "Shop/shop";
    }

    @GetMapping("/shop/viewproduct/{id}")
    public String viewProductById(Model model, @PathVariable Long id) {
        model.addAttribute("product", productService.getProductById(id).orElse(null));
        model.addAttribute("cartCount", GlobalData.cart.size());
        return "Shop/viewProduct";
    }
}

