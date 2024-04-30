package com.moderneinstein.web.vertx.puzzles.utils;

import io.vertx.core.buffer.Buffer ; 
import io.vertx.core.json.JsonArray ; 
import io.vertx.core.json.JsonObject ; 

import java.util.TreeMap ; 
import java.util.Arrays ;
import java.util.Vector ; 
import java.util.Properties ; 

import  com.moderneinstein.web.vertx.puzzles.utils.PropertiesSource ;
import com.moderneinstein.web.vertx.puzzles.models.Puzzle ; 
import com.moderneinstein.web.vertx.puzzles.models.FCUser ; 

public class Serialisers {
    
    public static Properties properties = PropertiesSource.deriveProperties ( ) ; 

    
    public static JsonArray SerialiseArray(int[][] source){
        int spansX =  source[0].length ;
        int spansY = source.length ;
        JsonArray parent = new JsonArray() ; 
        for(int dy=0;dy<spansY;dy++){
            JsonArray phase = new JsonArray()  ; 
            for(int dx=0;dx<spansX;dx++ ){
               phase.add (source[dy] [dx]) ; 
            } 
            parent.add(phase) ; 
        }
        return null ; 
    }    
    public static int[][] DeserialiseArray(JsonArray source){  
        int  height = source.size() ; 
        Vector<int[]> verve =  new Vector<int[]>(); 
        for( int dy =0;dy<height;dy++){
            JsonArray temps = source.getJsonArray(dy) ; 
            int[] terms = new int[temps.size( )] ; 
            for( int dx=0;dx<temps.size();dx++ ){
                terms[dx] =  temps.getInteger(dx) ;
            }  
            verve.add (terms)   ;  
        } 
        int breadth =  verve.get(0).length ;  
        int[][] buffer = verve.toArray (new int[verve.size()][breadth]) ;
         return  buffer  ;   //   null   ; 
    }
    public static JsonObject  SerialisePuzzle(Puzzle source){
        JsonObject created = new JsonObject() ; 
        created.put("identity",source.deriveIdentity()) ; 
        int[][] nested = source.deriveCores() ; 
        JsonArray temps = SerialiseArray(nested )  ;  
        created.put("puzzle",temps)  ;  
        return  created  ; 
    }   
    public static Puzzle DeserialisePuzzle(JsonObject source){
        Puzzle puzzle = new Puzzle()  ; 
        puzzle.emplaceIdentity(source.getString("identity",new String( ))) ; 
        JsonArray arrays = source.getJsonArray("puzzle") ; 
         int[][] crest = Serialisers.DeserialiseArray(arrays) ;  
        puzzle.emplaceCores (crest) ;  
        return puzzle ;   // null ; 
    }  

    public static JsonObject SerialiseUser(FCUser source){
        JsonObject created = new JsonObject() ; 
        created.put ("email",source.deriveEmail()) ; 
        created.put ("identity",source.deriveIdentity()) ;  
        created.put("username",source.deriveUsername()) ; 
        return created; 
    }  
    public static FCUser DeserialiseUser(JsonObject source){
         FCUser users = new FCUser(source.getString("email")) ; 
        users.setIdentity( source.getString("identity")) ; 
        users.setUsername(source.getString("username")) ;
         return users ; 
    }
}
