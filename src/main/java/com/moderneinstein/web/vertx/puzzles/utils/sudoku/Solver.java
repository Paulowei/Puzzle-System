package com.moderneinstein.web.vertx.puzzles.utils.sudoku;

import java.util.Arrays ;
import java.util.Vector ; 

public class Solver {
    

    public static boolean emplace(int  pointX,int pointY,int rangeX,int rangeY,int digit,int[][] source){  
        int roots =  (int)Math.pow(rangeX, 0.5) ; 
        int[] lines = new int[]{pointX-pointX%roots ,pointY-pointY%roots } ; 
        for(int dy=rangeY-1;dy>=0;dy-- ){  
            if(source[dy][pointX]==digit){
                return false ;
            }
        } 
        for(int dx=rangeX-1 ;dx>=0;dx-- ){
            if(source[pointY][dx]==digit){
                return false ;
            }
        } 
        for(int dy=0;dy<roots;dy++){
            for(int  dx=0;dx<roots;dx++){
                if(source[pointY+dy][pointX+dx]==digit){
                    return false ; 
                }
            }
        }
        return true ; 
    }

    public static boolean backtrack(int phase,Vector<int[]> points, int[ ][] source){
        if(phase>= points .size ()){return true ; } 
        int[] lines =  points.get(phase) ;
        int prevs = source[lines[1]][lines[0]] ;
        int range = source.length ;
        for( int  cs=range ;cs>=0;cs--){
            if(emplace(lines[0],lines[1],range,range,cs,source)){
                source[lines[1]][lines[0]] = cs ;
                boolean choice = backtrack(phase+1,points,source) ; 
                if(choice==true){return true ; }
                source[lines[1]][lines[ 0 ]] = prevs ; 
            }
        } 
        return false ; 
    }
    public static boolean solve(int[][] source){
        int rangeX = source[0 ].length ; 
        int rangeY = source.length ;
        Vector<int[]> points = new Vector<int[]>( ) ;
        for(int  cs=0;cs<rangeY; cs++){
            for(int ce=0;ce<rangeX; ce++){
                if(source[cs][ce]== 0){
                    int[] temps = new int[]{ce, cs} ; 
                    points.add(temps) ; 
                }
            }
        }
        boolean cases = backtrack(0,points,source) ;  
        return cases ; 
    }
}
