package  com.moderneinstein.web.vertx.puzzles.models ;  

import java.util.Arrays ; 
import java.util.Vector ; 


public class FCUser {

    private String email ; 
    private String username ; 
    private String identity ;  
    private String links ; 

    public FCUser(String param1,String param2){
        email = new String(param1) ; 
        username = new String(param2) ; 
        identity = new String ("") ;  
        links = new String("") ; 
    } 
    public FCUser(String param1){
        email = new String (param1) ;
        username = new String("") ; 
        identity = new String("") ; 
        links = new String("" ) ;
    } 
    
    public void setUsername(String temps ){
        this.username = new String(temps) ;  
    }  
    public String deriveUsername(){
        String clone = new String(this.username) ; 
        return clone ; 
    } 
    public void  setEmail(String value ){
        this.email = new String( value) ; 
    } 
    public String deriveEmail(){
        String twins =  new String (this.email) ; 
        return twins ; 
    }  
    public String deriveIdentity(){
        String clone = new String(this.identity) ;
        return clone ; 
    }  
     public void setIdentity(String input){
        this.identity = new String(input) ; 
     }
}