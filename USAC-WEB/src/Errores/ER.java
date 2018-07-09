/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Errores;

/**
 *
 * @author danielvega
 */
public class ER {
    private int fila;
    private int columna;
    private String lexema;
    private String tipoError;
    private String informacion;
    
    public ER(int f, int c, String lex, int tipo, String info){
        this.fila=f;
        this.columna=c;
        this.lexema=lex;
        this.tipoError=getTipoString(tipo);
        this.informacion=info;
    }
    
    
    private String getTipoString(int error){
        if(error==1){
            return "lexico";
        }else if(error==2){
            return "sintactico";
        }else if(error==3){
            return "semantico";
        }
        return "";
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
     * @return the columna
     */
    public int getColumna() {
        return columna;
    }

    /**
     * @param columna the columna to set
     */
    public void setColumna(int columna) {
        this.columna = columna;
    }

    /**
     * @return the lexema
     */
    public String getLexema() {
        return lexema;
    }

    /**
     * @param lexema the lexema to set
     */
    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    /**
     * @return the tipoError
     */
    public String getTipoError() {
        return tipoError;
    }

    /**
     * @param tipoError the tipoError to set
     */
    public void setTipoError(String tipoError) {
        this.tipoError = tipoError;
    }

    /**
     * @return the informacion
     */
    public String getInformacion() {
        return informacion;
    }

    /**
     * @param informacion the informacion to set
     */
    public void setInformacion(String informacion) {
        this.informacion = informacion;
    }
    
    
}
