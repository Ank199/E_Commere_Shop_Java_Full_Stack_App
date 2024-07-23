package com.Contact.Management.Controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.Contact.Management.Models.Product;
import com.Contact.Management.Models.User;
import com.Contact.Management.Repository.UserRepository;
import com.Contact.Management.global.GlobalData;
import com.Contact.Management.services.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;





@Controller
public class CartController {
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
    
    @GetMapping("/user/addToCart/{id}")
    public String getMethodName(@PathVariable Long id) {
      
        GlobalData.cart.add(productService.getProductById(id).get());
        return "redirect:/user/shop";
    }
    
    @GetMapping("/user/cart")
    public String ShowCart(Model model) {
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product ::getPrice).sum());
        model.addAttribute("cart", GlobalData.cart);
       return "/Shop/cart";
    }

    @GetMapping("/user/cart/removeItem/{index}")
    public String cartItemRemove(@PathVariable int index) {
        GlobalData.cart.remove(index);
        return "redirect:/user/cart";
    }
    
    @GetMapping("/user/checkout")
    public String Checkout(Model model) {
       model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());

       return "/Shop/checkout";
    }
    
    
    
}
