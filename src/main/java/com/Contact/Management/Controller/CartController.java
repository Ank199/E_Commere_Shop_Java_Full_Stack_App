package com.Contact.Management.Controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.Contact.Management.Models.Product;
import com.Contact.Management.Models.User;
import com.Contact.Management.Repository.UserRepository;
import com.Contact.Management.global.GlobalData;
import com.Contact.Management.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class CartController {

    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private UserRepository userRepository;

    @ModelAttribute
    public void addCommonData(Model model, Principal principal) {
        if (principal != null) {
            String userName = principal.getName();
            logger.info("UserName: " + userName);
            User user = userRepository.getUserByUsername(userName);
            logger.info("User: " + user);
            model.addAttribute("user", user);
        } else {
            logger.warn("Principal is null, user not authenticated");
        }
    }

    @GetMapping("/user/addToCart/{id}")
    public String addToCart(@PathVariable Long id) {
        Optional<Product> product = productService.getProductById(id);
        if (product.isPresent()) {
            GlobalData.cart.add(product.get());
            return "redirect:/user/shop";
        } else {
            logger.error("Product not found with ID: " + id);
            return "redirect:/user/shop?error=ProductNotFound";
        }
    }

    @GetMapping("/user/cart")
    public String showCart(Model model) {
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
        model.addAttribute("cart", GlobalData.cart);
        return "Shop/cart";
    }

    @GetMapping("/user/cart/removeItem/{index}")
    public String cartItemRemove(@PathVariable int index) {
        if (index >= 0 && index < GlobalData.cart.size()) {
            GlobalData.cart.remove(index);
        } else {
            logger.warn("Invalid cart index: " + index);
        }
        return "redirect:/user/cart";
    }

    @GetMapping("/user/checkout")
    public String checkout(Model model) {
        model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
        return "Shop/checkout";
    }
}
