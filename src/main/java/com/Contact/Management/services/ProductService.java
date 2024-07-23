package com.Contact.Management.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Contact.Management.Models.Product;
import com.Contact.Management.Repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    
    public List<Product>GetAllProduct(){
      return this.productRepository.findAll();
    }
    public void addProduct(Product product){
         this.productRepository.save(product);
    }

    public void RemoveProductById(Long id){
       this.productRepository.deleteById(id);
    }
    public Optional<Product> getProductById(Long id){
        return this.productRepository.findById(id);
    }

    public List<Product>getAllProductByCategoryId(int id){
        return productRepository.findAllByCategory_Id(id);
    }
}
