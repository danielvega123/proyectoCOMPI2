/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EDDTS;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author danielvega
 */
public class ListaCSS {
    private HashMap<String,CSS> lcss;
    
    
    public ListaCSS(){
        this.lcss=new HashMap<>();
    }
    
    public boolean insertarHijo(String n){
        if(existeNombre(n)==null){
            lcss.put(n, new CSS());
        }
        return false;
    }
    
    public CSS existeNombre(String n){
        return this.lcss.get(n);
    }
    
    public CSS existeNombre(String n, int v){
        for (Map.Entry k : this.lcss.entrySet()) {
            CSS cs = (CSS)k.getValue();
            if(cs!=null){
                if(k.getKey().equals(n))
                    return cs;
            }
        }
        return null;
    }
}
