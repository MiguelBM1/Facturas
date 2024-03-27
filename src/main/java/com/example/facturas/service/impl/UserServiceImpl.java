package com.example.facturas.service.impl;

import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.facturas.constantes.FacturaConstantes;
import com.example.facturas.dao.UserDAO;
import com.example.facturas.pojo.User;
import com.example.facturas.service.UserService;
import com.example.facturas.util.FacturaUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDAO userDAO;

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        log.info("Registro interno {}", requestMap);
        try {
            
            if(validateSignMap(requestMap)) {
                
                
                User user = userDAO.fyndByEmail(requestMap.get("email"));
                if (Objects.isNull(user)){
                    
                    userDAO.save(getUserFromMap(requestMap));
                    return FacturaUtils.getResponseEntity("Usuario registrado", HttpStatus.CREATED);
                }else {
                    return FacturaUtils.getResponseEntity("El usuario ya existe", HttpStatus.BAD_REQUEST);

                }
            }else{
                return FacturaUtils.getResponseEntity(FacturaConstantes.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return FacturaUtils.getResponseEntity(FacturaConstantes.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR); 


    }

    private boolean validateSignMap(Map<String, String> requestMap) {
        
        if(requestMap.containsKey("nombre") && requestMap.containsKey("numeroDeContacto") && requestMap.containsKey("email") && requestMap.containsKey("password")) {
            
            return true;
        }
        return false;
    }

    private User getUserFromMap(Map<String, String> requestMap) {
        
        User user = new User();
        user.setNombre(requestMap.get("nombre"));
        user.setNumeroDeContacto(requestMap.get("numeroDeContacto"));
        user.setEmail(requestMap.get("email"));
        user.setPassword(requestMap.get("password"));
        user.setRol("user");
        user.setStatus("false");

        return user;
    }
}
