/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usac.web;

import Interfaz.Inicio;
import Interfaz.PantallaInicio;

/**
 *
 * @author danielvega
 */
public class USACWEB {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
      /*  Interfaz.Inicio ini = new Inicio();
        ini.setVisible(true);*/
        
       Interfaz.PantallaInicio ini1= PantallaInicio.getInstancia();
        ini1.setVisible(true);
    }
    
}
