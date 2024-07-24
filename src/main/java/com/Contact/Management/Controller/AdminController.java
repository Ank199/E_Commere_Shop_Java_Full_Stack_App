package com.Contact.Management.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.Contact.Management.Models.Category;
import com.Contact.Management.Models.Product;
import com.Contact.Management.dto.ProductDTO;
import com.Contact.Management.services.CategoryService;
import com.Contact.Management.services.ProductService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller

public class AdminController {

    // Ensure this is set correctly
    public static String uploadDir = "E:/New Project/EcommerceApplication/Management/src/main/resources/static/productImages" ;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @GetMapping("/admin/index")
    public String adminHome() {
        return "Admin/Admin_Home";
    }

    @GetMapping("/admin/categories/add")
    public String showAddCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "/Admin/categoriesadd"; // replace with your form view name
    }

    @PostMapping("/admin/categories/add")
    public String addCategory(@ModelAttribute("category") Category category) {
        // Save the category using the categoryService
        this.categoryService.addCategory(category);
        // Redirect to the /admin/categories page after saving
        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/categories")
    public String listCategories(Model model) {
        model.addAttribute("category", categoryService.getAllCategory());
        return "/Admin/categories"; // replace with your list view name
    }

    @RequestMapping("/admin/categories/delete/{id}")
    public String removeCategory(@PathVariable int id) {
        this.categoryService.RemoveCategoryById(id);
        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/categories/update/{id}")
    public String updateCategory(@PathVariable int id, Model model) {
        Optional<Category> category = this.categoryService.getCategoryById(id);
        if (category.isPresent()) {
            model.addAttribute("category", category.get());
            return "/Admin/categoriesadd";
        } else {
            return "404";
        }
    }

    // Product Section -------------------------->

    @GetMapping("/admin/products")
    public String listProducts(Model model) {
        model.addAttribute("products", this.productService.GetAllProduct());
        return "/Admin/products";
    }

    @GetMapping("/admin/products/add")
    public String showAddProductForm(Model model) {
        model.addAttribute("productDTO", new ProductDTO());
        model.addAttribute("categories", categoryService.getAllCategory());
        return "/Admin/productsadd";
    }

  @PostMapping("/admin/products/add")
    public String addProduct(@ModelAttribute("productDTO") ProductDTO productDTO,
                             @RequestParam("productImage") MultipartFile file,
                             @RequestParam("imgName") String imgName) throws IOException {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setCategory(categoryService.getCategoryById(productDTO.getCategoryId()).orElse(null));
        product.setPrice(productDTO.getPrice());
        product.setWeight(productDTO.getWeight());
        product.setDescription(productDTO.getDescription());

        String imageUUID;
        if (!file.isEmpty()) {
            imageUUID = file.getOriginalFilename();
            Path uploadPath = Paths.get(uploadDir);

            // Ensure the upload directory exists
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path fileNameAndPath = uploadPath.resolve(imageUUID);
            Files.copy(file.getInputStream(), fileNameAndPath, StandardCopyOption.REPLACE_EXISTING);
        } else {
            imageUUID = imgName;
        }

        product.setImageName(imageUUID);
        productService.addProduct(product);
        return "redirect:/admin/products";
    }


    @RequestMapping("/admin/product/delete/{id}")
    public String requestMethodName(@PathVariable Long id) {
        this.productService.RemoveProductById(id);
        return "redirect:/admin/products";
    }
    
  @GetMapping("/admin/product/update/{id}")
  public String UpdateProductById(@PathVariable long id, Model model){
   
    Product product = productService.getProductById(id).get();
    ProductDTO productDTO =new ProductDTO();

    productDTO.setId(product.getId());
    productDTO.setName(product.getName());
    productDTO.setCategoryId(product.getCategory().getId());
    productDTO.setPrice(product.getPrice());
    productDTO.setWeight(product.getWeight());
    productDTO.setDescription(product.getDescription());
    product.setImageName(product.getImageName());

    model.addAttribute("categories", categoryService.getAllCategory());
    model.addAttribute("productDTO", productDTO);


    return "/Admin/productsadd";

  }

}
