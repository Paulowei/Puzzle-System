package com.moderneinstein.web.vertx.puzzles.services;

import io.vertx.core.Vertx ; 
import io.vertx.core.Future ;
import io.vertx.core.Handler ; 
import io.vertx.ext.mongo.MongoClient ;  
import io.vertx.core.json.JsonObject ; 
import io.vertx.ext.web.Router ; 

import java.util.Properties ;

import com.moderneinstein.web.vertx.puzzles.utils.PropertiesSource ;

public class ConnectionSource {
    
    private static Vertx context;  
    private static MongoClient client ;    
    private static Properties properties ;    
    private static boolean state = false ;    

    public static  JsonObject content(){  
        JsonObject created = new JsonObject() ;
        
        return  created  ; 
    }
    public static void configure(){  
        JsonObject holder = content() ;  
        client =  MongoClient.createShared (context, holder) ;  
        
    }  

    public static MongoClient CreateClient(Vertx vertx){     
        properties = PropertiesSource.deriveProperties() ;    
        context =  vertx ; 
        if(state==false){    
        configure() ;  
        state = true ;    }  
        return  client   ; 
    }
}
