package com.moderneinstein.web.vertx.puzzles.endpoints;

import io.vertx.core.Vertx ;
import io.vertx.core.Handler ;
import io.vertx.core.Future ;  
import io.vertx.core.AsyncResult ; 

import io.vertx.ext.web.Router ;
import io.vertx.ext.web.RoutingContext ; 
import io.vertx.ext.web.Route ; 

import io.vertx.core.json.JsonArray ; 
import io.vertx.core.json.JsonObject ;
import io.vertx.core.buffer.Buffer ;

import io.vertx.core.http.HttpServer ;
import io.vertx.core.http.HttpServerRequest ; 
import io.vertx.core.http.HttpServerResponse ; 
import io.vertx.core.http.HttpMethod ;  

import io.vertx.ext.auth.jwt.JWTAuth ; 

import  com.moderneinstein.web.vertx.puzzles.utils.Handlers ; 
import com.moderneinstein.web.vertx.puzzles.utils.Serialisers ; 
import com.moderneinstein.web.vertx.puzzles.services.UserServices ; 
import com.moderneinstein.web.vertx.puzzles.services.PuzzleServices ;
import com.moderneinstein.web.vertx.puzzles.models.FCUser ; 
import com.moderneinstein.web.vertx.puzzles.models.Puzzle ; 


public class PuzzleEndpoint {

    private Vertx context ; 
    private HttpServer server ; 
    private  Router router ; 
    private JWTAuth auths ; 
    private PuzzleServices services ;
     private UserServices alias ; 

    public void ServeRoutes(){
        
    }
    public PuzzleEndpoint(Vertx  frames,HttpServer http,Router tpLink){
        context = frames ; 
        server = http ; 
        router = tpLink ;    
        services = new PuzzleServices(frames) ; 
        ServeRoutes() ; 
    }

    public void serveRoutes1(){
        Route route = router.route() ; 
        route.method(HttpMethod.POST) ; 
        route.path(new String("")) ;   
        route.handler( new Handler<RoutingContext>(){
            @Override 
            public void handle(RoutingContext  context){
                Future<Buffer> futures3 = context.request().body() ;  
                futures3.onFailure(new Handler<Throwable>(){
                    @Override 
                    public void handle(Throwable Error){
                        Handlers.HandleError(context.response( ),Error,"",false,true,true) ;
                    }
                }).onSuccess(new Handler<Buffer>(){
                    @Override 
                    public void handle(Buffer buffers){
                         JsonObject  message =  buffers.toJsonObject() ;
                         Puzzle puzzle =  Serialisers.DeserialisePuzzle(message.getJsonObject("puzzle")) ;
                    }
                }) ;
            }
        }) ;  
    }
    
}
