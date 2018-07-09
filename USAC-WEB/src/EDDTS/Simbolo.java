/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EDDTS;
import EDD.CSJ.*;

/**
 *
 * @author danielvega
 */
public class Simbolo {
    private String nombre;
    private Object valor;
    private int fila;
    private int col;
    private boolean Array;
    private String tipo;
    private int tamvector;
    
    public Simbolo(String n, int f,int c, boolean IA, Object v,String t){
        this.fila=f;
        this.col=c;
        this.nombre=n;
        this.Array=IA;
        this.valor=v;
        this.tipo=t;
        this.tamvector=-1;
    }

    public Simbolo(String n, int f,int c, boolean IA, Object v,String t, int tam){
        this.fila=f;
        this.col=c;
        this.nombre=n;
        this.Array=IA;
        this.valor=v;
        this.tipo=t;
        this.tamvector=tam;
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
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the tamvector
     */
    public int getTamvector() {
        return tamvector;
    }

    /**
     * @param tamvector the tamvector to set
     */
    public void setTamvector(int tamvector) {
        this.tamvector = tamvector;
    }
    
    
}
