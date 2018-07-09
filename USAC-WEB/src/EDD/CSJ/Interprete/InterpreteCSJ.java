/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EDD.CSJ.Interprete;

import EDD.CSJ.nodo;
import EDDTS.*;
import Errores.ER;
import Errores.ListaConsolas;
import Errores.ListaErrores;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *
 * @author danielvega
 */
public class InterpreteCSJ {

    //private final HashMap<String, TablaAmbitos> ALS;
    private TablaAmbitos ambitos;
    public ListaErrores errores;
    private boolean retorno = false, detener = false;
    private HashMap<String, Eventos> events;
    public boolean actuar = false;
    public ListaConsolas consola;
    public InterpreteCSJ() {
        this.ambitos = new TablaAmbitos();
        this.ambitos.AgregarNuevoMetodo("global", null);
        consola = new ListaConsolas();
        /*this.ambitos.AgregarNuevoMetodo("$click", null);
        this.ambitos.AgregarNuevoMetodo("$modificado", null);
        this.ambitos.AgregarNuevoMetodo("$listo", null);*/
        //this.ambitos.getTablaActual("global").aumentarAmbito();
        this.errores = ListaErrores.getListaerror();
    }

    public TablaAmbitos getAmbitos() {
        return this.ambitos;
    }

    public void setTablaAmbitos(TablaAmbitos amb) {
        this.ambitos = amb;
    }

    public void setEventos(HashMap<String, Eventos> ev) {
        this.events = ev;
    }

    private void agregarNuevoTableEvento(String n) {
        if (!this.events.containsKey(n)) {
            this.events.put(n, new Eventos());
        }
    }

    private void nuevoEventoObjeto(String id, String metodo, String t) {
        Eventos evt = this.events.get(id);
        if (evt != null) {
            switch (t.toLowerCase()) {
                case "c":
                    evt.getClick().add(metodo);
                    break;
                case "l":
                    evt.getListo().add(metodo);
                    break;
                case "m":
                    evt.getModi().add(metodo);
                    break;
                default:
                    evt.setRuta(metodo);
                    break;
            }
        }
    }

    /**
     * *******************************************************************************************************************
     **********************************************************************************************************************
     *******************VERIFICAR*********************************************************************************************
     * *************************ERRORES***************************************************************************************
     * ********************************DE***********************************************************************************
     * ************************************PRECOMPILACION*************************************************************************
     * ********************************************************************************************************************
     * @param raiz
     * @param ambito
     */
    public void verificarerroresPrecompilacion(nodo raiz, String ambito) {
        if (raiz != null) {
            if (raiz.getNombre().equals("INI")) {
                for (int i = 0; i < raiz.getHijos().size(); i++) {
                    nodo hijo = raiz.getHijos().get(i);
                    switch (hijo.getNombre()) {
                        case "metodo":
                            int instru = hijo.getHijos().size();
                            verificarInstruccions(hijo.getHijos().get(instru - 1));
                            contador = 0;
                            error = false;
                            /*if(error==true){
                                
                            }*/
                            break;
                    }
                }
            }
        }
    }

    private int contador = 0;
    private boolean ret = false;
    private boolean error = false;

    /*private void verificarDetener(nodo raiz) {
        if (raiz != null) {
            if (raiz.getNombre().equals("listainstrucciones")) {
                for (int i = 0; i < raiz.getHijos().size() && error==false; i++) {
                    nodo hijo = raiz.getHijos().get(i);
                    switch (hijo.getNombre()) {
                        case "if":
                            verificarIF(hijo);
                            break;
                        case "select":
                            verificarSELECT(hijo);
                            break;
                        case "for":
                            contador++;
                            verificarFOR(hijo);
                            contador--;
                            break;
                        case "while":
                            contador++;
                            verificarWhile(hijo);
                            contador--;
                            break;
                        case "detener":
                            if (contador <= 0) {
                                error = true;
                            } else {
                                error = false;
                            }
                            break;
                    }

                }

            }
        }
    }*/
    
   
    private void verificarInstruccions(nodo raiz) {
        if (raiz != null) {
            if (raiz.getNombre().equals("listainstrucciones")) {
                for (int i = 0; i < raiz.getHijos().size() && error == false; i++) {
                    nodo hijo = raiz.getHijos().get(i);
                    switch (hijo.getNombre()) {
                        case "if":
                            verificarIF(hijo);
                            /*if (ret == false) {
                                error = true;
                                errores.agregarerror(new ER(hijo.getFila(), hijo.getCol(), hijo.getNombre(), 3,
                                        "Esta sentencia no contiene un valor de retorno"));
                            }*/
                            break;
                        case "select":
                            contador++;
                            verificarSELECT(hijo);
                            /*if (ret == false) {
                                error = true;
                                errores.agregarerror(new ER(hijo.getFila(), hijo.getCol(), hijo.getNombre(), 3,
                                        "Esta sentencia no contiene un valor de retorno"));
                            }*/
                            contador--;
                            break;
                        case "for":
                            contador++;
                            verificarFOR(hijo);
                            /*if (ret == false) {
                                error = true;
                                errores.agregarerror(new ER(hijo.getFila(), hijo.getCol(), hijo.getNombre(), 3,
                                        "Esta sentencia no contiene un valor de retorno"));
                            }*/
                            contador--;
                            break;
                        case "while":
                            contador++;
                            verificarWhile(hijo);
                            /*if (ret == false) {
                                error = true;
                                errores.agregarerror(new ER(hijo.getFila(), hijo.getCol(), hijo.getNombre(), 3,
                                        "Esta sentencia no contiene un valor de retorno"));
                            }*/
                            contador--;
                            break;
                        case "retorno":
                            ret = true;
                            break;
                        case "detener":
                            if (contador <= 0) {
                                error = true;
                                errores.agregarerror(new ER(hijo.getFila(), hijo.getCol(), hijo.getNombre(), 3,
                                        "No puede venir un valor detener afuera de una sentencia FOR, WHILE, SWITCH"));
                            } else {
                                error = false;
                            }
                            break;
                    }

                }

            }
        }
    }

    private void verificarWhile(nodo raiz) {
        if (raiz != null) {
            int hijos = raiz.getHijos().size();
            if (hijos == 2) {
                nodo instru = raiz.getHijos().get(hijos - 1);
                verificarInstruccions(instru);
            }
        }
    }

    private void verificarFOR(nodo raiz) {
        if (raiz != null) {
            int hijos = raiz.getHijos().size();
            if (hijos == 4) {
                nodo instru = raiz.getHijos().get(hijos - 1);
                verificarInstruccions(instru);
            }
        }
    }

    private void verificarSELECT(nodo raiz) {
        if (raiz != null) {
            int hijos = raiz.getHijos().size();
            if (hijos == 2) {
                nodo listacasos = raiz.getHijos().get(1);
                for (int i = 0; i < listacasos.getHijos().size(); i++) {
                    nodo caso = listacasos.getHijos().get(i);
                    verificarInstruccions(caso.getHijos().get(1));
                }
            }

        }
    }

    private void verificarIF(nodo raiz) {
        if (raiz != null) {
            int hijos = raiz.getHijos().size();
            if (hijos == 2) {
                verificarInstruccions(raiz.getHijos().get(1));
            } else if (hijos == 3) {
                verificarInstruccions(raiz.getHijos().get(1));
                nodo sino = raiz.getHijos().get(2);
                verificarInstruccions(sino.getHijos().get(0));
            }
        }
    }

    /**
     * *******************************************************************************************************************
     **********************************************************************************************************************
     *******************LLENAR*********************************************************************************************
     * *************************TABLA***************************************************************************************
     * ********************************DE***********************************************************************************
     * ************************************SIMBOLOS*************************************************************************
     * ********************************************************************************************************************
     * @param raiz
     * @param ambito
     */
    public void llenarMetodos(nodo raiz, String ambito) {
        if (raiz != null) {
            if (raiz.getNombre().equals("INI")) {
                for (int i = 0; i < raiz.getHijos().size(); i++) {
                    nodo hijo = raiz.getHijos().get(i);
                    switch (hijo.getNombre()) {
                        case "metodo":
                            System.out.println("se declara un metodo");
                            declararMetodo(hijo, ambito);
                            break;
                    }
                }
            }
        }
    }

    public Object llenarTablaSimbolos(nodo raiz, String ambito) {
        Object val = null;
        if (raiz != null) {
            if (raiz.getNombre().equals("INI")) {
                for (int i = 0; i < raiz.getHijos().size(); i++) {
                    nodo hijo = raiz.getHijos().get(i);
                    switch (hijo.getNombre()) {
                        case "decvar":
                            System.out.println("se declara variable " + ambito);
                            declararvariable(hijo, ambito);
                            break;
                        /*case "metodo":
                            System.out.println("se declara un metodo");
                            declararMetodo(hijo, ambito);
                            break;*/
                        case "observador_doc":
                            observadorDocumento(hijo, ambito);
                            break;
                        case "asignar":
                            asignarValor(hijo, ambito);
                            break;
                        case "imprimir":
                            ejecutarImprimir(hijo, ambito);
                            break;
                        case "if":
                            val = ejecutarIF(hijo, ambito);
                            break;
                        case "for":
                            val = EjecutarFor(hijo, ambito);
                            detener = false;
                            break;
                        case "while":
                            val = EjecutarWhile(hijo, ambito);
                            detener = false;
                            break;
                        case "select":
                            val = EjecutarSwitch(hijo, ambito);
                            detener = false;
                            break;
                        case "obtener_doc":
                            ejecutarDocumento(hijo, ambito);
                            break;
                        case "mensaje":
                            mostrarMensaje(hijo, ambito);
                            break;
                        case "observador_id":
                            observadorID(hijo, ambito);
                            break;
                        case "llamada":
                            val = ejecutarLLamada(hijo, ambito);
                            break;
                    }
                }
            }
        }
        return val;
    }

    public void ejecutarArbol(nodo raiz, String ambito) {
        if (raiz != null) {
            if (raiz.getNombre().equals("INI")) {
                for (int i = 0; i < raiz.getHijos().size(); i++) {
                    nodo hijos = raiz.getHijos().get(i);
                    switch (hijos.getNombre()) {
                        case "metodo":
                            ejecutarMetodo(hijos.getHijos().get(1), hijos.getHijos().get(0).getNombre());
                            break;

                    }
                }
            }
        }
    }

    public Object ejecutarMetodo(nodo raiz, String ambito) {
        Object val = null;
        if (raiz != null) {
            //int ins = raiz.getHijos().size()-1;
            nodo instru = raiz;
            for (int i = 0; i < instru.getHijos().size() && val==null; i++) {
                nodo hijo = instru.getHijos().get(i);
                switch (hijo.getNombre()) {
                    case "decvar":
                        declararvariable(hijo, ambito);
                        break;
                    case "imprimir":
                        ejecutarImprimir(hijo, ambito);
                        break;
                    case "if":
                        this.ambitos.getTablaActual(ambito).aumentarAmbito();
                        val = ejecutarIF(hijo, ambito);
                        this.ambitos.getTablaActual(ambito).disminuirAmbito();
                        break;
                    case "retorno":
                        this.retorno = true;
                        return ejecutarExpresion(hijo.getHijos().get(0), ambito);
                    case "for":
                        this.ambitos.getTablaActual(ambito).aumentarAmbito();
                        val = EjecutarFor(hijo, ambito);
                        this.ambitos.getTablaActual(ambito).disminuirAmbito();
                        detener = false;
                        break;
                    case "while":
                        this.ambitos.getTablaActual(ambito).aumentarAmbito();
                        val = EjecutarWhile(hijo, ambito);
                        this.ambitos.getTablaActual(ambito).disminuirAmbito();
                        detener = false;
                        break;
                    case "detener":
                        detener = true;
                        break;
                    case "select":
                        this.ambitos.getTablaActual(ambito).aumentarAmbito();
                        val = EjecutarSwitch(hijo, ambito);
                        this.ambitos.getTablaActual(ambito).disminuirAmbito();
                        detener = false;
                        break;
                    case "asignar":
                        asignarValor(hijo, ambito);
                        break;
                    case "obtener_doc":
                        ejecutarDocumento(hijo, ambito);
                        break;
                    case "observador_doc":
                        observadorDocumento(hijo, ambito);
                        break;
                    case "mensaje":
                        mostrarMensaje(hijo, ambito);
                        break;
                    case "llamada":
                        val = ejecutarLLamada(hijo, ambito);
                        break;                      
                }
            }              
        }
        
        return val;
    }

    private void ejecutarDocumento(nodo raiz, String ambito) {
        if (raiz != null) {
            ambito = "objetos";
            int hijos = raiz.getHijos().size();
            if (hijos == 2) {
                nodo obj = raiz.getHijos().get(0);
                nodo accion = raiz.getHijos().get(1);
                Object valS = ejecutarExpresion(obj.getHijos().get(0), ambito);
                Object val = null;
                String tipo = "";
                if (tipoObjeto(valS) == 1) {
                    Simbolo s = existeVariable(valS.toString(), ambito);
                    if (s != null) {
                        val = s.getValor();
                        tipo = s.getTipo();
                    } else {
                        errores.agregarerror(new ER(obj.getFila(), obj.getCol(), valS.toString(), 3, ""
                                + "El objeto no existe"));
                    }
                }
                if (val != null) {
                    //int tipo = tipoObjeto(val);
                    if (val instanceof JLabel) {
                        JLabel jbl = (JLabel) val;
                        setElementoButton(jbl, accion, ambito, tipo);
                    } else if (val instanceof JPanel) {
                        JPanel pan = (JPanel) val;
                        setElementoButton(pan, accion, ambito, tipo);
                    } else if (val instanceof JTextField) {
                        JTextField txt = (JTextField) val;
                        setElementoButton(txt, accion, ambito, tipo);
                    } else if (val instanceof JButton) {
                        JButton btn = (JButton) val;
                        setElementoButton(btn, accion, ambito, tipo);                        
                    } else if (val instanceof JTextArea) {
                        JTextArea txtarea = (JTextArea) val;
                        setElementoButton(txtarea, accion, ambito, tipo);
                    } else if (val instanceof JSpinner) {
                        JSpinner spi = (JSpinner) val;
                        setElementoButton(spi, accion, ambito, tipo);
                    } else if (val instanceof JTable) {
                        JTable jt = (JTable) val;
                        setElementoButton(jt, accion, ambito, tipo);
                    } else if (val instanceof JComboBox) {
                        JComboBox jc = (JComboBox) val;
                        setElementoButton(jc, accion, ambito, tipo);
                    }
                }
            }
        }
    }

    private void setElementoButton(Object jbl, nodo objeto, String ambito, String tipo) {
        if (jbl != null && objeto != null) {
            int hijos = objeto.getHijos().size();
            if (hijos == 2) {
                nodo ide = objeto.getHijos().get(0);
                nodo valor = objeto.getHijos().get(1);
                Object val = ejecutarExpresion(ide.getHijos().get(0), ambito);
                if (tipoObjeto(val) == 1) {
                    String prop = val.toString();
                    val = ejecutarExpresion(valor.getHijos().get(0), ambito);
                    if (tipoObjeto(val) != 0) {
                       // String val1 = val.toString().replace("\"", "");
                        settearNuevoValor(jbl, prop, val, tipo);
                    }
                }
            }
        }
    }

    private MouseAdapter acciones(String name, boolean aplicarruta){
        MouseAdapter adapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Eventos evt = events.get(name);
                if (evt != null && actuar==false) {
                    ArrayList<String> clk = evt.getClick();
                    for (int i = 0; i < clk.size(); i++) {
                        String meto = clk.get(i).replace("(", "").replace(")", "");
                        TablaGeneral tg = ambitos.getTablaActual(meto);
                        if (tg != null) {
                            ejecutarMetodo(tg.getInst(), meto);
                        }
                        if(!evt.getRuta().equals("") && aplicarruta==true){
                        System.out.println("Tengo redireccion:" + evt.getRuta());
                        }
                    }
                }
            }
        };
        return adapter;
    }
    
    private PropertyChangeListener accionescambio(String name, boolean aplicarruta){
        PropertyChangeListener adapter = new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt1) {
                Eventos evt = events.get(name);
                if (evt != null && actuar==false) {
                    ArrayList<String> clk = evt.getModi();
                    for (int i = 0; i < clk.size(); i++) {
                        String meto = clk.get(i).replace("(", "").replace(")", "").toLowerCase();
                        TablaGeneral tg = ambitos.getTablaActual(meto);
                        if (tg != null) {
                            ejecutarMetodo(tg.getInst(), meto);
                        }
                       /* if(!evt.getRuta().equals("") && aplicarruta==true){
                        System.out.println("Tengo redireccion:" + evt.getRuta());
                        }*/
                    }
                }
            }
        };
        return adapter;
    }
    
    
    private void settearNuevoValor(Object comp, String propiedad, Object nuevoval, String tipo) {
        if (comp != null) {
            switch (propiedad.toLowerCase()) {
                case "ruta":
                    switch (tipo.toLowerCase()) {
                        case "imagen":
                            JButton jb = (JButton) comp;
                            Dimension d = jb.getPreferredSize();
                            int width = d.width;
                            int height = d.height;
                            Image img = new ImageIcon(nuevoval.toString()).getImage();
                            ImageIcon imgescala = new ImageIcon(img.getScaledInstance(width, height, Image.SCALE_DEFAULT));
                            jb.setIcon(imgescala);
                            break;
                        case "boton":
                            JButton jb1 = (JButton) comp;
                            String id = jb1.getName();
                            Eventos ev = this.events.get(id);
                            if (ev != null) {
                                nuevoEventoObjeto(jb1.getName(), nuevoval.toString(), "r");
                            }
                            break;
                        case "enlace":
                            JLabel enl = (JLabel) comp;
                            String iden = enl.getName();
                            Eventos auxevt = this.events.get(iden);
                            if (auxevt != null) {
                                nuevoEventoObjeto(iden, nuevoval.toString(), "r");
                            } else {
                                agregarNuevoTableEvento(iden);
                                nuevoEventoObjeto(iden, nuevoval.toString(), "r");
                            }
                            break;
                        case "cajatexto":
                            JTextField jt = (JTextField) comp;
                            String idjt = jt.getName();
                            Eventos evct = this.events.get(idjt);
                            if (evct != null) {
                                nuevoEventoObjeto(idjt, nuevoval.toString(), "r");
                            }
                            break;
                        case "texto":
                            JLabel txt = (JLabel) comp;
                            String idtxt = txt.getName();
                            Eventos evtxt = this.events.get(idtxt);
                            if (evtxt != null) {
                                nuevoEventoObjeto(idtxt, nuevoval.toString(), "r");
                            }
                            break;
                        case "areatexto":
                            JTextArea jtxt = (JTextArea) comp;
                            String idjtxt = jtxt.getName();
                            Eventos evtjxt = this.events.get(idjtxt);
                            if (evtjxt != null) {
                                nuevoEventoObjeto(idjtxt, nuevoval.toString(), "r");
                            }
                            break;
                        case "spinner":
                            JSpinner spn = (JSpinner) comp;
                            String idjspn = spn.getName();
                            Eventos evtspn = this.events.get(idjspn);
                            if (evtspn != null) {
                                nuevoEventoObjeto(idjspn, nuevoval.toString(), "r");
                            }
                            break;
                        case "cajaopcion":
                            JComboBox cb = (JComboBox) comp;
                            String idcb = cb.getName();
                            Eventos evtcb = this.events.get(idcb);
                            if (evtcb != null) {
                                nuevoEventoObjeto(idcb, nuevoval.toString(), "r");
                            }
                            break;
                    }
                    break;
                case "click":
                    switch (tipo.toLowerCase()) {
                        case "imagen": {
                            JButton jb1 = (JButton) comp;
                            String metodo = jb1.getName();
                            Eventos ev = this.events.get(metodo);
                            if (ev != null) {
                                //ev.getClick().add(nuevoval.toString());
                                nuevoEventoObjeto(jb1.getName(), nuevoval.toString(), "c");
                            }
                            jb1.addMouseListener(acciones(jb1.getName(), false));
                        }
                        break;
                        case "boton": {
                            JButton jb1 = (JButton) comp;
                            String id = jb1.getName();
                            Eventos ev = this.events.get(id);
                            if (ev != null) {
                                nuevoEventoObjeto(jb1.getName(), nuevoval.toString(), "c");
                            }
                            jb1.addMouseListener(acciones(jb1.getName(), true));
                        }
                        break;
                        case "enlace": {
                            JLabel enl = (JLabel) comp;
                            String iden = enl.getName();
                            Eventos auxevt = this.events.get(iden);
                            if (auxevt != null) {
                                nuevoEventoObjeto(iden, nuevoval.toString(), "c");
                            }
                            enl.addMouseListener(acciones(enl.getName(), true));
                        }
                        break;
                        case "cajatexto":
                            JTextField jt = (JTextField) comp;
                            String idjt = jt.getName();
                            Eventos evct = this.events.get(idjt);
                            if (evct != null) {
                                nuevoEventoObjeto(idjt, nuevoval.toString(), "c");
                            }
                            jt.addMouseListener(acciones(jt.getName(), true));
                            break;
                        case "texto":
                            JLabel txt = (JLabel) comp;
                            String idtxt = txt.getName();
                            Eventos evtxt = this.events.get(idtxt);
                            if (evtxt != null) {
                                nuevoEventoObjeto(idtxt, nuevoval.toString(), "c");
                            }
                            txt.addMouseListener(acciones(txt.getName(), true));
                            break;
                        case "areatexto":
                            JTextArea jtxt = (JTextArea) comp;
                            String idjtxt = jtxt.getName();
                            Eventos evtjxt = this.events.get(idjtxt);
                            if (evtjxt != null) {
                                nuevoEventoObjeto(idjtxt, nuevoval.toString(), "c");
                            }
                            jtxt.addMouseListener(acciones(jtxt.getName(), true));
                            break;
                        case "spinner":
                            JSpinner spn = (JSpinner) comp;
                            String idjspn = spn.getName();
                            Eventos evtspn = this.events.get(idjspn);
                            if (evtspn != null) {
                                nuevoEventoObjeto(idjspn, nuevoval.toString(), "c");
                            }
                            spn.addMouseListener(acciones(spn.getName(), true));
                            break;
                        case "cajaopcion":
                            JComboBox cb = (JComboBox) comp;
                            String idcb = cb.getName();
                            Eventos evtcb = this.events.get(idcb);
                            if (evtcb != null) {
                                nuevoEventoObjeto(idcb, nuevoval.toString(), "c");
                            }
                            cb.addMouseListener(acciones(cb.getName(), true));

                            break;
                        case "panel":
                            JPanel pna = (JPanel) comp;
                            String idp = pna.getName();
                            Eventos evtp = this.events.get(idp);
                            if (evtp != null) {
                                nuevoEventoObjeto(idp, nuevoval.toString(), "c");
                            }
                            pna.addMouseListener(acciones(pna.getName(), true));
                            break;
                    }
                    break;
                case "id":
                    switch (tipo.toLowerCase()) {
                        case "imagen":
                        case "boton":
                            JButton jb1 = (JButton) comp;
                            cambiarIDboton(jb1, "objetos", tipo, nuevoval.toString());
                            break;
                        case "panel": {
                            JPanel pan = (JPanel) comp;
                            cambiarIDpanel(pan, "objetos", tipo, nuevoval.toString());
                        }
                        break;
                        case "texto":
                        case "enlace":
                            JLabel enl = (JLabel) comp;
                            cambiarIDLabel(enl, "objetos", tipo, nuevoval.toString());
                            break;
                        case "cajatexto": {
                            JTextField pan = (JTextField) comp;
                            cambiarIDTextField(pan, "objetos", tipo, nuevoval.toString());
                        }
                        break;
                        case "areatexto": {
                            JTextArea pan = (JTextArea) comp;
                            cambiarIDjtextArea(pan, "objetos", tipo, nuevoval.toString());
                        }
                        break;
                        case "spinner": {
                            JSpinner pan = (JSpinner) comp;
                            cambiarIDSpinner(pan, "objetos", tipo, nuevoval.toString());
                        }
                        break;
                        case "tabla": {
                            JTable pan = (JTable) comp;
                            cambiarIDtable(pan, "objetos", tipo, nuevoval.toString());
                        }
                        break;
                        case "cajaopcion": {
                            JComboBox pan = (JComboBox) comp;
                            cambiarIDCombobox(pan, "objetos", tipo, nuevoval.toString());
                        }
                        break;
                    }
                    break;
               /* case "grupo":
                    switch (tipo.toLowerCase()) {
                        case "imagen":
                            JButton jb1 = (JButton) comp;
                            String metodo = jb1.getActionCommand();
                            TablaGeneral ta = this.ambitos.getTablaActual(metodo);
                            if (ta != null) {

                            }
                            this.ambitos.QuitarMetodo(jb1.getActionCommand());
                            jb1.setActionCommand(nuevoval.toString());
                            break;

                    }
                    break;*/
                case "alto":
                    switch (tipo.toLowerCase()) {
                        case "imagen":
                        case "boton": {
                            JButton jb1 = (JButton) comp;
                            nuevoval = casteoImplicito("ancho", nuevoval.toString());
                            if (tipoObjeto(nuevoval) == 2 || tipoObjeto(nuevoval) == 3) {
                                if (tipoObjeto(nuevoval) == 2) {
                                    int alto = Integer.parseInt(nuevoval.toString());
                                    Dimension d = jb1.getPreferredSize();
                                    int width = d.width;
                                    int height = alto;
                                    jb1.setPreferredSize(new Dimension(width, height));
                                }else if (tipoObjeto(nuevoval) == 3) {
                                    float valfl = Float.parseFloat(nuevoval.toString());
                                    int alto = (int) Math.round(valfl);
                                    Dimension d = jb1.getPreferredSize();
                                    int width = d.width;
                                    int height = alto;
                                    jb1.setPreferredSize(new Dimension(width, height));
                                }
                            }
                            jb1.repaint();
                        }
                        break;
                        case "panel": {
                            JPanel jb1 = (JPanel) comp;
                            if (tipoObjeto(nuevoval) == 2 || tipoObjeto(nuevoval) == 3) {
                                if (tipoObjeto(nuevoval) == 2) {
                                    int alto = Integer.parseInt(nuevoval.toString());
                                    Dimension d = jb1.getPreferredSize();
                                    int width = d.width;
                                    int height = alto;
                                    jb1.setPreferredSize(new Dimension(width, height));
                                }else if (tipoObjeto(nuevoval) == 3) {
                                    float valfl = Float.parseFloat(nuevoval.toString());
                                    int alto = (int) Math.round(valfl);
                                    Dimension d = jb1.getPreferredSize();
                                    int width = d.width;
                                    int height = alto;
                                    jb1.setPreferredSize(new Dimension(width, height));
                                }
                            }
                            jb1.repaint();
                        }
                        break;
                        case "texto":
                        case "enlace": {
                            JLabel jb1 = (JLabel) comp;
                            if (tipoObjeto(nuevoval) == 2 || tipoObjeto(nuevoval) == 3) {
                                if (tipoObjeto(nuevoval) == 2) {
                                    int alto = Integer.parseInt(nuevoval.toString());
                                    Dimension d = jb1.getPreferredSize();
                                    int width = d.width;
                                    int height = alto;
                                    jb1.setPreferredSize(new Dimension(width, height));
                                }else if (tipoObjeto(nuevoval) == 3) {
                                    float valfl = Float.parseFloat(nuevoval.toString());
                                    int alto = (int) Math.round(valfl);
                                    Dimension d = jb1.getPreferredSize();
                                    int width = d.width;
                                    int height = alto;
                                    jb1.setPreferredSize(new Dimension(width, height));
                                }
                            }
                            jb1.repaint();
                        }
                        break;
                        case "cajatexto": {
                            JTextField jb1 = (JTextField) comp;
                            if (tipoObjeto(nuevoval) == 2 || tipoObjeto(nuevoval) == 3) {
                                if (tipoObjeto(nuevoval) == 2) {
                                    int alto = Integer.parseInt(nuevoval.toString());
                                    Dimension d = jb1.getPreferredSize();
                                    int width = d.width;
                                    int height = alto;
                                    jb1.setPreferredSize(new Dimension(width, height));
                                }else if (tipoObjeto(nuevoval) == 3) {
                                    float valfl = Float.parseFloat(nuevoval.toString());
                                    int alto = (int) Math.round(valfl);
                                    Dimension d = jb1.getPreferredSize();
                                    int width = d.width;
                                    int height = alto;
                                    jb1.setPreferredSize(new Dimension(width, height));
                                }
                            }
                            jb1.repaint();
                        }
                        break;
                        case "areatexto": {
                            JTextArea jb1 = (JTextArea) comp;
                            if (tipoObjeto(nuevoval) == 2 || tipoObjeto(nuevoval) == 3) {
                                if (tipoObjeto(nuevoval) == 2) {
                                    int alto = Integer.parseInt(nuevoval.toString());
                                    Dimension d = jb1.getPreferredSize();
                                    int width = d.width;
                                    int height = alto;
                                    jb1.setPreferredSize(new Dimension(width, height));
                                }else if (tipoObjeto(nuevoval) == 3) {
                                    float valfl = Float.parseFloat(nuevoval.toString());
                                    int alto = (int) Math.round(valfl);
                                    Dimension d = jb1.getPreferredSize();
                                    int width = d.width;
                                    int height = alto;
                                    jb1.setPreferredSize(new Dimension(width, height));
                                }
                            }
                            jb1.repaint();
                        }
                        break;
                        case "spinner": {
                            JSpinner jb1 = (JSpinner) comp;
                            if (tipoObjeto(nuevoval) == 2 || tipoObjeto(nuevoval) == 3) {
                                if (tipoObjeto(nuevoval) == 2) {
                                    int alto = Integer.parseInt(nuevoval.toString());
                                    Dimension d = jb1.getPreferredSize();
                                    int width = d.width;
                                    int height = alto;
                                    jb1.setPreferredSize(new Dimension(width, height));
                                }else if (tipoObjeto(nuevoval) == 3) {
                                    float valfl = Float.parseFloat(nuevoval.toString());
                                    int alto = (int) Math.round(valfl);
                                    Dimension d = jb1.getPreferredSize();
                                    int width = d.width;
                                    int height = alto;
                                    jb1.setPreferredSize(new Dimension(width, height));
                                }
                            }
                            jb1.repaint();
                        }
                        break;
                        case "tabla": {
                            JTable jb1 = (JTable) comp;
                            if (tipoObjeto(nuevoval) == 2 || tipoObjeto(nuevoval) == 3) {
                                if (tipoObjeto(nuevoval) == 2) {
                                    int alto = Integer.parseInt(nuevoval.toString());
                                    Dimension d = jb1.getPreferredSize();
                                    int width = d.width;
                                    int height = alto;
                                    jb1.setPreferredSize(new Dimension(width, height));
                                }else if (tipoObjeto(nuevoval) == 3) {
                                    float valfl = Float.parseFloat(nuevoval.toString());
                                    int alto = (int) Math.round(valfl);
                                    Dimension d = jb1.getPreferredSize();
                                    int width = d.width;
                                    int height = alto;
                                    jb1.setPreferredSize(new Dimension(width, height));
                                }
                            }
                            jb1.repaint();
                        }
                        break;
                        case "cajaopcion": {
                            JComboBox jb1 = (JComboBox) comp;
                            if (tipoObjeto(nuevoval) == 2 || tipoObjeto(nuevoval) == 3) {
                                if (tipoObjeto(nuevoval) == 2) {
                                    int alto = Integer.parseInt(nuevoval.toString());
                                    Dimension d = jb1.getPreferredSize();
                                    int width = d.width;
                                    int height = alto;
                                    jb1.setPreferredSize(new Dimension(width, height));
                                }else if (tipoObjeto(nuevoval) == 3) {
                                    float valfl = Float.parseFloat(nuevoval.toString());
                                    int alto = (int) Math.round(valfl);
                                    Dimension d = jb1.getPreferredSize();
                                    int width = d.width;
                                    int height = alto;
                                    jb1.setPreferredSize(new Dimension(width, height));
                                }
                            }
                            jb1.repaint();
                        }
                        break;
                    }
                    break;
                case "ancho":
                    switch (tipo.toLowerCase()) {
                        case "imagen":
                        case "boton": {
                            JButton jb1 = (JButton) comp;
                            nuevoval = casteoImplicito("ancho", nuevoval.toString());
                            if (tipoObjeto(nuevoval) == 2 || tipoObjeto(nuevoval) == 3) {
                                if (tipoObjeto(nuevoval) == 2) {
                                    int ancho = Integer.parseInt(nuevoval.toString());
                                    Dimension d = jb1.getPreferredSize();
                                    int width = ancho;
                                    int height = d.height;
                                    jb1.setPreferredSize(new Dimension(width, height));
                                }else if (tipoObjeto(nuevoval) == 3) {
                                    float valfl = Float.parseFloat(nuevoval.toString());
                                    int ancho = (int) Math.round(valfl);
                                    Dimension d = jb1.getPreferredSize();
                                    int width = ancho;
                                    int height = d.height;
                                    jb1.setPreferredSize(new Dimension(width, height));
                                }
                            }
                            jb1.repaint();
                        }
                        break;
                        case "panel": {
                            JPanel jb1 = (JPanel) comp;
                            nuevoval = casteoImplicito("ancho", nuevoval.toString());
                            if (tipoObjeto(nuevoval) == 2 || tipoObjeto(nuevoval) == 3) {
                                if (tipoObjeto(nuevoval) == 2) {
                                    int ancho = Integer.parseInt(nuevoval.toString());
                                    Dimension d = jb1.getPreferredSize();
                                    int width = ancho;
                                    int height = d.height;
                                    jb1.setPreferredSize(new Dimension(width, height));
                                }else if (tipoObjeto(nuevoval) == 3) {
                                    float valfl = Float.parseFloat(nuevoval.toString());
                                    int ancho = (int) Math.round(valfl);
                                    Dimension d = jb1.getPreferredSize();
                                    int width = ancho;
                                    int height = d.height;
                                    jb1.setPreferredSize(new Dimension(width, height));
                                }
                            }
                            jb1.repaint();
                        }
                        break;
                        case "texto":
                        case "enlace": {
                            JLabel jb1 = (JLabel) comp;
                            nuevoval = casteoImplicito("ancho", nuevoval.toString());
                            if (tipoObjeto(nuevoval) == 2 || tipoObjeto(nuevoval) == 3) {
                                if (tipoObjeto(nuevoval) == 2) {
                                    int ancho = Integer.parseInt(nuevoval.toString());
                                    Dimension d = jb1.getPreferredSize();
                                    int width = ancho;
                                    int height = d.height;
                                    jb1.setPreferredSize(new Dimension(width, height));
                                }else if (tipoObjeto(nuevoval) == 3) {
                                    float valfl = Float.parseFloat(nuevoval.toString());
                                    int ancho = (int) Math.round(valfl);
                                    Dimension d = jb1.getPreferredSize();
                                    int width = ancho;
                                    int height = d.height;
                                    jb1.setPreferredSize(new Dimension(width, height));
                                }
                            }
                            jb1.repaint();
                        }
                        break;
                        case "cajatexto": {
                            JTextField jb1 = (JTextField) comp;
                            nuevoval = casteoImplicito("ancho", nuevoval.toString());
                            if (tipoObjeto(nuevoval) == 2 || tipoObjeto(nuevoval) == 3) {
                                if (tipoObjeto(nuevoval) == 2) {
                                    int ancho = Integer.parseInt(nuevoval.toString());
                                    Dimension d = jb1.getPreferredSize();
                                    int width = ancho;
                                    int height = d.height;
                                    jb1.setPreferredSize(new Dimension(width, height));
                                }else if (tipoObjeto(nuevoval) == 3) {
                                    float valfl = Float.parseFloat(nuevoval.toString());
                                    int ancho = (int) Math.round(valfl);
                                    Dimension d = jb1.getPreferredSize();
                                    int width = ancho;
                                    int height = d.height;
                                    jb1.setPreferredSize(new Dimension(width, height));
                                }
                            }
                            jb1.repaint();
                        }
                        break;
                        case "areatexto": {
                            JTextArea jb1 = (JTextArea) comp;
                           nuevoval = casteoImplicito("ancho", nuevoval.toString());
                            if (tipoObjeto(nuevoval) == 2 || tipoObjeto(nuevoval) == 3) {
                                if (tipoObjeto(nuevoval) == 2) {
                                    int ancho = Integer.parseInt(nuevoval.toString());
                                    Dimension d = jb1.getPreferredSize();
                                    int width = ancho;
                                    int height = d.height;
                                    jb1.setPreferredSize(new Dimension(width, height));
                                }else if (tipoObjeto(nuevoval) == 3) {
                                    float valfl = Float.parseFloat(nuevoval.toString());
                                    int ancho = (int) Math.round(valfl);
                                    Dimension d = jb1.getPreferredSize();
                                    int width = ancho;
                                    int height = d.height;
                                    jb1.setPreferredSize(new Dimension(width, height));
                                }
                            }
                            jb1.repaint();
                        }
                        break;
                        case "spinner": {
                            JSpinner jb1 = (JSpinner) comp;
                            nuevoval = casteoImplicito("ancho", nuevoval.toString());
                            if (tipoObjeto(nuevoval) == 2 || tipoObjeto(nuevoval) == 3) {
                                if (tipoObjeto(nuevoval) == 2) {
                                    int ancho = Integer.parseInt(nuevoval.toString());
                                    Dimension d = jb1.getPreferredSize();
                                    int width = ancho;
                                    int height = d.height;
                                    jb1.setPreferredSize(new Dimension(width, height));
                                }else if (tipoObjeto(nuevoval) == 3) {
                                    float valfl = Float.parseFloat(nuevoval.toString());
                                    int ancho = (int) Math.round(valfl);
                                    Dimension d = jb1.getPreferredSize();
                                    int width = ancho;
                                    int height = d.height;
                                    jb1.setPreferredSize(new Dimension(width, height));
                                }
                            }
                            jb1.repaint();
                        }
                        break;
                        case "tabla": {
                            JTable jb1 = (JTable) comp;
                            nuevoval = casteoImplicito("ancho", nuevoval.toString());
                            if (tipoObjeto(nuevoval) == 2 || tipoObjeto(nuevoval) == 3) {
                                if (tipoObjeto(nuevoval) == 2) {
                                    int ancho = Integer.parseInt(nuevoval.toString());
                                    Dimension d = jb1.getPreferredSize();
                                    int width = ancho;
                                    int height = d.height;
                                    jb1.setPreferredSize(new Dimension(width, height));
                                }else if (tipoObjeto(nuevoval) == 3) {
                                    float valfl = Float.parseFloat(nuevoval.toString());
                                    int ancho = (int) Math.round(valfl);
                                    Dimension d = jb1.getPreferredSize();
                                    int width = ancho;
                                    int height = d.height;
                                    jb1.setPreferredSize(new Dimension(width, height));
                                }
                            }
                            jb1.repaint();
                        }
                        break;
                        case "cajaopcion": {
                            JComboBox jb1 = (JComboBox) comp;
                           nuevoval = casteoImplicito("ancho", nuevoval.toString());
                            if (tipoObjeto(nuevoval) == 2 || tipoObjeto(nuevoval) == 3) {
                                if (tipoObjeto(nuevoval) == 2) {
                                    int ancho = Integer.parseInt(nuevoval.toString());
                                    Dimension d = jb1.getPreferredSize();
                                    int width = ancho;
                                    int height = d.height;
                                    jb1.setPreferredSize(new Dimension(width, height));
                                }else if (tipoObjeto(nuevoval) == 3) {
                                    float valfl = Float.parseFloat(nuevoval.toString());
                                    int ancho = (int) Math.round(valfl);
                                    Dimension d = jb1.getPreferredSize();
                                    int width = ancho;
                                    int height = d.height;
                                    jb1.setPreferredSize(new Dimension(width, height));
                                }
                            }
                            jb1.repaint();
                        }
                        break;
                    }
                    break;
                case "alineado":
                    switch (tipo.toLowerCase()) {
                        case "imagen":
                        case "boton":
                            JButton jb1 = (JButton) comp;
                            setAlign(jb1, nuevoval.toString());
                            break;
                        case "panel": {
                            JPanel pan = (JPanel) comp;
                            setAlign(pan, nuevoval.toString());
                        }
                        break;
                        case "texto":
                        case "enlace":
                            JLabel enl = (JLabel) comp;
                            setAlign(enl, nuevoval.toString());
                            break;
                        case "cajatexto": {
                            JTextField pan = (JTextField) comp;
                            setAlign(pan, nuevoval.toString());
                        }
                        break;
                        case "areatexto": {
                            JTextArea pan = (JTextArea) comp;
                            setAlign(pan, nuevoval.toString());
                        }
                        break;
                        case "spinner": {
                            JSpinner pan = (JSpinner) comp;
                            setAlign(pan, nuevoval.toString());
                        }
                        break;
                        case "tabla": {
                            JTable pan = (JTable) comp;
                            setAlign(pan, nuevoval.toString());
                        }
                        break;
                        case "cajaopcion": {
                            JComboBox pan = (JComboBox) comp;
                            setAlign(pan, nuevoval.toString());
                        }   
                        break;
                    }
                    break;
            }
        }
    }

    private void cambiarIDboton(JButton container, String ambito, String tipo, String nuevoID) {
        if (container != null) {
            TablaGeneral gen = this.ambitos.getTablaActual(ambito);
            Simbolo aux = null;
            JButton img = container;
            if (gen != null) {
                String nombre = img.getName();
                aux = gen.existevariable(nombre);
                //INGRESANDO LA NUEVA VARIABLE CON UN ID Y ELIMINANDO LA DEL VIEJO ID
                if (aux != null) {
                    img.setName(nuevoID);
                    gen.nuevavariable(nuevoID, img, aux.getFila(), aux.getCol(), aux.isArray(), tipo);
                    //CAMBIANDO LOS EVENTOS AL NUEVO BOTON
                    Eventos auxevt = this.events.get(nombre);
                    if (auxevt != null) {
                        agregarNuevoTableEvento(nuevoID);
                        this.events.replace(nuevoID, auxevt);
                    } else {
                        //NO TENIA EVENTOS ASOCIADOS
                        agregarNuevoTableEvento(nuevoID);
                        this.events.replace(nuevoID, new Eventos());
                    }
                    this.events.remove(nombre);
                    gen.eliminarVariable(nombre);
                }
            }
        }
    }

    private void cambiarIDLabel(JLabel container, String ambito, String tipo, String nuevoID) {
        if (container != null) {
            TablaGeneral gen = this.ambitos.getTablaActual(ambito);
            Simbolo aux = null;
            JLabel img = container;
            if (gen != null) {
                String nombre = img.getName();
                aux = gen.existevariable(nombre);
                //INGRESANDO LA NUEVA VARIABLE CON UN ID Y ELIMINANDO LA DEL VIEJO ID
                if (aux != null) {
                    img.setName(nuevoID);
                    gen.nuevavariable(nuevoID, img, aux.getFila(), aux.getCol(), aux.isArray(), tipo);
                    //CAMBIANDO LOS EVENTOS AL NUEVO BOTON
                    Eventos auxevt = this.events.get(nombre);
                    if (auxevt != null) {
                        agregarNuevoTableEvento(nuevoID);
                        this.events.replace(nuevoID, auxevt);
                    } else {
                        //NO TENIA EVENTOS ASOCIADOS
                        agregarNuevoTableEvento(nuevoID);
                        this.events.replace(nuevoID, new Eventos());
                    }
                    this.events.remove(nombre);
                    gen.eliminarVariable(nombre);
                }
            }
        }
    }

    private void cambiarIDTextField(JTextField container, String ambito, String tipo, String nuevoID) {
        if (container != null) {
            TablaGeneral gen = this.ambitos.getTablaActual(ambito);
            Simbolo aux = null;

            JTextField img = container;
            if (gen != null) {
                String nombre = img.getName();
                aux = gen.existevariable(nombre);
                //INGRESANDO LA NUEVA VARIABLE CON UN ID Y ELIMINANDO LA DEL VIEJO ID
                if (aux != null) {
                    img.setName(nuevoID);
                    gen.nuevavariable(nuevoID, img, aux.getFila(), aux.getCol(), aux.isArray(), tipo);
                    //CAMBIANDO LOS EVENTOS AL NUEVO BOTON
                    Eventos auxevt = this.events.get(nombre);
                    if (auxevt != null) {
                        agregarNuevoTableEvento(nuevoID);
                        this.events.replace(nuevoID, auxevt);
                    } else {
                        //NO TENIA EVENTOS ASOCIADOS
                        agregarNuevoTableEvento(nuevoID);
                        this.events.replace(nuevoID, new Eventos());
                    }
                    this.events.remove(nombre);
                    gen.eliminarVariable(nombre);
                }
            }
        }
    }

    private void cambiarIDSpinner(JSpinner container, String ambito, String tipo, String nuevoID) {
        if (container != null) {
            TablaGeneral gen = this.ambitos.getTablaActual(ambito);
            Simbolo aux = null;

            JSpinner img = container;
            if (gen != null) {
                String nombre = img.getName();
                aux = gen.existevariable(nombre);
                //INGRESANDO LA NUEVA VARIABLE CON UN ID Y ELIMINANDO LA DEL VIEJO ID
                if (aux != null) {
                    img.setName(nuevoID);
                    gen.nuevavariable(nuevoID, img, aux.getFila(), aux.getCol(), aux.isArray(), tipo);
                    //CAMBIANDO LOS EVENTOS AL NUEVO BOTON
                    Eventos auxevt = this.events.get(nombre);
                    if (auxevt != null) {
                        agregarNuevoTableEvento(nuevoID);
                        this.events.replace(nuevoID, auxevt);
                    } else {
                        //NO TENIA EVENTOS ASOCIADOS
                        agregarNuevoTableEvento(nuevoID);
                        this.events.replace(nuevoID, new Eventos());
                    }
                    this.events.remove(nombre);
                    gen.eliminarVariable(nombre);
                }
            }
        }
    }

    private void cambiarIDCombobox(JComboBox container, String ambito, String tipo, String nuevoID) {
        if (container != null) {
            TablaGeneral gen = this.ambitos.getTablaActual(ambito);
            Simbolo aux = null;

            JComboBox img = container;
            if (gen != null) {
                String nombre = img.getName();
                aux = gen.existevariable(nombre);
                //INGRESANDO LA NUEVA VARIABLE CON UN ID Y ELIMINANDO LA DEL VIEJO ID
                if (aux != null) {
                    img.setName(nuevoID);
                    gen.nuevavariable(nuevoID, img, aux.getFila(), aux.getCol(), aux.isArray(), tipo);
                    //CAMBIANDO LOS EVENTOS AL NUEVO BOTON
                    Eventos auxevt = this.events.get(nombre);
                    if (auxevt != null) {
                        agregarNuevoTableEvento(nuevoID);
                        this.events.replace(nuevoID, auxevt);
                    } else {
                        //NO TENIA EVENTOS ASOCIADOS
                        agregarNuevoTableEvento(nuevoID);
                        this.events.replace(nuevoID, new Eventos());
                    }
                    this.events.remove(nombre);
                    gen.eliminarVariable(nombre);
                }
            }
        }
    }

    private void cambiarIDpanel(JPanel container, String ambito, String tipo, String nuevoID) {
        if (container != null) {
            TablaGeneral gen = this.ambitos.getTablaActual(ambito);
            Simbolo aux = null;

            JPanel img = container;
            if (gen != null) {
                String nombre = img.getName();
                aux = gen.existevariable(nombre);
                //INGRESANDO LA NUEVA VARIABLE CON UN ID Y ELIMINANDO LA DEL VIEJO ID
                if (aux != null) {
                    img.setName(nuevoID);
                    gen.nuevavariable(nuevoID, img, aux.getFila(), aux.getCol(), aux.isArray(), tipo);
                    //CAMBIANDO LOS EVENTOS AL NUEVO BOTON
                    Eventos auxevt = this.events.get(nombre);
                    if (auxevt != null) {
                        agregarNuevoTableEvento(nuevoID);
                        this.events.replace(nuevoID, auxevt);
                    } else {
                        //NO TENIA EVENTOS ASOCIADOS
                        agregarNuevoTableEvento(nuevoID);
                        this.events.replace(nuevoID, new Eventos());
                    }
                    this.events.remove(nombre);
                    gen.eliminarVariable(nombre);
                }
            }
        }
    }

    private void cambiarIDtable(JTable container, String ambito, String tipo, String nuevoID) {
        if (container != null) {
            TablaGeneral gen = this.ambitos.getTablaActual(ambito);
            Simbolo aux = null;

            JTable img = container;
            if (gen != null) {
                String nombre = img.getName();
                aux = gen.existevariable(nombre);
                //INGRESANDO LA NUEVA VARIABLE CON UN ID Y ELIMINANDO LA DEL VIEJO ID
                if (aux != null) {
                    img.setName(nuevoID);
                    gen.nuevavariable(nuevoID, img, aux.getFila(), aux.getCol(), aux.isArray(), tipo);
                    //CAMBIANDO LOS EVENTOS AL NUEVO BOTON
                    Eventos auxevt = this.events.get(nombre);
                    if (auxevt != null) {
                        agregarNuevoTableEvento(nuevoID);
                        this.events.replace(nuevoID, auxevt);
                    } else {
                        //NO TENIA EVENTOS ASOCIADOS
                        agregarNuevoTableEvento(nuevoID);
                        this.events.replace(nuevoID, new Eventos());
                    }
                    this.events.remove(nombre);
                    gen.eliminarVariable(nombre);
                }
            }
        }
    }

    private void cambiarIDjtextArea(JTextArea container, String ambito, String tipo, String nuevoID) {
        if (container != null) {
            TablaGeneral gen = this.ambitos.getTablaActual(ambito);
            Simbolo aux = null;

            JTextArea img = container;
            if (gen != null) {
                String nombre = img.getName();
                aux = gen.existevariable(nombre);
                //INGRESANDO LA NUEVA VARIABLE CON UN ID Y ELIMINANDO LA DEL VIEJO ID
                if (aux != null) {
                    img.setName(nuevoID);
                    gen.nuevavariable(nuevoID, img, aux.getFila(), aux.getCol(), aux.isArray(), tipo);
                    //CAMBIANDO LOS EVENTOS AL NUEVO BOTON
                    Eventos auxevt = this.events.get(nombre);
                    if (auxevt != null) {
                        agregarNuevoTableEvento(nuevoID);
                        this.events.replace(nuevoID, auxevt);
                    } else {
                        //NO TENIA EVENTOS ASOCIADOS
                        agregarNuevoTableEvento(nuevoID);
                        this.events.replace(nuevoID, new Eventos());
                    }
                    this.events.remove(nombre);
                    gen.eliminarVariable(nombre);
                }
            }
        }
    }

    private void observadorID(nodo raiz, String ambito) {
        if (raiz != null) {
            //ambito = "objetos";
            int hijos = raiz.getHijos().size();
            if (hijos == 3) {
                nodo id = raiz.getHijos().get(0);
                nodo event = raiz.getHijos().get(1);
                nodo accion = raiz.getHijos().get(2);

                Simbolo s = existeVariable(id.getHijos().get(0).getNombre().replace("\"", ""), ambito);
                if (s != null) {
                    Object val = ejecutarExpresion(event.getHijos().get(0), ambito);
                    String nombreEvent = val.toString();
                    if (nombreEvent.equals("listo") || nombreEvent.equals("click") || nombreEvent.equals("modificado")) {
                        val = s.getValor();
                        switch (nombreEvent) {
                            case "listo":
                                switch (tipoObjeto(val)) {
                                    case 7:
                                        JLabel jbl = (JLabel) val;
                                        if (accion.getNombre().equals("funcionanidada")) {
                                            nodo instru = accion.getHijos().get(0);
                                            TablaAmbitos am = getAmbitos();
                                            if (am != null) {
                                                this.ambitos.AgregarNuevoMetodo("$" + s.getNombre() + "listo", instru);
                                                nuevoEventoObjeto(jbl.getName(), "$" + s.getNombre() + "listo", "l");
                                            }
                                        } else if (accion.getNombre().equals("llamada")) {
                                            nodo metodo = accion.getHijos().get(0);
                                            TablaAmbitos am = getAmbitos();
                                            if (am != null) {
                                                TablaGeneral nuevogen = am.getTablaActual(metodo.getNombre().replace("\"", ""));
                                                if (nuevogen != null) {
                                                    am.AgregarNuevoMetodo("$" + s.getNombre() + "listo", nuevogen.getInst());
                                                    nuevoEventoObjeto(jbl.getName(), "$" + s.getNombre() + "listo", "l");
                                                }
                                            }
                                        }
                                        break;
                                    case 8:
                                        JPanel pan = (JPanel) val;
                                        if (accion.getNombre().equals("funcionanidada")) {
                                            nodo instru = accion.getHijos().get(0);
                                            TablaAmbitos am = getAmbitos();
                                            if (am != null) {
                                                this.ambitos.AgregarNuevoMetodo("$" + s.getNombre() + "listo", instru);
                                                nuevoEventoObjeto(pan.getName(), "$" + s.getNombre() + "listo", "l");
                                            }
                                        } else if (accion.getNombre().equals("llamada")) {
                                            nodo metodo = accion.getHijos().get(0);
                                            TablaAmbitos am = getAmbitos();
                                            if (am != null) {
                                                TablaGeneral nuevogen = am.getTablaActual(metodo.getNombre().replace("\"", ""));
                                                if (nuevogen != null) {
                                                    am.AgregarNuevoMetodo("$" + s.getNombre() + "listo", nuevogen.getInst());
                                                    nuevoEventoObjeto(pan.getName(), "$" + s.getNombre() + "listo", "l");
                                                }
                                            }
                                        }
                                        break;
                                    case 9: {
                                        JTextField jt = (JTextField) val;
                                        if (accion.getNombre().equals("funcionanidada")) {
                                            nodo instru = accion.getHijos().get(0);
                                            TablaAmbitos am = getAmbitos();
                                            if (am != null) {
                                                this.ambitos.AgregarNuevoMetodo("$" + s.getNombre() + "listo", instru);
                                                nuevoEventoObjeto(jt.getName(), "$" + s.getNombre() + "listo", "l");
                                            }
                                        } else if (accion.getNombre().equals("llamada")) {
                                            nodo metodo = accion.getHijos().get(0);
                                            TablaAmbitos am = getAmbitos();
                                            if (am != null) {
                                                TablaGeneral nuevogen = am.getTablaActual(metodo.getNombre().replace("\"", ""));
                                                if (nuevogen != null) {
                                                    am.AgregarNuevoMetodo("$" + s.getNombre() + "listo", nuevogen.getInst());
                                                    nuevoEventoObjeto(jt.getName(), "$" + s.getNombre() + "listo", "l");
                                                }
                                            }
                                        }
                                        break;
                                    }
                                    case 10:
                                        JButton jb = (JButton) val;
                                        if (accion.getNombre().equals("funcionanidada")) {
                                            nodo instru = accion.getHijos().get(0);
                                            TablaAmbitos am = getAmbitos();
                                            if (am != null) {
                                                this.ambitos.AgregarNuevoMetodo("$" + s.getNombre() + "listo", instru);
                                                nuevoEventoObjeto(jb.getName(), "$" + s.getNombre() + "listo", "l");
                                            }
                                        } else if (accion.getNombre().equals("llamada")) {
                                            nodo metodo = accion.getHijos().get(0);
                                            TablaAmbitos am = getAmbitos();
                                            if (am != null) {
                                                TablaGeneral nuevogen = am.getTablaActual(metodo.getNombre().replace("\"", ""));
                                                if (nuevogen != null) {
                                                    am.AgregarNuevoMetodo("$" + s.getNombre() + "listo", nuevogen.getInst());
                                                    nuevoEventoObjeto(jb.getName(), "$" + s.getNombre() + "listo", "l");
                                                }
                                            }
                                        }
                                        break;
                                    case 11:
                                        JTextArea jta = (JTextArea) val;
                                        if (accion.getNombre().equals("funcionanidada")) {
                                            nodo instru = accion.getHijos().get(0);
                                            TablaAmbitos am = getAmbitos();
                                            if (am != null) {
                                                this.ambitos.AgregarNuevoMetodo("$" + s.getNombre() + "listo", instru);
                                                nuevoEventoObjeto(jta.getName(), "$" + s.getNombre() + "listo", "l");
                                            }
                                        } else if (accion.getNombre().equals("llamada")) {
                                            nodo metodo = accion.getHijos().get(0);
                                            TablaAmbitos am = getAmbitos();
                                            if (am != null) {
                                                TablaGeneral nuevogen = am.getTablaActual(metodo.getNombre().replace("\"", ""));
                                                if (nuevogen != null) {
                                                    am.AgregarNuevoMetodo("$" + s.getNombre() + "listo", nuevogen.getInst());
                                                    nuevoEventoObjeto(jta.getName(), "$" + s.getNombre() + "listo", "l");
                                                }
                                            }
                                        }
                                        break;
                                    case 12:
                                        JSpinner spi = (JSpinner) val;
                                        if (accion.getNombre().equals("funcionanidada")) {
                                            nodo instru = accion.getHijos().get(0);
                                            TablaAmbitos am = getAmbitos();
                                            if (am != null) {
                                                this.ambitos.AgregarNuevoMetodo("$" + s.getNombre() + "listo", instru);
                                                nuevoEventoObjeto(spi.getName(), "$" + s.getNombre() + "listo", "l");
                                            }
                                        } else if (accion.getNombre().equals("llamada")) {
                                            nodo metodo = accion.getHijos().get(0);
                                            TablaAmbitos am = getAmbitos();
                                            if (am != null) {
                                                TablaGeneral nuevogen = am.getTablaActual(metodo.getNombre().replace("\"", ""));
                                                if (nuevogen != null) {
                                                    am.AgregarNuevoMetodo("$" + s.getNombre() + "listo", nuevogen.getInst());
                                                    nuevoEventoObjeto(spi.getName(), "$" + s.getNombre() + "listo", "l");
                                                }
                                            }
                                        }
                                        break;
                                    case 13: {
                                        JTable jt = (JTable) val;
                                        if (accion.getNombre().equals("funcionanidada")) {
                                            nodo instru = accion.getHijos().get(0);
                                            TablaAmbitos am = getAmbitos();
                                            if (am != null) {
                                                this.ambitos.AgregarNuevoMetodo("$" + s.getNombre() + "listo", instru);
                                                nuevoEventoObjeto(jt.getName(), "$" + s.getNombre() + "listo", "l");
                                            }
                                        } else if (accion.getNombre().equals("llamada")) {
                                            nodo metodo = accion.getHijos().get(0);
                                            TablaAmbitos am = getAmbitos();
                                            if (am != null) {
                                                TablaGeneral nuevogen = am.getTablaActual(metodo.getNombre().replace("\"", ""));
                                                if (nuevogen != null) {
                                                    am.AgregarNuevoMetodo("$" + s.getNombre() + "listo", nuevogen.getInst());
                                                    nuevoEventoObjeto(jt.getName(), "$" + s.getNombre() + "listo", "l");
                                                }
                                            }
                                        }
                                        break;
                                    }
                                    case 14:
                                        JComboBox jc = (JComboBox) val;
                                        if (accion.getNombre().equals("funcionanidada")) {
                                            nodo instru = accion.getHijos().get(0);
                                            TablaAmbitos am = getAmbitos();
                                            if (am != null) {
                                                this.ambitos.AgregarNuevoMetodo("$" + s.getNombre() + "listo", instru);
                                                nuevoEventoObjeto(jc.getName(), "$" + s.getNombre() + "listo", "l");
                                            }
                                        } else if (accion.getNombre().equals("llamada")) {
                                            nodo metodo = accion.getHijos().get(0);
                                            TablaAmbitos am = getAmbitos();
                                            if (am != null) {
                                                TablaGeneral nuevogen = am.getTablaActual(metodo.getNombre().replace("\"", ""));
                                                if (nuevogen != null) {
                                                    am.AgregarNuevoMetodo("$" + s.getNombre() + "listo", nuevogen.getInst());
                                                    nuevoEventoObjeto(jc.getName(), "$" + s.getNombre() + "listo", "l");
                                                }
                                            }
                                        }
                                        break;
                                    default:
                                        break;
                                }
                                break;
                            case "click":
                                switch (tipoObjeto(val)) {
                                    case 7:
                                        JLabel jbl = (JLabel) val;
                                        if (accion.getNombre().equals("funcionanidada")) {
                                            nodo instru = accion.getHijos().get(0);
                                            TablaAmbitos am = getAmbitos();
                                            if (am != null) {
                                                this.ambitos.AgregarNuevoMetodo("$" + s.getNombre() + "click", instru);
                                                nuevoEventoObjeto(jbl.getName(), "$" + s.getNombre() + "click", "c");
                                            }
                                           jbl.addMouseListener(acciones(jbl.getName(), false));
                                        } else if (accion.getNombre().equals("llamada")) {
                                            nodo metodo = accion.getHijos().get(0);
                                            TablaAmbitos am = getAmbitos();
                                            if (am != null) {
                                                TablaGeneral nuevogen = am.getTablaActual(metodo.getNombre().replace("\"", ""));
                                                if (nuevogen != null) {
                                                    am.AgregarNuevoMetodo("$" + s.getNombre() + "click", nuevogen.getInst());
                                                    nuevoEventoObjeto(jbl.getName(), "$" + s.getNombre() + "click", "c");
                                                }
                                              jbl.addMouseListener(acciones(jbl.getName(), false));
                                            }
                                        }
                                        break;
                                    case 8:
                                        JPanel pan = (JPanel) val;
                                        if (accion.getNombre().equals("funcionanidada")) {
                                            nodo instru = accion.getHijos().get(0);
                                            TablaAmbitos am = getAmbitos();
                                            if (am != null) {
                                                this.ambitos.AgregarNuevoMetodo("$" + s.getNombre() + "click", instru);
                                                nuevoEventoObjeto(pan.getName(), "$" + s.getNombre() + "click", "c");
                                            }
                                           pan.addMouseListener(acciones(pan.getName(), true));
                                        } else if (accion.getNombre().equals("llamada")) {
                                            nodo metodo = accion.getHijos().get(0);
                                            TablaAmbitos am = getAmbitos();
                                            if (am != null) {
                                                TablaGeneral nuevogen = am.getTablaActual(metodo.getNombre().replace("\"", ""));
                                                if (nuevogen != null) {
                                                    am.AgregarNuevoMetodo("$" + s.getNombre() + "click", nuevogen.getInst());
                                                    nuevoEventoObjeto(pan.getName(), "$" + s.getNombre() + "click", "c");
                                                }
                                                pan.addMouseListener(acciones(pan.getName(), true));
                                            }
                                        }
                                        break;
                                    case 9: {
                                        JTextField jt = (JTextField) val;
                                        if (accion.getNombre().equals("funcionanidada")) {
                                            nodo instru = accion.getHijos().get(0);
                                            TablaAmbitos am = getAmbitos();
                                            if (am != null) {
                                                this.ambitos.AgregarNuevoMetodo("$" + s.getNombre() + "click", instru);
                                                nuevoEventoObjeto(jt.getName(), "$" + s.getNombre() + "click", "c");
                                            }
                                           jt.addMouseListener(acciones(jt.getName(), true));
                                        } else if (accion.getNombre().equals("llamada")) {
                                            nodo metodo = accion.getHijos().get(0);
                                            TablaAmbitos am = getAmbitos();
                                            if (am != null) {
                                                TablaGeneral nuevogen = am.getTablaActual(metodo.getNombre().replace("\"", ""));
                                                if (nuevogen != null) {
                                                    am.AgregarNuevoMetodo("$" + s.getNombre() + "click", nuevogen.getInst());
                                                    nuevoEventoObjeto(jt.getName(), "$" + s.getNombre() + "click", "c");
                                                }
                                            }
                                            jt.addMouseListener(acciones(jt.getName(), true));
                                        }
                                        break;
                                    }
                                    case 10:
                                        JButton jb = (JButton) val;
                                        if (accion.getNombre().equals("funcionanidada")) {
                                            nodo instru = accion.getHijos().get(0);
                                            TablaAmbitos am = getAmbitos();
                                            if (am != null) {
                                                this.ambitos.AgregarNuevoMetodo("$" + s.getNombre() + "click", instru);
                                                nuevoEventoObjeto(jb.getName(), "$" + s.getNombre() + "click", "c");
                                            }
                                            jb.addMouseListener(acciones(jb.getName(), true));
                                        } else if (accion.getNombre().equals("llamada")) {
                                            nodo metodo = accion.getHijos().get(0);
                                            TablaAmbitos am = getAmbitos();
                                            if (am != null) {
                                                TablaGeneral nuevogen = am.getTablaActual(metodo.getNombre().replace("\"", ""));
                                                if (nuevogen != null) {
                                                    am.AgregarNuevoMetodo("$" + s.getNombre() + "click", nuevogen.getInst());
                                                    nuevoEventoObjeto(jb.getName(), "$" + s.getNombre() + "click", "c");
                                                }
                                            }
                                            jb.addMouseListener(acciones(jb.getName(), true));
                                        }
                                        break;
                                    case 11:
                                        JTextArea jta = (JTextArea) val;
                                        if (accion.getNombre().equals("funcionanidada")) {
                                            nodo instru = accion.getHijos().get(0);
                                            TablaAmbitos am = getAmbitos();
                                            if (am != null) {
                                                this.ambitos.AgregarNuevoMetodo("$" + s.getNombre() + "click", instru);
                                                nuevoEventoObjeto(jta.getName(), "$" + s.getNombre() + "click", "c");
                                            }
                                           jta.addMouseListener(acciones(jta.getName(), true));
                                        } else if (accion.getNombre().equals("llamada")) {
                                            nodo metodo = accion.getHijos().get(0);
                                            TablaAmbitos am = getAmbitos();
                                            if (am != null) {
                                                TablaGeneral nuevogen = am.getTablaActual(metodo.getNombre().replace("\"", ""));
                                                if (nuevogen != null) {
                                                    am.AgregarNuevoMetodo("$" + s.getNombre() + "click", nuevogen.getInst());
                                                    nuevoEventoObjeto(jta.getName(), "$" + s.getNombre() + "click", "c");
                                                }
                                            }
                                           jta.addMouseListener(acciones(jta.getName(), true));
                                        }
                                        break;
                                    case 12:
                                        JSpinner spi = (JSpinner) val;
                                        if (accion.getNombre().equals("funcionanidada")) {
                                            nodo instru = accion.getHijos().get(0);
                                            TablaAmbitos am = getAmbitos();
                                            if (am != null) {
                                                this.ambitos.AgregarNuevoMetodo("$" + s.getNombre() + "click", instru);
                                                nuevoEventoObjeto(spi.getName(), "$" + s.getNombre() + "click", "c");
                                            }
                                            spi.addMouseListener(acciones(spi.getName(), true));
                                        } else if (accion.getNombre().equals("llamada")) {
                                            nodo metodo = accion.getHijos().get(0);
                                            TablaAmbitos am = getAmbitos();
                                            if (am != null) {
                                                TablaGeneral nuevogen = am.getTablaActual(metodo.getNombre().replace("\"", ""));
                                                if (nuevogen != null) {
                                                    am.AgregarNuevoMetodo("$" + s.getNombre() + "click", nuevogen.getInst());
                                                    nuevoEventoObjeto(spi.getName(), "$" + s.getNombre() + "click", "c");
                                                }
                                            }
                                            spi.addMouseListener(acciones(spi.getName(), true));
                                        }
                                        break;
                                    case 13: {
                                        JTable jt = (JTable) val;
                                        if (accion.getNombre().equals("funcionanidada")) {
                                            nodo instru = accion.getHijos().get(0);
                                            TablaAmbitos am = getAmbitos();
                                            if (am != null) {
                                                this.ambitos.AgregarNuevoMetodo("$" + s.getNombre() + "click", instru);
                                                nuevoEventoObjeto(jt.getName(), "$" + s.getNombre() + "click", "c");
                                            }
                                           jt.addMouseListener(acciones(jt.getName(), true));
                                        } else if (accion.getNombre().equals("llamada")) {
                                            nodo metodo = accion.getHijos().get(0);
                                            TablaAmbitos am = getAmbitos();
                                            if (am != null) {
                                                TablaGeneral nuevogen = am.getTablaActual(metodo.getNombre().replace("\"", ""));
                                                if (nuevogen != null) {
                                                    am.AgregarNuevoMetodo("$" + s.getNombre() + "click", nuevogen.getInst());
                                                    nuevoEventoObjeto(jt.getName(), "$" + s.getNombre() + "click", "c");
                                                }
                                            }
                                            jt.addMouseListener(acciones(jt.getName(), true));
                                        }
                                        break;
                                    }
                                    case 14:
                                        JComboBox jc = (JComboBox) val;
                                        if (accion.getNombre().equals("funcionanidada")) {
                                            nodo instru = accion.getHijos().get(0);
                                            TablaAmbitos am = getAmbitos();
                                            if (am != null) {
                                                this.ambitos.AgregarNuevoMetodo("$" + s.getNombre() + "click", instru);
                                                nuevoEventoObjeto(jc.getName(), "$" + s.getNombre() + "click", "c");
                                            }
                                            jc.addMouseListener(acciones(jc.getName(), true));
                                        } else if (accion.getNombre().equals("llamada")) {
                                            nodo metodo = accion.getHijos().get(0);
                                            TablaAmbitos am = getAmbitos();
                                            if (am != null) {
                                                TablaGeneral nuevogen = am.getTablaActual(metodo.getNombre().replace("\"", ""));
                                                if (nuevogen != null) {
                                                    am.AgregarNuevoMetodo("$" + s.getNombre() + "click", nuevogen.getInst());
                                                    nuevoEventoObjeto(jc.getName(), "$" + s.getNombre() + "click", "c");
                                                }
                                                jc.addMouseListener(acciones(jc.getName(), true));
                                            }
                                        }
                                        break;
                                    default:
                                        break;
                                }
                                break;
                            case "modificado":
                                switch (tipoObjeto(val)) {
                                    case 7:
                                        JLabel jbl = (JLabel) val;
                                        if (accion.getNombre().equals("funcionanidada")) {
                                            nodo instru = accion.getHijos().get(0);
                                            TablaAmbitos am = getAmbitos();
                                            if (am != null) {
                                                this.ambitos.AgregarNuevoMetodo("$" + s.getNombre() + "modificado", instru);
                                                nuevoEventoObjeto(jbl.getName(), "$" + s.getNombre() + "modificado", "m");
                                            }
                                            jbl.addPropertyChangeListener(accionescambio(jbl.getName(), true));
                                        } else if (accion.getNombre().equals("llamada")) {
                                            nodo metodo = accion.getHijos().get(0);
                                            TablaAmbitos am = getAmbitos();
                                            if (am != null) {
                                                TablaGeneral nuevogen = am.getTablaActual(metodo.getNombre().replace("\"", ""));
                                                if (nuevogen != null) {
                                                    am.AgregarNuevoMetodo("$" + s.getNombre() + "modificado", nuevogen.getInst());
                                                    nuevoEventoObjeto(jbl.getName(), "$" + s.getNombre() + "modificado", "m");
                                                }
                                                jbl.addPropertyChangeListener(accionescambio(jbl.getName(), true));
                                            }
                                        }
                                        break;
                                    case 8:
                                        JPanel pan = (JPanel) val;
                                        if (accion.getNombre().equals("funcionanidada")) {
                                            nodo instru = accion.getHijos().get(0);
                                            TablaAmbitos am = getAmbitos();
                                            if (am != null) {
                                                this.ambitos.AgregarNuevoMetodo("$" + s.getNombre() + "modificado", instru);
                                                nuevoEventoObjeto(pan.getName(), "$" + s.getNombre() + "modificado", "m");
                                            }
                                            pan.addPropertyChangeListener(accionescambio(pan.getName(), true));
                                        } else if (accion.getNombre().equals("llamada")) {
                                            nodo metodo = accion.getHijos().get(0);
                                            TablaAmbitos am = getAmbitos();
                                            if (am != null) {
                                                TablaGeneral nuevogen = am.getTablaActual(metodo.getNombre().replace("\"", ""));
                                                if (nuevogen != null) {
                                                    am.AgregarNuevoMetodo("$" + s.getNombre() + "modificado", nuevogen.getInst());
                                                    nuevoEventoObjeto(pan.getName(), "$" + s.getNombre() + "modificado", "m");
                                                }
                                                pan.addPropertyChangeListener(accionescambio(pan.getName(), true));
                                            }
                                        }
                                        break;
                                    case 9: {
                                        JTextField jt = (JTextField) val;
                                        if (accion.getNombre().equals("funcionanidada")) {
                                            nodo instru = accion.getHijos().get(0);
                                            TablaAmbitos am = getAmbitos();
                                            if (am != null) {
                                                this.ambitos.AgregarNuevoMetodo("$" + s.getNombre() + "modificado", instru);
                                                nuevoEventoObjeto(jt.getName(), "$" + s.getNombre() + "modificado", "m");
                                            }
                                            jt.addPropertyChangeListener(accionescambio(jt.getName(), true));
                                        } else if (accion.getNombre().equals("llamada")) {
                                            nodo metodo = accion.getHijos().get(0);
                                            TablaAmbitos am = getAmbitos();
                                            if (am != null) {
                                                TablaGeneral nuevogen = am.getTablaActual(metodo.getNombre().replace("\"", ""));
                                                if (nuevogen != null) {
                                                    am.AgregarNuevoMetodo("$" + s.getNombre() + "modificado", nuevogen.getInst());
                                                    nuevoEventoObjeto(jt.getName(), "$" + s.getNombre() + "modificado", "m");
                                                }
                                            }
                                            jt.addPropertyChangeListener(accionescambio(jt.getName(), true));
                                        }
                                        break;
                                    }
                                    case 10:
                                        JButton jb = (JButton) val;
                                        if (accion.getNombre().equals("funcionanidada")) {
                                            nodo instru = accion.getHijos().get(0);
                                            TablaAmbitos am = getAmbitos();
                                            if (am != null) {
                                                this.ambitos.AgregarNuevoMetodo("$" + s.getNombre() + "modificado", instru);
                                                nuevoEventoObjeto(jb.getName(), "$" + s.getNombre() + "modificado", "m");
                                            }
                                            jb.addPropertyChangeListener(accionescambio(jb.getName(), true));

                                        } else if (accion.getNombre().equals("llamada")) {
                                            nodo metodo = accion.getHijos().get(0);
                                            TablaAmbitos am = getAmbitos();
                                            if (am != null) {
                                                TablaGeneral nuevogen = am.getTablaActual(metodo.getNombre().replace("\"", ""));
                                                if (nuevogen != null) {
                                                    am.AgregarNuevoMetodo("$" + s.getNombre() + "modificado", nuevogen.getInst());
                                                    nuevoEventoObjeto(jb.getName(), "$" + s.getNombre() + "modificado", "m");
                                                }
                                            }
                                        jb.addPropertyChangeListener(accionescambio(jb.getName(), true));

                                        }
                                        break;
                                    case 11:
                                        JTextArea jta = (JTextArea) val;
                                        if (accion.getNombre().equals("funcionanidada")) {
                                            nodo instru = accion.getHijos().get(0);
                                            TablaAmbitos am = getAmbitos();
                                            if (am != null) {
                                                this.ambitos.AgregarNuevoMetodo("$" + s.getNombre() + "modificado", instru);
                                                nuevoEventoObjeto(jta.getName(), "$" + s.getNombre() + "modificado", "m");
                                            }
                                            jta.addPropertyChangeListener(accionescambio(jta.getName(), true));
                                        } else if (accion.getNombre().equals("llamada")) {
                                            nodo metodo = accion.getHijos().get(0);
                                            TablaAmbitos am = getAmbitos();
                                            if (am != null) {
                                                TablaGeneral nuevogen = am.getTablaActual(metodo.getNombre().replace("\"", ""));
                                                if (nuevogen != null) {
                                                    am.AgregarNuevoMetodo("$" + s.getNombre() + "modificado", nuevogen.getInst());
                                                    nuevoEventoObjeto(jta.getName(), "$" + s.getNombre() + "modificado", "m");
                                                }
                                            }
                                            jta.addPropertyChangeListener(accionescambio(jta.getName(), true));
                                        }
                                        break;
                                    case 12:
                                        JSpinner spi = (JSpinner) val;
                                        if (accion.getNombre().equals("funcionanidada")) {
                                            nodo instru = accion.getHijos().get(0);
                                            TablaAmbitos am = getAmbitos();
                                            if (am != null) {
                                                this.ambitos.AgregarNuevoMetodo("$" + s.getNombre() + "modificado", instru);
                                                nuevoEventoObjeto(spi.getName(), "$" + s.getNombre() + "modificado", "m");
                                            }
                                            spi.addPropertyChangeListener(accionescambio(spi.getName(), true));
                                        } else if (accion.getNombre().equals("llamada")) {
                                            nodo metodo = accion.getHijos().get(0);
                                            TablaAmbitos am = getAmbitos();
                                            if (am != null) {
                                                TablaGeneral nuevogen = am.getTablaActual(metodo.getNombre().replace("\"", ""));
                                                if (nuevogen != null) {
                                                    am.AgregarNuevoMetodo("$" + s.getNombre() + "modificado", nuevogen.getInst());
                                                    nuevoEventoObjeto(spi.getName(), "$" + s.getNombre() + "modificado", "m");
                                                }
                                            }
                                            spi.addPropertyChangeListener(accionescambio(spi.getName(), true));
                                        }
                                        break;
                                    case 13: {
                                        JTable jt = (JTable) val;
                                        if (accion.getNombre().equals("funcionanidada")) {
                                            nodo instru = accion.getHijos().get(0);
                                            TablaAmbitos am = getAmbitos();
                                            if (am != null) {
                                                this.ambitos.AgregarNuevoMetodo("$" + s.getNombre() + "modificado", instru);
                                                nuevoEventoObjeto(jt.getName(), "$" + s.getNombre() + "modificado", "m");
                                            }
                                            jt.addPropertyChangeListener(accionescambio(jt.getName(), true));
                                        } else if (accion.getNombre().equals("llamada")) {
                                            nodo metodo = accion.getHijos().get(0);
                                            TablaAmbitos am = getAmbitos();
                                            if (am != null) {
                                                TablaGeneral nuevogen = am.getTablaActual(metodo.getNombre().replace("\"", ""));
                                                if (nuevogen != null) {
                                                    am.AgregarNuevoMetodo("$" + s.getNombre() + "modificado", nuevogen.getInst());
                                                    nuevoEventoObjeto(jt.getName(), "$" + s.getNombre() + "modificado", "m");
                                                }
                                            }
                                            jt.addPropertyChangeListener(accionescambio(jt.getName(), true));
                                        }
                                        break;
                                    }
                                    case 14:
                                        JComboBox jc = (JComboBox) val;
                                        if (accion.getNombre().equals("funcionanidada")) {
                                            nodo instru = accion.getHijos().get(0);
                                            TablaAmbitos am = getAmbitos();
                                            if (am != null) {
                                                this.ambitos.AgregarNuevoMetodo("$" + s.getNombre() + "modificado", instru);
                                                nuevoEventoObjeto(jc.getName(), "$" + s.getNombre() + "modificado", "m");
                                            }
                                            jc.addPropertyChangeListener(accionescambio(jc.getName(), true));
                                        } else if (accion.getNombre().equals("llamada")) {
                                            nodo metodo = accion.getHijos().get(0);
                                            TablaAmbitos am = getAmbitos();
                                            if (am != null) {
                                                TablaGeneral nuevogen = am.getTablaActual(metodo.getNombre().replace("\"", ""));
                                                if (nuevogen != null) {
                                                    am.AgregarNuevoMetodo("$" + s.getNombre() + "modificado", nuevogen.getInst());

                                                }
                                            jc.addPropertyChangeListener(accionescambio(jc.getName(), true));
                                            }
                                        }
                                        break;
                                    default:
                                        break;
                                }
                        }
                    } else {
                        errores.agregarerror(new ER(event.getFila(), event.getCol(), nombreEvent, 3, "los eventos validos unicamente son "
                                + " CLICK|MODIFICADO|LISTO"));
                    }
                }
            }
        }
    }

    private void observadorDocumento(nodo raiz, String ambito) {
        if (raiz != null) {
            int hijos = raiz.getHijos().size();
            if (hijos == 2) {
                nodo evento = raiz.getHijos().get(0);
                nodo accion = raiz.getHijos().get(1);
                Object val = ejecutarExpresion(evento.getHijos().get(0), ambito);
                if (val != null) {
                    String nombreEvent = val.toString();
                    if (nombreEvent.equals("listo") || nombreEvent.equals("click") || nombreEvent.equals("modificado")) {
                        switch (nombreEvent) {
                            case "listo":
                                if (accion.getNombre().equals("funcionanidada")) {
                                    nodo instru = accion.getHijos().get(0);
                                    TablaAmbitos am = getAmbitos();
                                    if (am != null) {
                                        this.ambitos.AgregarNuevoMetodo("$doclisto", instru);
                                    }
                                } else if (accion.getNombre().equals("llamada")) {
                                    nodo metodo = accion.getHijos().get(0);
                                    TablaAmbitos am = getAmbitos();
                                    if (am != null) {
                                        TablaGeneral nuevogen = am.getTablaActual(metodo.getNombre().replace("\"", ""));
                                        if (nuevogen != null) {
                                            am.AgregarNuevoMetodo("$doclisto", nuevogen.getInst());
                                        }
                                    }
                                }
                                break;
                            case "click":
                                if (accion.getNombre().equals("funcionanidada")) {
                                    nodo instru = accion.getHijos().get(0);
                                    TablaAmbitos am = getAmbitos();
                                    if (am != null) {
                                        this.ambitos.AgregarNuevoMetodo("$docclick", instru);
                                    }
                                } else if (accion.getNombre().equals("llamada")) {
                                    nodo metodo = accion.getHijos().get(0);
                                    TablaAmbitos am = getAmbitos();
                                    if (am != null) {
                                        TablaGeneral nuevogen = am.getTablaActual(metodo.getNombre().replace("\"", ""));
                                        if (nuevogen != null) {
                                            am.AgregarNuevoMetodo("$doccclick", nuevogen.getInst());

                                        }
                                    }
                                }
                                break;
                            case "modificado":
                                if (accion.getNombre().equals("funcionanidada")) {
                                    nodo instru = accion.getHijos().get(0);
                                    TablaAmbitos am = getAmbitos();
                                    if (am != null) {
                                        this.ambitos.AgregarNuevoMetodo("$docmodificado", instru);
                                    }
                                } else if (accion.getNombre().equals("llamada")) {
                                    nodo metodo = accion.getHijos().get(0);
                                    TablaAmbitos am = getAmbitos();
                                    if (am != null) {
                                        TablaGeneral nuevogen = am.getTablaActual(metodo.getNombre().replace("\"", ""));
                                        if (nuevogen != null) {
                                            am.AgregarNuevoMetodo("$docclick", nuevogen.getInst());

                                        }
                                    }
                                }
                                break;
                        }
                    } else {
                        errores.agregarerror(new ER(evento.getFila(), evento.getCol(), nombreEvent, 3, "los eventos validos unicamente son "
                                + "CLICK|MODIFICADO|LISTO"));
                    }
                }
            }
        }
    }

    private void mostrarMensaje(nodo raiz, String ambito) {
        if (raiz != null) {
            nodo expr = raiz.getHijos().get(0);
            Object val = ejecutarExpresion(expr, ambito);
            if (val != null) {
                JOptionPane.showMessageDialog(null, val);
            }
        }
    }

    private void asignarValor(nodo raiz, String ambito) {
        if (raiz != null) {
            nodo tipoval = raiz.getHijos().get(1);
            nodo iden = raiz.getHijos().get(0);
            Simbolo s;
            Object val;
            switch (tipoval.getNombre()) {
                case "valor":
                    val = ejecutarExpresion(tipoval.getHijos().get(0), ambito);
                    s = existeVariable(iden.getNombre(), ambito);
                    if(s==null){
                        s = existeVariable(iden.getNombre(), "global");
                    }
                    if (s != null && !s.isArray()) {
                        if (val != null) {
                            s.setValor(val);
                        } else {
                            this.errores.agregarerror(new ER(s.getFila(), s.getCol(), s.getNombre() + " = nulo",
                                    3, "No se le puede asignar un valor nulo a las variables"));
                        }
                    } else {
                        if (s == null) {
                            this.errores.agregarerror(new ER(iden.getFila(), iden.getCol(), iden.getNombre(),
                                    3, "No se puede realizar la asignacion, variable no encontrada"));
                        } else if (s.isArray()) {
                            this.errores.agregarerror(new ER(iden.getFila(), iden.getCol(), iden.getNombre(),
                                    3, "No se puede asignar asignacion sin ingresar la posicion del vector"));
                        }
                    }
                    break;
                case "listavector":
                    ArrayList<Object> valo = new ArrayList<>();
                    s = existeVariable(iden.getNombre(), ambito);
                    if(s==null){
                        s = existeVariable(iden.getNombre(), "global");
                    }
                    if (s != null) {
                        nodo ldatos = tipoval.getHijos().get(0);
                        for (nodo n : ldatos.getHijos()) {
                            if (ejecutarExpresion(n, ambito) == null) {
                                this.error = true;
                                break;
                            } else {
                                valo.add(ejecutarExpresion(n, ambito));
                            }
                        }
                        if (this.error = false) {
                            s.setValor(valo);
                            s.setArray(true);
                        } else {
                            this.errores.agregarerror(new ER(iden.getFila(), iden.getCol(), iden.getNombre(),
                                    3, "No se puede realizar la asignacion con valores nulos al vector"));
                            this.error = false;
                        }
                    } else {
                        if (s == null) {
                            this.errores.agregarerror(new ER(iden.getFila(), iden.getCol(), iden.getNombre(),
                                    3, "No se puede realizar la asignacion, variable no encontrada"));
                        }
                    }
                    break;
                case "posicionvector":
                    //VAL = INDEX
                    val = ejecutarExpresion(tipoval.getHijos().get(0), ambito);
                    s = existeVariable(iden.getNombre(), ambito);
                    if(s==null){
                        s = existeVariable(iden.getNombre(), "global");
                    }
                    if (s != null && s.isArray()) {
                        int tipo = tipoObjeto(val);
                        if (tipo == 2) {
                            ArrayList<Object> array = (ArrayList<Object>) s.getValor();
                            int pos = Integer.parseInt(val.toString());
                            nodo valor = tipoval.getHijos().get(1);
                            if (pos < 0) {
                                this.errores.agregarerror(new ER(s.getFila(), s.getCol(), s.getNombre() + "[" + s.getTamvector() + "]", 3,
                                        "El indice no puede ser menor a 0: " + val.toString()));
                            } else {
                                if (pos >= s.getTamvector()) {
                                    this.errores.agregarerror(new ER(s.getFila(), s.getCol(), s.getNombre() + "[" + s.getTamvector() + "]", 3,
                                            "El indice sobrepasa la longitud del vector: " + val.toString()));
                                } else {
                                    array.set(pos, ejecutarExpresion(valor.getHijos().get(0), ambito));
                                    System.out.println(array.toString().replace("[", "").replace("]", ""));
                                }
                            }

                        }
                    } else {
                        if (s == null) {
                            this.errores.agregarerror(new ER(iden.getFila(), iden.getCol(), iden.getNombre(),
                                    3, "No se puede realizar la asignacion, variable no encontrada"));
                        } else if (!s.isArray()) {
                            this.errores.agregarerror(new ER(iden.getFila(), iden.getCol(), iden.getNombre(),
                                    3, "No se puede asignar asignacion con posicion a una variable normal"));
                        }
                    }

            }
        }
    }

    private Object EjecutarSwitch(nodo raiz, String ambito) {
        Object vale = null;
        if (raiz != null) {
            int hijos = raiz.getHijos().size();
            if (hijos == 2) {
                nodo expr = raiz.getHijos().get(0);
                nodo listacasos = raiz.getHijos().get(1);
                Object valselect = ejecutarExpresion(expr.getHijos().get(0), ambito);
                int tipoval = tipoObjeto(valselect);
                //RECORRIENDO LOS CASES                                
                boolean entro = false;
                for (int i = 0; i < listacasos.getHijos().size() && detener == false && retorno == false; i++) {
                    /*if(listacasos.getHijos().size()-1==i && entro==true){
                        i=0;
                    }*/
                    nodo casos = listacasos.getHijos().get(i);
                    nodo valor = casos.getHijos().get(0);
                    nodo instr = casos.getHijos().get(1);
                    if (entro == false) {
                        Object val = ejecutarExpresion(valor.getHijos().get(0), ambito);
                        int tipo = tipoObjeto(val);
                        if (tipo == tipoval) {
                            //TODO OK
                            if (valselect.equals(val)) {
                                entro = true;
                                vale = ejecutarMetodo(instr, ambito);
//                            break;
                            }
                        } else if ((tipo == 2 && tipoval == 3) || (tipo == 3 && tipoval == 2)) {
                            //ER.AN ENTEROS AMBOS
                            if (valselect.equals(val)) {
                                entro = true;
                                vale = ejecutarMetodo(instr, ambito);
                                //                          break;
                            }
                        }
                    } else {
                        vale = ejecutarMetodo(instr, ambito);
                    }
                }
            } else if (hijos == 3) {
                nodo expr = raiz.getHijos().get(0);
                nodo listacasos = raiz.getHijos().get(1);
                nodo defecto = raiz.getHijos().get(2);
                Object valselect = ejecutarExpresion(expr.getHijos().get(0), ambito);
                int tipoval = tipoObjeto(valselect);

                boolean defect = true;
                boolean entro = false;
                for (int i = 0; i < listacasos.getHijos().size() && detener == false && retorno == false; i++) {
                    /*if(listacasos.getHijos().size()-1==i && entro==true){
                        i=0;
                    }*/
                    nodo casos = listacasos.getHijos().get(i);
                    nodo valor = casos.getHijos().get(0);
                    nodo instr = casos.getHijos().get(1);
                    if (entro == false) {
                        Object val = ejecutarExpresion(valor.getHijos().get(0), ambito);
                        int tipo = tipoObjeto(val);
                        if (tipo == tipoval) {
                            //TODO OK
                            if (valselect.equals(val)) {
                                entro = true;
                                vale = ejecutarMetodo(instr, ambito);
//                            break;
                            }
                        } else if ((tipo == 2 && tipoval == 3) || (tipo == 3 && tipoval == 2)) {
                            //ER.AN ENTEROS AMBOS
                            if (valselect.equals(val)) {
                                entro = true;
                                vale = ejecutarMetodo(instr, ambito);
                                //                          break;
                            }
                        }
                    } else {
                        vale = ejecutarMetodo(instr, ambito);
                    }
                }
                if (defect == false) {
                    vale = ejecutarMetodo(defecto.getHijos().get(0), ambito);
                }
            }
        }

        return vale;
    }

    private Object EjecutarWhile(nodo raiz, String ambito) {
        Object vale = null;
        if (raiz != null) {
            int hijos = raiz.getHijos().size();
            if (hijos == 2) {
                nodo cond = raiz.getHijos().get(0);
                nodo instr = raiz.getHijos().get(1);
                Object val = ejecutarExpresion(cond.getHijos().get(0), ambito);
                if (val == null) {
                    return vale;
                }
                if (tipoObjeto(val) == 4) {
                    if ((Boolean) val == true) {
                        vale = ejecutarMetodo(instr, ambito);
                        if (retorno == false && detener == false) {
                            vale = EjecutarWhile(raiz, ambito);
                        }
                    }
                }
            }
        }
        return vale;
    }

    private Object EjecutarFor(nodo raiz, String ambito) {
        //4 HIJOS AMBOS CASOS
        //CASO 1: 1ER HIJO = ASIGNA->E 
        //CASO 2: 1ER HIJO = ID
        Object val = null;
        if (raiz != null) {
            int hijos = raiz.getHijos().size();
            if (hijos == 4) {
                nodo var = raiz.getHijos().get(0);

                if (var.getNombre().equals("valor")) {
                    nodo id = var.getHijos().get(0);
                    Simbolo s = ambitos.getTablaActual(ambito).existevariable(id.getNombre());
                    if (s != null) {
                        s.setValor(ejecutarExpresion(var.getHijos().get(1), ambito));
                        val = ejecutarPara(raiz, ambito);
                    }
                }
            }

        }
        return val;
    }

    private Object ejecutarPara(nodo raiz, String ambito) {
        Object vale = null;
        nodo cond = raiz.getHijos().get(1);
        nodo instr = raiz.getHijos().get(3);

        Object val = ejecutarExpresion(cond.getHijos().get(0), ambito);
        if (val == null) {
            return vale;
        }
        if (tipoObjeto(val) == 4) {
            if ((Boolean) val == true) {
                vale = ejecutarMetodo(instr, ambito);
                ejecutarINCDEC(raiz, ambito);
                //vale = ejecutarPara(raiz, ambito);
                if (retorno == false && detener == false) {
                    vale = ejecutarPara(raiz, ambito);
                }
            }
        }
        return vale;
    }

    private void ejecutarINCDEC(nodo raiz, String ambito) {
        if (raiz != null) {
            nodo id = raiz.getHijos().get(0);
            nodo var = id.getHijos().get(0);
            nodo operacion = raiz.getHijos().get(2);
            nodo ope = operacion.getHijos().get(0);
            if (ope.getNombre().equals("++")) {
                Simbolo aux = ambitos.getTablaActual(ambito).existevariable(var.getNombre());
                if (aux != null) {
                    Object val = aux.getValor();
                    int tipoval = tipoObjeto(val);
                    if (tipoval == 3) {
                        double vale = (Double) val + 1;
                        aux.setValor(vale);
                    } else if (tipoval == 2) {
                        int vale = (Integer) val + 1;
                        aux.setValor(vale);
                    }
                }
            } else if (ope.getNombre().equals("--")) {
                Simbolo aux = ambitos.getTablaActual(ambito).existevariable(var.getNombre());
                if (aux != null) {
                    Object val = aux.getValor();
                    int tipoval = tipoObjeto(val);
                    if (tipoval == 3) {
                        double vale = (Double) val - 1;
                        aux.setValor(vale);
                    } else if (tipoval == 2) {
                        int vale = (Integer) val - 1;
                        aux.setValor(vale);
                    }
                }
            }
        }
    }

    private Object ejecutarIF(nodo raiz, String ambito) {
        Object vale = null;
        if (raiz != null) {
            int hijos = raiz.getHijos().size();
            //HIJOS==2 TIENE IF E INSTRUCC
            //HIJOS == 3 TIENE IF Y ELSE
            if (hijos == 2) {
                nodo cond = raiz.getHijos().get(0);
                Object val = ejecutarExpresion(cond.getHijos().get(0), ambito);
                if (val == null) {
                    return vale;
                }
                if (tipoObjeto(val) == 4) {
                    if ((Boolean) val == true) {
                        vale = ejecutarMetodo(raiz.getHijos().get(1), ambito);
                    }
                }
            } else if (hijos == 3) {
                nodo cond = raiz.getHijos().get(0);
                Object val = ejecutarExpresion(cond.getHijos().get(0), ambito);
                if (val == null) {
                    return vale;
                }
                if (tipoObjeto(val) == 4) {
                    if ((Boolean) val == true) {
                        vale = ejecutarMetodo(raiz.getHijos().get(1), ambito);
                    } else {
                        nodo sino = raiz.getHijos().get(2);
                        vale = ejecutarMetodo(sino.getHijos().get(0), ambito);
                    }
                }
            }
        }
        return vale;
    }

    private void ejecutarImprimir(nodo raiz, String ambito) {
        if (raiz != null) {
            Object val = ejecutarExpresion(raiz.getHijos().get(0), ambito);
            if (val != null) {
               // System.out.println(val);
               consola.agregarerror(new ER(raiz.getFila(), raiz.getCol(), val.toString(), 0, "salida"));
            }
        }
    }

    private void declararMetodo(nodo raiz, String ambito) {
        int hijos = raiz.getHijos().size();
        if (hijos == 2) {
            //FUNCION SIN PARAMETROS
            nodo nombre = raiz.getHijos().get(0);
            nodo listainstru = raiz.getHijos().get(1);

            TablaAmbitos ta = ambitos;
            ta.AgregarNuevoMetodo(nombre.getNombre().toLowerCase(), listainstru);
        } else if (hijos == 3) {
            //FUNCION CON PARAMETROS
            nodo nombre = raiz.getHijos().get(0);
            nodo listaID = raiz.getHijos().get(1);
            nodo listainstru = raiz.getHijos().get(2);

            boolean existe = existeVariable(listaID, "global");
            if (existe == false) {
                TablaAmbitos ta = ambitos;
                ta.AgregarNuevoMetodo(nombre.getNombre().toLowerCase() + "%" + listaID.getHijos().size(), listainstru);

                TablaGeneral ts = ambitos.getTablaActual(nombre.getNombre().toLowerCase() + "%" + listaID.getHijos().size());

                for (nodo hijo : listaID.getHijos()) {
                    nodo variable = hijo.getHijos().get(0);
                    ts.nuevavariable(variable.getNombre().toLowerCase(), null, variable.getFila(), variable.getCol(), false, "parametro");
                }
            }

        }
    }

    private void declararvariable(nodo raiz, String ambito) {
        if (raiz != null) {
            int hijos = raiz.getHijos().size();
            //hijos = 1; solo declaracion
            //hijos = 2; declaracion con asignacion
            nodo lid = raiz.getHijos().get(0);

            //if (existe == false) {
            if (hijos == 1) {
                for (int i = 0; i < lid.getHijos().size(); i++) {
                    nodo id = lid.getHijos().get(i);
                    Simbolo existe = existeVariable(id.getNombre(), "global");
                    if (existe == null) {
                        TablaGeneral ta = ambitos.getTablaActual(ambito);
                        ta.nuevavariable(id.getNombre().toLowerCase(), null, id.getFila(), id.getCol(), id.isArray(), "variable");
                    } else {
                        this.errores.agregarerror(new ER(id.getFila(), id.getCol(), id.getNombre(), 3,
                                "La variable " + id.getNombre() + " ya existe en el ambito global"));
                    }
                }
            } else if (hijos == 2) {
                if (raiz.getHijos().get(1).getNombre().equals("valor")) {
                    nodo expr = raiz.getHijos().get(1).getHijos().get(0);
                    Object valor = ejecutarExpresion(expr, ambito);
                    //System.out.println(valor);
                    for (int i = 0; i < lid.getHijos().size(); i++) {
                        nodo id = lid.getHijos().get(i);
                        if (valor != null) {
                            Simbolo existe = existeVariable(id.getNombre(), "global");
                            if (existe == null) {
                                TablaGeneral ta = ambitos.getTablaActual(ambito);
                                ta.nuevavariable(id.getNombre().toLowerCase(), valor, id.getFila(), id.getCol(), id.isArray(), "variable");
                            } else {
                                this.errores.agregarerror(new ER(id.getFila(), id.getCol(), id.getNombre(), 3,
                                        "La variable " + id.getNombre() + " ya existe en el ambito global"));
                            }
                        } else {
                            this.errores.agregarerror(new ER(id.getFila(), id.getCol(), id.getNombre() + " = nulo",
                                    3, "No se le puede asignar un valor nulo a las variables"));
                        }
                    }
                } else if (raiz.getHijos().get(1).getNombre().equals("listavector")) {
                    //VECTORES
                    nodo lval = raiz.getHijos().get(1).getHijos().get(0);
                    ArrayList<Object> val = new ArrayList<>();
                    for (nodo s : lval.getHijos()) {
                        if (ejecutarExpresion(s, ambito) == null) {
                            this.error = true;
                            break;
                        }
                        val.add(ejecutarExpresion(s, ambito));
                    }
                    //System.out.println(valor);
                    for (int i = 0; i < lid.getHijos().size(); i++) {
                        nodo id = lid.getHijos().get(i);
                        if (this.error == false) {
                            Simbolo existe = existeVariable(id.getNombre(), "global");
                            if (existe == null) {
                                TablaGeneral ta = ambitos.getTablaActual(ambito);
                                ta.nuevavariable(id.getNombre().toLowerCase(), val, id.getFila(), id.getCol(), true, "vector", val.size());
                            } else {
                                this.errores.agregarerror(new ER(id.getFila(), id.getCol(), id.getNombre(), 3,
                                        "La variable " + id.getNombre() + " ya existe en el ambito global"));
                            }

                        } else {
                            this.errores.agregarerror(new ER(id.getFila(), id.getCol(), id.getNombre() + " = nulo",
                                    3, "No se le puede asignar un valor nulo a los elementos del vector"));
                            this.error = false;
                        }
                    }
                } else if (raiz.getHijos().get(1).getNombre().equals("posicionvector")) {
                    //VECTORES
                    nodo expr = raiz.getHijos().get(1).getHijos().get(0);
                    Object valor = ejecutarExpresion(expr, ambito);

                    //System.out.println(valor);
                    for (int i = 0; i < lid.getHijos().size(); i++) {
                        nodo id = lid.getHijos().get(i);
                        if (valor != null) {
                            ArrayList<Object> vect = new ArrayList<>();
                            int inid = Integer.parseInt(valor.toString());
                            for (int i1 = 0; i1 < inid; i1++) {
                                vect.add(null);
                            }
                            Simbolo s1 = existeVariable(id.getNombre(), "global");
                            if (tipoObjeto(valor) == 2) {
                                if (s1 == null) {
                                    TablaGeneral ta = ambitos.getTablaActual(ambito);
                                    ta.nuevavariable(id.getNombre().toLowerCase(), vect, id.getFila(), id.getCol(), true, "vector", Integer.parseInt(valor.toString()));
                                } else {
                                    this.errores.agregarerror(new ER(id.getFila(), id.getCol(), id.getNombre(), 3,
                                            "La variable " + id.getNombre() + " ya existe en el ambito global"));
                                }
                            }
                        } else {
                            this.errores.agregarerror(new ER(id.getFila(), id.getCol(), id.getNombre() + " tamanio nulo",
                                    3, "No se le puede asignar un tamanio nulo al vector"));
                        }

                    }
                }
            }
            //}
        }
    }

    private Object ejecutarExpresion(nodo raiz, String ambito) {
        if (raiz != null) {
            int hijos = raiz.getHijos().size();
            if (hijos == 2) {
                switch (raiz.getNombre()) {
                    case "&&":
                        return ejecutarAND(raiz.getHijos().get(0), raiz.getHijos().get(1), ambito);
                    case "||":
                        return ejecutarOR(raiz.getHijos().get(0), raiz.getHijos().get(1), ambito);
                    case ">":
                        return ejecutarMayor(raiz.getHijos().get(0), raiz.getHijos().get(1), ambito);
                    case ">=":
                        return ejecutarMayorIgual(raiz.getHijos().get(0), raiz.getHijos().get(1), ambito);
                    case "<":
                        return ejecutarMenor(raiz.getHijos().get(0), raiz.getHijos().get(1), ambito);
                    case "<=":
                        return ejecutarMenorIgual(raiz.getHijos().get(0), raiz.getHijos().get(1), ambito);
                    case "==":
                        return ejecutarIgual(raiz.getHijos().get(0), raiz.getHijos().get(1), ambito);
                    case "!=":
                        return ejecutardiferente(raiz.getHijos().get(0), raiz.getHijos().get(1), ambito);
                    case "+":
                        return ejecutarSuma(raiz.getHijos().get(0), raiz.getHijos().get(1), ambito);
                    case "-":
                        return ejecutarResta(raiz.getHijos().get(0), raiz.getHijos().get(1), ambito);
                    case "*":
                        return ejecutarMulti(raiz.getHijos().get(0), raiz.getHijos().get(1), ambito);
                    case "/":
                        return ejecutarDivi(raiz.getHijos().get(0), raiz.getHijos().get(1), ambito);
                    case "%":
                        return ejecutarMod(raiz.getHijos().get(0), raiz.getHijos().get(1), ambito);
                    case "^":
                        return ejecutarPot(raiz.getHijos().get(0), raiz.getHijos().get(1), ambito);
                    case "llamada":
                         Object val = ejecutarLLamada(raiz, ambito);
                        this.retorno=false;
                        return val;
                    case "accesovector":
                        return accesoVector(raiz, ambito);
                }
            } else if (hijos == 1) {
                switch (raiz.getNombre()) {
                    case "!":
                        return ejecutarNOT(raiz.getHijos().get(0), ambito);
                    case "num":
                        return Integer.parseInt(raiz.getHijos().get(0).getNombre());
                    case "decimal":
                        return Double.parseDouble(raiz.getHijos().get(0).getNombre());
                    case "iden":
                        return evaluarID(raiz.getHijos().get(0), ambito);
                    case "false":
                        return false;
                    case "true":
                        return true;
                    case "cadena":
                    case "date":
                    case "datetime":
                        return raiz.getHijos().get(0).getNombre().replace("\"", "");
                    case "llamada":
                        return ejecutarLLamada(raiz, ambito);
                    case "incrementar":
                        return incrementarValor(raiz.getHijos().get(0), ambito);
                    case "decrementar":
                        return decrementarValor(raiz.getHijos().get(0), ambito);
                    case "-":
                        return ejecutarNegativo(raiz.getHijos().get(0), ambito);
                    case "documento":
                        return obtenerDocumento(raiz.getHijos().get(0), "objetos");
                    case "atexto":
                        return cadenaATexto(raiz.getHijos().get(0), ambito);
                    case "conteo":
                        return conteoVector(raiz.getHijos().get(0), ambito);
                }
            }
            /*else if (hijos >= 3 && hijos <= 4) {
                switch (raiz.getNombre()) {
                    case "date":
                        return evaluarDatetime(raiz, ambito);
                }

            }*/
        }
        return null;
    }
    
    private Object conteoVector(nodo raiz, String ambito){
       if (raiz != null) {
            Simbolo s = existeVariable(raiz.getNombre(), ambito);
            if(s==null){
                s = existeVariable(raiz.getNombre(), "global");
            }
            if (s.getTipo().equals("vector") && s.isArray() == true) {
                if (s.getValor() != null) {
                    ArrayList<Object> aux = (ArrayList<Object>) s.getValor();
                    int contador = 0;
                    for(int i = 0; i < aux.size();i++){
                        if(aux.get(i)!=null){
                            contador++;
                        }
                    }
                    //String cadena = aux.toString().replace("[", "{").replace("]", "}").replace(" ", "");
                    //System.out.println(cadena);
    //                return aux.size();
    return contador;
                }
            }
        }
        return null; 
    }

    private Object cadenaATexto(nodo raiz, String ambito) {
        if (raiz != null) {
            Simbolo s = existeVariable(raiz.getNombre(), ambito);
            if(s==null){
                s=existeVariable(raiz.getNombre(), "global");
            }
            //if (s.getTipo().equals("vector") /*&& s.getValor()!=null*/) {
                if (s.getValor() != null) {
                    try{
                    ArrayList<Object> aux = (ArrayList<Object>) s.getValor();
                    String cadena = aux.toString().replace("[", "{").replace("]", "}").replace(" ", "");
                    //System.out.println(cadena);
                    return cadena;
                    }catch(Exception e){
                        errores.agregarerror(raiz.getFila(), raiz.getCol(), raiz.getNombre(), 3, 
                                "Unicamente es valido convertir a texto variables de vector");
                    }
                }
            //}
        }
        return null;
    }

    private Object obtenerDocumento(nodo raiz, String ambito) {
        if (raiz != null) {
            nodo id = raiz.getHijos().get(0);
            Simbolo s = existeVariable(id.getNombre().replace("\"", ""), ambito);
            if (s != null) {
                return s.getValor();
            }
        }
        return null;
    }

    private Object ejecutarNegativo(nodo raiz, String ambito) {
        Object val = ejecutarExpresion(raiz, ambito);
        int tipo = tipoObjeto(val);

        if (tipo == 0) {
            return null;
        }

        switch (tipo) {
            case 2:
                int num = Integer.parseInt(val.toString()) * -1;
                return num;
            case 3:
                double db = Double.parseDouble(val.toString()) * -1;
                return db;
            case 4:
                int valu;
                if ((boolean) val == true) {
                    valu = 1;
                } else {
                    valu = 0;
                }
                return valu * -1;
            default:
                errores.agregarerror(new ER(raiz.getFila(), raiz.getCol(), " - " + val.toString(), 3,
                        "no puede operar con valor negativo a  tipo " + TipoString(tipo)));
        }

        return null;
    }

    private Object incrementarValor(nodo raiz, String ambito) {
        Object val = ejecutarExpresion(raiz, ambito);
        int tipo = tipoObjeto(val);

        if (tipo == 0) {
            return null;
        }

        switch (tipo) {
            case 2:
                int num = Integer.parseInt(val.toString());
                return num + 1;
            case 3:
                double db = Double.parseDouble(val.toString());
                return db + 1;
            case 4:
                int valu;
                if ((boolean) val == true) {
                    valu = 1;
                } else {
                    valu = 0;
                }
                return valu + 1;
            default:
                errores.agregarerror(new ER(raiz.getFila(), raiz.getCol(), val.toString() + "++", 3,
                        "no puede incrementarse valores de tipo " + TipoString(tipo)));
        }
        return null;
    }

    private Object decrementarValor(nodo raiz, String ambito) {
        Object val = ejecutarExpresion(raiz, ambito);
        int tipo = tipoObjeto(val);

        if (tipo == 0) {
            return null;
        }
        switch (tipo) {
            case 2:
                int num = Integer.parseInt(val.toString());
                return num - 1;
            case 3:
                double db = Double.parseDouble(val.toString());
                return db - 1;
            case 4:
                int valu;
                if ((boolean) val == true) {
                    valu = 1;
                } else {
                    valu = 0;
                }
                return valu - 1;
            default:
                errores.agregarerror(new ER(raiz.getFila(), raiz.getCol(), val.toString() + "--", 3,
                        "no puede decrementarse valores de tipo " + TipoString(tipo)));
        }
        return null;
    }

    private Object accesoVector(nodo raiz, String ambito) {
        if (raiz != null) {
            nodo iden = raiz.getHijos().get(0);
            nodo ind = raiz.getHijos().get(1);

            Simbolo s = existeVariable(iden.getNombre(), ambito);
            if (s != null && s.isArray()) {
                Object val = ejecutarExpresion(ind, ambito);
                int tipo = tipoObjeto(val);
                if (tipo == 2) {
                    int indice = Integer.parseInt(val.toString());
                    if (indice >= 0) {
                        if (indice < s.getTamvector()) {
                            ArrayList<Object> array = (ArrayList<Object>) s.getValor();
                            return array.get(indice);
                        } else {
                            this.errores.agregarerror(new ER(s.getFila(), s.getCol(), s.getNombre() + "[" + s.getTamvector() + "]", 3,
                                    "El indice sobrepasa la longitud del vector: " + val.toString()));
                            return null;
                        }
                    } else {
                        this.errores.agregarerror(new ER(s.getFila(), s.getCol(), s.getNombre() + "[" + s.getTamvector() + "]", 3,
                                "El indice no puede ser menor a 0: " + val.toString()));
                        return null;
                    }
                } else {
                    this.errores.agregarerror(new ER(s.getFila(), s.getCol(), s.getNombre() + "[" + s.getTamvector() + "]", 3,
                            "El indice es incosistente: " + val.toString()));
                    return null;
                }
            }
        }

        return null;
    }

    private Object ejecutarLLamada(nodo raiz, String ambito) {
        Object val = null;
        if (raiz != null) {
            int hijos = raiz.getHijos().size();
            if (hijos == 1) {
                //LLAMADA SIN PARAMETROS
                nodo nombre = raiz.getHijos().get(0);
                ambito = nombre.getNombre();
                TablaGeneral ta = ambitos.getTablaActual(ambito.toLowerCase());
                if (ta != null) {
                    return ejecutarMetodo(ta.getInst(), ambito.toLowerCase());
                } else {
                    errores.agregarerror(new ER(nombre.getFila(), nombre.getCol(), ambito, 3,
                            "El metodo no existe"));
                }

            } else if (hijos == 2) {
                //LLAMADA CON PARAMETROS
                nodo nombre = raiz.getHijos().get(0);
                nodo ldatos = raiz.getHijos().get(1);
                String nuevoambito = (nombre.getNombre() + "%" + ldatos.getHijos().size()).toLowerCase();
                TablaGeneral ta = ambitos.getTablaActual(nuevoambito);
                if (ta != null) {
                    //RECOLECTANDO LOS VALORES DE LAS VARIABLES
                    //int sizeta = ta.getPila().size();
                    //TablaSimbolo ts = ta.getPila().get(sizeta-1);
                    TablaSimbolo ts = ta.getPila().firstElement();
                    Object[] valoresviejos = new Object[20];
                    //  Object [] valores = new Object[20];
                    int contadorObj = 0;//contadorVie=0;

                    for (Simbolo s : ts.getSimbolos()) {
                        // tsaux.nuevosimbolo(s.getFila(), s.getCol(), s.getNombre(), s.isArray(), s.getValor());
                        valoresviejos[contadorObj] = s.getValor();
                        contadorObj++;
                    }

                    TablaSimbolo tsaux = ta.getPila().firstElement();
                    
                    //TablaSimbolo tsaux = ta.getPila().get(sizeta-1);
                    for (int i = 0; i < ldatos.getHijos().size(); i++) {
                        Simbolo s = tsaux.getSimbolos().get(i);
                        Simbolo s1= ts.getSimbolos().get(i);
                        val = ejecutarExpresion(ldatos.getHijos().get(i), ambito);
                        if(s1!=null){
                            s.setArray(s1.isArray());
                            s.setTamvector(s1.getTamvector());
                        }
                        s.setValor(val);
                        
                    }
                    //ta.aumentarAmbito();
                    //ta.aumentarAmbito();
                    // ta.aumentarAmbito();
                    val = ejecutarMetodo(ta.getInst(), nuevoambito);

                    for (int i = 0; i < contadorObj; i++) {
                        Simbolo s = tsaux.getSimbolos().get(i);
                        if (s != null) {
                            s.setValor(valoresviejos[i]);
                        }
                    }
                    //  ta.disminuirAmbito();
                    //ta.disminuirAmbito();
                    //ta.disminuirAmbito();
                    this.retorno = false;
                } else {
                    errores.agregarerror(new ER(0, 0, ambito, 3,
                            "El metodo no existe"));
                }
            }
        }
        return val;
    }

    private Object evaluarID(nodo raiz, String ambito) {
        Object val = null;
        if (raiz != null) {
            TablaGeneral tg = ambitos.getTablaActual(ambito);
            if (tg != null) {
                Simbolo s = tg.existevariable(raiz.getNombre());
                if (s != null) {
                    return s.getValor();
                } else {
                    tg = ambitos.getTablaActual("global");
                    if (tg != null) {
                        s = tg.existevariable(raiz.getNombre());
                        if (s != null) {
                            return s.getValor();
                        } else {
                            errores.agregarerror(new ER(0, 0, raiz.getNombre(), 3,
                                    "no existe la variable"));
                        }
                    }
                }
            }
        }
        return val;
    }

    private Object ejecutarSuma(nodo izq, nodo der, String ambito) {
        Object val1 = ejecutarExpresion(izq, ambito);
        Object val2 = ejecutarExpresion(der, ambito);
        int tipo1 = tipoObjeto(val1);
        int tipo2 = tipoObjeto(val2);

        if (tipo1 == 0 || tipo2 == 0) {
            return null;
        }
        if (tipo1 == 1) {
            //TEXTO
            switch (tipo2) {
                case 1:
                    return val1.toString() + val2.toString();
                case 2:
                    return val1.toString() + val2.toString();
                case 3:
                    return val1.toString() + val2.toString();
                case 4:
                    return val1.toString() + val2.toString();
                case 5:
                    return val1.toString() + val2.toString();
                case 6:
                    return val1.toString() + val2.toString();
            }
        } else if (tipo1 == 2) {
            switch (tipo2) {
                case 1:
                    return val1.toString() + val2.toString();
                case 2:
                    return Integer.parseInt(val1.toString()) + Integer.parseInt((val2.toString()));
                case 3:
                    return Double.parseDouble(val1.toString()) + Double.parseDouble((val2.toString()));
                case 4:
                    if ((boolean) val2 == true) {
                        val2 = 1;
                    } else if ((boolean) val2 == false) {
                        val2 = 0;
                    }
                    return Integer.parseInt(val1.toString()) + Integer.parseInt((val2.toString()));
                default:
                    errores.agregarerror(new ER(0, 0, val1.toString() + " + " + val2.toString(), 3,
                            "no se pueden sumar valores de tipo " + TipoString(tipo1) + "con tipo " + TipoString(tipo2)));
            }
        } else if (tipo1 == 3) {
            switch (tipo2) {
                case 1:
                    return val1.toString() + val2.toString();
                case 2:
                    return Double.parseDouble(val1.toString()) + Double.parseDouble((val2.toString()));
                case 3:
                    return Double.parseDouble(val1.toString()) + Double.parseDouble((val2.toString()));
                case 4:
                    if ((boolean) val2 == true) {
                        val2 = 1;
                    } else if ((boolean) val2 == false) {
                        val2 = 0;
                    }
                    return Double.parseDouble(val1.toString()) + Double.parseDouble((val2.toString()));
                default:
                    errores.agregarerror(new ER(0, 0, val1.toString() + " + " + val2.toString(), 3,
                            "no se pueden sumar valores de tipo " + TipoString(tipo1) + "con tipo " + TipoString(tipo2)));
            }
        } else if (tipo1 == 4) {
            switch (tipo2) {
                case 1:
                    return val1.toString() + val2.toString();
                case 2:
                    if ((boolean) val1 == true) {
                        val1 = 1;
                    } else if ((boolean) val1 == false) {
                        val1 = 0;
                    }
                    return Integer.parseInt(val1.toString()) + Integer.parseInt((val2.toString()));
                case 3:
                    if ((boolean) val1 == true) {
                        val1 = 1;
                    } else if ((boolean) val1 == false) {
                        val1 = 0;
                    }
                    return Double.parseDouble(val1.toString()) + Double.parseDouble((val2.toString()));
                case 4:
                    return ejecutarOR(izq, der, ambito);
                default:
                    errores.agregarerror(new ER(0, 0, val1.toString() + " + " + val2.toString(), 3,
                            "no se pueden sumar valores de tipo " + TipoString(tipo1) + "con tipo " + TipoString(tipo2)));

            }
        } else if (tipo1 == 5) {
            switch (tipo2) {
                case 1:
                    return val1.toString() + val2.toString();
                default:
                    errores.agregarerror(new ER(0, 0, val1.toString() + " + " + val2.toString(), 3,
                            "no se pueden sumar valores de tipo " + TipoString(tipo1) + "con tipo " + TipoString(tipo2)));
            }
        } else if (tipo1 == 6) {
            switch (tipo2) {
                case 1:
                    return val1.toString() + val2.toString();
                default:
                    errores.agregarerror(new ER(0, 0, val1.toString() + " + " + val2.toString(), 3,
                            "no se pueden sumar valores de tipo " + TipoString(tipo1) + "con tipo " + TipoString(tipo2)));
            }
        }
        return null;
    }

    private Object ejecutarResta(nodo izq, nodo der, String ambito) {
        Object val1 = ejecutarExpresion(izq, ambito);
        Object val2 = ejecutarExpresion(der, ambito);
        int tipo1 = tipoObjeto(val1);
        int tipo2 = tipoObjeto(val2);

        if (tipo1 == 0 || tipo2 == 0) {
            return null;
        }

        if (tipo1 == 1) {
            errores.agregarerror(new ER(0, 0, val1.toString() + " - " + val2.toString(), 3,
                    "no se pueden restar valores de tipo " + TipoString(tipo1) + "con tipo " + TipoString(tipo2)));
        } else if (tipo1 == 2) {
            switch (tipo2) {
                case 2:
                    return Integer.parseInt(val1.toString()) - Integer.parseInt((val2.toString()));
                case 3:
                    return Double.parseDouble(val1.toString()) - Double.parseDouble((val2.toString()));
                case 4:
                    if ((boolean) val2 == true) {
                        val2 = 1;
                    } else if ((boolean) val2 == false) {
                        val2 = 0;
                    }
                    return Integer.parseInt(val1.toString()) - Integer.parseInt((val2.toString()));
                default:
                    errores.agregarerror(new ER(0, 0, val1.toString() + " - " + val2.toString(), 3,
                            "no se pueden restar valores de tipo " + TipoString(tipo1) + "con tipo " + TipoString(tipo2)));
            }
        } else if (tipo1 == 3) {
            switch (tipo2) {
                case 2:
                    return Double.parseDouble(val1.toString()) - Double.parseDouble((val2.toString()));
                case 3:
                    return Double.parseDouble(val1.toString()) - Double.parseDouble((val2.toString()));
                case 4:
                    if ((boolean) val2 == true) {
                        val2 = 1;
                    } else if ((boolean) val2 == false) {
                        val2 = 0;
                    }
                    return Double.parseDouble(val1.toString()) + Double.parseDouble((val2.toString()));
                default:
                    errores.agregarerror(new ER(0, 0, val1.toString() + " - " + val2.toString(), 3,
                            "no se pueden restar valores de tipo " + TipoString(tipo1) + "con tipo " + TipoString(tipo2)));
            }
        } else if (tipo1 == 4) {
            switch (tipo2) {
                case 2:
                    if ((boolean) val1 == true) {
                        val1 = 1;
                    } else if ((boolean) val1 == false) {
                        val1 = 0;
                    }
                    return Integer.parseInt(val1.toString()) - Integer.parseInt((val2.toString()));
                case 3:
                    if ((boolean) val1 == true) {
                        val1 = 1;
                    } else if ((boolean) val1 == false) {
                        val1 = 0;
                    }
                    return Double.parseDouble(val1.toString()) - Double.parseDouble((val2.toString()));
                default:
                    errores.agregarerror(new ER(0, 0, val1.toString() + " - " + val2.toString(), 3,
                            "no se pueden restar valores de tipo " + TipoString(tipo1) + "con tipo " + TipoString(tipo2)));

            }
        } else if (tipo1 == 5) {
            errores.agregarerror(new ER(0, 0, val1.toString() + " - " + val2.toString(), 3,
                    "no se pueden restar valores de tipo " + TipoString(tipo1) + "con tipo " + TipoString(tipo2)));
        } else if (tipo1 == 6) {
            errores.agregarerror(new ER(0, 0, val1.toString() + " - " + val2.toString(), 3,
                    "no se pueden restar valores de tipo " + TipoString(tipo1) + "con tipo " + TipoString(tipo2)));
        }
        return null;
    }

    private Object ejecutarMulti(nodo izq, nodo der, String ambito) {
        Object val1 = ejecutarExpresion(izq, ambito);
        Object val2 = ejecutarExpresion(der, ambito);
        int tipo1 = tipoObjeto(val1);
        int tipo2 = tipoObjeto(val2);

        if (tipo1 == 0 || tipo2 == 0) {
            return null;
        }

        if (tipo1 == 1) {
            errores.agregarerror(new ER(0, 0, val1.toString() + " * " + val2.toString(), 3,
                    "no se pueden multiplicar valores de tipo " + TipoString(tipo1) + "con tipo " + TipoString(tipo2)));
        } else if (tipo1 == 2) {
            switch (tipo2) {
                case 2:
                    return Integer.parseInt(val1.toString()) * Integer.parseInt((val2.toString()));
                case 3:
                    return Double.parseDouble(val1.toString()) * Double.parseDouble((val2.toString()));
                case 4:
                    if ((boolean) val2 == true) {
                        val2 = 1;
                    } else if ((boolean) val2 == false) {
                        val2 = 0;
                    }
                    return Integer.parseInt(val1.toString()) * Integer.parseInt((val2.toString()));
                default:
                    errores.agregarerror(new ER(0, 0, val1.toString() + " * " + val2.toString(), 3,
                            "no se pueden multiplicar valores de tipo " + TipoString(tipo1) + "con tipo " + TipoString(tipo2)));
            }
        } else if (tipo1 == 3) {
            switch (tipo2) {
                case 2:
                    return Double.parseDouble(val1.toString()) * Double.parseDouble((val2.toString()));
                case 3:
                    return Double.parseDouble(val1.toString()) * Double.parseDouble((val2.toString()));
                case 4:
                    if ((boolean) val2 == true) {
                        val2 = 1;
                    } else if ((boolean) val2 == false) {
                        val2 = 0;
                    }
                    return Double.parseDouble(val1.toString()) * Double.parseDouble((val2.toString()));
                default:
                    errores.agregarerror(new ER(0, 0, val1.toString() + " * " + val2.toString(), 3,
                            "no se pueden multiplicar valores de tipo " + TipoString(tipo1) + "con tipo " + TipoString(tipo2)));
            }
        } else if (tipo1 == 4) {
            switch (tipo2) {
                case 2:
                    if ((boolean) val1 == true) {
                        val1 = 1;
                    } else if ((boolean) val1 == false) {
                        val1 = 0;
                    }
                    return Integer.parseInt(val1.toString()) * Integer.parseInt((val2.toString()));
                case 3:
                    if ((boolean) val1 == true) {
                        val1 = 1;
                    } else if ((boolean) val1 == false) {
                        val1 = 0;
                    }
                    return Double.parseDouble(val1.toString()) * Double.parseDouble((val2.toString()));
                case 4:
                    return ejecutarAND(izq, der, ambito);
                default:
                    errores.agregarerror(new ER(0, 0, val1.toString() + " * " + val2.toString(), 3,
                            "no se pueden multiplicar valores de tipo " + TipoString(tipo1) + "con tipo " + TipoString(tipo2)));

            }
        } else if (tipo1 == 5) {
            errores.agregarerror(new ER(0, 0, val1.toString() + " * " + val2.toString(), 3,
                    "no se pueden multiplicar valores de tipo " + TipoString(tipo1) + "con tipo " + TipoString(tipo2)));
        } else if (tipo1 == 6) {
            errores.agregarerror(new ER(0, 0, val1.toString() + " * " + val2.toString(), 3,
                    "no se pueden multiplicar valores de tipo " + TipoString(tipo1) + "con tipo " + TipoString(tipo2)));
        }
        return null;
    }

    private Object ejecutarDivi(nodo izq, nodo der, String ambito) {
        Object val1 = ejecutarExpresion(izq, ambito);
        Object val2 = ejecutarExpresion(der, ambito);
        int tipo1 = tipoObjeto(val1);
        int tipo2 = tipoObjeto(val2);

        if (tipo1 == 0 || tipo2 == 0) {
            return null;
        }

        if (tipo1 == 1) {
            errores.agregarerror(new ER(0, 0, val1.toString() + " / " + val2.toString(), 3,
                    "no se pueden dividir valores de tipo " + TipoString(tipo1) + "con tipo " + TipoString(tipo2)));
        } else if (tipo1 == 2) {
            switch (tipo2) {
                case 2:
                    if (val2.toString().equals("0")) {
                        errores.agregarerror(new ER(0, 0, val1.toString() + " / " + val2.toString(), 3,
                                "no se puede dividir dentro de 0"));
                        return null;
                    }
                    return Integer.parseInt(val1.toString()) / Integer.parseInt((val2.toString()));
                case 3:
                    if (val2.toString().equals("0")) {
                        errores.agregarerror(new ER(0, 0, val1.toString() + " / " + val2.toString(), 3,
                                "no se puede dividir dentro de 0"));
                        return null;
                    }
                    return Double.parseDouble(val1.toString()) / Double.parseDouble((val2.toString()));
                case 4:
                    if ((boolean) val2 == true) {
                        val2 = 1;
                    } else if ((boolean) val2 == false) {
                        val2 = 0;
                    }
                    if (val2.toString().equals("0")) {
                        errores.agregarerror(new ER(0, 0, val1.toString() + " / " + val2.toString(), 3,
                                "no se puede dividir dentro de 0"));
                        return null;
                    }
                    return Integer.parseInt(val1.toString()) / Integer.parseInt((val2.toString()));
                default:
                    errores.agregarerror(new ER(0, 0, val1.toString() + " - " + val2.toString(), 3,
                            "no se pueden dividir valores de tipo " + TipoString(tipo1) + "con tipo " + TipoString(tipo2)));
            }
        } else if (tipo1 == 3) {
            switch (tipo2) {
                case 2:
                    if (val2.toString().equals("0")) {
                        errores.agregarerror(new ER(0, 0, val1.toString() + " / " + val2.toString(), 3,
                                "no se puede dividir dentro de 0"));
                        return null;
                    }
                    return Double.parseDouble(val1.toString()) / Double.parseDouble((val2.toString()));
                case 3:
                    if (val2.toString().equals("0")) {
                        errores.agregarerror(new ER(0, 0, val1.toString() + " / " + val2.toString(), 3,
                                "no se puede dividir dentro de 0"));
                        return null;
                    }
                    return Double.parseDouble(val1.toString()) / Double.parseDouble((val2.toString()));
                case 4:
                    if ((boolean) val2 == true) {
                        val2 = 1;
                    } else if ((boolean) val2 == false) {
                        val2 = 0;
                    }
                    if (val2.toString().equals("0")) {
                        errores.agregarerror(new ER(0, 0, val1.toString() + " / " + val2.toString(), 3,
                                "no se puede dividir dentro de 0"));
                        return null;
                    }
                    return Double.parseDouble(val1.toString()) / Double.parseDouble((val2.toString()));
                default:
                    errores.agregarerror(new ER(0, 0, val1.toString() + " - " + val2.toString(), 3,
                            "no se pueden dividir valores de tipo " + TipoString(tipo1) + "con tipo " + TipoString(tipo2)));
            }
        } else if (tipo1 == 4) {
            switch (tipo2) {
                case 2:
                    if ((boolean) val1 == true) {
                        val1 = 1;
                    } else if ((boolean) val1 == false) {
                        val1 = 0;
                    }
                    if (val2.toString().equals("0")) {
                        errores.agregarerror(new ER(0, 0, val1.toString() + " / " + val2.toString(), 3,
                                "no se puede dividir dentro de 0"));
                        return null;
                    }
                    return Integer.parseInt(val1.toString()) / Integer.parseInt((val2.toString()));
                case 3:
                    if ((boolean) val1 == true) {
                        val1 = 1;
                    } else if ((boolean) val1 == false) {
                        val1 = 0;
                    }
                    if (val2.toString().equals("0")) {
                        errores.agregarerror(new ER(0, 0, val1.toString() + " / " + val2.toString(), 3,
                                "no se puede dividir dentro de 0"));
                        return null;
                    }
                    return Double.parseDouble(val1.toString()) / Double.parseDouble((val2.toString()));
                default:
                    errores.agregarerror(new ER(0, 0, val1.toString() + " / " + val2.toString(), 3,
                            "no se pueden dividir valores de tipo " + TipoString(tipo1) + "con tipo " + TipoString(tipo2)));

            }
        } else if (tipo1 == 5) {
            errores.agregarerror(new ER(0, 0, val1.toString() + " / " + val2.toString(), 3,
                    "no se pueden dividir valores de tipo " + TipoString(tipo1) + "con tipo " + TipoString(tipo2)));
        } else if (tipo1 == 6) {
            errores.agregarerror(new ER(0, 0, val1.toString() + " / " + val2.toString(), 3,
                    "no se pueden dividir valores de tipo " + TipoString(tipo1) + "con tipo " + TipoString(tipo2)));
        }
        return null;
    }

    private Object ejecutarPot(nodo izq, nodo der, String ambito) {
        Object val1 = ejecutarExpresion(izq, ambito);
        Object val2 = ejecutarExpresion(der, ambito);
        int tipo1 = tipoObjeto(val1);
        int tipo2 = tipoObjeto(val2);

        if (tipo1 == 0 || tipo2 == 0) {
            return null;
        }

        if (tipo1 == 1) {
            errores.agregarerror(new ER(0, 0, val1.toString() + " ^ " + val2.toString(), 3,
                    "no se pueden elevar valores de tipo " + TipoString(tipo1) + "con tipo " + TipoString(tipo2)));
        } else if (tipo1 == 2) {
            switch (tipo2) {
                case 2:
                    return Math.pow(Double.parseDouble(val1.toString()), Double.parseDouble(val2.toString()));
                case 3:
                    return Math.pow(Double.parseDouble(val1.toString()), Double.parseDouble(val2.toString()));
                case 4:
                    if ((boolean) val2 == true) {
                        val2 = 1;
                    } else if ((boolean) val2 == false) {
                        val2 = 0;
                    }
                    return Math.pow(Double.parseDouble(val1.toString()), Double.parseDouble(val2.toString()));
                default:
                    errores.agregarerror(new ER(0, 0, val1.toString() + " ^ " + val2.toString(), 3,
                            "no se pueden elevar valores de tipo " + TipoString(tipo1) + "con tipo " + TipoString(tipo2)));
            }
        } else if (tipo1 == 3) {
            switch (tipo2) {
                case 2:
                    return Math.pow(Double.parseDouble(val1.toString()), Double.parseDouble(val2.toString()));
                case 3:
                    return Math.pow(Double.parseDouble(val1.toString()), Double.parseDouble(val2.toString()));
                case 4:
                    if ((boolean) val2 == true) {
                        val2 = 1;
                    } else if ((boolean) val2 == false) {
                        val2 = 0;
                    }
                    return Math.pow(Double.parseDouble(val1.toString()), Double.parseDouble(val2.toString()));
                default:
                    errores.agregarerror(new ER(0, 0, val1.toString() + " ^ " + val2.toString(), 3,
                            "no se pueden elevar valores de tipo " + TipoString(tipo1) + "con tipo " + TipoString(tipo2)));
            }
        } else if (tipo1 == 4) {
            switch (tipo2) {
                case 2:
                    if ((boolean) val1 == true) {
                        val1 = 1;
                    } else if ((boolean) val1 == false) {
                        val1 = 0;
                    }
                    return Math.pow(Double.parseDouble(val1.toString()), Double.parseDouble(val2.toString()));
                case 3:
                    if ((boolean) val1 == true) {
                        val1 = 1;
                    } else if ((boolean) val1 == false) {
                        val1 = 0;
                    }
                    return Math.pow(Double.parseDouble(val1.toString()), Double.parseDouble(val2.toString()));
                default:
                    errores.agregarerror(new ER(0, 0, val1.toString() + " ^ " + val2.toString(), 3,
                            "no se pueden elevar valores de tipo " + TipoString(tipo1) + "con tipo " + TipoString(tipo2)));

            }
        } else if (tipo1 == 5) {
            errores.agregarerror(new ER(0, 0, val1.toString() + " ^ " + val2.toString(), 3,
                    "no se pueden elevar valores de tipo " + TipoString(tipo1) + "con tipo " + TipoString(tipo2)));
        } else if (tipo1 == 6) {
            errores.agregarerror(new ER(0, 0, val1.toString() + " ^ " + val2.toString(), 3,
                    "no se pueden elevar valores de tipo " + TipoString(tipo1) + "con tipo " + TipoString(tipo2)));
        }
        return null;
    }

    private Object ejecutarMod(nodo izq, nodo der, String ambito) {
        Object val1 = ejecutarExpresion(izq, ambito);
        Object val2 = ejecutarExpresion(der, ambito);
        int tipo1 = tipoObjeto(val1);
        int tipo2 = tipoObjeto(val2);

        if (tipo1 == 0 || tipo2 == 0) {
            return null;
        }

        if (tipo1 == 1) {
            errores.agregarerror(new ER(0, 0, val1.toString() + " % " + val2.toString(), 3,
                    "no se pueden dividir valores de tipo " + TipoString(tipo1) + "con tipo " + TipoString(tipo2)));
        } else if (tipo1 == 2) {
            switch (tipo2) {
                case 2:
                    if (val2.toString().equals("0")) {
                        errores.agregarerror(new ER(0, 0, val1.toString() + " % " + val2.toString(), 3,
                                "no se puede dividir dentro de 0"));
                        return null;
                    }
                    return Integer.parseInt(val1.toString()) % Integer.parseInt((val2.toString()));
                case 3:
                    if (val2.toString().equals("0")) {
                        errores.agregarerror(new ER(0, 0, val1.toString() + " % " + val2.toString(), 3,
                                "no se puede dividir dentro de 0"));
                        return null;
                    }
                    return Double.parseDouble(val1.toString()) % Double.parseDouble((val2.toString()));
                case 4:
                    if ((boolean) val2 == true) {
                        val2 = 1;
                    } else if ((boolean) val2 == false) {
                        val2 = 0;
                    }
                    if (val2.toString().equals("0")) {
                        errores.agregarerror(new ER(0, 0, val1.toString() + " % " + val2.toString(), 3,
                                "no se puede dividir dentro de 0"));
                        return null;
                    }
                    return Integer.parseInt(val1.toString()) % Integer.parseInt((val2.toString()));
                default:
                    errores.agregarerror(new ER(0, 0, val1.toString() + " % " + val2.toString(), 3,
                            "no se pueden dividir valores de tipo " + TipoString(tipo1) + "con tipo " + TipoString(tipo2)));
            }
        } else if (tipo1 == 3) {
            switch (tipo2) {
                case 2:
                    if (val2.toString().equals("0")) {
                        errores.agregarerror(new ER(0, 0, val1.toString() + " % " + val2.toString(), 3,
                                "no se puede dividir dentro de 0"));
                        return null;
                    }
                    return Double.parseDouble(val1.toString()) % Double.parseDouble((val2.toString()));
                case 3:
                    if (val2.toString().equals("0")) {
                        errores.agregarerror(new ER(0, 0, val1.toString() + " % " + val2.toString(), 3,
                                "no se puede dividir dentro de 0"));
                        return null;
                    }
                    return Double.parseDouble(val1.toString()) % Double.parseDouble((val2.toString()));
                case 4:
                    if ((boolean) val2 == true) {
                        val2 = 1;
                    } else if ((boolean) val2 == false) {
                        val2 = 0;
                    }
                    if (val2.toString().equals("0")) {
                        errores.agregarerror(new ER(0, 0, val1.toString() + " % " + val2.toString(), 3,
                                "no se puede dividir dentro de 0"));
                        return null;
                    }
                    return Double.parseDouble(val1.toString()) % Double.parseDouble((val2.toString()));
                default:
                    errores.agregarerror(new ER(0, 0, val1.toString() + " % " + val2.toString(), 3,
                            "no se pueden dividir valores de tipo " + TipoString(tipo1) + "con tipo " + TipoString(tipo2)));
            }
        } else if (tipo1 == 4) {
            switch (tipo2) {
                case 2:
                    if ((boolean) val1 == true) {
                        val1 = 1;
                    } else if ((boolean) val1 == false) {
                        val1 = 0;
                    }
                    if (val2.toString().equals("0")) {
                        errores.agregarerror(new ER(0, 0, val1.toString() + " % " + val2.toString(), 3,
                                "no se puede dividir dentro de 0"));
                        return null;
                    }
                    return Integer.parseInt(val1.toString()) % Integer.parseInt((val2.toString()));
                case 3:
                    if ((boolean) val1 == true) {
                        val1 = 1;
                    } else if ((boolean) val1 == false) {
                        val1 = 0;
                    }
                    if (val2.toString().equals("0")) {
                        errores.agregarerror(new ER(0, 0, val1.toString() + " % " + val2.toString(), 3,
                                "no se puede dividir dentro de 0"));
                        return null;
                    }
                    return Double.parseDouble(val1.toString()) % Double.parseDouble((val2.toString()));
                default:
                    errores.agregarerror(new ER(0, 0, val1.toString() + " % " + val2.toString(), 3,
                            "no se pueden dividir valores de tipo " + TipoString(tipo1) + "con tipo " + TipoString(tipo2)));

            }
        } else if (tipo1 == 5) {
            errores.agregarerror(new ER(0, 0, val1.toString() + " % " + val2.toString(), 3,
                    "no se pueden dividir valores de tipo " + TipoString(tipo1) + "con tipo " + TipoString(tipo2)));
        } else if (tipo1 == 6) {
            errores.agregarerror(new ER(0, 0, val1.toString() + " % " + val2.toString(), 3,
                    "no se pueden dividir valores de tipo " + TipoString(tipo1) + "con tipo " + TipoString(tipo2)));
        }
        return null;
    }

    private Object ejecutarMenor(nodo izq, nodo der, String ambito) {
        Object val1 = ejecutarExpresion(izq, ambito);
        Object val2 = ejecutarExpresion(der, ambito);
        int tipo1 = tipoObjeto(val1);
        int tipo2 = tipoObjeto(val2);

        if (tipo1 == 0 || tipo2 == 0) {
            return null;
        }

        if (tipo1 == 1) {
            switch (tipo2) {
                case 1:
                    return val1.toString().compareTo(val2.toString()) < 0;
                case 2:
                    int val = Integer.parseInt(val2.toString());
                    return val1.toString().length() < val;
                case 3:
                    double db = Double.parseDouble(val2.toString());
                    return val1.toString().length() < db;
                case 5:
                case 6:
                    return val1.toString().compareTo(val2.toString()) < 0;
                default:
                    errores.agregarerror(new ER(0, 0, val1.toString() + " < " + val2.toString(), 3,
                            "no se pueden verificar relacionalmente valores de tipo " + TipoString(tipo1) + "con tipo " + TipoString(tipo2)));
            }
        } else if (tipo1 == 2) {
            switch (tipo2) {
                case 1:
                    int val = Integer.parseInt(val1.toString());
                    return val < val2.toString().length();
                case 2:
                    return Integer.parseInt(val1.toString()) < Integer.parseInt(val2.toString());
                case 3:
                    return Double.parseDouble(val1.toString()) < Double.parseDouble(val2.toString());
                case 4:
                    int v;
                    if ((boolean) val2 == true) {
                        v = 1;
                    } else {
                        v = 0;
                    }
                    return Integer.parseInt(val1.toString()) < v;
                default:
                    errores.agregarerror(new ER(0, 0, val1.toString() + " < " + val2.toString(), 3,
                            "no se pueden verificar relacionalmente valores de tipo " + TipoString(tipo1) + "con tipo " + TipoString(tipo2)));
            }
        } else if (tipo1 == 3) {
            switch (tipo2) {
                case 1:
                    double val = Double.parseDouble(val1.toString());
                    return val < val2.toString().length();
                case 2:
                    return Double.parseDouble(val1.toString()) < Double.parseDouble(val2.toString());
                case 3:
                    return Double.parseDouble(val1.toString()) < Double.parseDouble(val2.toString());
                case 4:
                    int v;
                    if ((boolean) val2 == true) {
                        v = 1;
                    } else {
                        v = 0;
                    }
                    return Double.parseDouble(val1.toString()) < v;
                default:
                    errores.agregarerror(new ER(0, 0, val1.toString() + " < " + val2.toString(), 3,
                            "no se pueden verificar relacionalmente valores de tipo " + TipoString(tipo1) + "con tipo " + TipoString(tipo2)));
            }
        } else if (tipo1 == 4) {
            switch (tipo2) {
                case 2:
                    int v;
                    if ((boolean) val1 == true) {
                        v = 1;
                    } else {
                        v = 0;
                    }
                    return v < Integer.parseInt(val2.toString());
                case 3:
                    float v1;
                    if ((boolean) val1 == true) {
                        v1 = 1;
                    } else {
                        v1 = 0;
                    }
                    return v1 < Double.parseDouble(val2.toString());
                case 4:
                    int v2,
                     v3;
                    if ((boolean) val1 == true) {
                        v2 = 1;
                    } else {
                        v2 = 0;
                    }
                    if ((boolean) val2 == true) {
                        v3 = 1;
                    } else {
                        v3 = 0;
                    }
                    return v2 < v3;
                default:
                    errores.agregarerror(new ER(0, 0, val1.toString() + " < " + val2.toString(), 3,
                            "no se pueden verificar relacionalmente valores de tipo " + TipoString(tipo1) + "con tipo " + TipoString(tipo2)));
            }
        } else if (tipo1 == 5 || tipo1 == 6) {
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            SimpleDateFormat formato2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
            Date fecha1 = null, fecha2 = null;
            switch (tipo2) {
                case 1:
                    return val1.toString().compareTo(val2.toString()) < 0;
                case 5:
                    if (tipo1 == 5) {
                        try {
                            fecha1 = formato.parse(val1.toString());
                        } catch (ParseException ex) {
                            Logger.getLogger(InterpreteCSJ.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else if (tipo1 == 6) {
                        try {
                            fecha1 = formato2.parse(val1.toString());
                        } catch (ParseException ex) {
                            Logger.getLogger(InterpreteCSJ.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    try {
                        fecha2 = formato.parse(val2.toString());
                    } catch (ParseException ex) {
                        Logger.getLogger(InterpreteCSJ.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (fecha1 != null && fecha2 != null) {
                        return fecha1.compareTo(fecha2) < 0;
                    }
                case 6:
                    if (tipo1 == 5) {
                        try {
                            fecha1 = formato.parse(val1.toString());
                        } catch (ParseException ex) {
                            Logger.getLogger(InterpreteCSJ.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else if (tipo1 == 6) {
                        try {
                            fecha1 = formato2.parse(val1.toString());
                        } catch (ParseException ex) {
                            Logger.getLogger(InterpreteCSJ.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    try {
                        fecha2 = formato2.parse(val2.toString());
                    } catch (ParseException ex) {
                        Logger.getLogger(InterpreteCSJ.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (fecha1 != null && fecha2 != null) {
                        return fecha1.compareTo(fecha2) < 0;
                    }
                default:
                    errores.agregarerror(new ER(0, 0, val1.toString() + " < " + val2.toString(), 3,
                            "no se pueden verificar relacionalmente valores de tipo " + TipoString(tipo1) + "con tipo " + TipoString(tipo2)));
            }
        }
        return null;
    }

    private Object ejecutarMayor(nodo izq, nodo der, String ambito) {
        Object val1 = ejecutarExpresion(izq, ambito);
        Object val2 = ejecutarExpresion(der, ambito);
        int tipo1 = tipoObjeto(val1);
        int tipo2 = tipoObjeto(val2);

        if (tipo1 == 0 || tipo2 == 0) {
            return null;
        }

        if (tipo1 == 1) {
            switch (tipo2) {
                case 1:
                    return val1.toString().compareTo(val2.toString()) > 0;
                case 2:
                    int val = Integer.parseInt(val2.toString());
                    return val1.toString().length() > val;
                case 3:
                    double db = Double.parseDouble(val2.toString());
                    return val1.toString().length() > db;
                case 5:
                case 6:
                    return val1.toString().compareTo(val2.toString()) > 0;
                default:
                    errores.agregarerror(new ER(0, 0, val1.toString() + " > " + val2.toString(), 3,
                            "no se pueden verificar relacionalmente valores de tipo " + TipoString(tipo1) + "con tipo " + TipoString(tipo2)));
            }
        } else if (tipo1 == 2) {
            switch (tipo2) {
                case 1:
                    int val = Integer.parseInt(val1.toString());
                    return val > val2.toString().length();
                case 2:
                    return Integer.parseInt(val1.toString()) > Integer.parseInt(val2.toString());
                case 3:
                    return Double.parseDouble(val1.toString()) > Double.parseDouble(val2.toString());
                case 4:
                    int v;
                    if ((boolean) val2 == true) {
                        v = 1;
                    } else {
                        v = 0;
                    }
                    return Integer.parseInt(val1.toString()) > v;
                default:
                    errores.agregarerror(new ER(0, 0, val1.toString() + " > " + val2.toString(), 3,
                            "no se pueden verificar relacionalmente valores de tipo " + TipoString(tipo1) + "con tipo " + TipoString(tipo2)));
            }
        } else if (tipo1 == 3) {
            switch (tipo2) {
                case 1:
                    double val = Double.parseDouble(val1.toString());
                    return val > val2.toString().length();
                case 2:
                    return Double.parseDouble(val1.toString()) > Double.parseDouble(val2.toString());
                case 3:
                    return Double.parseDouble(val1.toString()) > Double.parseDouble(val2.toString());
                case 4:
                    int v;
                    if ((boolean) val2 == true) {
                        v = 1;
                    } else {
                        v = 0;
                    }
                    return Double.parseDouble(val1.toString()) > v;
                default:
                    errores.agregarerror(new ER(0, 0, val1.toString() + " > " + val2.toString(), 3,
                            "no se pueden verificar relacionalmente valores de tipo " + TipoString(tipo1) + "con tipo " + TipoString(tipo2)));
            }
        } else if (tipo1 == 4) {
            switch (tipo2) {
                case 2:
                    int v;
                    if ((boolean) val1 == true) {
                        v = 1;
                    } else {
                        v = 0;
                    }
                    return v > Integer.parseInt(val2.toString());
                case 3:
                    float v1;
                    if ((boolean) val1 == true) {
                        v1 = 1;
                    } else {
                        v1 = 0;
                    }
                    return v1 > Double.parseDouble(val2.toString());
                case 4:
                    int v2,
                     v3;
                    if ((boolean) val1 == true) {
                        v2 = 1;
                    } else {
                        v2 = 0;
                    }
                    if ((boolean) val2 == true) {
                        v3 = 1;
                    } else {
                        v3 = 0;
                    }
                    return v2 > v3;
                default:
                    errores.agregarerror(new ER(0, 0, val1.toString() + " > " + val2.toString(), 3,
                            "no se pueden verificar relacionalmente valores de tipo " + TipoString(tipo1) + "con tipo " + TipoString(tipo2)));
            }
        } else if (tipo1 == 5 || tipo1 == 6) {
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            SimpleDateFormat formato2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
            Date fecha1 = null, fecha2 = null;
            switch (tipo2) {
                case 1:
                    return val1.toString().compareTo(val2.toString()) > 0;
                case 5:
                    if (tipo1 == 5) {
                        try {
                            fecha1 = formato.parse(val1.toString());
                        } catch (ParseException ex) {
                            Logger.getLogger(InterpreteCSJ.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else if (tipo1 == 6) {
                        try {
                            fecha1 = formato2.parse(val1.toString());
                        } catch (ParseException ex) {
                            Logger.getLogger(InterpreteCSJ.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    try {
                        fecha2 = formato.parse(val2.toString());
                    } catch (ParseException ex) {
                        Logger.getLogger(InterpreteCSJ.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (fecha1 != null && fecha2 != null) {
                        return fecha1.compareTo(fecha2) > 0;
                    }
                case 6:
                    if (tipo1 == 5) {
                        try {
                            fecha1 = formato.parse(val1.toString());
                        } catch (ParseException ex) {
                            Logger.getLogger(InterpreteCSJ.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else if (tipo1 == 6) {
                        try {
                            fecha1 = formato2.parse(val1.toString());
                        } catch (ParseException ex) {
                            Logger.getLogger(InterpreteCSJ.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    try {
                        fecha2 = formato2.parse(val2.toString());
                    } catch (ParseException ex) {
                        Logger.getLogger(InterpreteCSJ.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (fecha1 != null && fecha2 != null) {
                        return fecha1.compareTo(fecha2) > 0;
                    }
                default:
                    errores.agregarerror(new ER(0, 0, val1.toString() + " > " + val2.toString(), 3,
                            "no se pueden verificar relacionalmente valores de tipo " + TipoString(tipo1) + "con tipo " + TipoString(tipo2)));
            }
        }
        return null;
    }

    private Object ejecutarMenorIgual(nodo izq, nodo der, String ambito) {
        Object val1 = ejecutarExpresion(izq, ambito);
        Object val2 = ejecutarExpresion(der, ambito);
        int tipo1 = tipoObjeto(val1);
        int tipo2 = tipoObjeto(val2);

        if (tipo1 == 0 || tipo2 == 0) {
            return null;
        }

        if (tipo1 == 1) {
            switch (tipo2) {
                case 1:
                    return val1.toString().compareTo(val2.toString()) <= 0;
                case 2:
                    int val = Integer.parseInt(val2.toString());
                    return val1.toString().length() <= val;
                case 3:
                    double db = Double.parseDouble(val2.toString());
                    return val1.toString().length() <= db;
                case 5:
                case 6:
                    return val1.toString().compareTo(val2.toString()) <= 0;
                default:
                    errores.agregarerror(new ER(0, 0, val1.toString() + " <= " + val2.toString(), 3,
                            "no se pueden verificar relacionalmente valores de tipo " + TipoString(tipo1) + "con tipo " + TipoString(tipo2)));
            }
        } else if (tipo1 == 2) {
            switch (tipo2) {
                case 1:
                    int val = Integer.parseInt(val1.toString());
                    return val <= val2.toString().length();
                case 2:
                    return Integer.parseInt(val1.toString()) <= Integer.parseInt(val2.toString());
                case 3:
                    return Double.parseDouble(val1.toString()) <= Double.parseDouble(val2.toString());
                case 4:
                    int v;
                    if ((boolean) val2 == true) {
                        v = 1;
                    } else {
                        v = 0;
                    }
                    return Integer.parseInt(val1.toString()) <= v;
                default:
                    errores.agregarerror(new ER(0, 0, val1.toString() + " <= " + val2.toString(), 3,
                            "no se pueden verificar relacionalmente valores de tipo " + TipoString(tipo1) + "con tipo " + TipoString(tipo2)));
            }
        } else if (tipo1 == 3) {
            switch (tipo2) {
                case 1:
                    double val = Double.parseDouble(val1.toString());
                    return val <= val2.toString().length();
                case 2:
                    return Double.parseDouble(val1.toString()) <= Double.parseDouble(val2.toString());
                case 3:
                    return Double.parseDouble(val1.toString()) <= Double.parseDouble(val2.toString());
                case 4:
                    int v;
                    if ((boolean) val2 == true) {
                        v = 1;
                    } else {
                        v = 0;
                    }
                    return Double.parseDouble(val1.toString()) <= v;
                default:
                    errores.agregarerror(new ER(0, 0, val1.toString() + " <= " + val2.toString(), 3,
                            "no se pueden verificar relacionalmente valores de tipo " + TipoString(tipo1) + "con tipo " + TipoString(tipo2)));
            }
        } else if (tipo1 == 4) {
            switch (tipo2) {
                case 2:
                    int v;
                    if ((boolean) val1 == true) {
                        v = 1;
                    } else {
                        v = 0;
                    }
                    return v <= Integer.parseInt(val2.toString());
                case 3:
                    float v1;
                    if ((boolean) val1 == true) {
                        v1 = 1;
                    } else {
                        v1 = 0;
                    }
                    return v1 <= Double.parseDouble(val2.toString());
                case 4:
                    int v2,
                     v3;
                    if ((boolean) val1 == true) {
                        v2 = 1;
                    } else {
                        v2 = 0;
                    }
                    if ((boolean) val2 == true) {
                        v3 = 1;
                    } else {
                        v3 = 0;
                    }
                    return v2 <= v3;
                default:
                    errores.agregarerror(new ER(0, 0, val1.toString() + " <= " + val2.toString(), 3,
                            "no se pueden verificar relacionalmente valores de tipo " + TipoString(tipo1) + "con tipo " + TipoString(tipo2)));
            }
        } else if (tipo1 == 5 || tipo1 == 6) {
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            SimpleDateFormat formato2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
            Date fecha1 = null, fecha2 = null;
            switch (tipo2) {
                case 1:
                    return val1.toString().compareTo(val2.toString()) <= 0;
                case 5:
                    if (tipo1 == 5) {
                        try {
                            fecha1 = formato.parse(val1.toString());
                        } catch (ParseException ex) {
                            Logger.getLogger(InterpreteCSJ.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else if (tipo1 == 6) {
                        try {
                            fecha1 = formato2.parse(val1.toString());
                        } catch (ParseException ex) {
                            Logger.getLogger(InterpreteCSJ.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    try {
                        fecha2 = formato.parse(val2.toString());
                    } catch (ParseException ex) {
                        Logger.getLogger(InterpreteCSJ.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (fecha1 != null && fecha2 != null) {
                        return fecha1.compareTo(fecha2) <= 0;
                    }
                case 6:
                    if (tipo1 == 5) {
                        try {
                            fecha1 = formato.parse(val1.toString());
                        } catch (ParseException ex) {
                            Logger.getLogger(InterpreteCSJ.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else if (tipo1 == 6) {
                        try {
                            fecha1 = formato2.parse(val1.toString());
                        } catch (ParseException ex) {
                            Logger.getLogger(InterpreteCSJ.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    try {
                        fecha2 = formato2.parse(val2.toString());
                    } catch (ParseException ex) {
                        Logger.getLogger(InterpreteCSJ.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (fecha1 != null && fecha2 != null) {
                        return fecha1.compareTo(fecha2) <= 0;
                    }
                default:
                    errores.agregarerror(new ER(0, 0, val1.toString() + " <= " + val2.toString(), 3,
                            "no se pueden verificar relacionalmente valores de tipo " + TipoString(tipo1) + "con tipo " + TipoString(tipo2)));
            }
        }
        return null;
    }

    private Object ejecutarMayorIgual(nodo izq, nodo der, String ambito) {
        Object val1 = ejecutarExpresion(izq, ambito);
        Object val2 = ejecutarExpresion(der, ambito);
        int tipo1 = tipoObjeto(val1);
        int tipo2 = tipoObjeto(val2);

        if (tipo1 == 0 || tipo2 == 0) {
            return null;
        }

        if (tipo1 == 1) {
            switch (tipo2) {
                case 1:
                    return val1.toString().compareTo(val2.toString()) >= 0;
                case 2:
                    int val = Integer.parseInt(val2.toString());
                    return val1.toString().length() >= val;
                case 3:
                    double db = Double.parseDouble(val2.toString());
                    return val1.toString().length() >= db;
                case 5:
                case 6:
                    return val1.toString().compareTo(val2.toString()) >= 0;
                default:
                    errores.agregarerror(new ER(0, 0, val1.toString() + " >= " + val2.toString(), 3,
                            "no se pueden verificar relacionalmente valores de tipo " + TipoString(tipo1) + "con tipo " + TipoString(tipo2)));
            }
        } else if (tipo1 == 2) {
            switch (tipo2) {
                case 1:
                    int val = Integer.parseInt(val1.toString());
                    return val >= val2.toString().length();
                case 2:
                    return Integer.parseInt(val1.toString()) >= Integer.parseInt(val2.toString());
                case 3:
                    return Double.parseDouble(val1.toString()) >= Double.parseDouble(val2.toString());
                case 4:
                    int v;
                    if ((boolean) val2 == true) {
                        v = 1;
                    } else {
                        v = 0;
                    }
                    return Integer.parseInt(val1.toString()) >= v;
                default:
                    errores.agregarerror(new ER(0, 0, val1.toString() + " >= " + val2.toString(), 3,
                            "no se pueden verificar relacionalmente valores de tipo " + TipoString(tipo1) + "con tipo " + TipoString(tipo2)));
            }
        } else if (tipo1 == 3) {
            switch (tipo2) {
                case 1:
                    double val = Double.parseDouble(val1.toString());
                    return val >= val2.toString().length();
                case 2:
                    return Double.parseDouble(val1.toString()) >= Double.parseDouble(val2.toString());
                case 3:
                    return Double.parseDouble(val1.toString()) >= Double.parseDouble(val2.toString());
                case 4:
                    int v;
                    if ((boolean) val2 == true) {
                        v = 1;
                    } else {
                        v = 0;
                    }
                    return Double.parseDouble(val1.toString()) >= v;
                default:
                    errores.agregarerror(new ER(0, 0, val1.toString() + " >= " + val2.toString(), 3,
                            "no se pueden verificar relacionalmente valores de tipo " + TipoString(tipo1) + "con tipo " + TipoString(tipo2)));
            }
        } else if (tipo1 == 4) {
            switch (tipo2) {
                case 2:
                    int v;
                    if ((boolean) val1 == true) {
                        v = 1;
                    } else {
                        v = 0;
                    }
                    return v >= Integer.parseInt(val2.toString());
                case 3:
                    float v1;
                    if ((boolean) val1 == true) {
                        v1 = 1;
                    } else {
                        v1 = 0;
                    }
                    return v1 >= Double.parseDouble(val2.toString());
                case 4:
                    int v2,
                     v3;
                    if ((boolean) val1 == true) {
                        v2 = 1;
                    } else {
                        v2 = 0;
                    }
                    if ((boolean) val2 == true) {
                        v3 = 1;
                    } else {
                        v3 = 0;
                    }
                    return v2 >= v3;
                default:
                    errores.agregarerror(new ER(0, 0, val1.toString() + " >= " + val2.toString(), 3,
                            "no se pueden verificar relacionalmente valores de tipo " + TipoString(tipo1) + "con tipo " + TipoString(tipo2)));
            }
        } else if (tipo1 == 5 || tipo1 == 6) {
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            SimpleDateFormat formato2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
            Date fecha1 = null, fecha2 = null;
            switch (tipo2) {
                case 1:
                    return val1.toString().compareTo(val2.toString()) >= 0;
                case 5:
                    if (tipo1 == 5) {
                        try {
                            fecha1 = formato.parse(val1.toString());
                        } catch (ParseException ex) {
                            Logger.getLogger(InterpreteCSJ.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else if (tipo1 == 6) {
                        try {
                            fecha1 = formato2.parse(val1.toString());
                        } catch (ParseException ex) {
                            Logger.getLogger(InterpreteCSJ.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    try {
                        fecha2 = formato.parse(val2.toString());
                    } catch (ParseException ex) {
                        Logger.getLogger(InterpreteCSJ.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (fecha1 != null && fecha2 != null) {
                        return fecha1.compareTo(fecha2) >= 0;
                    }
                case 6:
                    if (tipo1 == 5) {
                        try {
                            fecha1 = formato.parse(val1.toString());
                        } catch (ParseException ex) {
                            Logger.getLogger(InterpreteCSJ.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else if (tipo1 == 6) {
                        try {
                            fecha1 = formato2.parse(val1.toString());
                        } catch (ParseException ex) {
                            Logger.getLogger(InterpreteCSJ.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    try {
                        fecha2 = formato2.parse(val2.toString());
                    } catch (ParseException ex) {
                        Logger.getLogger(InterpreteCSJ.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (fecha1 != null && fecha2 != null) {
                        return fecha1.compareTo(fecha2) >= 0;
                    }
                default:
                    errores.agregarerror(new ER(0, 0, val1.toString() + " >= " + val2.toString(), 3,
                            "no se pueden verificar relacionalmente valores de tipo " + TipoString(tipo1) + "con tipo " + TipoString(tipo2)));
            }
        }
        return null;
    }

    private Object ejecutardiferente(nodo izq, nodo der, String ambito) {
        Object val1 = ejecutarExpresion(izq, ambito);
        Object val2 = ejecutarExpresion(der, ambito);
        int tipo1 = tipoObjeto(val1);
        int tipo2 = tipoObjeto(val2);

        if (tipo1 == 0 || tipo2 == 0) {
            return null;
        }

        if (tipo1 == 1) {
            switch (tipo2) {
                case 1:
                    return val1.toString().compareTo(val2.toString()) != 0;
                case 2:
                    int val = Integer.parseInt(val2.toString());
                    return val1.toString().length() != val;
                case 3:
                    double db = Double.parseDouble(val2.toString());
                    return val1.toString().length() != db;
                case 5:
                case 6:
                    return val1.toString().compareTo(val2.toString()) != 0;
                default:
                    errores.agregarerror(new ER(0, 0, val1.toString() + " != " + val2.toString(), 3,
                            "no se pueden verificar relacionalmente valores de tipo " + TipoString(tipo1) + "con tipo " + TipoString(tipo2)));
            }
        } else if (tipo1 == 2) {
            switch (tipo2) {
                case 1:
                    int val = Integer.parseInt(val1.toString());
                    return val != val2.toString().length();
                case 2:
                    return Integer.parseInt(val1.toString()) != Integer.parseInt(val2.toString());
                case 3:
                    return Double.parseDouble(val1.toString()) != Double.parseDouble(val2.toString());
                case 4:
                    int v;
                    if ((boolean) val2 == true) {
                        v = 1;
                    } else {
                        v = 0;
                    }
                    return Integer.parseInt(val1.toString()) != v;
                default:
                    errores.agregarerror(new ER(0, 0, val1.toString() + " != " + val2.toString(), 3,
                            "no se pueden verificar relacionalmente valores de tipo " + TipoString(tipo1) + "con tipo " + TipoString(tipo2)));
            }
        } else if (tipo1 == 3) {
            switch (tipo2) {
                case 1:
                    double val = Double.parseDouble(val1.toString());
                    return val != val2.toString().length();
                case 2:
                    return Double.parseDouble(val1.toString()) != Double.parseDouble(val2.toString());
                case 3:
                    return Double.parseDouble(val1.toString()) != Double.parseDouble(val2.toString());
                case 4:
                    int v;
                    if ((boolean) val2 == true) {
                        v = 1;
                    } else {
                        v = 0;
                    }
                    return Double.parseDouble(val1.toString()) != v;
                default:
                    errores.agregarerror(new ER(0, 0, val1.toString() + " != " + val2.toString(), 3,
                            "no se pueden verificar relacionalmente valores de tipo " + TipoString(tipo1) + "con tipo " + TipoString(tipo2)));
            }
        } else if (tipo1 == 4) {
            switch (tipo2) {
                case 2:
                    int v;
                    if ((boolean) val1 == true) {
                        v = 1;
                    } else {
                        v = 0;
                    }
                    return v != Integer.parseInt(val2.toString());
                case 3:
                    float v1;
                    if ((boolean) val1 == true) {
                        v1 = 1;
                    } else {
                        v1 = 0;
                    }
                    return v1 != Double.parseDouble(val2.toString());
                case 4:
                    int v2,
                     v3;
                    if ((boolean) val1 == true) {
                        v2 = 1;
                    } else {
                        v2 = 0;
                    }
                    if ((boolean) val2 == true) {
                        v3 = 1;
                    } else {
                        v3 = 0;
                    }
                    return v2 != v3;
                default:
                    errores.agregarerror(new ER(0, 0, val1.toString() + " != " + val2.toString(), 3,
                            "no se pueden verificar relacionalmente valores de tipo " + TipoString(tipo1) + "con tipo " + TipoString(tipo2)));
            }
        } else if (tipo1 == 5 || tipo1 == 6) {
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            SimpleDateFormat formato2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
            Date fecha1 = null, fecha2 = null;
            switch (tipo2) {
                case 1:
                    return val1.toString().compareTo(val2.toString()) != 0;
                case 5:
                    if (tipo1 == 5) {
                        try {
                            fecha1 = formato.parse(val1.toString());
                        } catch (ParseException ex) {
                            Logger.getLogger(InterpreteCSJ.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else if (tipo1 == 6) {
                        try {
                            fecha1 = formato2.parse(val1.toString());
                        } catch (ParseException ex) {
                            Logger.getLogger(InterpreteCSJ.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    try {
                        fecha2 = formato.parse(val2.toString());
                    } catch (ParseException ex) {
                        Logger.getLogger(InterpreteCSJ.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (fecha1 != null && fecha2 != null) {
                        return fecha1.compareTo(fecha2) != 0;
                    }
                case 6:
                    if (tipo1 == 5) {
                        try {
                            fecha1 = formato.parse(val1.toString());
                        } catch (ParseException ex) {
                            Logger.getLogger(InterpreteCSJ.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else if (tipo1 == 6) {
                        try {
                            fecha1 = formato2.parse(val1.toString());
                        } catch (ParseException ex) {
                            Logger.getLogger(InterpreteCSJ.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    try {
                        fecha2 = formato2.parse(val2.toString());
                    } catch (ParseException ex) {
                        Logger.getLogger(InterpreteCSJ.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (fecha1 != null && fecha2 != null) {
                        return fecha1.compareTo(fecha2) != 0;
                    }
                default:
                    errores.agregarerror(new ER(0, 0, val1.toString() + " != " + val2.toString(), 3,
                            "no se pueden verificar relacionalmente valores de tipo " + TipoString(tipo1) + "con tipo " + TipoString(tipo2)));
            }
        }
        return null;
    }

    private Object ejecutarIgual(nodo izq, nodo der, String ambito) {
        Object val1 = ejecutarExpresion(izq, ambito);
        Object val2 = ejecutarExpresion(der, ambito);
        int tipo1 = tipoObjeto(val1);
        int tipo2 = tipoObjeto(val2);

        if (tipo1 == 0 || tipo2 == 0) {
            return null;
        }

        if (tipo1 == 1) {
            switch (tipo2) {
                case 1:
                    return val1.toString().compareTo(val2.toString()) == 0;
                case 2:
                    int val = Integer.parseInt(val2.toString());
                    return val1.toString().length() == val;
                case 3:
                    double db = Double.parseDouble(val2.toString());
                    return val1.toString().length() == db;
                case 5:
                case 6:
                    return val1.toString().compareTo(val2.toString()) == 0;
                default:
                    errores.agregarerror(new ER(0, 0, val1.toString() + " == " + val2.toString(), 3,
                            "no se pueden verificar relacionalmente valores de tipo " + TipoString(tipo1) + "con tipo " + TipoString(tipo2)));
            }
        } else if (tipo1 == 2) {
            switch (tipo2) {
                case 1:
                    int val = Integer.parseInt(val1.toString());
                    return val == val2.toString().length();
                case 2:
                    return Integer.parseInt(val1.toString()) == Integer.parseInt(val2.toString());
                case 3:
                    return Double.parseDouble(val1.toString()) == Double.parseDouble(val2.toString());
                case 4:
                    int v;
                    if ((boolean) val2 == true) {
                        v = 1;
                    } else {
                        v = 0;
                    }
                    return Integer.parseInt(val1.toString()) == v;
                default:
                    errores.agregarerror(new ER(0, 0, val1.toString() + " == " + val2.toString(), 3,
                            "no se pueden verificar relacionalmente valores de tipo " + TipoString(tipo1) + "con tipo " + TipoString(tipo2)));
            }
        } else if (tipo1 == 3) {
            switch (tipo2) {
                case 1:
                    double val = Double.parseDouble(val1.toString());
                    return val == val2.toString().length();
                case 2:
                    return Double.parseDouble(val1.toString()) == Double.parseDouble(val2.toString());
                case 3:
                    return Double.parseDouble(val1.toString()) == Double.parseDouble(val2.toString());
                case 4:
                    int v;
                    if ((boolean) val2 == true) {
                        v = 1;
                    } else {
                        v = 0;
                    }
                    return Double.parseDouble(val1.toString()) == v;
                default:
                    errores.agregarerror(new ER(0, 0, val1.toString() + " == " + val2.toString(), 3,
                            "no se pueden verificar relacionalmente valores de tipo " + TipoString(tipo1) + "con tipo " + TipoString(tipo2)));
            }
        } else if (tipo1 == 4) {
            switch (tipo2) {
                case 2:
                    int v;
                    if ((boolean) val1 == true) {
                        v = 1;
                    } else {
                        v = 0;
                    }
                    return v == Integer.parseInt(val2.toString());
                case 3:
                    float v1;
                    if ((boolean) val1 == true) {
                        v1 = 1;
                    } else {
                        v1 = 0;
                    }
                    return v1 == Double.parseDouble(val2.toString());
                case 4:
                    int v2,
                     v3;
                    if ((boolean) val1 == true) {
                        v2 = 1;
                    } else {
                        v2 = 0;
                    }
                    if ((boolean) val2 == true) {
                        v3 = 1;
                    } else {
                        v3 = 0;
                    }
                    return v2 == v3;
                default:
                    errores.agregarerror(new ER(0, 0, val1.toString() + " == " + val2.toString(), 3,
                            "no se pueden verificar relacionalmente valores de tipo " + TipoString(tipo1) + "con tipo " + TipoString(tipo2)));
            }
        } else if (tipo1 == 5 || tipo1 == 6) {
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            SimpleDateFormat formato2 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
            Date fecha1 = null, fecha2 = null;
            switch (tipo2) {
                case 1:
                    return val1.toString().compareTo(val2.toString()) == 0;
                case 5:
                    if (tipo1 == 5) {
                        try {
                            fecha1 = formato.parse(val1.toString());
                        } catch (ParseException ex) {
                            Logger.getLogger(InterpreteCSJ.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else if (tipo1 == 6) {
                        try {
                            fecha1 = formato2.parse(val1.toString());
                        } catch (ParseException ex) {
                            Logger.getLogger(InterpreteCSJ.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    try {
                        fecha2 = formato.parse(val1.toString());
                    } catch (ParseException ex) {
                        Logger.getLogger(InterpreteCSJ.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (fecha1 != null && fecha2 != null) {
                        return fecha1.compareTo(fecha2) == 0;
                    }
                case 6:
                    if (tipo1 == 5) {
                        try {
                            fecha1 = formato.parse(val1.toString());
                        } catch (ParseException ex) {
                            Logger.getLogger(InterpreteCSJ.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else if (tipo1 == 6) {
                        try {
                            fecha1 = formato2.parse(val1.toString());
                        } catch (ParseException ex) {
                            Logger.getLogger(InterpreteCSJ.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    try {
                        fecha2 = formato2.parse(val1.toString());
                    } catch (ParseException ex) {
                        Logger.getLogger(InterpreteCSJ.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (fecha1 != null && fecha2 != null) {
                        return fecha1.compareTo(fecha2) == 0;
                    }
                default:
                    errores.agregarerror(new ER(0, 0, val1.toString() + " == " + val2.toString(), 3,
                            "no se pueden verificar relacionalmente valores de tipo " + TipoString(tipo1) + "con tipo " + TipoString(tipo2)));
            }
        }
        return null;
    }

    private Object ejecutarNOT(nodo der, String ambito) {
        Boolean val = false;
        Object val2 = ejecutarExpresion(der, ambito);
        int tipo = tipoObjeto(val2);
        if (tipo == 0) {
            return null;
        }
        if (tipo == 4) {
            val = Boolean.parseBoolean(val2.toString());
            return !val;
        }
        return null;
    }

    private Object ejecutarOR(nodo izq, nodo der, String ambito) {
        Object valee1 = ejecutarExpresion(izq, ambito);
        Object valee2 = ejecutarExpresion(der, ambito);

        if (tipoObjeto(valee2) == 0 || tipoObjeto(valee1) == 0) {
            return null;
        }
        try {
            boolean val1, val2;
            valee1 = ejecutarExpresion(izq, ambito);
            valee2 = ejecutarExpresion(der, ambito);

            val1 = Boolean.parseBoolean(valee1.toString());
            val2 = Boolean.parseBoolean(valee2.toString());
            return val1 || val2;
        } catch (Exception e) {
            errores.agregarerror(new ER(0, 0, valee1.toString() + " && " + valee2.toString(), 3,
                    "unicamente valores booleanos, no se pueden comparar valores de tipo " + TipoString(tipoObjeto(valee1)) + "con tipo " + TipoString(tipoObjeto(valee2))));
            return null;
        }
    }

    private Object ejecutarAND(nodo izq, nodo der, String ambito) {
        Object valee1 = ejecutarExpresion(izq, ambito);
        Object valee2 = ejecutarExpresion(der, ambito);

        if (tipoObjeto(valee2) == 0 || tipoObjeto(valee1) == 0) {
            return null;
        }

        try {
            boolean val1, val2;
            valee1 = ejecutarExpresion(izq, ambito);
            valee2 = ejecutarExpresion(der, ambito);

            val1 = Boolean.parseBoolean(valee1.toString());
            val2 = Boolean.parseBoolean(valee2.toString());
            return val1 && val2;
        } catch (Exception e) {
            errores.agregarerror(new ER(0, 0, valee1.toString() + " && " + valee2.toString(), 3,
                    "unicamente valores booleanos, no se pueden comparar valores de tipo " + TipoString(tipoObjeto(valee1)) + "con tipo " + TipoString(tipoObjeto(valee2))));
            return null;
        }
    }

    private int tipoObjeto(Object val) {
        if (val instanceof Integer) {
            return 2;
        } else if (val instanceof Double) {
            return 3;
        } else if (val instanceof Boolean) {
            return 4;
        } else if (val instanceof String) {
            try {

                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
                String valprueba = val.toString().replace(" ", "");
                formato.setLenient(false);
                if (valprueba.length() == 18) {
                    formato.parse(val.toString());
                    return 6;
                } else {
                    try {
                        formato = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        valprueba = val.toString().replace(" ", "");
                        formato.setLenient(false);
                        if (valprueba.length() == 10) {
                            formato.parse(val.toString());
                            return 5;
                        }
                    } catch (ParseException e1) {
                        return 1;
                    }
                }
            } catch (ParseException e) {
                try {
                    SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    String valprueba = val.toString().replace(" ", "");
                    formato.setLenient(false);
                    if (valprueba.length() == 10) {
                        formato.parse(val.toString());
                        return 5;
                    }
                } catch (ParseException e1) {
                    return 1;
                }
            }
            return 1;

        } else if (val instanceof JLabel) {
            return 7;
        } else if (val instanceof JPanel) {
            return 8;
        } else if (val instanceof JTextField) {
            return 9;
        } else if (val instanceof JButton) {
            return 10;
        } else if (val instanceof JTextArea) {
            return 11;
        } else if (val instanceof JSpinner) {
            return 12;
        } else if (val instanceof JTable) {
            return 13;
        } else if (val instanceof JComboBox) {
            return 14;
        }
        return 0;
    }

    private String TipoString(int tipo) {
        switch (tipo) {
            case 1:
                return "texto";
            case 2:
            case 3:
                return "Numerico";
            case 4:
                return "Booleano";
            case 5:
                return "Date";
            case 6:
                return "Datetime";
        }
        return "";
    }

    private Simbolo existeVariable(String id, String ambito) {
        TablaGeneral ta = this.ambitos.getTablaActual(ambito);
        if (ta != null) {
            return ta.existevariable(id.toLowerCase());
        }
        return null;
    }

    private Object casteoImplicito(String propiedad, String val) {
        switch (propiedad) {
            case "ancho":
                try {
                    int v = Integer.parseInt(val);
                    return v;
                } catch (Exception e) {
                    try {
                        Double v = Double.parseDouble(val);
                        return v;
                    } catch (Exception e1) {
                        return null;
                    }
                }
            case "alto":
                try {
                    int v = Integer.parseInt(val);
                    return v;
                } catch (Exception e) {
                    try {
                        int v = Integer.parseInt(val);
                        return v;
                    } catch (Exception e1) {
                        Double v = Double.parseDouble(val);
                        return v;
                    }
                }
        }
        return null;
    }

    private boolean existeVariable(nodo lid, String ambito) {
        TablaGeneral ta = this.ambitos.getTablaActual(ambito);
        if (ta != null) {
            for (nodo s : lid.getHijos()) {
                if (ta.existevariable(s.getNombre()) != null) {
                    this.errores.agregarerror(new ER(s.getFila(), s.getCol(), s.getNombre(), 3,
                            "La variable " + s.getNombre() + " ya existe en el ambito " + ambito));
                    return true;
                }
            }
        }
        return false;
    }
    
    public void actualizarPanel(){
        TablaGeneral gen = this.ambitos.getTablaActual("objetos");
        if(gen!=null){
            /*Object[] obj = gen.getPila().toArray();
            for (int i = 0; i < obj.length; i++) {
                TablaSimbolo tab = (TablaSimbolo) obj[i];
                for (int j = 0; j < tab.getSimbolos().size(); j++) {
                    Simbolo aux = tab.getSimbolos().get(j);
                    if (aux.getTipo().equals("panel")) {
                        JPanel pan = (JPanel)aux.getValor();
                        pan.validate();
                    }
                }
            }*/
            Simbolo s = gen.existevariable("cuerpo");
            if(s.getTipo().equals("panel")){
                JPanel pan = (JPanel)s.getValor();
                this.actuar=true;
                SwingUtilities.updateComponentTreeUI(pan);
                pan.validate();
                this.actuar=false;
            }
        }
    }
   
      public String getAlineado(String contenido, int tipo){
        switch (tipo) {
            case 1:
                //DERECHA
                return "<html><p align=right>" + contenido + "</p></html>";
            case 2:
                //IZQUIERDA
                return "<html><p align=left>" + contenido + "</p></html>";
            case 3:
                //CENTRADO
                return "<html><p align=center>" + contenido + "</p></html>";
            case 4:
                //JUSTIFICADO
                return "<html><p align=leading>" + contenido + "</p></html>";
            default:
                break;
        }
     return contenido;   
    }
      
       private void setAlign(Object objeto,String align) {
        int tipo = tipoObjeto(objeto);
        //if (tipo == 6) {
        //JPanel pan = 
        switch (align.toLowerCase()) {
            case "izquierda":
                switch (tipo) {
                    case 8:
                        JPanel pan = (JPanel) objeto;
                        pan.setLayout(new FlowLayout(FlowLayout.LEFT));
                        break;
                    case 7:
                        JLabel jbl = (JLabel) objeto;
                        //jbl.setLayout(new FlowLayout(FlowLayout.LEFT));
                        jbl.setText(getAlineado(jbl.getText(), 2));
                        break;
                    case 9:
                        JTextField txt = (JTextField) objeto;
                       // txt.setLayout(new FlowLayout(FlowLayout.LEFT));
                        txt.setText(getAlineado(txt.getText(), 2));
                        break;
                    case 10:
                        JButton btn = (JButton) objeto;
                        //btn.setLayout(new FlowLayout(FlowLayout.LEFT));
                        btn.setText(getAlineado(btn.getText(), 2));
                        break;
                    case 11:
                        JTextArea jtxt = (JTextArea) objeto;
                        //jtxt.setLayout(new FlowLayout(FlowLayout.LEFT));
                        jtxt.setText(getAlineado(jtxt.getText(), 2));
                        break;
                    case 12:
                        JSpinner spi = (JSpinner) objeto;
                        spi.setLayout(new FlowLayout(FlowLayout.LEFT));
                        break;
                    case 13:
                        JTable tab = (JTable) objeto;
                        tab.setLayout(new FlowLayout(FlowLayout.LEFT));
                        break;
                    case 14:
                        JComboBox box = (JComboBox) objeto;
                        box.setLayout(new FlowLayout(FlowLayout.LEFT));
                        break;
                }
                break;
            case "derecha":
                switch (tipo) {
                    case 8:
                        JPanel pan = (JPanel) objeto;
                        pan.setLayout(new FlowLayout(FlowLayout.RIGHT));
                        break;
                    case 7:
                        JLabel jbl = (JLabel) objeto;
                        //jbl.setLayout(new FlowLayout(FlowLayout.RIGHT));
                        jbl.setText(getAlineado(jbl.getText(), 1));
                        break;
                    case 9:
                        JTextField txt = (JTextField) objeto;
                        //txt.setLayout(new FlowLayout(FlowLayout.RIGHT));
                        txt.setText(getAlineado(txt.getText(), 1));
                        break;
                    case 10:
                        JButton btn = (JButton) objeto;
                        //btn.setLayout(new FlowLayout(FlowLayout.RIGHT));
                        btn.setText(getAlineado(btn.getText(), 1));
                        break;
                    case 11:
                        JTextArea jtxt = (JTextArea) objeto;
                        jtxt.setLayout(new FlowLayout(FlowLayout.RIGHT));
                        break;
                    case 12:
                        JSpinner spi = (JSpinner) objeto;
                        spi.setLayout(new FlowLayout(FlowLayout.RIGHT));
                        break;
                    case 13:
                        JTable tab = (JTable) objeto;
                        tab.setLayout(new FlowLayout(FlowLayout.RIGHT));
                        break;
                    case 14:
                        JComboBox box = (JComboBox) objeto;
                        box.setLayout(new FlowLayout(FlowLayout.RIGHT));
                        break;
                }
                break;
            case "centrado":
                switch (tipo) {
                    case 8:
                        JPanel pan = (JPanel) objeto;
                        pan.setLayout(new FlowLayout(FlowLayout.CENTER));
                        break;
                    case 7:
                        JLabel jbl = (JLabel) objeto;
                        //jbl.setLayout(new FlowLayout(FlowLayout.CENTER));
                        jbl.setText(getAlineado(jbl.getText(), 3));
                        break;
                    case 9:
                        JTextField txt = (JTextField) objeto;
                        //txt.setLayout(new FlowLayout(FlowLayout.CENTER));
                        txt.setText(getAlineado(txt.getText(), 3));
                        break;
                    case 10:
                        JButton btn = (JButton) objeto;
                        //btn.setLayout(new FlowLayout(FlowLayout.CENTER));
                        btn.setText(getAlineado(btn.getText(), 3));
                        break;
                    case 11:
                        JTextArea jtxt = (JTextArea) objeto;
                        //jtxt.setLayout(new FlowLayout(FlowLayout.CENTER));
                        jtxt.setText(getAlineado(jtxt.getText(), 3));
                        break;
                    case 12:
                        JSpinner spi = (JSpinner) objeto;
                        spi.setLayout(new FlowLayout(FlowLayout.CENTER));
                        break;
                    case 13:
                        JTable tab = (JTable) objeto;
                        tab.setLayout(new FlowLayout(FlowLayout.CENTER));
                        break;
                    case 14:
                        JComboBox box = (JComboBox) objeto;
                        box.setLayout(new FlowLayout(FlowLayout.CENTER));
                        break;
                }
                break;
            case "justificado":
                switch (tipo) {
                    case 8:
                        JPanel pan = (JPanel) objeto;
                        pan.setLayout(new FlowLayout(FlowLayout.LEADING));
                        break;
                    case 7:
                        JLabel jbl = (JLabel) objeto;
                        //jbl.setLayout(new FlowLayout(FlowLayout.LEADING));
                        jbl.setText(getAlineado(jbl.getText(), 4));
                        break;
                    case 9:
                        JTextField txt = (JTextField) objeto;
                        //txt.setLayout(new FlowLayout(FlowLayout.LEADING));
                        txt.setText(getAlineado(txt.getText(), 4));
                        break;
                    case 10:
                        JButton btn = (JButton) objeto;
                        //btn.setLayout(new FlowLayout(FlowLayout.LEADING));
                        btn.setText(getAlineado(btn.getText(), 4));
                        break;
                    case 11:
                        JTextArea jtxt = (JTextArea) objeto;
                      //  jtxt.setLayout(new FlowLayout(FlowLayout.LEADING));
                        jtxt.setText(getAlineado(jtxt.getText(), 4));
                        break;
                    case 12:
                        JSpinner spi = (JSpinner) objeto;
                        spi.setLayout(new FlowLayout(FlowLayout.LEADING));
                        break;
                    case 13:
                        JTable tab = (JTable) objeto;
                        tab.setLayout(new FlowLayout(FlowLayout.LEADING));
                        break;
                    case 14:
                        JComboBox box = (JComboBox) objeto;
                        box.setLayout(new FlowLayout(FlowLayout.LEADING));
                        break;
                }
                break;
        }
    }
}
