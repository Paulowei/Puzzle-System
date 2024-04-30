package com.moderneinstein.web.vertx.puzzles.endpoints;

import java.util.Arrays ;
import java.util.Vector ; 
import java.util.Map ;  

import io.vertx.core.Vertx ; 
import io.vertx.core.Future ; 
import io.vertx.core.Handler ;

import io.vertx.core.http.HttpServer ; 
import io.vertx.core.http.HttpServerRequest ;
import io.vertx.core.http.HttpServerResponse ;
import io.vertx.core.http.HttpMethod ; 

import io.vertx.core.buffer.Buffer ; 
import io.vertx.core.json.JsonArray ; 
import io.vertx.core.json.JsonObject ; 

import io.vertx.ext.web.Router ; 
import io.vertx.ext.web.RoutingContext ; 
import io.vertx.ext.web.Route ;  

import com.moderneinstein.web.vertx.puzzles.models.FCUser ; 
import com.moderneinstein.web.vertx.puzzles.models.Puzzle ;   
import com.moderneinstein.web.vertx.puzzles.services.UserServices ; 
import com.moderneinstein.web.vertx.puzzles.services.PuzzleServices ; 
import com.moderneinstein.web.vertx.puzzles.utils.Serialisers  ; 
import com.moderneinstein.web.vertx.puzzles.utils.Handlers ; 


public class UserEndpoint {
    
    private HttpServer server ;
    private Router router ;
    private  Vertx context ;  
     private UserServices services ; 

    public void ServeRoutes(){

    }
    public UserEndpoint(Vertx parent,HttpServer crest,Router links){
        context = parent ; 
        router =  links ; 
        server = crest ;  
        ServeRoutes() ; 
    } 
    
    public void serveRoutes1(){
        Route paths =  router.route() ; 
        paths.method(HttpMethod.PUT) ;
        paths.path(new String("")) ; 
        paths.handler (new Handler<RoutingContext>(){
            @Override 
            public void handle(RoutingContext context){
                Future<Buffer> planning =  context.request().body() ; 
                planning.onSuccess(new Handler<Buffer>(){
                    @Override 
                    public void handle(Buffer buffer){
                        JsonObject message = buffer.toJsonObject() ;
                        FCUser users = Serialisers.DeserialiseUser(message.getJsonObject("user")) ;  
                        String password = message.getString("password") ;  
                        Future<String> future3 = services.insertUser(users,password) ;
                     }
                } ) ;
            }
        }) ;
    }
}
