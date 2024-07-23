package com.Contact.Management.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Contact.Management.Models.User;

public interface UserRepository extends JpaRepository<User,Integer> {
     
    @Query("select u from User u where u.email=:email")
    public User getUserByUsername(@Param("email") String email);

    static Optional<User> findById(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }
}
