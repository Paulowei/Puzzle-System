package com.moderneinstein.web.vertx.puzzles.services;


import java.util.Vector ;
import java.util.Arrays ;  

import io.vertx.core.Future ; 
import io.vertx.core.Handler ;
import io.vertx.core.Promise  ;   
import io.vertx.core.AsyncResult ; 

import io.vertx.core.json.JsonObject ; 
import io.vertx.core.json.JsonArray ; 
 
import io.vertx.core.Vertx ;
import io.vertx.ext.mongo.MongoClient ; 
import io.vertx.ext.mongo.MongoClientUpdateResult ; 
import io.vertx.ext.mongo.MongoClientDeleteResult ;

import com.moderneinstein.web.vertx.puzzles.models.Puzzle ; 
import com.moderneinstein.web.vertx.puzzles.utils.TaskScheduler ;
import com.moderneinstein.web.vertx.puzzles.utils.Serialisers ;
import com.moderneinstein.web.vertx.puzzles.services.ConnectionSource ; 

public class PuzzleServices {
    
    private MongoClient client ; 
     private Vertx context ; 

    public void configure(){
        client = ConnectionSource.CreateClient(context) ; 
    }
    public PuzzleServices(Vertx parent){
       context = parent ; 
       //client = mongo ; 
       configure() ; 
    } 
     
    public Future<Boolean>  ValidatePuzzle(Puzzle puzzle){  
        int[][] cores = puzzle.deriveCores( ) ; 
        Promise<Boolean> promise4 =  Promise.promise() ;
        Future<Boolean> future4 = TaskScheduler.ValidatePuzzle(cores) ;   
        future4.onSuccess(new Handler<Boolean>(){
            @Override 
            public void handle(Boolean choice){
               promise4.complete (choice) ; 
            }
        }) ; 
        future4.onFailure(new Handler<Throwable>(){
            @Override 
            public void handle(Throwable Error){
                promise4.fail(Error) ; 
            }
        }) ;
        return null ; 
    }

    public Future<int[][]> SolvePuzzle(Puzzle puzzle){
        int[][ ] arrays = puzzle.deriveCores() ; 
        Promise<int[][]> pending =  Promise.promise( )  ;
        Future<int[][]> versions =  TaskScheduler.SolvePuzzle(arrays) ;  
        versions.onFailure(
            new Handler<Throwable>( ){
                @Override 
                public void handle(Throwable Exception){
                    pending.fail(Exception) ;
                }
            }
        ).onSuccess(new Handler<int[][]>( ){
            @Override 
            public void handle(int[][] source){
                 pending.complete(source) ; 
            }
        }) ;  
        Future<int[][]> planning = pending.future() ; 
         return planning   ;
    }

    public Future<int[][]> GeneratePuzzle(int spans ,int slots ){
        Promise<int[][ ]> promise4 = Promise.promise() ;  
        Future<int[][]> possible = TaskScheduler.GeneratePuzzle(spans,slots) ; 
        possible.onComplete(
        new Handler<AsyncResult<int[][]>>(){
            @Override 
            public void handle(AsyncResult<int[][]> pending){
                if( !pending.succeeded ()){
                    promise4.fail(pending.cause()) ; 
                    return  ; 
                }
                promise4.complete(pending.result( )) ; 
            }
        }
        ) ;
        return promise4.future() ; 
    }

}
