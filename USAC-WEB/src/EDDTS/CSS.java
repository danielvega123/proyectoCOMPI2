/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EDDTS;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author danielvega
 */
public class CSS {
    private HashMap<String, Propiedades> prop;
    
    public CSS(){
        this.prop=new HashMap<>();        
    }
    
    public void insertarNuevo(String n){
        if(!prop.containsKey(n)){
            prop.put(n, new Propiedades());
        }
    }
    
    public ArrayList<Object> getPropiedad(String n){
        Propiedades aux = prop.get(n);
        if(aux!=null){
            return aux.getPropiedades();
        }
        return null;
    }
 
    public HashMap<String, Propiedades> getTabla(){
        return this.prop;
    }
    
    
    
    
}
