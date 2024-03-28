package com.example.facturas.security;

import java.util.ArrayList;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.facturas.dao.UserDAO;
import com.example.facturas.pojo.User;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomerDetailsService  implements UserDetailsService{
    
    @Autowired
    private UserDAO userDAO;

    private User userDetails;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
     
        log.info("dentro de LoadUserByUsername", username);
        userDetails = userDAO.fyndByEmail(username);
        if (!Objects.isNull(userDetails)) {
            return  new org.springframework.security.core.userdetails.User(userDetails.getEmail(), userDetails.getPassword(), new ArrayList<>());
            
        }else{

            throw new UsernameNotFoundException("Usuario no encontrado");
        }
        
    }

    public User getUserDetails() {
        return userDetails;
        
    }

}
