package com.Contact.Management.Controller;

import org.springframework.web.bind.annotation.RequestMapping;

import com.Contact.Management.Models.User;
import com.Contact.Management.Repository.UserRepository;
import com.Contact.Management.global.GlobalData;
import com.Contact.Management.services.CategoryService;
import com.Contact.Management.services.ProductService;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

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
    String userName = principal.getName();
    System.out.println("UserName " + userName);
    User user = userRepository.getUserByUsername(userName);
    System.out.println("User " + user);
    model.addAttribute("user", user);

  }
  
  @GetMapping("/index")
  public String Home(Model model,Principal principal) {
    
      return "/Shop/index";
  }

  @GetMapping("/shop")
  public String Shop(Model model) {
    model.addAttribute("categories", this.categoryService.getAllCategory());
    model.addAttribute("products", productService.GetAllProduct());
     model.addAttribute("cartCount", GlobalData.cart.size());
    return "/Shop/shop";
  }
  @GetMapping("/shop/category/{id}")
  public String ShopByCategory(Model model,@PathVariable int id) {
    model.addAttribute("categories", this.categoryService.getAllCategory());
    model.addAttribute("products", productService.getAllProductByCategoryId(id));
    // model.addAttribute("cartCount", GlobalData.cart.size());
    return "/Shop/shop";
  }
  @GetMapping("/shop/viewproduct/{id}")
  public String ViewProductById(Model model,@PathVariable Long id) {

    model.addAttribute("product", productService.getProductById(id).get());
    model.addAttribute("cartCount", GlobalData.cart.size());

    return "/Shop/viewProduct";
  }
  

}
