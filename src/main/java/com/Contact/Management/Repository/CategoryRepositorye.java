package com.Contact.Management.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.Contact.Management.Models.Category;


public interface CategoryRepositorye extends JpaRepository<Category,Integer>{
    
    Optional<Category> findById(Integer id);
    
}
