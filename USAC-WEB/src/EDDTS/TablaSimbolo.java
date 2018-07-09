/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EDDTS;

import java.util.ArrayList;

/**
 *
 * @author danielvega
 */
public class TablaSimbolo {
    private ArrayList<Simbolo> Simbolos;
    
    public TablaSimbolo(){
        this.Simbolos=new ArrayList<>();
    }
    
    public void nuevosimbolo(int f,int c, String n, boolean IA, Object val, String t){
        if(getsym(n)==null){
            this.getSimbolos().add(new Simbolo(n, f, c, IA, val,t));
        }else{
            
        }
    }
    
    public void nuevosimbolo(int f,int c, String n, boolean IA, Object val, String t, int tam){
        if(getsym(n)==null){
            this.getSimbolos().add(new Simbolo(n, f, c, IA, val,t,tam));
        }else{
            
        }
    }
    
    public Simbolo getsym(String nombre){
      for(int i = 0; i<this.getSimbolos().size();i++){
          Simbolo aux = this.getSimbolos().get(i);
          if(aux.getNombre().equals(nombre)){
              return aux;
          }
      }
      return null;
  }

    /**
     * @return the Simbolos
     */
    public ArrayList<Simbolo> getSimbolos() {
        return Simbolos;
    }

    /**
     * @param Simbolos the Simbolos to set
     */
    public void setSimbolos(ArrayList<Simbolo> Simbolos) {
        this.Simbolos = Simbolos;
    }
    
}
