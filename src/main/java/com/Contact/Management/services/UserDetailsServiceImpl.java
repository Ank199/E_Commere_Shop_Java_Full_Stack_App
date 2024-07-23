package com.Contact.Management.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.Contact.Management.Config.CustomUserDetails;
import com.Contact.Management.Models.User;
import com.Contact.Management.Repository.UserRepository;

public class UserDetailsServiceImpl implements UserDetailsService {
    
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       
        //fetch user from database

      User user = userRepository.getUserByUsername(username);
      if(user == null){
        throw new UsernameNotFoundException("couldnot found user ");
        
      }
      CustomUserDetails customUserDetails = new CustomUserDetails(user);
     return customUserDetails;
    }

    
   
    
}
