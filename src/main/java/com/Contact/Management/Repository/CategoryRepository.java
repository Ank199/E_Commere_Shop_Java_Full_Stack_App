package com.Contact.Management.Repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.Contact.Management.Models.Category;


public interface CategoryRepository extends JpaRepository<Category,Integer>{
   
}
