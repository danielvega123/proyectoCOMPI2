/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EDDTS;

import EDD.CSJ.*;
import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author danielvega
 */
public class TablaGeneral {

    //PILA DE AMBITOS
    private Stack<TablaSimbolo> pila;
    private nodo inst;

    public TablaGeneral(){
        this.pila= new Stack<>();
    }
    /**
     * @return the pila
     */
    public Stack<TablaSimbolo> getPila() {
        return pila;
    }

    /**
     * @param pila the pila to set
     */
    public void setPila(Stack<TablaSimbolo> pila) {
        this.pila = pila;
    }


    public TablaSimbolo aumentarAmbito() {
        if (pila.isEmpty() == false) {
           // TablaSimbolo actual = this.pila.firstElement();
            TablaSimbolo ns = new TablaSimbolo();
            /*for(int i = 0; i < actual.getSimbolos().size();i++){
                Simbolo s = actual.getSimbolos().get(i);
                ns.getSimbolos().add(new Simbolo(s.getNombre(), s.getFila(), s.getCol(), s.isArray(), s.getValor(), s.getTipo(), s.getTamvector()));
                
            }*/
            //pila.add(0, ns);
            pila.push(ns);
        } else {
            pila.add(new TablaSimbolo());
        }
        return pila.firstElement();
    }
    
    public TablaSimbolo disminuirAmbito(){
        pila.pop();
        return pila.firstElement();
    }
    
    public void nuevavariable(String n, Object val, int f, int c, boolean arr, String t) {
        if (existevariable(n) == null) {
            this.pila.peek().nuevosimbolo(f,c,n,arr,val,t);
        } else {
            //System.out.println("La variable " + n + " ya existe");
        }
    }
    
    public void nuevavariable(String n, Object val, int f, int c, boolean arr, String t, int ta) {
        if (existevariable(n) == null) {
            this.pila.peek().nuevosimbolo(f,c,n,arr,val,t,ta);
        } else {
            //System.out.println("La variable " + n + " ya existe");
        }
    }
    
    public boolean eliminarVariable(String nombre){
        Object[] obj = this.pila.toArray();
        for (int i = 0; i < obj.length; i++) {
            TablaSimbolo tab = (TablaSimbolo) obj[i];
            for (int j = 0; j < tab.getSimbolos().size(); j++) {
                Simbolo aux = tab.getSimbolos().get(j);
                if (aux.getNombre().equals(nombre)) {
                    tab.getSimbolos().remove(aux);
                    return true;
                }
            }
        }
        return false;
    }

    public Simbolo existevariable(String nombre) {
        //return this.pila.firstElement().getsym(nombre);
        Object[] obj = this.pila.toArray();
        for (int i = 0; i < obj.length; i++) {
            TablaSimbolo tab = (TablaSimbolo) obj[i];
            for (int j = 0; j < tab.getSimbolos().size(); j++) {
                Simbolo aux = tab.getSimbolos().get(j);
                if (aux.getNombre().equals(nombre)) {
                    return aux;
                }
            }
        }
        return null;
    }

    /**
     * @return the inst
     */
    public nodo getInst() {
        return inst;
    }

    /**
     * @param inst the inst to set
     */
    public void setInst(nodo inst) {
        this.inst = inst;
    }
}
