package com.example.facturas.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class FacturaUtils {

    private FacturaUtils() {
    
    }

    public static ResponseEntity<String> getResponseEntity(String message, HttpStatus httpSataus){
        return new ResponseEntity<String>("Mensaje: " + message, httpSataus);
    }
}
