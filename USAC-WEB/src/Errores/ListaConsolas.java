/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Errores;

import java.util.ArrayList;

/**
 *
 * @author danielvega
 */
public class ListaConsolas {
    private static ArrayList<ER> lista;
    
    public ListaConsolas(){
          lista = new ArrayList<>();
    }

    
    /**
     * @param f
     * @param c
     * @param lex
     * @param tipo
     * @param desc
     */
    public void  agregarerror(int f, int c, String lex, int tipo, String desc) {
        lista.add(new ER(f,c,lex,tipo,desc));
    }   
    
    public void  agregarerror(ER er) {
        lista.add(er);
    }
    
    public ArrayList<ER> getErrores(){
        return lista;
    }
    public void recorrerErrores(){
        for(int i =0;i<lista.size();i++){
            ER error = lista.get(i);
            System.out.println(error.getColumna() + "-"+error.getFila()+"-"+error.getInformacion()+"-"+error.getTipoError()+
                        "-"+error.getLexema());
        }
    }
    
    
}
