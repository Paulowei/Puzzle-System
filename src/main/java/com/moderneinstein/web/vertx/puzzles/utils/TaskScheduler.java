package com.moderneinstein.web.vertx.puzzles.utils ; 
 
import java.util.Arrays ; 
import java.util.Vector  ;

import io.vertx.core.Handler ; 
import io.vertx.core.AsyncResult ; 
import io.vertx.core.Future ; 
import io.vertx.core.Promise ; 

import  com.moderneinstein.web.vertx.puzzles.utils.sudoku.Validator ; 
import  com.moderneinstein.web.vertx.puzzles.utils.sudoku.Solver ; 
import com.moderneinstein.web.vertx.puzzles.utils.sudoku.Generator ; 

public class TaskScheduler { 
     public static Future<Boolean> ValidatePuzzle(int[][] source){  
        Promise<Boolean> promise5 =  Promise.promise() ;  
        Thread  context = new Thread(){
            @Override 
            public void run(){   
              boolean cases = false ;
                try{
                   cases = Validator.validate (source)  ;
                  promise5.complete(cases) ;    
                  }catch(Exception except){
                    promise5.fail(except) ; 
                  }  
                  promise5.complete(cases) ; 
            }
        } ; 
        context.start() ;   
        return  promise5.future() ; 
     }  
     public static Future<int[][]> SolvePuzzle( int[][] source){
      Promise<int[][]> promise4 =  Promise.promise() ;  
      int range = source.length ; 
      Thread  version =  new Thread(){
        @Override 
        public void run( ){ 
            int[][] replica = new int[range][range] ;
            for(int  fs=0;fs<source.length;fs++){
              replica[fs]  =Arrays.copyOf(source[fs],range) ; 
            } 
            try{
            Solver.solve(replica) ; 
            }catch (Exception Error){
              promise4.fail( Error) ;
            }    
          promise4.complete(replica) ; 
        }
      } ; 
      Future<int[][]> planning = promise4.future( ) ; 
      return  planning ; 
     }   
     public static Future<int[][]> GeneratePuzzle(int range,int spots){
    //  int spans =source.length ; 
      Promise<int[][]> premise =  Promise.promise() ;  
      Thread frames = new Thread(){
        @Override 
        public void run(){  
          int[][] lines  = null ; 
          try{
           lines = Generator.generate(range,spots) ;   
        }catch(Exception Error){
            premise.fail(Error) ;  
            return  ; 
        }  
        premise.complete(lines) ; 
        } 
      } ; 
      Future<int[][]>  after = premise.future() ;
      return after  ; 
     }
}
