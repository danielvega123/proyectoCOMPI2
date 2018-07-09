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
public class Eventos {
    private ArrayList<String> click;
    private ArrayList<String> modi;
    private ArrayList<String> listo;
    private String ruta;
    public Eventos(){
        this.click=new ArrayList<>();
        this.modi=new ArrayList<>();
        this.listo=new ArrayList<>();
        this.ruta="";
    }
    
    public void nuevoEvento(String name, String t){
        switch(t.toLowerCase()){
            case "c":
                this.getClick().add(name);
                break;
            case "m":
                this.getModi().add(name);
                break;
            case "l":
                this.getListo().add(name);
                break;
        }
    }    
    
    public void setRuta(String r){
        this.ruta=r;
    }
    
    public String getRuta(){
        return this.ruta;
    }

    /**
     * @return the click
     */
    public ArrayList<String> getClick() {
        return click;
    }

    /**
     * @param click the click to set
     */
    public void setClick(ArrayList<String> click) {
        this.click = click;
    }

    /**
     * @return the modi
     */
    public ArrayList<String> getModi() {
        return modi;
    }

    /**
     * @param modi the modi to set
     */
    public void setModi(ArrayList<String> modi) {
        this.modi = modi;
    }

    /**
     * @return the listo
     */
    public ArrayList<String> getListo() {
        return listo;
    }

    /**
     * @param listo the listo to set
     */
    public void setListo(ArrayList<String> listo) {
        this.listo = listo;
    }
    
    
}
