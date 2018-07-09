/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EDDTS;

import EDD.CSJ.nodo;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author danielvega
 */
public class TablaAmbitos {

    private HashMap<String, TablaGeneral> tabla;

    public TablaAmbitos() {
        this.tabla = new HashMap();
    }

    public void AgregarNuevoMetodo(String nombre, nodo ins) {
        if (getTablaActual(nombre) == null) {
            TablaGeneral gen = new TablaGeneral();
            gen.setInst(ins);
            gen.aumentarAmbito();
            this.tabla.put(nombre, gen);
        }
    }
    
    public void QuitarMetodo(String nomber){        
        if(this.tabla.containsKey(nomber)){
            this.tabla.remove(nomber);
        }        
    }

    public TablaGeneral getTablaActual(String n) {
        if (this.tabla.containsKey(n)) {
            return this.tabla.get(n);
        }
        return null;
    }
    
    

}
