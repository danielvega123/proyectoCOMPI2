/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import javax.swing.JPanel;

/**
 *
 * @author danielvega
 */
public class ListaPestanas {
    Pagina sig;
    Pagina ant;
    String titulo;
    JPanel HTML;
    String ruta;
    
    public ListaPestanas(String t, JPanel pag, String ruta){
        this.titulo=t;
        this.HTML=pag;
        this.ruta=ruta;
        this.sig=null;
        this.ant=null;
    }
    
    
}
