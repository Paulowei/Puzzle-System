package com.moderneinstein.web.vertx.puzzles.services;

import io.vertx.core.Vertx ;
import io.vertx.core.Handler ;
import io.vertx.core.Promise ;  
import io.vertx.core.Future ; 
import io.vertx.core.AsyncResult ; 

import io.vertx.core.json.JsonObject ; 
import io.vertx.core.json.JsonArray ; 

import io.vertx.ext.mongo.MongoClient ; 
import io.vertx.ext.mongo.MongoClientUpdateResult ;
import io.vertx.ext.mongo.MongoClientDeleteResult ; 

import io.vertx.ext.auth.HashingStrategy ;  

import java.util.Map ; 
import java.util.HashMap ; 
import java.util.Properties ;
import java.util.Arrays ; 
import java.util.List ; 

import com.moderneinstein.web.vertx.puzzles.services.ConnectionSource ;
import com.moderneinstein.web.vertx.puzzles.utils.PropertiesSource ; 
import com.moderneinstein.web.vertx.puzzles.models.FCUser ; 
import com.moderneinstein.web.vertx.puzzles.models.Puzzle ; 
import com.moderneinstein.web.vertx.puzzles.utils.Serialisers ; 

public class UserServices {
    
    private Vertx  context ; 
    private MongoClient client ; 
    private HashingStrategy strategy  ; 
    private Map<String,String> mapper ; 
    private String  Algorithm ; 
    private Properties properties ; 
    private String HashingSalt ; 
    public static final String  collection = "Users" ; 
    public static final String  repository = "Extras" ; 
    public  void createSource(){
        client.createCollection ("Users",new Handler<AsyncResult<Void>>(){
            @Override
            public void handle(AsyncResult<Void> pending){

            }
        }) ;  
        client.createCollection("Extras",new Handler<AsyncResult<Void>>(){
            @Override 
            public void handle(AsyncResult<Void> pending){

            }

        }) ; 
    }
    public void configure(){  
       client = ConnectionSource.CreateClient(context) ;   
    mapper = new HashMap<String,String>() ; 
    Algorithm = properties.getProperty("hashing-algorithm") ;  
    HashingSalt =  properties.getProperty ("hashing-salt");
    }  

    public UserServices(Vertx parent){
        this.context = parent ;  
        properties = PropertiesSource.deriveProperties() ;  
        configure () ; 
        strategy = HashingStrategy.load() ;  
    }

    public Future<String> insertUser (FCUser user,String password){
          JsonObject input  = Serialisers.SerialiseUser(user) ; 
          Promise<String> promise4 = Promise.promise() ; 
         Future<String> pending =  client.insert (collection,input ) ;  
         String hashes =   strategy.hash(Algorithm,mapper, HashingSalt,password) ;
        pending.onSuccess ( new Handler<String>(){
            @Override 
            public void handle(String param1){
                JsonObject edits =  new JsonObject().put("identity",param1).put("password",hashes) ; 
                JsonObject delta = new JsonObject().put("$set",edits) ; 
                JsonObject checks =  new JsonObject().put("_id",param1) ; 
                client.updateCollection (collection,checks, edits ).
            onFailure(new Handler<Throwable>(){
                @Override 
                public void handle(Throwable Error){
                    
                }
            }).onSuccess(new Handler<MongoClientUpdateResult>(){
                 @Override 
                 public void handle(MongoClientUpdateResult result){
                   
                 }
            }) ;
            }
        }).onFailure(new Handler<Throwable>(){
            @Override 
            public void handle(Throwable Exception){
                promise4.fail(Exception) ;  
            }
        }) ;
        Future<String> planning = promise4.future( ) ;
        return  planning  ; 
        
    }
    public Future<String> loginUser(String email,String password){
        JsonObject created = new JsonObject( ).put("email",email) ; 
        Promise<String> promise4 = Promise.promise()   ; 
        Future<List<JsonObject>> future4 =  client.find (collection,created) ; 
        future4.onFailure(new Handler<Throwable>(){
            @Override 
            public void handle(Throwable Exception){
                promise4.fail(Exception) ; 
            }
        }).onSuccess(
            new Handler<List<JsonObject>>(){
                @Override 
                public void handle(List<JsonObject> serial ){
                    if(serial.size ()<=0){
                        Throwable Error = new Throwable ("Sorry, the details were not Found") ; 
                        promise4.fail(Error) ;  return ;  }  
                 JsonObject jsons = serial.get(0) ; 
                String temps = jsons.getString("password") ;   
                boolean cases = strategy.verify(temps,password) ;  
                if(cases==false){
                    promise4.fail(new Throwable("Credentials were Incorrect")) ;
                }  else{  
                 //   JsonObject gains  = new JsonObject()   ;   
                    JsonObject wrapper= new JsonObject ().put ("success",true) ;  
                 promise4.complete(wrapper.toString ()) ;  
                }
                } 
            }
        ) ; 
        return promise4.future( ) ;
    }  
    
}
