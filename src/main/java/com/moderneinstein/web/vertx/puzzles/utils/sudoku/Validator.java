package com.moderneinstein.web.vertx.puzzles.utils.sudoku;

import java.util.Arrays ; 
import java.util.Vector  ;
import java.util.Set ; 
import java.util.TreeSet ; 
import java.util.HashSet  ; 

public class Validator {
    
    
    public static boolean CheckSquares(int spansX,int spansY,int[][] source){
        int roots =  (int)Math.pow(spansY,0.5) ; 
        for(int dy=0;dy<spansY;dy+=roots ){
            for(int dx=0 ;dx<spansX;dx+=roots){
               boolean[] visited =new boolean[spansY] ;
               Set<Integer> setty = new TreeSet<Integer>() ; 
            for( int  cy=0;cy<roots;cy++){
                for(int cx=0;cx<roots;cx++){
                    int data =  source[dy+cy][dx+cx] ;  
                    if(data==0){continue ;  }
                    if(setty.contains(data)){return false ; } 
                    setty.add(data) ; 
                }
            }
            }
        } 
        return  true ; 
    }

    public static boolean  validate(int[][] source){
        int  rangeX =  source[0].length ;  
        int rangeY = source.length ; 
        for( int cy=rangeY-1;cy>=0;cy--){
            Set<Integer> kami = new HashSet<Integer>() ;
            for(int  cx=rangeX-1;cx>=0;cx-- ){  
                if(source[cy][cx]==0){continue  ; }
                if(kami.contains(source[cy][cx])){return false ; } 
                kami.add(source[cy][cx]); 
            } 
            kami.clear() ;
            for(int  cx=rangeX-1;cx>=0;cx-- ){  
                if(source[cx][cy]==0 ){continue ;  }
                if(kami.contains(source[cx][cy])){return false ; }  
                kami.add(source[cx][cy]) ; 
            }
        }
        boolean temps = CheckSquares(rangeX,rangeY,source) ;
        return  temps&& true ; 
    }  
    public static boolean validate(char[][] source){
        int rangeY =  source.length ; 
        int rangeX =  source[0].length ; 
        int[][] nested =new int[rangeY][rangeX] ;  
        for( int ce=0;ce<rangeX;ce++){
            for(int  dy=rangeY-1;dy>=0;dy--){  
                int  number  =  (int)(source[dy][ce]-48) ; 
                nested[dy][ce] =  number ; 
            }
        }   
        boolean choices =   validate(nested) ;  
        return choices  ; 
     }
}
