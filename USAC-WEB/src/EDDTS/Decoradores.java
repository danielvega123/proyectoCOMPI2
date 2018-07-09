/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EDDTS;

import java.util.HashMap;

/**
 *
 * @author danielvega
 */
public class Decoradores {
    HashMap<String,ListaCSS> css;
    
    public Decoradores(){
        this.css=new HashMap<>();
        
    }
    
    public ListaCSS obtenersubconjunto(String n){
        return css.get(n);
    }
    
    public boolean insertarNuevo(String n){
        if(this.css.containsKey(n)){
            return false;
        }
        /*ListaCSS aux = new ListaCSS();
        aux.insertarHijo("ID");
        aux.insertarHijo("GRUPO");*/
        
        css.put(n, new ListaCSS());
        return true;
    }
}
