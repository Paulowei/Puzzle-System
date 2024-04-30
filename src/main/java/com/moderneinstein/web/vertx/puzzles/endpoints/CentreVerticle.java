package com.moderneinstein.web.vertx.puzzles.endpoints  ; 

import io.vertx.core.AbstractVerticle;

import io.vertx.core.Vertx ; 
import io.vertx.core.Verticle ; 
import io.vertx.core.Handler ;


 import io.vertx.core.AsyncResult ;  
 import io.vertx.core.Future ; 
 import io.vertx.core.http.HttpServer ; 
 import io.vertx.core.http.HttpServerOptions ; 

 import io.vertx.ext.web.Router ; 
 import io.vertx.ext.web.RoutingContext ;

 import java.util.Properties ;
  import java.util.Map ; 

  import com.moderneinstein.web.vertx.puzzles.utils.PropertiesSource  ; 


  
public class CentreVerticle extends AbstractVerticle {

    private HttpServer server ; 
    private Router router ; 
    private  HttpServerOptions options ; 
    private Properties properties ; 

    public HttpServerOptions  configure( ){
        HttpServerOptions options = new HttpServerOptions( ) ;
        options.setPort(Integer.parseInt(properties.getProperty ("server-port"))) ; 
        options.setHost(new String(properties.getProperty ("server-host") )) ; 
        options.setSsl(Boolean.parseBoolean(properties.getProperty ("server-ssl"))) ; 
        options.setUseAlpn(Boolean.parseBoolean(properties.getProperty("server-use-alpn"))) ; 
        return options ; 
    }  

    public void  Endpoints( ){
        UserEndpoint endpoint1 = new UserEndpoint(vertx,server,router) ; 
        PuzzleEndpoint endpoint2 = new PuzzleEndpoint(vertx,server,router) ;
    }
    public void startServer(){
        server.exceptionHandler(
            new Handler<Throwable>( ){
                @Override 
                public void handle(Throwable Error){

                }
            } 
        );    
        server.requestHandler(router) ; 
      //  server.requestListener(router) ;     
    Handler<AsyncResult<HttpServer>> listener =  new Handler<AsyncResult<HttpServer>>( ){
        @Override 
         public void handle(AsyncResult<HttpServer> pending){
            
         }
    } ;   
         server.listen(8080,listener) ;
    }
    @Override
    public void start() {
         vertx =  Vertx.vertx ( )  ;   
        properties = PropertiesSource.deriveProperties() ; 
        HttpServerOptions options =  configure( ) ; 
        server = vertx.createHttpServer(options) ; 
         router = Router.router(vertx ) ;    
        startServer( ) ;    
    }

}
