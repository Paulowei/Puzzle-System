package com.moderneinstein.web.vertx.puzzles.models;

import java.util.Arrays ; 
import java.util.Vector ; 
import java.util.Set ; 




public class Puzzle {
    private String identity ;
    private int[][]  cores ; 
    public static final int DEFAULT_WIDTH = 3 ; 
    public static final int DEFAULT_HEIGHT = 3 ;
    public int[][] replicate(int rangeX,int rangeY,int[][] source){
        int[][] lines = new int[source.length][source[0].length ] ; 
        for(int fy=0;fy<rangeY;fy++){
            for(int fx=rangeX-1;fx>=0;fx-- ){
                lines[fy][fx] =  source[fy][fx] ;
            }
        } 
        return lines; 
    }
    public Puzzle(int[][] nested){
   //     int[][] nested = new int[nested.length][nested[0].length] ; 
     //   for(int cs=)  
        cores = replicate (nested[0].length,nested.length,nested) ; 
        identity = new String ("") ; 
    }  
    public Puzzle(){
       identity = new String ("")  ;  
        cores = new int[DEFAULT_HEIGHT][DEFAULT_WIDTH] ;
    } 
    public Puzzle(Puzzle other){
   //     cores = replicate(null) ;      
        identity = new String("") ; 
    }  
    public int[][] deriveCores (){
        int[][] lines = replicate(cores[0].length,cores.length,cores) ;  
          return  lines  ; 
    }  
    public  String deriveIdentity(){
         String patterns = new String(identity) ; 
        return patterns  ; 
    }  
    public  void emplaceCores(int[][] source){
        int[][]  crest = replicate(source[0].length,source.length,source) ; 
        this.cores = crest   ; 
    }  
    public  void emplaceIdentity(String sample){
        String verse =  new String (sample) ; 
        this. identity =  verse ; 
    }  
}
