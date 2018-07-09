/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EDD.CSJ.Interprete;

import EDD.CSJ.*;
import EDDTS.*;
import Errores.ER;
import Errores.ListaConsolas;
import Errores.ListaErrores;
import Gramaticas.CHTML.AnalisisLex_CHTML;
import Gramaticas.CHTML.SintacticoCHTML;
import Gramaticas.CJS.*;
import Gramaticas.CSS.*;
import Interfaz.Inicio;
import Interfaz.Pagina;
import Interfaz.PantallaInicio;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author danielvega
 */
public class InterpreteCHTML {

    private JPanel panel;
    public HashMap<String, TablaAmbitos> cjs;
    public HashMap<String, InterpreteCSS> css;
    private InterpreteCSJ intcsj;
    private InterpreteCSS intcss;
    public HashMap<String, Eventos> events;
    private ArrayList<String> rutasCJS;
    private ArrayList<String> rutasCSS;
    public String titulo = "";
    public ListaErrores errores;
    public ListaConsolas consola;

    private boolean error = false;

    public boolean actur = false;
    boolean negrilla = false;
    boolean cursiva = false;
    boolean mayus = false;
    boolean minus = false;
    boolean capital = false, click = false;
    boolean opaque = false, borde = false, visible = true, autoredi = false, tienepropiedades = false;
    float tamtext = -1, tamborde = -1;
    Object tamtemp = null, tambordeTEMP = null;
    String align = "", texto = "", letra = "", fondo_elemento = "", color_text = "";
    String autored = "", color_brde = "", id = "", ruta = "", metodo = "";
    int ancho = -1, alto = -1;
    int contador = 0;

    public String contenidoCHTML="";
    public String contenidoCCS="";
    public String contenidoCJS="";
    public String comandoactual = "";
    public JFrame result;
    Pagina actual = null;
    public void settearaccion(boolean ba) {
        this.intcsj.actuar = ba;
    }

    private void limpiarVariables() {
        this.cursiva = false;
        this.negrilla = false;
        this.mayus = false;
        this.minus = false;
        this.capital = false;
        this.opaque = false;
        this.borde = false;
        this.visible = true;
        this.autoredi = false;
        this.tamtext = -1;
        this.tamborde = -1;
        this.tamtemp = null;
        this.tambordeTEMP = null;
        this.align = "";
        this.texto = "";
        this.letra = "";
        this.fondo_elemento = "";
        this.color_text = "";
        this.autored = "";
        this.color_brde = "";
        this.ancho = -1;
        this.alto = -1;
        this.id = "";
        this.ruta = "";
        this.click = false;
        this.metodo = "";
        this.tienepropiedades = false;
    }

    public InterpreteCHTML(Pagina pag) {

        //this.panel = Pagina.getInstancia().panelHTML;
        panel = pag.panelHTML;
        actual=pag;
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        this.panel.setOpaque(false);
        this.cjs = new HashMap<>();
        this.css = new HashMap<>();
        this.intcsj = new InterpreteCSJ();
        this.intcss = new InterpreteCSS();
        this.events = new HashMap<>();
        this.rutasCJS = new ArrayList<>();
        this.rutasCSS = new ArrayList<>();
        this.errores = ListaErrores.getListaerror();
        agregarNuevoGrupoObjetos("panel");
        agregarNuevoGrupoObjetos("texto");
        agregarNuevoGrupoObjetos("cajatexto");
        agregarNuevoGrupoObjetos("boton");
        agregarNuevoGrupoObjetos("areatexto");
        agregarNuevoGrupoObjetos("spinner");
        agregarNuevoGrupoObjetos("imagen");
        agregarNuevoGrupoObjetos("tabla");
        agregarNuevoGrupoObjetos("enlace");
        agregarNuevoGrupoObjetos("cajaopcion");
    }

    private void agregarNuevoGrupoObjetos(String n) {
        if (!this.events.containsKey(n)) {
            this.events.put(n, new Eventos());
        }
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
            }
        }
    }

    public JFrame getFrame() {
        return this.result;
    }

    public JPanel getPanel() {
        return this.panel;
    }

    private String getContenido(String ruta) {
        String nombre = ruta.replace("\"", "");
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        try {
            archivo = new File(nombre);
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            String contenido = "";
            String linea;
            while ((linea = br.readLine()) != null) {
                //System.out.println(linea);
                contenido = contenido + linea + "\n";
            }

            return contenido;

        } catch (IOException e) {
        } finally {
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (IOException e2) {
            }
        }
        return "";
    }
    
    private JScrollPane compilarCHTML(String ruta){
        String contenido = getContenido(ruta);
        AnalisisLex_CHTML lexico = new AnalisisLex_CHTML(new BufferedReader(new StringReader(contenido)));
        SintacticoCHTML sintactico = new SintacticoCHTML(lexico);
        try {

            //SE INICIA LA COMPILACION LEXICO Y SINTACTICO
            sintactico.parse();

            ArbolCSJ raiz = sintactico.arbol;

            if (raiz != null) {
                //  System.out.println(raiz.getRaiz().hashCode());
                GraficarArbol grafo = new GraficarArbol();
                grafo.recorrrerarbol(raiz.getRaiz(), 0);
                grafo.contador = 0;
                grafo.enlazararbol(raiz.getRaiz(), 0);
                grafo.graficar(grafo.auxdot, "chtml.png");
                Pagina n = new Pagina();
                this.panel.removeAll();
                //InterpreteCHTML chtml = new InterpreteCHTML(n);
                ejecutarchtml(raiz.getRaiz(), "global");
                //this.titulo=chtml.titulo;
                
                int indice = PantallaInicio.getInstancia().getIndexSelect();
                PantallaInicio.getInstancia().getPestanias().setTitleAt(indice, titulo);
                actual.HabilitarBotonones();
                actual.getHistorial().add(actual.contador,ruta);
                //actual.contador++;
                this.panel.repaint();
                

            }
        } catch (Exception ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void ejecutarchtml(nodo raiz, String ambito) {
        if (raiz != null) {

            if (raiz.getNombre().equals("INI")) {
                nodo ch = raiz.getHijos().get(0);
                for (nodo s : ch.getHijos()) {
                    switch (s.getNombre()) {
                        case "encabezado":
                            ejecutarencabezado(s, ambito, true);
                            break;
                        case "cuerpo":
                            nuevoGrupoTablaSimbotos("objetos");
                            Object val = ejecutarCuerpo(s, ambito);
                            nuevoObjeto("cuerpo", s.getFila(), s.getCol(), this.panel, "panel");
                            break;
                    }
                }

                for (nodo s : ch.getHijos()) {
                    switch (s.getNombre()) {
                        case "encabezado":
                            ejecutarencabezado(s, ambito, false);
                            break;
                    }
                }

                //VALIDANDO QUE TENGA EVENTO LISTO
                TablaAmbitos ta = getTablaAmbitos("$doclisto");
                if (ta != null) {
                    TablaGeneral gen = ta.getTablaActual("$doclisto");
                    if (gen != null) {
                        nodo ins = getInstruccionesMetodo("$doclisto");
                        if (ins != null) {
                            this.intcsj.setTablaAmbitos(getTablaAmbitos("$doclisto"));
                            this.intcsj.ejecutarMetodo(ins, "$doclisto");
                        }
                    }
                }
            }
        }
    }

    private Object ejecutarCuerpo(nodo raiz, String ambito) {
        Object val = null;
        if (raiz != null) {
            JPanel panelsito = new JPanel();
            panelsito.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
            for (nodo s : raiz.getHijos()) {
                switch (s.getNombre()) {
                    case "panel":
                        JPanel pan = (JPanel) dibujarpanel(s, ambito);
                        pan.validate();
                        //this.panel.add(pan);
                        //this.panel.validate();
                        if (this.error == false) {
                            panelsito.add(pan);
                        }
                        
                        //nuevoObjeto(s, val)
                        break;
                    case "texto":
                        val = dibujarTexto(s, ambito);
                        if (tipoObjeto(val) == 5) {
                            JLabel lbl = (JLabel) val;
                            /*    this.panel.add(lbl);
                            this.panel.validate();*/
                            if (this.error == false) {
                                panelsito.add(lbl);
                            }
                        }
                        limpiarVariables();
                        break;
                    case "cajatexto":
                        val = dibujarCajaTexto(s, ambito);
                        if (tipoObjeto(val) == 7) {
                            JTextField lbl = (JTextField) val;
                            if (this.error == false) {
                                panelsito.add(lbl);
                            }
                        }
                        limpiarVariables();
                        break;
                    case "boton":
                        val = dibujarBoton(s, ambito);
                        if (tipoObjeto(val) == 8) {
                            JButton lbl = (JButton) val;
                            //this.panel.add(lbl);
                            if (this.error == false) {
                                panelsito.add(lbl);
                            }
                        }
                        limpiarVariables();
                        break;
                    case "areatexto":
                        val = dibujarAreaTexto(s, ambito);
                        if (tipoObjeto(val) == 9) {
                            JTextArea lbl = (JTextArea) val;
                            /*lbl.setRows(5);
                            lbl.setColumns(20);*/
                            JPanel pane = new JPanel();
                            JScrollPane js = new JScrollPane(lbl);
                            //js.setViewportView(lbl);
                            pane.add(js);
                            //this.panel.add(pane);
                            if (this.error == false) {
                                panelsito.add(pane);
                            }
                        }
                        limpiarVariables();
                        break;
                    case "spinner":
                        val = dibujarSpinner(s, ambito);
                        if (tipoObjeto(val) == 10) {
                            JSpinner lbl = (JSpinner) val;
                            //this.panel.add(lbl);
                            if (this.error == false) {
                                panelsito.add(lbl);
                            }
                        }
                        limpiarVariables();
                        break;
                    case "imagen":
                        val = dibujarimagen(s, ambito);
                        if (tipoObjeto(val) == 8) {
                            JButton bt = (JButton) val;
                            //this.panel.add(bt);
                            if (this.error == false) {
                                panelsito.add(bt);
                            }
                        }
                        limpiarVariables();
                        break;
                    case "tabla":
                        val = dibujartabla(s, ambito);
                        if (tipoObjeto(val) == 11) {
                            JTable tb = (JTable) val;
                            JScrollPane scrollPane = new JScrollPane(tb, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                            //this.panel.add(scrollPane);
                            if (this.error == false) {
                                panelsito.add(scrollPane);
                            }
                        }
                        limpiarVariables();
                        break;
                    case "enlace":
                        val = dibujarEnlace(s, ambito);
                        if (tipoObjeto(val) == 5) {
                            JLabel jbl = (JLabel) val;
                            //this.panel.add(jbl);
                            if (this.error == false) {
                                panelsito.add(jbl);
                            }
                        }
                        limpiarVariables();
                        break;
                    case "cajaopcion":
                        val = dibujarCajaOpcion(s, ambito);
                        if (tipoObjeto(val) == 12) {
                            JComboBox jbl = (JComboBox) val;
                            //this.panel.add(jbl);
                            if (this.error == false) {
                                panelsito.add(jbl);
                            }
                        }
                        limpiarVariables();
                        break;
                    case "salto":
                        val = dibujarSalto(s, ambito);
                        if (tipoObjeto(val) == 5) {
                            /* JLabel jbl = (JLabel) val;
                            this.panel.add(jbl);*/
                            this.panel.add(panelsito);
                            panelsito = new JPanel();
                            panelsito.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
                        }
                        limpiarVariables();
                        break;
                }
                this.error = false;
            }
            this.panel.add(panelsito);
        }
        return val;
    }

    private Object dibujarSalto(nodo raiz, String ambito) {
        if (raiz != null) {
            JLabel lbl = new JLabel();
            String text = "";
            for (int i = 0; i < 800; i++) {
                text = text + " ";
            }
            lbl.setText(text);
            lbl.setSize(2, 800);
            return lbl;
        }
        return null;
    }

    private Object dibujarCajaOpcion(nodo raiz, String ambito) {
        if (raiz != null) {
            int hijos = raiz.getHijos().size();
            if (hijos == 1) {
                nodo listaopciones = raiz.getHijos().get(0);
                JComboBox box = new JComboBox();
                Vector<Object> opciones = new Vector<>();
                Object val = null;
                for (nodo s : listaopciones.getHijos()) {
                    val = getElementosOpcion(s, ambito);
                    if (val != null) {

                        opciones.add(val);
                    }
                }
                box = new JComboBox(opciones);
                box.setSelectedIndex(opciones.size() - 1);
                return box;
            } else if (hijos == 2) {
                nodo propiedades = raiz.getHijos().get(0);
                setPropiedades(propiedades);
                nodo listaopciones = raiz.getHijos().get(1);
//                JComboBox box = new JComboBox();
                Vector<Object> opciones = new Vector<>();
                Object val = null;
                for (nodo s : listaopciones.getHijos()) {
                    val = getElementosOpcion(s, ambito);
                    if (val != null) {

                        opciones.add(val);
                    }
                }
                JComboBox box = new JComboBox(opciones);
                box.setSelectedIndex(opciones.size() - 1);

                //PROPIEDADES
                //TODAS
                if (!"".equals(align)) {
                    setAlign(box);
                }
                if (this.tienepropiedades == true) {
                    if (!"".equals(align)) {
                        setAlign(box);
                    }
                    if (!"".equals(this.letra)) {
                        if (this.tamtext != -1) {
                            int tam = (int) this.tamtext;
                            if (this.negrilla == true && this.cursiva == true) {
                                Font f = new Font(this.letra, Font.BOLD | Font.ITALIC, tam);
                                box.setFont(f);
                            } else if (this.negrilla == true) {
                                Font f = new Font(this.letra, Font.BOLD, tam);
                                box.setFont(f);
                            } else if (this.cursiva == true) {
                                Font f = new Font(this.letra, Font.ITALIC, tam);
                                box.setFont(f);
                            }
                        }
                    }
                    if (this.tamborde > 0 && !"".equals(this.color_brde)) {
                        //AbstractBorder brdrLeft = new TextBubbleBorder(Color.BLACK,2,16,16);
                        Color cl = getColor(this.color_brde);
                        LineBorder line = new LineBorder(cl, Integer.parseInt(this.tambordeTEMP.toString()), this.borde);
                        box.setBorder(line);
                    }
                    box.setOpaque(this.opaque);
                    if (!"".equals(this.fondo_elemento)) {
                        Color cl = getColor(this.fondo_elemento);
                        if (cl != null) {
                            box.setBackground(cl);
                        }
                    }
                    box.setVisible(this.visible);

                } else {
                    //VALIDANDO QUE NO HAYA UN ID PARA PINTAR
                    if (!this.id.equals("")) {
                        CSS propID = getID(this.id);
                        if (propID != null) {
                            setPropiedades(propID);

                            if (!"".equals(align)) {
                                setAlign(box);
                            }
                            if (!"".equals(this.letra)) {
                                if (this.tamtext != -1) {
                                    int tam = (int) this.tamtext;
                                    if (this.negrilla == true && this.cursiva == true) {
                                        Font f = new Font(this.letra, Font.BOLD | Font.ITALIC, tam);
                                        box.setFont(f);
                                    } else if (this.negrilla == true) {
                                        Font f = new Font(this.letra, Font.BOLD, tam);
                                        box.setFont(f);
                                    } else if (this.cursiva == true) {
                                        Font f = new Font(this.letra, Font.ITALIC, tam);
                                        box.setFont(f);
                                    }
                                }
                            }
                            if (this.tamborde > 0 && !"".equals(this.color_brde)) {
                                Color cl = getColor(this.color_brde);
                                LineBorder line = new LineBorder(cl, Integer.parseInt(this.tambordeTEMP.toString()), this.borde);
                                box.setBorder(line);
                            }
                            box.setOpaque(this.opaque);
                            if (!"".equals(this.fondo_elemento)) {
                                Color cl = getColor(this.fondo_elemento);
                                if (cl != null) {
                                    box.setBackground(cl);
                                }
                            }

                            box.setVisible(this.visible);
                        }
                    }
                }
                if (this.error == false) {
                    nuevoObjeto(this.id, raiz.getFila(), raiz.getCol(), box, "cajaopcion");
                    if (!"".equals(this.ruta)) {
                        Eventos evt = events.get(this.id);
                        if (evt != null) {
                            evt.setRuta(this.ruta);
                        }
                    }
                    if (!"".equals(this.id)) {
                        box.setName(this.id);
                    }
                    if (this.alto > 0 && this.ancho > 0) {
                        box.setPreferredSize(new Dimension(ancho, alto));
                    }
                    if (this.click == true && !"".equals(this.metodo)) {
                        nuevoEventoObjeto(this.id, this.metodo, "c");
                    }
                    box.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Eventos evt = events.get(box.getName());
                            if (evt != null) {
                                ArrayList<String> clk = evt.getClick();
                                for (int i = 0; i < clk.size(); i++) {
                                    String meto = clk.get(i).replace("(", "").replace(")", "");
                                    nodo ins = getInstruccionesMetodo(meto);
                                    if (ins != null) {
                                        InterpreteCHTML.this.intcsj.setTablaAmbitos(getTablaAmbitos(meto));
                                        InterpreteCHTML.this.intcsj.ejecutarMetodo(ins, meto);
                                    }
                                }
                                if (!evt.getRuta().equals("") /*&& aplicaruta == true*/) {
                                    System.out.println("Tengo redireccion:" + evt.getRuta());
                                }
                            }
                        }
                    });                   
                    verificarListo(box.getName());
                }
                box.repaint();
                return box;
            }
        }

        return null;
    }

    private Object getElementosOpcion(nodo raiz, String ambito) {
        if (raiz != null) {
            Object val = null;
            int hijos = raiz.getHijos().size();
            if (hijos == 2) {
                nodo eti = raiz.getHijos().get(1).getHijos().get(0);
                switch (eti.getNombre()) {
                    case "iden":
                        val = eti.getHijos().get(0).getNombre().replace("\"", "");
                        //val = "<html><b>"+eti.getHijos().get(0).getNombre().replace("\"", "") + "</b></html>";
                        break;
                    case "num":
                        val = ejecutarExpresion(eti, ambito);
                        break;
                    case "cadena":
                        val = eti.getHijos().get(0).getNombre().replace("\"", "");
                        break;
                }
            }
            if (val != null) {
                if (mayus == true) {
                    val = val.toString().toUpperCase();
                } else if (minus == true) {
                    val = val.toString().toLowerCase();
                } else if (capital == true) {
                    val = capitalT(val.toString());
                }
                
                if(!"".equals(this.align)){
                    setAlign(val);
                }
            }

            /* if (!"".equals(this.letra)) {
                        if (this.tamtext != -1) {
                            int tam = (int) this.tamtext;
                            if (this.negrilla == true && this.cursiva == true) {
                                Font f = new Font(this.letra, Font.BOLD | Font.ITALIC, tam);
                                jbl.setFont(f);
                            } else if (this.negrilla == true) {
                                Font f = new Font(this.letra, Font.BOLD, tam);
                                jbl.setFont(f);
                            } else if (this.cursiva == true) {
                                Font f = new Font(this.letra, Font.ITALIC, tam);
                                jbl.setFont(f);
                            }
                        }
                    }
                    if (this.tamborde > 0 && !"".equals(this.color_brde)) {
                        //AbstractBorder brdrLeft = new TextBubbleBorder(Color.BLACK,2,16,16);
                        Color cl = getColor(this.color_brde);
                        LineBorder line = new LineBorder(cl, Integer.parseInt(this.tambordeTEMP.toString()), this.borde);
                        jbl.setBorder(line);
                    }
                    jbl.setOpaque(this.opaque);
                    if (!"".equals(this.fondo_elemento)) {
                        Color cl = getColor(this.fondo_elemento);
                        if (cl != null) {
                            jbl.setBackground(cl);
                        }
                    }
                    if (this.alto > 0 && this.ancho > 0) {
                        jbl.setPreferredSize(new Dimension(ancho, alto));
                    }
                    if (!"".equals(this.id)) {
                        jbl.setName(this.id);
                    }
                    jbl.setVisible(this.visible);

                    if (this.click == true && !"".equals(this.metodo)) {
                        jbl.setActionCommand(this.metodo);
                        jbl.addActionListener((ActionEvent e) -> {
                            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                            System.out.println(jbl.getActionCommand());
                            String meto = jbl.getActionCommand().replace("(", "").replace(")", "");
                            nodo ins = getInstruccionesMetodo(meto);
                            if (ins != null) {
                                this.intcsj.setTablaAmbitos(getTablaAmbitos(meto));
                                this.intcsj.ejecutarMetodo(ins, meto);
                            }

                            //this.comandoactual=jbl.getActionCommand();
                        });
                    }*/
            return val;
        }
        return null;
    }

    private Object dibujarEnlace(nodo raiz, String ambito) {
        if (raiz != null) {
            int hijos = raiz.getHijos().size();
            if (hijos == 2) {
                JLabel jbl = new JLabel();
                nodo propiedades = raiz.getHijos().get(0);
                nodo expr = raiz.getHijos().get(1);
                setPropiedades(propiedades);
                Object val = ejecutarExpresion(expr, ambito);
                if (tipoObjeto(val) == 1) {
                    jbl.setText("<html><body><span style='text-decoration: line-through; '>" + val.toString() + "</span></body></html>");
                    jbl.setForeground(Color.BLUE);
                    /*jbl.setText("<html><p align=right>" + val.toString() + "</p></html>");
                    jbl.setOpaque(false);
                    jbl.setBackground(Color.red);
                    jbl.setPreferredSize(new Dimension(100,100));
                    //jbl.setText(val.toString());
                    jbl.setForeground(Color.BLUE);
                    //jbl.setBackground(Color.BLUE);*/
                }
                if (propiedades.getNombre().equals("listapropiedades")) {
                    setPropiedades(propiedades);
                    //PROPIEDADES
                    //TODAS

                    if (!"".equals(align)) {
                        setAlign(jbl);
                    }
                    if (!"".equals(this.texto)) {
                        jbl.setText(val.toString());
                    }
                    if (mayus == true) {
                        jbl.setText(jbl.getText().toUpperCase());
                    } else if (minus == true) {
                        jbl.setText(jbl.getText().toLowerCase());
                    } else if (capital == true) {
                        jbl.setText(capitalT(jbl.getText()));
                    }
                    if (!"".equals(this.letra)) {
                        if (this.tamtext != -1) {
                            int tam = (int) this.tamtext;
                            if (this.negrilla == true && this.cursiva == true) {
                                Font f = new Font(this.letra, Font.BOLD | Font.ITALIC, tam);
                                jbl.setFont(f);
                            } else if (this.negrilla == true) {
                                Font f = new Font(this.letra, Font.BOLD, tam);
                                jbl.setFont(f);
                            } else if (this.cursiva == true) {
                                Font f = new Font(this.letra, Font.ITALIC, tam);
                                jbl.setFont(f);
                            }
                        }
                    }
                    if (this.tamborde > 0 && !"".equals(this.color_brde)) {
                        //AbstractBorder brdrLeft = new TextBubbleBorder(Color.BLACK,2,16,16);
                        Color cl = getColor(this.color_brde);
                        LineBorder line = new LineBorder(cl, Integer.parseInt(this.tambordeTEMP.toString()), this.borde);
                        jbl.setBorder(line);
                    }
                    jbl.setOpaque(this.opaque);
                    if (!"".equals(this.fondo_elemento)) {
                        Color cl = getColor(this.fondo_elemento);
                        if (cl != null) {
                            jbl.setBackground(cl);
                        }
                    }
                    if (this.alto > 0 && this.ancho > 0) {
                        jbl.setPreferredSize(new Dimension(ancho, alto));
                    }
                    if (!"".equals(this.id)) {
                        jbl.setName(this.id);
                    } else {
                        jbl.setName("enlace" + contador);
                        contador++;
                    }
                    jbl.setVisible(this.visible);

                    nuevoObjeto(jbl.getName(), raiz.getFila(), raiz.getCol(), jbl, "enlace");
                    if (!"".equals(this.ruta)) {
                        Eventos evt = events.get(jbl.getName());
                        if (evt != null) {
                            evt.setRuta(this.ruta);
                        }
                    }
                    jbl.addMouseListener(acciones(jbl.getName(), true));

                }
                jbl.repaint();
                return jbl;
            }
        }
        return null;
    }

    private Object dibujartabla(nodo raiz, String ambito) {
        if (raiz != null) {
            int hijos = raiz.getHijos().size();
            if (hijos == 2) {
                JTable jbl = new JTable();
                nodo listacol = raiz.getHijos().get(1);
                nodo propiedades = raiz.getHijos().get(0);
                DefaultTableModel lista = null;//{"id","lexema"}
                for (nodo s : listacol.getHijos()) {
                    switch (s.getNombre()) {
                        case "listaceldas":
                            lista = getTitulos(s, ambito);
                            break;

                    }
                    if (lista != null) {
                        break;
                    }
                }
                if (lista == null) {
                    return null;
                }

                Object[] val = null;
                for (nodo s : listacol.getHijos()) {
                    switch (s.getNombre()) {
                        case "listaceldas":
                            val = getAtributosTabla(s, ambito);
                            break;

                    }
                    if (val != null) {
                        lista.addRow(val);
                        //lista.addRow(val);
                        jbl.setModel(lista);
                        Object[] l = (Object[]) val;
                        for (int j = 0; j < l.length; j++) {
                            jbl.getColumnModel().getColumn(j).setCellRenderer(render);

                        }
                    }
                }
                count = 0;
                //jbl.setModel(lista);

                //APLICANDO PROPIEDADES
                if (propiedades.getNombre().equals("listapropiedades")) {
                    setPropiedades(propiedades);
                    //PROPIEDADES
                    //TODAS
                    if (this.tienepropiedades == true) {
                        if (!"".equals(align)) {
                            setAlign(jbl);
                        }
                        /*if (!"".equals(this.texto)) {
                            jbl.setText(this.texto);
                        }
                        if (mayus == true) {
                            jbl.setText(jbl.getText().toUpperCase());
                        } else if (minus == true) {
                            jbl.setText(jbl.getText().toLowerCase());
                        } else if (capital == true) {
                            jbl.setText(capitalT(jbl.getText()));
                        }*/
                        if (!"".equals(this.letra)) {
                            if (this.tamtext != -1) {
                                int tam = (int) this.tamtext;
                                if (this.negrilla == true && this.cursiva == true) {
                                    Font f = new Font(this.letra, Font.BOLD | Font.ITALIC, tam);
                                    jbl.setFont(f);
                                } else if (this.negrilla == true) {
                                    Font f = new Font(this.letra, Font.BOLD, tam);
                                    jbl.setFont(f);
                                } else if (this.cursiva == true) {
                                    Font f = new Font(this.letra, Font.ITALIC, tam);
                                    jbl.setFont(f);
                                }
                            }
                        }
                        if (this.tamborde > 0 && !"".equals(this.color_brde)) {
                            //AbstractBorder brdrLeft = new TextBubbleBorder(Color.BLACK,2,16,16);
                            Color cl = getColor(this.color_brde);
                            LineBorder line = new LineBorder(cl, Integer.parseInt(this.tambordeTEMP.toString()), this.borde);
                            jbl.setBorder(line);
                        }
                        jbl.setOpaque(this.opaque);
                        if (!"".equals(this.fondo_elemento)) {
                            Color cl = getColor(this.fondo_elemento);
                            if (cl != null) {
                                jbl.setBackground(cl);
                            }
                        }
                        if (this.alto > 0 && this.ancho > 0) {
                            jbl.setPreferredSize(new Dimension(ancho, alto));
                        }

                        jbl.setVisible(this.visible);

                    } else {
                        //VALIDANDO QUE NO HAYA UN ID PARA PINTAR
                        if (!this.id.equals("")) {
                            CSS propID = getID(this.id);
                            if (propID != null) {
                                setPropiedades(propID);
                                if (!"".equals(align)) {
                                    setAlign(val);
                                }
                                /*if (!"".equals(this.texto)) {
                            jbl.setText(this.texto);
                        }
                        if (mayus == true) {
                            jbl.setText(jbl.getText().toUpperCase());
                        } else if (minus == true) {
                            jbl.setText(jbl.getText().toLowerCase());
                        } else if (capital == true) {
                            jbl.setText(capitalT(jbl.getText()));
                        }*/
                                if (!"".equals(this.letra)) {
                                    if (this.tamtext != -1) {
                                        int tam = (int) this.tamtext;
                                        if (this.negrilla == true && this.cursiva == true) {
                                            Font f = new Font(this.letra, Font.BOLD | Font.ITALIC, tam);
                                            jbl.setFont(f);
                                        } else if (this.negrilla == true) {
                                            Font f = new Font(this.letra, Font.BOLD, tam);
                                            jbl.setFont(f);
                                        } else if (this.cursiva == true) {
                                            Font f = new Font(this.letra, Font.ITALIC, tam);
                                            jbl.setFont(f);
                                        }
                                    }
                                }
                                if (this.tamborde > 0 && !"".equals(this.color_brde)) {
                                    //AbstractBorder brdrLeft = new TextBubbleBorder(Color.BLACK,2,16,16);
                                    Color cl = getColor(this.color_brde);
                                    LineBorder line = new LineBorder(cl, Integer.parseInt(this.tambordeTEMP.toString()), this.borde);
                                    jbl.setBorder(line);
                                }
                                jbl.setOpaque(this.opaque);
                                if (!"".equals(this.fondo_elemento)) {
                                    Color cl = getColor(this.fondo_elemento);
                                    if (cl != null) {
                                        jbl.setBackground(cl);
                                    }
                                }

                                jbl.setVisible(this.visible);

                            }
                        }
                    }
                }
                if (this.error == false) {
                    nuevoObjeto(this.id, raiz.getFila(), raiz.getCol(), jbl, "tabla");
                    if (!"".equals(this.id)) {
                        jbl.setName(this.id);
                    }
                    if (!"".equals(this.ruta)) {
                        Eventos evt = events.get(jbl.getName());
                        if (evt != null) {
                            evt.setRuta(this.ruta);
                        }
                    }
                    if (this.alto > 0 && this.ancho > 0) {
                        jbl.setPreferredSize(new Dimension(ancho, alto));
                    }
                    if (this.click == true && !"".equals(this.metodo)) {
                        nuevoEventoObjeto(this.id, this.metodo, "c");
                        jbl.addMouseListener(acciones(this.id, true));
                    }

                }
                return jbl;
            } else if (hijos == 1) {
                JTable jbl = new JTable();
                nodo listacol = raiz.getHijos().get(0);
                DefaultTableModel lista = null;
                for (nodo s : listacol.getHijos()) {
                    switch (s.getNombre()) {
                        case "listaceldas":
                            lista = getTitulos(s, ambito);
                            break;

                    }
                    if (lista != null) {
                        break;
                    }
                }
                if (lista != null) {
                    // jbl.setModel(lista);

                } else {
                    return null;
                }

                Object[] val = null;
                for (nodo s : listacol.getHijos()) {
                    switch (s.getNombre()) {
                        case "listaceldas":
                            val = getAtributosTabla(s, ambito);
                            break;

                    }
                    if (val != null) {
                        lista.addRow(val);
                        jbl.setModel(lista);
                        Object[] l = (Object[]) val;
                        for (int j = 0; j < l.length; j++) {
                            jbl.getColumnModel().getColumn(j).setCellRenderer(render);

                        }
                    }
                }
                nuevoObjeto(this.id, raiz.getFila(), raiz.getCol(), jbl, "tabla");
                count = 0;

                return jbl;
            }

        }
        return null;
    }
    int count = 0;

    TableCellRenderer render = (JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) -> {
        JLabel obj = new JLabel(value.toString());
        switch (tipoObjeto(value)) {
            case 8:
                JButton btn = (JButton) value;
                return btn;
            default:
                return obj;

        }
    };

    private Object[] getAtributosTabla(nodo raiz, String ambito) {
        if (raiz != null) {
            int encabezado = 0;
            for (nodo s : raiz.getHijos()) {
                switch (s.getNombre()) {
                    case "celda":
                        encabezado++;
                        break;
                }
            }
            if (encabezado == 0) {
                return null;
            }
            Object[] valores = new Object[encabezado];
            int contador = 0;
            for (nodo s : raiz.getHijos()) {
                switch (s.getNombre()) {
                    case "celda":
                        nodo id = s.getHijos().get(0);
                        switch (id.getNombre()) {
                            case "iden":
                                valores[contador] = id.getHijos().get(0).getNombre().replace("\"", "");
                                count++;
                                contador++;
                                break;
                            case "num":
                                valores[contador] = ejecutarExpresion(id, ambito);
                                count++;
                                contador++;
                                break;
                            case "cadena":
                                valores[contador] = ejecutarExpresion(id, ambito);
                                count++;
                                contador++;
                                break;
                            case "boton":
                                valores[contador] = (JButton) dibujarBoton(id, ambito);
                                count++;
                                contador++;
                                break;
                            case "imagen":
                                valores[contador] = (JButton) dibujarimagen(id, ambito);
                                count++;
                                contador++;
                                break;
                        }
                }
            }
            return valores;
        }
        return null;
    }

    private DefaultTableModel getTitulos(nodo raiz, String ambito) {
        if (raiz != null) {
            int encabezado = 0;
            DefaultTableModel dtm = new DefaultTableModel(0, 0);
            for (nodo s : raiz.getHijos()) {
                switch (s.getNombre()) {
                    case "celdaencabezado":
                        encabezado++;
                        break;
                }
            }
            if (encabezado == 0) {
                return null;
            }
            String[] titulos = new String[encabezado];
            int contador = 0;
            for (nodo s : raiz.getHijos()) {
                switch (s.getNombre()) {
                    case "celdaencabezado":
                        nodo ide = s.getHijos().get(0);
                        switch (ide.getNombre()) {
                            case "iden":
                                titulos[contador] = ide.getHijos().get(0).getNombre().replace("\"", "");
                                contador++;
                                break;
                        }
                        break;
                }
            }
            dtm.setColumnIdentifiers(titulos);
            return dtm;
        }
        return null;
    }

    private Object dibujarSpinner(nodo raiz, String ambito) {
        if (raiz != null) {
            int hijos = raiz.getHijos().size();
            if (hijos == 1) {
                nodo exp = raiz.getHijos().get(0);
                Object val = ejecutarExpresion(exp, ambito);
                if (tipoObjeto(val) == 2 || tipoObjeto(val) == 3) {
                    if (tipoObjeto(val) == 2) {
                        int ini = Integer.parseInt(val.toString());

                        SpinnerModel btn = new SpinnerNumberModel(ini, ini, ini + 10000, 1);
                        JSpinner js = new JSpinner(btn);
                        return js;
                    } else if (tipoObjeto(val) == 3) {
                        float ini = Float.parseFloat(val.toString());
                        SpinnerModel btn = new SpinnerNumberModel(ini, ini, ini + 10000, 1);
                        JSpinner js = new JSpinner(btn);
                        return js;
                    }
                }
            } else if (hijos == 2) {
                JSpinner jbl = new JSpinner();
                nodo propiedades = raiz.getHijos().get(0);
                nodo expr = raiz.getHijos().get(1);
                Object val = ejecutarExpresion(expr, ambito);
                if (tipoObjeto(val) == 2 || tipoObjeto(val) == 3) {
                    if (tipoObjeto(val) == 2) {
                        int ini = Integer.parseInt(val.toString());

                        SpinnerModel btn = new SpinnerNumberModel(ini, ini, ini + 10000, 1);
                        JSpinner js = new JSpinner(btn);
                        return js;
                    } else if (tipoObjeto(val) == 3) {
                        float ini = Float.parseFloat(val.toString());
                        SpinnerModel btn = new SpinnerNumberModel(ini, ini, ini + 10000, 1);
                        JSpinner js = new JSpinner(btn);
                        return js;
                    }
                }
                if (propiedades.getNombre().equals("listapropiedades")) {
                    setPropiedades(propiedades);
                    //PROPIEDADES
                    //TODAS
                    if (!"".equals(align)) {
                        setAlign(jbl);
                    }
                    if (this.tienepropiedades == true) {
                        if (!"".equals(align)) {
                            setAlign(val);
                        }
                        jbl.setOpaque(this.opaque);
                        jbl.setVisible(this.visible);
                    } else {
                        if (!this.id.equals("")) {
                            CSS propID = getID(this.id);
                            if (propID != null) {
                                setPropiedades(propID);
                                if (!"".equals(align)) {
                                    setAlign(val);
                                }
                                jbl.setOpaque(this.opaque);
                                jbl.setVisible(this.visible);
                            }
                        }
                    }
                    if (this.error == false) {
                        nuevoObjeto(this.id, raiz.getFila(), raiz.getCol(), jbl, "spinner");
                        if (this.alto > 0 && this.ancho > 0) {
                            jbl.setPreferredSize(new Dimension(ancho, alto));
                        }
                        if (!"".equals(this.id)) {
                            jbl.setName(this.id);
                        }
                        if (!"".equals(this.ruta)) {
                            Eventos evt = events.get(jbl.getName());
                            if (evt != null) {
                                evt.setRuta(this.ruta);
                            }
                        }
                        if (this.click == true && !"".equals(this.metodo)) {
                            nuevoEventoObjeto(this.id, this.metodo, "c");

                        }
                        jbl.addMouseListener(acciones(this.id, true));
                    }
                    jbl.repaint();
                    return jbl;
                }
            }
        }
        return null;
    }

    private MouseAdapter acciones(String name, boolean aplicaruta) {
        MouseAdapter adapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Eventos evt = events.get(name);
                if (evt != null) {
                    ArrayList<String> clk = evt.getClick();
                    for (int i = 0; i < clk.size(); i++) {
                        String meto = clk.get(i).replace("(", "").replace(")", "").toLowerCase();
                        nodo ins = getInstruccionesMetodo(meto);
                        if (ins != null) {
                            InterpreteCHTML.this.intcsj.setTablaAmbitos(getTablaAmbitos(meto));
                            InterpreteCHTML.this.intcsj.ejecutarMetodo(ins, meto);
                        }
                    }
                    if (!evt.getRuta().equals("") && aplicaruta == true) {
                        System.out.println("Tengo redireccion:" + evt.getRuta());
                        ejecutarenlace(evt.getRuta());
                    }
                }
            }

        };
        return adapter;
    }

     private void ejecutarenlace(String ruta){
        if(!"".equals(ruta)){
            /*Pagina pag = Pagina.getInstancia();
            pag.panelHTML.removeAll();
            pag.setContido(ruta);
            pag.panelHTML.validate();
            this.panel=pag.panelHTML;*/
            compilarCHTML(ruta);
        }
    }

    private Object dibujarAreaTexto(nodo raiz, String ambito) {
        if (raiz != null) {
            int hijos = raiz.getHijos().size();
            if (hijos == 1) {
                nodo exp = raiz.getHijos().get(0);
                Object val = ejecutarExpresion(exp, ambito);
                if (tipoObjeto(val) == 1) {
                    JTextArea btn = new JTextArea(val.toString());
                    btn.setAutoscrolls(false);
                    return btn;
                }
            } else if (hijos == 2) {
                JTextArea jbl = new JTextArea();
                nodo propiedades = raiz.getHijos().get(0);
                nodo expr = raiz.getHijos().get(1);
                Object val = ejecutarExpresion(expr, ambito);
                if (tipoObjeto(val) == 1) {
                    jbl.setText(val.toString());
                }
                if (propiedades.getNombre().equals("listapropiedades")) {
                    setPropiedades(propiedades);
                    //PROPIEDADES
                    //TODAS
                    if (!"".equals(align)) {
                        setAlign(jbl);
                    }
                    if (this.tienepropiedades == true) {
                        if (!"".equals(align)) {
                            setAlign(jbl);
                        }
                        if (!"".equals(this.texto)) {
                            jbl.setText(this.texto);
                        }
                        if (mayus == true) {
                            jbl.setText(jbl.getText().toUpperCase());
                        } else if (minus == true) {
                            jbl.setText(jbl.getText().toLowerCase());
                        } else if (capital == true) {
                            jbl.setText(capitalT(jbl.getText()));
                        }
                        if (!"".equals(this.letra)) {
                            if (this.tamtext != -1) {
                                int tam = (int) this.tamtext;
                                if (this.negrilla == true && this.cursiva == true) {
                                    Font f = new Font(this.letra, Font.BOLD | Font.ITALIC, tam);
                                    jbl.setFont(f);
                                } else if (this.negrilla == true) {
                                    Font f = new Font(this.letra, Font.BOLD, tam);
                                    jbl.setFont(f);
                                } else if (this.cursiva == true) {
                                    Font f = new Font(this.letra, Font.ITALIC, tam);
                                    jbl.setFont(f);
                                }
                            }
                        }
                        if (this.tamborde > 0 && !"".equals(this.color_brde)) {
                            //AbstractBorder brdrLeft = new TextBubbleBorder(Color.BLACK,2,16,16);
                            Color cl = getColor(this.color_brde);
                            LineBorder line = new LineBorder(cl, Integer.parseInt(this.tambordeTEMP.toString()), this.borde);
                            jbl.setBorder(line);
                        }
                        jbl.setOpaque(this.opaque);
                        if (!"".equals(this.fondo_elemento)) {
                            Color cl = getColor(this.fondo_elemento);
                            if (cl != null) {
                                jbl.setBackground(cl);
                            }
                        }
                        jbl.setVisible(this.visible);
                    } else {
                        if (!this.id.equals("")) {
                            CSS propID = getID(this.id);
                            if (propID != null) {
                                setPropiedades(propID);
                                if (!"".equals(align)) {
                                    setAlign(jbl);
                                }
                                if (!"".equals(this.texto)) {
                                    jbl.setText(this.texto);
                                }
                                if (mayus == true) {
                                    jbl.setText(jbl.getText().toUpperCase());
                                } else if (minus == true) {
                                    jbl.setText(jbl.getText().toLowerCase());
                                } else if (capital == true) {
                                    jbl.setText(capitalT(jbl.getText()));
                                }
                                if (!"".equals(this.letra)) {
                                    if (this.tamtext != -1) {
                                        int tam = (int) this.tamtext;
                                        if (this.negrilla == true && this.cursiva == true) {
                                            Font f = new Font(this.letra, Font.BOLD | Font.ITALIC, tam);
                                            jbl.setFont(f);
                                        } else if (this.negrilla == true) {
                                            Font f = new Font(this.letra, Font.BOLD, tam);
                                            jbl.setFont(f);
                                        } else if (this.cursiva == true) {
                                            Font f = new Font(this.letra, Font.ITALIC, tam);
                                            jbl.setFont(f);
                                        }
                                    }
                                }
                                if (this.tamborde > 0 && !"".equals(this.color_brde)) {
                                    //AbstractBorder brdrLeft = new TextBubbleBorder(Color.BLACK,2,16,16);
                                    Color cl = getColor(this.color_brde);
                                    LineBorder line = new LineBorder(cl, Integer.parseInt(this.tambordeTEMP.toString()), this.borde);
                                    jbl.setBorder(line);
                                }
                                jbl.setOpaque(this.opaque);
                                if (!"".equals(this.fondo_elemento)) {
                                    Color cl = getColor(this.fondo_elemento);
                                    if (cl != null) {
                                        jbl.setBackground(cl);
                                    }
                                }
                                if (this.alto > 0 && this.ancho > 0) {
                                    jbl.setPreferredSize(new Dimension(ancho, alto));
                                }
                                if (!"".equals(this.id)) {
                                    jbl.setName(this.id);
                                }
                                jbl.setVisible(this.visible);
                            }
                        }
                    }
                    if (this.error == false) {
                        nuevoObjeto(this.id, raiz.getFila(), raiz.getCol(), jbl, "areatexto");
                        if (this.alto > 0 && this.ancho > 0) {
                            jbl.setPreferredSize(new Dimension(ancho, alto));
                        }
                        if (!"".equals(this.id)) {
                            jbl.setName(this.id);
                        }
                        if (!"".equals(this.ruta)) {
                            Eventos evt = events.get(jbl.getName());
                            if (evt != null) {
                                evt.setRuta(this.ruta);
                            }
                        }
                        if (this.click == true && !"".equals(this.metodo)) {
                            nuevoEventoObjeto(this.id, this.metodo, "c");

                        }
                        jbl.addMouseListener(acciones(this.id, true));
                    }
                    jbl.repaint();
                    return jbl;
                }
            }
        }
        return null;
    }

    private Object dibujarBoton(nodo raiz, String ambito) {
        if (raiz != null) {
            int hijos = raiz.getHijos().size();
            if (hijos == 1) {
                nodo exp = raiz.getHijos().get(0);
                Object val = ejecutarExpresion(exp, ambito);
                if (tipoObjeto(val) == 1) {
                    JButton btn = new JButton(val.toString());
                    nuevoObjeto(this.id, exp.getFila(), exp.getCol(), btn, "boton");
                    return btn;
                }
            } else if (hijos == 2) {
                JButton jbl = new JButton();
                nodo propiedades = raiz.getHijos().get(0);
                nodo expr = raiz.getHijos().get(1);
                Object val = ejecutarExpresion(expr, ambito);
                if (tipoObjeto(val) == 1) {
                    jbl.setText(val.toString());
                }
                if (propiedades.getNombre().equals("listapropiedades")) {
                    setPropiedades(propiedades);
                    //PROPIEDADES
                    //TODAS
                    if (!"".equals(align)) {
                        setAlign(jbl);
                    }
                    if (this.tienepropiedades == true) {
                        if (!"".equals(align)) {
                            setAlign(jbl);
                        }
                        if (!"".equals(this.texto)) {
                            jbl.setText(this.texto);
                        }
                        if (mayus == true) {
                            jbl.setText(jbl.getText().toUpperCase());
                        } else if (minus == true) {
                            jbl.setText(jbl.getText().toLowerCase());
                        } else if (capital == true) {
                            jbl.setText(capitalT(jbl.getText()));
                        }
                        if (!"".equals(this.letra)) {
                            if (this.tamtext != -1) {
                                int tam = (int) this.tamtext;
                                if (this.negrilla == true && this.cursiva == true) {
                                    Font f = new Font(this.letra, Font.BOLD | Font.ITALIC, tam);
                                    jbl.setFont(f);
                                } else if (this.negrilla == true) {
                                    Font f = new Font(this.letra, Font.BOLD, tam);
                                    jbl.setFont(f);
                                } else if (this.cursiva == true) {
                                    Font f = new Font(this.letra, Font.ITALIC, tam);
                                    jbl.setFont(f);
                                }
                            }
                        }
                        if (this.tamborde > 0 && !"".equals(this.color_brde)) {
                            //AbstractBorder brdrLeft = new TextBubbleBorder(Color.BLACK,2,16,16);
                            Color cl = getColor(this.color_brde);
                            LineBorder line = new LineBorder(cl, Integer.parseInt(this.tambordeTEMP.toString()), this.borde);
                            jbl.setBorder(line);
                        }
                        jbl.setOpaque(this.opaque);
                        if (!"".equals(this.fondo_elemento)) {
                            Color cl = getColor(this.fondo_elemento);
                            if (cl != null) {
                                jbl.setBackground(cl);
                            }
                        }
                        jbl.setVisible(this.visible);
                    } else {
                        //VALIDANDO QUE NO HAYA UN ID PARA PINTAR
                        if (!this.id.equals("")) {
                            CSS propID = getID(this.id);
                            if (propID != null) {
                                setPropiedades(propID);
                                if (!"".equals(align)) {
                                    setAlign(jbl);
                                }
                                if (!"".equals(this.texto)) {
                                    jbl.setText(this.texto);
                                }
                                if (mayus == true) {
                                    jbl.setText(jbl.getText().toUpperCase());
                                } else if (minus == true) {
                                    jbl.setText(jbl.getText().toLowerCase());
                                } else if (capital == true) {
                                    jbl.setText(capitalT(jbl.getText()));
                                }
                                if (!"".equals(this.letra)) {
                                    if (this.tamtext != -1) {
                                        int tam = (int) this.tamtext;
                                        if (this.negrilla == true && this.cursiva == true) {
                                            Font f = new Font(this.letra, Font.BOLD | Font.ITALIC, tam);
                                            jbl.setFont(f);
                                        } else if (this.negrilla == true) {
                                            Font f = new Font(this.letra, Font.BOLD, tam);
                                            jbl.setFont(f);
                                        } else if (this.cursiva == true) {
                                            Font f = new Font(this.letra, Font.ITALIC, tam);
                                            jbl.setFont(f);
                                        }
                                    }
                                }
                                if (this.tamborde > 0 && !"".equals(this.color_brde)) {
                                    //AbstractBorder brdrLeft = new TextBubbleBorder(Color.BLACK,2,16,16);
                                    Color cl = getColor(this.color_brde);
                                    LineBorder line = new LineBorder(cl, Integer.parseInt(this.tambordeTEMP.toString()), this.borde);
                                    jbl.setBorder(line);
                                }
                                jbl.setOpaque(this.opaque);
                                if (!"".equals(this.fondo_elemento)) {
                                    Color cl = getColor(this.fondo_elemento);
                                    if (cl != null) {
                                        jbl.setBackground(cl);
                                    }
                                }
                                jbl.setVisible(this.visible);
                            }
                        }
                    }
                    if (this.error == false) {
                        nuevoObjeto(this.id, raiz.getFila(), raiz.getCol(), jbl, "boton");
                        if (this.alto > 0 && this.ancho > 0) {
                            jbl.setPreferredSize(new Dimension(ancho, alto));
                        }
                        if (!"".equals(this.id)) {
                            jbl.setName(this.id);
                        }
                        if (!"".equals(this.ruta)) {
                            Eventos evt = events.get(jbl.getName());
                            if (evt != null) {
                                evt.setRuta(this.ruta);
                            }
                        }
                        if (this.click == true && !"".equals(this.metodo)) {
                            nuevoEventoObjeto(this.id, this.metodo, "c");
                        }
                        jbl.addMouseListener(acciones(this.id, true));
                    }
                    //limpiarVariables();
                    jbl.repaint();
                    return jbl;
                }
            }

        }
        return null;
    }

    private Object dibujarCajaTexto(nodo raiz, String ambito) {
        if (raiz != null) {
            int hijos = raiz.getHijos().size();
            if (hijos == 1) {
                nodo expr = raiz.getHijos().get(0);
                Object val = ejecutarExpresion(expr, ambito);
                JTextField jbl = new JTextField();
                if (tipoObjeto(val) == 1) {
                    jbl.setText(val.toString());
                    return jbl;
                }
            } else if (hijos == 2) {
                //PRIEDADES
                //TODAS
                JTextField jbl = new JTextField();
                nodo propiedades = raiz.getHijos().get(0);
                nodo expr = raiz.getHijos().get(1);
                Object val = ejecutarExpresion(expr, ambito);
                if (tipoObjeto(val) == 1) {
                    jbl.setText(val.toString());
                }
                if (propiedades.getNombre().equals("listapropiedades")) {
                    setPropiedades(propiedades);
                    //PROPIEDADES
                    //TODAS
                    if (!"".equals(align)) {
                        setAlign(jbl);
                    }
                    if (this.tienepropiedades == true) {
                        if (!"".equals(align)) {
                            setAlign(jbl);
                        }
                        if (!"".equals(this.texto)) {
                            jbl.setText(val.toString());
                        }
                        if (mayus == true) {
                            jbl.setText(jbl.getText().toUpperCase());
                        } else if (minus == true) {
                            jbl.setText(jbl.getText().toLowerCase());
                        } else if (capital == true) {
                            jbl.setText(capitalT(jbl.getText()));
                        }
                        if (!"".equals(this.letra)) {
                            if (this.tamtext != -1) {
                                int tam = (int) this.tamtext;
                                if (this.negrilla == true && this.cursiva == true) {
                                    Font f = new Font(this.letra, Font.BOLD | Font.ITALIC, tam);
                                    jbl.setFont(f);
                                } else if (this.negrilla == true) {
                                    Font f = new Font(this.letra, Font.BOLD, tam);
                                    jbl.setFont(f);
                                } else if (this.cursiva == true) {
                                    Font f = new Font(this.letra, Font.ITALIC, tam);
                                    jbl.setFont(f);
                                }
                            }
                        }
                        if (this.tamborde > 0 && !"".equals(this.color_brde)) {
                            //AbstractBorder brdrLeft = new TextBubbleBorder(Color.BLACK,2,16,16);
                            Color cl = getColor(this.color_brde);
                            LineBorder line = new LineBorder(cl, Integer.parseInt(this.tambordeTEMP.toString()), this.borde);
                            jbl.setBorder(line);
                        }
                        jbl.setOpaque(this.opaque);
                        if (!"".equals(this.fondo_elemento)) {
                            Color cl = getColor(this.fondo_elemento);
                            if (cl != null) {
                                jbl.setBackground(cl);
                            }
                        }
                        jbl.setVisible(this.visible);
                    } else {
                        if (!this.id.equals("")) {
                            CSS propID = getID(this.id);
                            if (propID != null) {
                                if (!"".equals(align)) {
                                    setAlign(jbl);
                                }
                                if (!"".equals(this.texto)) {
                                    jbl.setText(val.toString());
                                }
                                if (mayus == true) {
                                    jbl.setText(jbl.getText().toUpperCase());
                                } else if (minus == true) {
                                    jbl.setText(jbl.getText().toLowerCase());
                                } else if (capital == true) {
                                    jbl.setText(capitalT(jbl.getText()));
                                }
                                if (!"".equals(this.letra)) {
                                    if (this.tamtext != -1) {
                                        int tam = (int) this.tamtext;
                                        if (this.negrilla == true && this.cursiva == true) {
                                            Font f = new Font(this.letra, Font.BOLD | Font.ITALIC, tam);
                                            jbl.setFont(f);
                                        } else if (this.negrilla == true) {
                                            Font f = new Font(this.letra, Font.BOLD, tam);
                                            jbl.setFont(f);
                                        } else if (this.cursiva == true) {
                                            Font f = new Font(this.letra, Font.ITALIC, tam);
                                            jbl.setFont(f);
                                        }
                                    }
                                }
                                if (this.tamborde > 0 && !"".equals(this.color_brde)) {
                                    //AbstractBorder brdrLeft = new TextBubbleBorder(Color.BLACK,2,16,16);
                                    Color cl = getColor(this.color_brde);
                                    LineBorder line = new LineBorder(cl, Integer.parseInt(this.tambordeTEMP.toString()), this.borde);
                                    jbl.setBorder(line);
                                }
                                jbl.setOpaque(this.opaque);
                                if (!"".equals(this.fondo_elemento)) {
                                    Color cl = getColor(this.fondo_elemento);
                                    if (cl != null) {
                                        jbl.setBackground(cl);
                                    }
                                }
                                jbl.setVisible(this.visible);
                            }
                        }
                    }
                    if (this.error == false) {
                        nuevoObjeto(this.id, raiz.getFila(), raiz.getCol(), jbl, "cajatexto");
                        if (this.alto > 0 && this.ancho > 0) {
                            jbl.setPreferredSize(new Dimension(ancho, alto));
                        }
                        if (!"".equals(this.id)) {
                            jbl.setName(this.id);
                        }

                        if (!"".equals(this.ruta)) {
                            Eventos evt = events.get(jbl.getName());
                            if (evt != null) {
                                evt.setRuta(this.ruta);
                            }
                        }
                        if (this.click == true && !"".equals(this.metodo)) {
                            nuevoEventoObjeto(this.id, this.metodo, "c");

                        }
                        jbl.addMouseListener(acciones(this.id, true));
                    }
                    //limpiarVariables();
                    return jbl;
                }
            }
        }
        return null;
    }

    private Object dibujarTexto(nodo raiz, String ambito) {
        if (raiz != null) {
            int hijos = raiz.getHijos().size();
            if (hijos == 1) {
                nodo expr = raiz.getHijos().get(0);
                Object val = ejecutarExpresion(expr, ambito);
                JLabel jbl = new JLabel();
                if (tipoObjeto(val) == 1) {
                    jbl.setText(val.toString());
                }
                return jbl;
            } else if (hijos == 2) {
                JLabel jbl = new JLabel();
                nodo propiedades = raiz.getHijos().get(0);
                nodo expr = raiz.getHijos().get(1);
                Object val = ejecutarExpresion(expr, ambito);
                if (tipoObjeto(val) == 1) {
                    jbl.setText(val.toString());
                }
                if (propiedades.getNombre().equals("listapropiedades")) {
                    setPropiedades(propiedades);
                    //PROPIEDADES
                    //TODAS
                    if (!"".equals(align)) {
                        setAlign(jbl);
                    }
                    if (this.tienepropiedades == true) {
                        if (!"".equals(align)) {
                            setAlign(jbl);
                        }
                        if (!"".equals(this.texto)) {
                            jbl.setText(this.texto);
                        }
                        if (mayus == true) {
                            jbl.setText(jbl.getText().toUpperCase());
                        } else if (minus == true) {
                            jbl.setText(jbl.getText().toLowerCase());
                        } else if (capital == true) {
                            jbl.setText(capitalT(jbl.getText()));
                        }
                        if (!"".equals(this.letra)) {
                            if (this.tamtext != -1) {
                                int tam = (int) this.tamtext;
                                if (this.negrilla == true && this.cursiva == true) {
                                    Font f = new Font(this.letra, Font.BOLD | Font.ITALIC, tam);
                                    jbl.setFont(f);
                                } else if (this.negrilla == true) {
                                    Font f = new Font(this.letra, Font.BOLD, tam);
                                    jbl.setFont(f);
                                } else if (this.cursiva == true) {
                                    Font f = new Font(this.letra, Font.ITALIC, tam);
                                    jbl.setFont(f);
                                }
                            }
                        }
                        if (this.tamborde > 0 && !"".equals(this.color_brde)) {
                            //AbstractBorder brdrLeft = new TextBubbleBorder(Color.BLACK,2,16,16);
                            Color cl = getColor(this.color_brde);
                            LineBorder line = new LineBorder(cl, Integer.parseInt(this.tambordeTEMP.toString()), this.borde);
                            jbl.setBorder(line);
                        }
                        jbl.setOpaque(this.opaque);
                        if (!"".equals(this.fondo_elemento)) {
                            Color cl = getColor(this.fondo_elemento);
                            if (cl != null) {
                                jbl.setBackground(cl);
                            }
                        }
                        jbl.setVisible(this.visible);

                    } else {
                        if (!this.id.equals("")) {
                            CSS propID = getID(this.id);
                            if (propID != null) {
                                setPropiedades(propID);
                                if (!"".equals(align)) {
                                    setAlign(jbl);
                                }
                                if (!"".equals(this.texto)) {
                                    jbl.setText(this.texto);
                                }
                                if (mayus == true) {
                                    jbl.setText(jbl.getText().toUpperCase());
                                } else if (minus == true) {
                                    jbl.setText(jbl.getText().toLowerCase());
                                } else if (capital == true) {
                                    jbl.setText(capitalT(jbl.getText()));
                                }
                                if (!"".equals(this.letra)) {
                                    if (this.tamtext != -1) {
                                        int tam = (int) this.tamtext;
                                        if (this.negrilla == true && this.cursiva == true) {
                                            Font f = new Font(this.letra, Font.BOLD | Font.ITALIC, tam);
                                            jbl.setFont(f);
                                        } else if (this.negrilla == true) {
                                            Font f = new Font(this.letra, Font.BOLD, tam);
                                            jbl.setFont(f);
                                        } else if (this.cursiva == true) {
                                            Font f = new Font(this.letra, Font.ITALIC, tam);
                                            jbl.setFont(f);
                                        }
                                    }
                                }
                                if (this.tamborde > 0 && !"".equals(this.color_brde)) {
                                    //AbstractBorder brdrLeft = new TextBubbleBorder(Color.BLACK,2,16,16);
                                    Color cl = getColor(this.color_brde);
                                    LineBorder line = new LineBorder(cl, Integer.parseInt(this.tambordeTEMP.toString()), this.borde);
                                    jbl.setBorder(line);
                                }
                                jbl.setOpaque(this.opaque);
                                if (!"".equals(this.fondo_elemento)) {
                                    Color cl = getColor(this.fondo_elemento);
                                    if (cl != null) {
                                        jbl.setBackground(cl);
                                    }
                                }

                                jbl.setVisible(this.visible);
                            }
                        }
                    }
                    if (this.error == false) {
                        nuevoObjeto(this.id, raiz.getFila(), raiz.getCol(), jbl, "texto");
                        if (this.alto > 0 && this.ancho > 0) {
                            jbl.setPreferredSize(new Dimension(ancho, alto));
                        }
                        if (!"".equals(this.id)) {
                            jbl.setName(this.id);
                        }
                        if (!"".equals(this.ruta)) {
                            Eventos evt = events.get(jbl.getName());
                            if (evt != null) {
                                evt.setRuta(this.ruta);
                            }
                        }
                        if (this.click == true && !"".equals(this.metodo)) {
                            nuevoEventoObjeto(this.id, this.metodo, "c");

                        }
                        jbl.addMouseListener(acciones(this.id, true));

                    }
                    //limpiarVariables();
                    jbl.repaint();
                    return jbl;
                }
            }
        }
        return null;
    }

    private Object dibujarimagen(nodo raiz, String ambito) {
        if (raiz != null) {
            int hijos = raiz.getHijos().size();
            if (hijos == 1) {
                nodo propiedades = raiz.getHijos().get(0);
                if (propiedades.getNombre().equals("cadena")) {
                    this.ruta = propiedades.getHijos().get(0).getNombre().replace("\"", "");
                    JButton button = new JButton();
                    try {
                        Image img = ImageIO.read(new File(ruta));
                        button.setIcon(new ImageIcon(img.getScaledInstance(this.ancho - 1, this.alto - 1, Image.SCALE_DEFAULT)));
                    } catch (IOException ex) {
                        System.out.println(ex);
                    }
                    //limpiarVariables();
                    return button;
                }else{
                    setPropiedades(propiedades);
                    JButton jbl = new JButton();
                    try {
                        if(this.ruta!=""){
                        Image img = ImageIO.read(new File(this.ruta));
                        jbl.setIcon(new ImageIcon(img.getScaledInstance(this.ancho - 1, this.alto - 1, Image.SCALE_DEFAULT)));
                        }
                    } catch (IOException ex) {
                        System.out.println(ex);
                    }
                    if (propiedades.getNombre().equals("listapropiedades")) {
                    setPropiedades(propiedades);
                    //PROPIEDADES
                    //TODAS
                    if (!"".equals(align)) {
                        setAlign(jbl);
                    }
                    if (this.tienepropiedades == true) {
                        if (!"".equals(align)) {
                            setAlign(jbl);
                        }

                        jbl.setOpaque(this.opaque);

                        jbl.setVisible(this.visible);

                    } else {
                        if (!this.id.equals("")) {
                            CSS propID = getID(this.id);
                            if (propID != null) {
                                setPropiedades(propID);
                                if (!"".equals(align)) {
                                    setAlign(jbl);
                                }

                                jbl.setOpaque(this.opaque);

                                jbl.setVisible(this.visible);
                            }
                        }
                    }
                    if (this.error == false) {
                        nuevoObjeto(this.id, raiz.getFila(), raiz.getCol(), jbl, "imagen");
                        if (this.alto > 0 && this.ancho > 0) {
                            jbl.setPreferredSize(new Dimension(ancho, alto));
                        }
                        if (!"".equals(this.id)) {
                            jbl.setName(this.id);
                        }
                        if (!"".equals(this.ruta)) {
                            Eventos evt = events.get(jbl.getName());
                            if (evt != null) {
                                evt.setRuta(this.ruta);
                            }
                        }
                        if (this.click == true && !"".equals(this.metodo)) {
                            nuevoEventoObjeto(this.id, this.metodo, "c");

                        }

                        jbl.addMouseListener(acciones(this.id, false));
                    }
                    }
                    
                    jbl.repaint();
                    return jbl;
                    }
                    //limpiarVariables();
            } else if (hijos == 2) {
                nodo propiedades = raiz.getHijos().get(0);
                nodo valruta = raiz.getHijos().get(1);
                setPropiedades(propiedades);
                JButton jbl = new JButton();
                try {
                    Image img = ImageIO.read(new File(this.ruta));
                    jbl.setIcon(new ImageIcon(img.getScaledInstance(this.ancho - 1, this.alto - 1, Image.SCALE_DEFAULT)));
                } catch (IOException ex) {
                    this.ruta = valruta.getHijos().get(0).getNombre().replace("\"", "");
                    try {
                        Image img = ImageIO.read(new File(this.ruta));
                        jbl.setIcon(new ImageIcon(img.getScaledInstance(this.ancho - 1, this.alto - 1, Image.SCALE_DEFAULT)));
                    } catch (IOException ex2) {
                        this.error = true;
                        this.errores.agregarerror(new ER(raiz.getFila(), raiz.getCol(), this.ruta, 3, ""
                                + "No existe la imagen en la ruta especificada "));
                        System.out.println(ex2);
                    }
                    System.out.println(ex);
                }
                if (propiedades.getNombre().equals("listapropiedades")) {
                    setPropiedades(propiedades);
                    //PROPIEDADES
                    //TODAS
                    if (!"".equals(align)) {
                        setAlign(jbl);
                    }
                    if (this.tienepropiedades == true) {
                        if (!"".equals(align)) {
                            setAlign(jbl);
                        }

                        jbl.setOpaque(this.opaque);

                        jbl.setVisible(this.visible);

                    } else {
                        if (!this.id.equals("")) {
                            CSS propID = getID(this.id);
                            if (propID != null) {
                                setPropiedades(propID);
                                if (!"".equals(align)) {
                                    setAlign(jbl);
                                }

                                jbl.setOpaque(this.opaque);

                                jbl.setVisible(this.visible);
                            }
                        }
                    }
                    if (this.error == false) {
                        nuevoObjeto(this.id, raiz.getFila(), raiz.getCol(), jbl, "imagen");
                        if (this.alto > 0 && this.ancho > 0) {
                            jbl.setPreferredSize(new Dimension(ancho, alto));
                        }
                        if (!"".equals(this.id)) {
                            jbl.setName(this.id);
                        }
                        if (!"".equals(this.ruta)) {
                            Eventos evt = events.get(jbl.getName());
                            if (evt != null) {
                                evt.setRuta(this.ruta);
                            }
                        }
                        if (this.click == true && !"".equals(this.metodo)) {
                            nuevoEventoObjeto(this.id, this.metodo, "c");

                        }

                        jbl.addMouseListener(acciones(this.id, false));
                    }
                    //limpiarVariables();
                    jbl.repaint();
                    return jbl;
                }
                return jbl;
            }

        }

        return null;
    }

    private Object dibujarpanel(nodo raiz, String ambito) {
        if (raiz != null) {
            int hijos = raiz.getHijos().size();
            if (hijos == 2) {
                JPanel pan = new JPanel();
                nodo propiedades = raiz.getHijos().get(0);
                nodo objetos = raiz.getHijos().get(1);
                setPropiedades(propiedades);
                pan.setLayout(new BoxLayout(pan, BoxLayout.Y_AXIS));
                if (propiedades.getNombre().equals("listapropiedades")) {

                    //APLICANDO ESTILO
                    //PANEL: ALINEADO, FONDOELEMENTO, BORDE, OPACO, ID,GRUPO, 
                    //AUTOREDIMENSIONADO
                    //TIPO = 6
                    if (!"".equals(this.align)) {
                        //setAlign(pan);
                        this.auxalineado=this.align;
                    }
                    if (this.tienepropiedades == true) {
                        if (!"".equals(this.align)) {
                            setAlign(pan);
                        }
                        if (this.tamborde > 0 && !"".equals(this.color_brde)) {
                            //AbstractBorder brdrLeft = new TextBubbleBorder(Color.BLACK,2,16,16);
                            Color cl = getColor(this.color_brde);
                            LineBorder line = new LineBorder(cl, Integer.parseInt(this.tambordeTEMP.toString()), this.borde);
                            pan.setBorder(line);
                        }
                        pan.setOpaque(this.opaque);
                        if (!"".equals(this.fondo_elemento)) {
                            Color cl = getColor(this.fondo_elemento);
                            if (cl != null) {
                                pan.setBackground(cl);
                            }
                        }
                        pan.setVisible(this.visible);

                    } else {
                        if (!this.id.equals("")) {
                            CSS propID = getID(this.id);
                            if (propID != null) {
                                if (!"".equals(this.align)) {
                                    setAlign(pan);
                                }
                                if (this.tamborde > 0 && !"".equals(this.color_brde)) {
                                    //AbstractBorder brdrLeft = new TextBubbleBorder(Color.BLACK,2,16,16);
                                    Color cl = getColor(this.color_brde);
                                    LineBorder line = new LineBorder(cl, Integer.parseInt(this.tambordeTEMP.toString()), this.borde);
                                    pan.setBorder(line);
                                }
                                pan.setOpaque(this.opaque);
                                if (!"".equals(this.fondo_elemento)) {
                                    Color cl = getColor(this.fondo_elemento);
                                    if (cl != null) {
                                        pan.setBackground(cl);
                                    }
                                }
                                pan.setVisible(this.visible);
                            }
                        }
                    }
                    if (this.error == false) {
                        nuevoObjeto(this.id, raiz.getFila(), raiz.getCol(), pan, "panel");
                        if (this.alto > 0 && this.ancho > 0) {
                            pan.setPreferredSize(new Dimension(ancho, alto));
                        }

                        if (!"".equals(this.id)) {
                            pan.setName(this.id);
                            pan.validate();
                        }

                        if (!"".equals(this.ruta)) {
                            Eventos evt = events.get(pan.getName());
                            if (evt != null) {
                                evt.setRuta(this.ruta);
                            }
                        }

                        if (this.click == true && !"".equals(this.metodo)) {
                            nuevoEventoObjeto(this.id, this.metodo, "c");

                        }
                        pan.addMouseListener(acciones(this.id, true));
                        //auxalineado=this.align;
                        limpiarVariables();

                        obtenerComponentes(pan, objetos, ambito);
                        auxalineado="";
                    }
                    //limpiarVariables();
                    pan.validate();
                    return pan;
                }
            }
            //697ffh
        }
        return null;
    }

    String auxalineado = "";
    private Object obtenerComponentes(Object contenedor, nodo s, String ambito) {
        JPanel pan = new JPanel();
        Object val = null;
        if (s != null) {
            if (tipoObjeto(contenedor) == 6) {
                pan = (JPanel) contenedor;
            }            
            JPanel panelsito = new JPanel();
            if (!"".equals(this.auxalineado)) {
                        setAlign(panelsito);
            }else{
               panelsito.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
            }
            //panelsito.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
            for (nodo n : s.getHijos()) {
                switch (n.getNombre().toLowerCase()) {
                    case "panel":
                        JPanel pane = (JPanel) dibujarpanel(n, ambito);
                        if (this.error == false) {
                            panelsito.add(pane);
                            pane.validate();
                        }
                        break;
                    case "texto":
                        JLabel lbl = (JLabel) dibujarTexto(n, "");
                        if (this.error == false) {
                            panelsito.add(lbl);
                        }
                        limpiarVariables();
                        break;
                    case "cajatexto":
                        JTextField fl = (JTextField) dibujarCajaTexto(n, "");
                        if (this.error == false) {
                            panelsito.add(fl);
                        }
                        limpiarVariables();
                        break;
                    case "boton":
                        JButton jb = (JButton) dibujarBoton(n, "");
                        if (this.error == false) {
                            panelsito.add(jb);
                        }
                        limpiarVariables();
                        break;
                    case "imagen":
                        JButton bt = (JButton) dibujarimagen(n, "");
                        if (this.error == false) {
                            panelsito.add(bt);
                        }
                        limpiarVariables();
                        break;
                    case "tabla":
                        val = dibujartabla(n, ambito);
                        if (tipoObjeto(val) == 11) {
                            JTable tb = (JTable) val;
                            JScrollPane scrollPane = new JScrollPane(tb, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                            if (this.error == false) {
                                panelsito.add(scrollPane);
                            }
                        }
                        limpiarVariables();
                        break;
                    case "enlace":
                        val = dibujarEnlace(n, ambito);
                        if (tipoObjeto(val) == 5) {
                            JLabel jbl = (JLabel) val;
                            if (this.error == false) {
                                panelsito.add(jbl);
                            }
                        }
                        limpiarVariables();
                        break;
                    case "cajaopcion":
                        val = dibujarCajaOpcion(n, ambito);
                        if (tipoObjeto(val) == 12) {
                            JComboBox jbl = (JComboBox) val;
                            if (this.error == false) {
                                panelsito.add(jbl);
                            }
                        }
                        limpiarVariables();
                        break;
                    case "salto":
                        val = dibujarSalto(s, ambito);
                        if (tipoObjeto(val) == 5) {
                            /* JLabel jbl = (JLabel) val;
                            this.panel.add(jbl);*/
                            pan.add(panelsito);
                             
                            panelsito = new JPanel();
                            if (!"".equals(this.auxalineado)) {
                                setAlign(pan);
                            } else {
                                panelsito.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
                            }
                            Dimension d = pan.getPreferredSize();
                            JLabel lbl1 = new JLabel();
                            lbl1.setPreferredSize(new Dimension(d.height,5));
                            panelsito.add(lbl1);
                            pan.add(panelsito);
                            panelsito = new JPanel();
                            if (!"".equals(this.auxalineado)) {
                                setAlign(pan);
                            } else {
                                panelsito.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
                            }
                        }
                        break;
                    case "spinner":
                        val = dibujarSpinner(n, ambito);
                        if (tipoObjeto(val) == 10) {
                            JSpinner jl = (JSpinner) val;
                            if (this.error == false) {
                                panelsito.add(jl);
                            }
                        }
                        limpiarVariables();
                        break;
                    case "areatexto":
                        val = dibujarAreaTexto(n, ambito);
                        if (tipoObjeto(val) == 9) {
                            JTextArea lbl1 = (JTextArea) val;
                            JPanel pane1 = new JPanel();
                            JScrollPane js = new JScrollPane();
                            js.setViewportView(lbl1);
                            pane1.add(js);
                            if (this.error == false) {
                                panelsito.add(pane1);
                            }
                        }
                        limpiarVariables();
                        break;

                }
                this.error = false;
            }
            pan.add(panelsito);
        }
        return val;
    }

    private void setAlign(Object objeto) {
        int tipo = tipoObjeto(objeto);
        //if (tipo == 6) {
        //JPanel pan = 
        switch (this.align.toLowerCase()) {
            case "izquierda":
                switch (tipo) {
                    case 6:
                        JPanel pan = (JPanel) objeto;
                        pan.setLayout(new FlowLayout(FlowLayout.LEFT,10,10));
                        break;
                    case 5:
                        JLabel jbl = (JLabel) objeto;
                        //jbl.setLayout(new FlowLayout(FlowLayout.LEFT));
                        jbl.setText(getAlineado(jbl.getText(), 2));
                        break;
                    case 7:
                        JTextField txt = (JTextField) objeto;
                        // txt.setLayout(new FlowLayout(FlowLayout.LEFT));
                        txt.setText(getAlineado(txt.getText(), 2));
                        break;
                    case 8:
                        JButton btn = (JButton) objeto;
                        //btn.setLayout(new FlowLayout(FlowLayout.LEFT));
                        btn.setText(getAlineado(btn.getText(), 2));
                        break;
                    case 9:
                        JTextArea jtxt = (JTextArea) objeto;
                        //jtxt.setLayout(new FlowLayout(FlowLayout.LEFT));
                        jtxt.setText(getAlineado(jtxt.getText(), 2));
                        break;
                    case 10:
                        JSpinner spi = (JSpinner) objeto;
                        spi.setLayout(new FlowLayout(FlowLayout.LEFT));
                        break;
                    case 11:
                        JTable tab = (JTable) objeto;
                        tab.setLayout(new FlowLayout(FlowLayout.LEFT));
                        break;
                    case 12:
                        JComboBox box = (JComboBox) objeto;
                        box.setLayout(new FlowLayout(FlowLayout.LEFT));
                        break;
                    default:
                        getAlineado(objeto.toString(), 2);
                        break;
                }
                break;
            case "derecha":
                switch (tipo) {
                    case 6:
                        JPanel pan = (JPanel) objeto;
                        pan.setLayout(new FlowLayout(FlowLayout.RIGHT));
                        break;
                    case 5:
                        JLabel jbl = (JLabel) objeto;
                        //jbl.setLayout(new FlowLayout(FlowLayout.RIGHT));
                        jbl.setText(getAlineado(jbl.getText(), 1));
                        break;
                    case 7:
                        JTextField txt = (JTextField) objeto;
                        //txt.setLayout(new FlowLayout(FlowLayout.RIGHT));
                        txt.setText(getAlineado(txt.getText(), 1));
                        break;
                    case 8:
                        JButton btn = (JButton) objeto;
                        //btn.setLayout(new FlowLayout(FlowLayout.RIGHT));
                        btn.setText(getAlineado(btn.getText(), 1));
                        break;
                    case 9:
                        JTextArea jtxt = (JTextArea) objeto;
                        jtxt.setLayout(new FlowLayout(FlowLayout.RIGHT));
                        break;
                    case 10:
                        JSpinner spi = (JSpinner) objeto;
                        spi.setLayout(new FlowLayout(FlowLayout.RIGHT));
                        break;
                    case 11:
                        JTable tab = (JTable) objeto;
                        tab.setLayout(new FlowLayout(FlowLayout.RIGHT));
                        break;
                    case 12:
                        JComboBox box = (JComboBox) objeto;
                        box.setLayout(new FlowLayout(FlowLayout.RIGHT));
                        break;
                    default:
                        getAlineado(objeto.toString(), 1);
                        break;
                }
                break;
            case "centrado":
                switch (tipo) {
                    case 6:
                        JPanel pan = (JPanel) objeto;
                        pan.setLayout(new FlowLayout(FlowLayout.CENTER));
                        break;
                    case 5:
                        JLabel jbl = (JLabel) objeto;
                        //jbl.setLayout(new FlowLayout(FlowLayout.CENTER));
                        jbl.setText(getAlineado(jbl.getText(), 3));
                        break;
                    case 7:
                        JTextField txt = (JTextField) objeto;
                        //txt.setLayout(new FlowLayout(FlowLayout.CENTER));
                        txt.setText(getAlineado(txt.getText(), 3));
                        break;
                    case 8:
                        JButton btn = (JButton) objeto;
                        //btn.setLayout(new FlowLayout(FlowLayout.CENTER));
                        btn.setText(getAlineado(btn.getText(), 3));
                        break;
                    case 9:
                        JTextArea jtxt = (JTextArea) objeto;
                        //jtxt.setLayout(new FlowLayout(FlowLayout.CENTER));
                        jtxt.setText(getAlineado(jtxt.getText(), 3));
                        break;
                    case 10:
                        JSpinner spi = (JSpinner) objeto;
                        spi.setLayout(new FlowLayout(FlowLayout.CENTER));
                        break;
                    case 11:
                        JTable tab = (JTable) objeto;
                        tab.setLayout(new FlowLayout(FlowLayout.CENTER));
                        break;
                    case 12:
                        JComboBox box = (JComboBox) objeto;
                        box.setLayout(new FlowLayout(FlowLayout.CENTER));
                        break;
                    default:
                        getAlineado(objeto.toString(), 3);
                        break;
                }
                break;
            case "justificado":
                switch (tipo) {
                    case 6:
                        JPanel pan = (JPanel) objeto;
                        pan.setLayout(new FlowLayout(FlowLayout.LEADING));
                        break;
                    case 5:
                        JLabel jbl = (JLabel) objeto;
                        //jbl.setLayout(new FlowLayout(FlowLayout.LEADING));
                        jbl.setText(getAlineado(jbl.getText(), 4));
                        break;
                    case 7:
                        JTextField txt = (JTextField) objeto;
                        //txt.setLayout(new FlowLayout(FlowLayout.LEADING));
                        txt.setText(getAlineado(txt.getText(), 4));
                        break;
                    case 8:
                        JButton btn = (JButton) objeto;
                        //btn.setLayout(new FlowLayout(FlowLayout.LEADING));
                        btn.setText(getAlineado(btn.getText(), 4));
                        break;
                    case 9:
                        JTextArea jtxt = (JTextArea) objeto;
                        //  jtxt.setLayout(new FlowLayout(FlowLayout.LEADING));
                        jtxt.setText(getAlineado(jtxt.getText(), 4));
                        break;
                    case 10:
                        JSpinner spi = (JSpinner) objeto;
                        spi.setLayout(new FlowLayout(FlowLayout.LEADING));
                        break;
                    case 11:
                        JTable tab = (JTable) objeto;
                        tab.setLayout(new FlowLayout(FlowLayout.LEADING));
                        break;
                    case 12:
                        JComboBox box = (JComboBox) objeto;
                        box.setLayout(new FlowLayout(FlowLayout.LEADING));
                        break;
                    default:
                        getAlineado(objeto.toString(), 4);
                        break;
                }
                break;
        }
    }

    private Color getColor(String nombre) {
        Color cl = null;
        switch (nombre.toLowerCase()) {
            case "red":
                return Color.RED;
            case "black":
                return Color.BLACK;
            case "blue":
                return Color.BLUE;
            case "cyan":
                return Color.CYAN;
            case "gray":
                return Color.GRAY;
            case "green":
                return Color.GREEN;
            case "magenta":
                return Color.MAGENTA;
            case "orange":
                return Color.ORANGE;
            case "pink":
                return Color.PINK;
            case "white":
                return Color.WHITE;
            case "yellow":
                return Color.YELLOW;
            default:
                //cl = new Color(getR(nombre), getG(nombre), getB(nombre));
                //cl  = new Color(getR(nombre), getG(nombre), getB(nombre));
                int r = getR(nombre);
                int g = getG(nombre);
                int b = getB(nombre);
                int h = r * 65536 + g * 256 + b;
                cl = new Color(h);
                return cl;
        }
    }

    private int getR(String codigo) {
        String cadaux = codigo.replace("#", "");
        String cade = cadaux.substring(0, 2);
        Integer r = Integer.parseInt(cade, 16);
        return r;
    }

    private int getG(String codigo) {
        String cadaux = codigo.replace("#", "");
        String cade = cadaux.substring(2, 4);
        Integer r = Integer.parseInt(cade, 16);
        return r;
    }

    private int getB(String codigo) {
        String cadaux = codigo.replace("#", "");
        String cade = cadaux.substring(4, 6);
        Integer r = Integer.parseInt(cade, 16);
        return r;
    }

    private void setPropiedades(nodo s) {
        if (s != null) {
            Object val = null;
            int tipo;
            for (nodo n : s.getHijos()) {
                switch (n.getNombre()) {
                    case "alto":
                        val = ejecutarExpresion(n.getHijos().get(0), "");
                        tipo = tipoObjeto(val);
                        if (tipo == 2) {
                            this.alto = Integer.parseInt(val.toString());
                        }
                        break;
                    case "ancho":
                        val = ejecutarExpresion(n.getHijos().get(0), "");
                        tipo = tipoObjeto(val);
                        if (tipo == 2) {
                            this.ancho = Integer.parseInt(val.toString());
                        }
                        break;
                    case "grupo":
                        String na = n.getHijos().get(0).getNombre().replace("\"", "");
                        CSS estilo = getGrupo(na);
                        if (estilo != null) {
                            setPropiedades(estilo);
                            this.tienepropiedades = true;
                        }
                        break;
                    case "id":
                        this.id = n.getHijos().get(0).getNombre().replace("\"", "");
                        break;
                    case "ruta":
                        this.ruta = n.getHijos().get(0).getNombre().replace("\"", "");
                        break;
                    case "click":
                        this.click = true;
                        this.metodo = n.getHijos().get(0).getNombre().replace("\"", "");
                        break;
                    case "alineado":
                        this.align = n.getHijos().get(0).getNombre().replace("\"", "");
                        break;
                }
            }
        }
    }

    private void setPropiedades(CSS propiedades) {
        for (Map.Entry e : propiedades.getTabla().entrySet()) {
            Propiedades d = (Propiedades) e.getValue();
            String key = e.getKey().toString();
            switch (key) {
                case "formato":
                    setFormato(d.getPropiedades());
                    break;
                case "letra":
                    this.letra = getTexto(d.getPropiedades());
                    break;
                case "tamtex":
                    this.tamtemp = getNumero(d.getPropiedades());
                    if (tipoObjeto(this.tamtemp) == 2 || tipoObjeto(this.tamtemp) == 3) {
                        this.tamtext = Float.parseFloat(tamtemp.toString().replace("\"", ""));
                    }
                    break;
                case "colortext":
                    this.color_text = getTexto(d.getPropiedades());
                    break;
                case "visible":
                    this.visible = getBooleano(d.getPropiedades());
                    break;
                case "autoredimension":
                    this.autored = getTexto(d.getPropiedades());
                    this.autoredi = getBooleano(d.getPropiedades());
                    break;
                case "alineado":
                    this.align = getTexto(d.getPropiedades());
                    break;
                case "opaco":
                    this.opaque = getBooleano(d.getPropiedades());
                    break;
                case "fondoelemento":
                    this.fondo_elemento = getTexto(d.getPropiedades());
                    break;
                case "borde":
                    this.color_brde = getTexto(d.getPropiedades());
                    this.borde = getBooleano(d.getPropiedades());
                    this.tambordeTEMP = getNumero(d.getPropiedades());
                    if (tipoObjeto(this.tambordeTEMP) == 2 || tipoObjeto(this.tambordeTEMP) == 3) {
                        this.tamborde = Float.parseFloat(tambordeTEMP.toString().replace("\"", ""));
                    }
                    break;
                case "texto":
                    this.texto = getTexto(d.getPropiedades());
                    break;
            }
        }
    }

    private boolean getBooleano(ArrayList<Object> lista) {
        boolean valo = false;
        for (Object obj : lista) {
            int tipo = tipoObjeto(obj);
            if (tipo == 4) {
                valo = Boolean.parseBoolean(obj.toString());
            }
        }
        return valo;
    }

    private Object getNumero(ArrayList<Object> lista) {
        Object val = null;
        for (Object obj : lista) {
            if (tipoObjeto(obj) == 2 || tipoObjeto(obj) == 3) {
                val = obj;
                break;
            }
        }
        return val;
    }

    private String getTexto(ArrayList<Object> lista) {
        String cadena = "";
        for (Object obj : lista) {
            if (tipoObjeto(obj) == 1) {
                cadena = obj.toString();
            }
        }
        return cadena;
    }

    private void setFormato(ArrayList<Object> lista) {

        for (Object obj : lista) {
            String tipoformato = obj.toString();
            switch (tipoformato) {
                case "negrilla":
                    negrilla = true;
                    break;
                case "cursiva":
                    cursiva = true;
                    break;
                case "mayuscula":
                    mayus = true;
                    minus = false;
                    capital = false;
                    break;
                case "minuscula":
                    mayus = false;
                    minus = true;
                    capital = false;
                    break;
                case "capital":
                    mayus = false;
                    minus = false;
                    capital = true;
                    break;
            }
        }
    }

    private String capitalT(String cadena) {
        char[] caracteres = cadena.toCharArray();
        caracteres[0] = Character.toUpperCase(caracteres[0]);
        for (int i = 0; i < cadena.length() - 2; i++) {
            // Es 'palabra'
            if (caracteres[i] == ' ' || caracteres[i] == '.' || caracteres[i] == ',') // Reemplazamos
            {
                caracteres[i + 1] = Character.toUpperCase(caracteres[i + 1]);
            }
        }
        return new String(caracteres);
    }

    private void ejecutarencabezado(nodo raiz, String ambito, boolean band) {
        if (raiz != null) {
            
            for (nodo s : raiz.getHijos() ) {
                switch (s.getNombre()) {
                    case "csj":
                        if(band==true){
                        llenarRutaCJS(s, ambito);
                        }
                        break;
                    case "css":
                        if(band==true){
                        llenarRutaCSS(s, ambito);
                        }
                        break;
                }
            }
            boolean entroCJS = false;
            for (nodo s : raiz.getHijos()) {
                switch (s.getNombre()) {
                    case "csj":
                        if (entroCJS == false) {
                            ejecutarCJS(s, ambito, band);
                            entroCJS = true;
                        }
                        break;
                    case "css":
                        if (band == true) {
                            ejecutarCCSS(s, ambito);
                        }
                        break;
                    case "titulo":
                        if (band == true) {
                            ejecutarTitulo(s, ambito);
                        }
                        break;

                }
            }
        }
    }

   private void ejecutarTitulo(nodo raiz, String ambito){
       if(raiz!=null){
           Object val = ejecutarExpresion(raiz.getHijos().get(0), ambito);
           this.titulo=val.toString();
       }
   }
    private void ejecutarCCSS(nodo raiz, String ambito) {
        if (raiz != null) {
            nodo id = raiz.getHijos().get(0);
            String nombre = id.getNombre().replace("\"", "");
            File archivo = null;
            FileReader fr = null;
            BufferedReader br = null;

            try {
                String contenido = "";

                for (int i = 0; i < this.rutasCSS.size(); i++) {
                    String linea;
                    archivo = new File(nombre);
                    fr = new FileReader(archivo);
                    br = new BufferedReader(fr);
                    while ((linea = br.readLine()) != null) {
                        //System.out.println(linea);
                        contenido = contenido + linea + "\n";
                    }
                }

                compilarcss(contenido, nombre);
            } catch (IOException e) {
            } finally {
                try {
                    if (null != fr) {
                        fr.close();
                    }
                } catch (IOException e2) {
                }
            }
        }
    }

    private void compilarcss(String contenido, String nombre) {
        AnalisisLex_CCSS lexico = new AnalisisLex_CCSS(new BufferedReader(new StringReader(contenido)));
        //SE INICIALIZA EL ANALISIS SINTACTICO TOMA COMO PARAMETRO EL LEXICO

        SintacticoCCSS sintactico = new SintacticoCCSS(lexico);
        try {

            //SE INICIA LA COMPILACION LEXICO Y SINTACTICO
            sintactico.parse();
            ArbolCSJ raiz = sintactico.arbol;
            if (raiz != null) {
                //  System.out.println(raiz.getRaiz().hashCode());
                GraficarArbol grafo = new GraficarArbol();
                grafo.recorrrerarbol(raiz.getRaiz(), 0);
                grafo.contador = 0;
                grafo.enlazararbol(raiz.getRaiz(), 0);
                grafo.graficar(grafo.auxdot, "css.png");
                InterpreteCSS css = new InterpreteCSS();
                css.ejecutarCSS(raiz.getRaiz());
                guardarNuevoCSS(nombre, css);
                this.contenidoCCS = contenido;

            }
        } catch (Exception ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void llenarRutaCJS(nodo raiz, String ambito) {
        if (raiz != null) {
            nodo ide = raiz.getHijos().get(0);
            String nombre = ide.getNombre().replace("\"", "");
            this.rutasCJS.add(nombre);
        }
    }
    
    private void llenarRutaCSS(nodo raiz, String ambito) {
        if (raiz != null) {
            nodo ide = raiz.getHijos().get(0);
            String nombre = ide.getNombre().replace("\"", "");
            this.rutasCSS.add(nombre);
        }
    }

    private void ejecutarCJS(nodo raiz, String ambito, boolean band) {
        if (raiz != null) {
            nodo id = raiz.getHijos().get(0);
            String nombre = id.getNombre().replace("\"", "");
            File archivo = null;
            FileReader fr = null;
            BufferedReader br = null;

            try {
                String contenido = "";
                boolean entro = false;
                for(int i = 0; i <this.rutasCJS.size();i++){
                    archivo = new File(this.rutasCJS.get(i));
                    fr = new FileReader(archivo);
                    br = new BufferedReader(fr);
                    
                    String linea;
                    while ((linea = br.readLine()) != null) {
                        //System.out.println(linea);
                        contenido = contenido + linea + "\n";
                    }
                }
                if (band == true && entro==false) {
                    llenartablacsj(contenido, nombre);
                    entro=true;
                } else if(entro==false) {
                    compilarcsj(contenido, nombre);
                    entro = true;
                }

            } catch (IOException e) {
            } finally {
                try {
                    if (null != fr) {
                        fr.close();
                    }
                } catch (IOException e2) {
                }
            }
        }
    }

    private void llenartablacsj(String contenido, String nombre) {
        AnalisisLex_CJS lexico = new AnalisisLex_CJS(new BufferedReader(new StringReader(contenido)));
        //SE INICIALIZA EL ANALISIS SINTACTICO TOMA COMO PARAMETRO EL LEXICO

        SintacticoCJS sintactico = new SintacticoCJS(lexico);
        try {

            //SE INICIA LA COMPILACION LEXICO Y SINTACTICO
            sintactico.parse();
            ArbolCSJ raiz = sintactico.arbol;
            if (raiz != null) {
                //  System.out.println(raiz.getRaiz().hashCode());
                GraficarArbol grafo = new GraficarArbol();
                grafo.recorrrerarbol(raiz.getRaiz(), 0);
                grafo.contador = 0;
                grafo.enlazararbol(raiz.getRaiz(), 0);
                grafo.graficar(grafo.auxdot, "cjs.png");
                InterpreteCSJ csj1 = new InterpreteCSJ();
                csj1.verificarerroresPrecompilacion(raiz.getRaiz(), "global");
                csj1.errores.recorrerErrores();
                csj1.llenarMetodos(raiz.getRaiz(), "global");
                this.intcsj = csj1;
                //csj1.llenarTablaSimbolos(raiz.getRaiz(), "global");

                guardarNuevoAmbito(nombre, csj1.getAmbitos());
                //csj.ejecutarArbol(raiz.getRaiz(), "global");
            }
        } catch (Exception ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void compilarcsj(String contenido, String nombre) {
        AnalisisLex_CJS lexico = new AnalisisLex_CJS(new BufferedReader(new StringReader(contenido)));
        //SE INICIALIZA EL ANALISIS SINTACTICO TOMA COMO PARAMETRO EL LEXICO

        SintacticoCJS sintactico = new SintacticoCJS(lexico);
        try {

            //SE INICIA LA COMPILACION LEXICO Y SINTACTICO
            sintactico.parse();
            ArbolCSJ raiz = sintactico.arbol;
            if (raiz != null) {
                GraficarArbol grafo = new GraficarArbol();
                grafo.recorrrerarbol(raiz.getRaiz(), 0);
                grafo.contador = 0;
                grafo.enlazararbol(raiz.getRaiz(), 0);
                grafo.graficar(grafo.auxdot, "cjs.png");
                InterpreteCSJ csj1 = this.intcsj;
                csj1.verificarerroresPrecompilacion(raiz.getRaiz(), "global");
                csj1.errores.recorrerErrores();
                csj1.setEventos(this.events);
                csj1.llenarTablaSimbolos(raiz.getRaiz(), "global");
                this.intcsj.actuar = true;
                guardarNuevoAmbito(nombre, csj1.getAmbitos());
                this.contenidoCJS = contenido;
                this.consola=csj1.consola;

            }
        } catch (Exception ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        // this.intcsj.actuar=false;
    }

    private void guardarNuevoAmbito(String nombre, TablaAmbitos ta) {
        if (!this.cjs.containsKey(nombre)) {
            this.cjs.put(nombre, ta);
        }
    }

    private void guardarNuevoCSS(String nombre, InterpreteCSS cs) {
        if (!this.css.containsKey(nombre)) {
            this.css.put(nombre, cs);
        }
    }

    private Object ejecutarExpresion(nodo raiz, String ambito) {
        if (raiz != null) {
            int hijos = raiz.getHijos().size();
            if (hijos == 1) {
                switch (raiz.getNombre()) {
                    case "num":
                        return Integer.parseInt(raiz.getHijos().get(0).getNombre().replace("\"", ""));
                    /*case "decimal":
                        return Double.parseDouble(raiz.getHijos().get(0).getNombre());
                    case "false":
                        return false;
                    case "true":
                        return true;*/
                    case "cadena":
                        return raiz.getHijos().get(0).getNombre().replace("\"", "");

                }
            }
        }
        return null;
    }

    private int tipoObjeto(Object val) {
        if (val instanceof String) {
            return 1;
        } else if (val instanceof Integer) {
            return 2;
        } else if (val instanceof Double) {
            return 3;
        } else if (val instanceof Boolean) {
            return 4;
        } else if (val instanceof JLabel) {
            return 5;
        } else if (val instanceof JPanel) {
            return 6;
        } else if (val instanceof JTextField) {
            return 7;
        } else if (val instanceof JButton) {
            return 8;
        } else if (val instanceof JTextArea) {
            return 9;
        } else if (val instanceof JSpinner) {
            return 10;
        } else if (val instanceof JTable) {
            return 11;
        } else if (val instanceof JComboBox) {
            return 12;
        }
        return 0;
    }

    private CSS getGrupo(String nombre) {
        for (Map.Entry e : this.css.entrySet()) {
            InterpreteCSS cs = (InterpreteCSS) e.getValue();
            if (cs != null) {
                HashMap<String, Decoradores> d = cs.getCSS();
                for (Map.Entry k : d.entrySet()) {
                    Decoradores de = (Decoradores) k.getValue();
                    if (de != null) {
                        ListaCSS auxcss = de.obtenersubconjunto("GRUPO");
                        if (auxcss != null) {
                            CSS ccss = auxcss.existeNombre(nombre, 1);
                            if (ccss != null) {
                                return ccss;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    private CSS getID(String nombre) {
        for (Map.Entry e : this.css.entrySet()) {
            InterpreteCSS cs = (InterpreteCSS) e.getValue();
            if (cs != null) {
                HashMap<String, Decoradores> d = cs.getCSS();
                for (Map.Entry k : d.entrySet()) {
                    Decoradores de = (Decoradores) k.getValue();
                    if (de != null) {
                        ListaCSS auxcss = de.obtenersubconjunto("ID");
                        if (auxcss != null) {
                            CSS ccss = auxcss.existeNombre(nombre, 1);
                            if (ccss != null) {
                                return ccss;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    public void nuevoObjeto(String name, int f, int c, Object val, String tipo) {
        TablaAmbitos ta = getTablaAmbitos("objetos");
        if (ta != null) {
            TablaGeneral tg = ta.getTablaActual("objetos");
            if (tg != null) {
                tg.nuevavariable(name, val, f, c, false, tipo);
                agregarNuevoTableEvento(name);
            }
        }
    }

    public void nuevoGrupoTablaSimbotos(String key) {
        for (Map.Entry k : this.cjs.entrySet()) {
            TablaAmbitos cs = (TablaAmbitos) k.getValue();
            if (cs != null) {
                cs.AgregarNuevoMetodo(key, null);
            }
        }
    }

    private nodo getInstruccionesMetodo(String key) {
        for (Map.Entry k : this.cjs.entrySet()) {
            TablaAmbitos cs = (TablaAmbitos) k.getValue();
            if (cs != null) {
                TablaGeneral gn = cs.getTablaActual(key);
                if (gn != null) {
                    return gn.getInst();
                }
            }
        }
        return null;
    }

    /* private TablaGeneral getTablaGeneral(String ke, TablaAmbitos amb){
        TablaGeneral ta = amb.getTablaActual(ke);
        
    }*/
    private TablaAmbitos getTablaAmbitos(String key) {
        for (Map.Entry k : this.cjs.entrySet()) {
            TablaAmbitos cs = (TablaAmbitos) k.getValue();
            if (cs != null) {
                if (cs.getTablaActual(key) != null) {
                    return cs;
                }
            }
        }
        return null;
    }

    public void actualizarPanel() {
        if (this.intcsj != null) {
            intcsj.actualizarPanel();
        }
    }

     private void verificarListo(String name){
        Eventos evt = events.get(name);
                if (evt != null ) {
                    ArrayList<String> clk = evt.getListo();
                    for (int i = 0; i < clk.size(); i++) {
                        String meto = clk.get(i).replace("(", "").replace(")", "");
                        TablaAmbitos amb = getTablaAmbitos(meto);
                        TablaGeneral tg = amb.getTablaActual(meto);
                        if (tg != null) {
                            this.intcsj.ejecutarMetodo(tg.getInst(), meto);
                        }
                    }
                }
    }
    
    public String getAlineado(String contenido, int tipo) {
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
                return "<html><p align=justify>" + contenido + "</p></html>";
            default:
                break;
        }
        return contenido;
    }
}
