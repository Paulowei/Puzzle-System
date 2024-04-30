package com.moderneinstein.web.vertx.puzzles.utils;

import io.vertx.core.json.JsonArray ; 
import io.vertx.core.json.JsonObject ;
import io.vertx.core.buffer.Buffer ; 

import java.util.Map ; 
import java.util.Arrays; 
import java.util.Set ; 

import io.vertx.core.http.HttpServerResponse  ; 
import io.vertx.core.http.HttpServerRequest ; 
import io.vertx.core.http.HttpServer ; 


public class Handlers {
    
    public static void HandleError(HttpServerResponse response,Throwable Error,String message,boolean success,boolean chunked,boolean terminate){
        JsonObject wrapper  = new JsonObject() ; 
        wrapper.put("success",success) ; 
        wrapper.put("message",message) ; 
        wrapper.put("error",Error) ; 
        response.setChunked(chunked) ; 
        response.write(wrapper.toString()) ; 
        if(terminate==true){
            response.end( ) ; 
        }
    } 
    public static void HandleResponse(HttpServerResponse response,Object frames,String message,boolean success,boolean chunked,boolean terminate){ 
        JsonObject contains= new JsonObject() ; 
        contains.put("success",success) ; 
        contains.put("message",message) ; 
        contains.put ("payload", frames) ;   
         response.setChunked(chunked) ; 
        response.write( contains.toString()) ; 
        if (terminate==true){
            response.end() ;
        }
     }
}
