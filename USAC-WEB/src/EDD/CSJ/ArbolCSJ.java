/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EDD.CSJ;

/**
 *
 * @author danielvega
 */
public class ArbolCSJ {

    private nodo raiz = null;

    public ArbolCSJ() {
        this.raiz = new nodo();
        this.raiz.setNombre(("INI"));
    }

    public nodo getRaiz() {
        return this.raiz;
    }
}
