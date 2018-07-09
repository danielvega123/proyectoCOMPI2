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
public class Propiedades {
    private ArrayList<Object> propiedades;
    
    public Propiedades(){
        this.propiedades=new ArrayList<>();
    }

    /**
     * @return the propiedades
     */
    public ArrayList<Object> getPropiedades() {
        return propiedades;
    }

    /**
     * @param propiedades the propiedades to set
     */
    public void setPropiedades(ArrayList<Object> propiedades) {
        this.propiedades = propiedades;
    }
    
    
}
