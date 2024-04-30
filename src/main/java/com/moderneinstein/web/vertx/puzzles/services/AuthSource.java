package com.moderneinstein.web.vertx.puzzles.services;


import java.util.Properties ; 


import io.vertx.core.Vertx ; 
import io.vertx.core.json.JsonObject ; 
import io.vertx.ext.auth.jwt.JWTAuth ; 
import io.vertx.ext.auth.jwt.JWTAuthOptions ; 
import io.vertx.ext.auth.JWTOptions ; 
import io.vertx.ext.auth.KeyStoreOptions ;


import com.moderneinstein.web.vertx.puzzles.utils.PropertiesSource ; 


public class AuthSource { 


    private static  JWTAuth central ; 
    private static boolean  started ; 
    private static Properties properties ; 
     private static  JWTAuthOptions alias ;   
     private static  Vertx   vertx ; 
    public static JWTOptions  createOptions(){
        JWTOptions options = new JWTOptions() ; 
        options.setIssuer(properties.getProperty("jwt-issuer")) ;  
        options.setAlgorithm(properties.getProperty("jwt-algorithm")) ; 
        options.setExpiresInMinutes(Integer.parseInt(properties.getProperty("jwt-minutes"))) ;  
        options.setIgnoreExpiration(Boolean.parseBoolean(properties.getProperty("jwt-ignore"))) ;  
        return options  ; 
    }
    public static void configure( ){
         KeyStoreOptions keystore = new  KeyStoreOptions() ;  
        keystore.setPath(properties.getProperty("jwt-path")) ; 
        keystore.setPassword(properties.getProperty("jwt-password"))  ;
        JWTAuthOptions  options = new JWTAuthOptions() ; 
        options.setJWTOptions(createOptions()) ;   
        alias = options ;      
    }
    public static JWTAuth  deriveAuth(Vertx source){  
        properties =  PropertiesSource.deriveProperties() ;  
        vertx = source  ;  
        if(started==false){  
            configure() ;  
             central = JWTAuth.create(vertx,alias) ;    
             started = true  ; 
        }   
        return central ;
    }
    
}
