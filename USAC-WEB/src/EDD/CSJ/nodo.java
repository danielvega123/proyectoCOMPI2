/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EDD.CSJ;

import java.util.ArrayList;

/**
 *
 * @author danielvega
 */
public class nodo {
    //VALORES DEL NODO
    private String nombre;
    private Object valor;
    private int fila;
    private int col;
    private boolean Array;
    
    //INSTRUCCIONES SI EN DADO CASO ES UN METODO
   
    private ArrayList<nodo> hijos;
    
    //SI ES UNA FUNCION SE AGREGA EL PUNTERO DE INSTRUCCIONES
    public nodo(){}

    public nodo(String n, int f, int c, boolean IA, Object val) {
        this.nombre=n;
        this.col=c;
        this.fila=f;
        this.Array= IA;
        this.valor=val;
    }
    
    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the valor
     */
    public Object getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(Object valor) {
        this.valor = valor;
    }

    /**
     * @return the fila
     */
    public int getFila() {
        return fila;
    }

    /**
     * @param fila the fila to set
     */
    public void setFila(int fila) {
        this.fila = fila;
    }

    /**
     * @return the col
     */
    public int getCol() {
        return col;
    }

    /**
     * @param col the col to set
     */
    public void setCol(int col) {
        this.col = col;
    }

     /**
     * @param IsArray the IsArray to set
     */
    public void setIsArray(boolean IsArray) {
        this.setArray(IsArray);
    }

    /**
     * @return the Array
     */
    public boolean isArray() {
        return Array;
    }

    /**
     * @param Array the Array to set
     */
    public void setArray(boolean Array) {
        this.Array = Array;
    }

    /**
     * @return the hijos
     */
    public ArrayList<nodo> getHijos() {
        return hijos;
    }

    /**
     * @param hijos the hijos to set
     */
    public void setHijos(ArrayList<nodo> hijos) {
        this.hijos = hijos;
    }
   
    
}
