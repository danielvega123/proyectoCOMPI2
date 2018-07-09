/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import EDD.CSJ.ArbolCSJ;
import EDD.CSJ.GraficarArbol;
import EDD.CSJ.Interprete.InterpreteCHTML;
import Errores.ER;
import Errores.ListaConsolas;
import Errores.ListaErrores;
import Gramaticas.CHTML.AnalisisLex_CHTML;
import Gramaticas.CHTML.SintacticoCHTML;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author danielvega
 */
public class Pagina {

    //OBJETOS GRAFICOS
    JPanel cuerpoprincipal;
    public JPanel panelHTML;
    public String titulo;
    public String ruta;
    public String contenidoCHTML;
    public String contenidoCSS;
    public String contenidoCJS;
    JScrollPane scrollHTML;
    JScrollPane scrollConsola;
    public JPanel panelsalida;
    public InterpreteCHTML valores;
    private javax.swing.JButton btnatreas;
    private javax.swing.JButton btnadelante;
    private javax.swing.JButton btnfav;
    private javax.swing.JButton btnhistorial;
    private javax.swing.JButton jsalida;
    private javax.swing.JButton btnActualizar;
    private javax.swing.JTextField txtruta;

    //OBJETOS AUXILIARES
    private final LinkedList<String> historial;
    private final ArrayList<ListaErrores> erroes;
    private final ArrayList<ListaConsolas> consolas;
    //public static ListaErrores errores;
    public int contador = 0;

    public Pagina() {

        cuerpoprincipal = new JPanel();
        panelHTML = new JPanel();
        panelsalida = new JPanel();
        historial = new LinkedList();
        erroes = new ArrayList<>();
        consolas=new ArrayList<>();
        contenidoCHTML = "";
        contenidoCJS = "";
        contenidoCSS = "";
        navegadorBarra();
        navegadorCuerpo();
        HabilitarBotonones();
    }

    private void navegadorCuerpo() {
        panelHTML.setPreferredSize(new Dimension(1300, 1024));
        JScrollPane scrollPane = new JScrollPane(panelHTML, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(0, 30, 1280, 400);
        cuerpoprincipal.add(scrollPane);
        panelsalida.setBounds(0, 431, 1280, 200);
        panelsalida.setLayout(null);
        /*JScrollPane scrollPane1 = new JScrollPane(panelsalida, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane1.setBounds(0, 431, 1280, 200);*/
        cuerpoprincipal.add(panelsalida);
    }

    private void navegadorBarra() {
        jsalida = new javax.swing.JButton();
        btnatreas = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        txtruta = new javax.swing.JTextField();
        btnhistorial = new javax.swing.JButton();
        btnfav = new javax.swing.JButton();
        btnadelante = new javax.swing.JButton();

        //cuerpoprincipal.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        cuerpoprincipal.setLayout(null);
        cuerpoprincipal.setPreferredSize(new Dimension(1000, 30));
        //cuerpoprincipal.setBackground(Color.red);
        btnatreas.setBounds(0, 0, 30, 30);

        btnatreas.setBackground(new java.awt.Color(1, 1, 1));
        btnatreas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/atras.png"))); // NOI18N
        btnatreas.addActionListener((java.awt.event.ActionEvent evt) -> {
            // btnatreasActionPerformed(evt);
            contador--;
            if (contador >= 0) {
                this.panelHTML.removeAll();
                String auxcontenido = this.historial.get(contador);
                setContido(auxcontenido);
                this.panelHTML.validate();
                HabilitarBotonones();
            }

        });
        cuerpoprincipal.add(btnatreas);

        btnadelante.setBounds(31, 0, 30, 30);
        btnadelante.setBackground(new java.awt.Color(1, 1, 1));
        btnadelante.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/adelante.jpg"))); // NOI18N
        btnadelante.addActionListener((java.awt.event.ActionEvent evt) -> {
            if (this.contador <= this.historial.size() - 1) {
                this.panelHTML.removeAll();
                String auxcontenido = this.historial.get(contador);
                HabilitarBotonones();
                setContido(auxcontenido);
                this.panelHTML.validate();
                contador++;
            }
        });
        cuerpoprincipal.add(btnadelante);

        btnActualizar.setBounds(62, 0, 30, 30);
        btnActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/actualizar.jpg"))); // NOI18N
        btnActualizar.addActionListener((java.awt.event.ActionEvent evt) -> {
            this.panelHTML.removeAll();
            HabilitarBotonones();
            setContido(this.txtruta.getText());
            this.historial.add(contador, this.txtruta.getText());
            this.panelHTML.validate();
            contador++;
        });
        cuerpoprincipal.add(btnActualizar);

        txtruta.setBounds(93, 0, 1000, 30);
        txtruta.setText("/home/danielvega/NetBeansProjects/USAC-WEB/src/Gramaticas/CHTML/entradrareal.chtml");
        txtruta.addActionListener((java.awt.event.ActionEvent evt) -> {
            //txtruta1ActionPerformed(evt);
        });

        cuerpoprincipal.add(txtruta);

        btnhistorial.setBounds(1094, 0, 30, 30);
        btnhistorial.setBackground(new java.awt.Color(1, 1, 1));
        btnhistorial.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/historial.jpg"))); // NOI18N
        cuerpoprincipal.add(btnhistorial);
        btnhistorial.addActionListener((ActionEvent e) -> {
            //scrollPaneHistorial.setVisible(!scrollPaneHistorial.isVisible());
            cuerpoprincipal.setVisible(true);
            //   navegadorBarra();
            // validate();
        });

        btnfav.setBounds(1125, 0, 30, 30);
        btnfav.setBackground(new java.awt.Color(1, 1, 1));
        btnfav.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fav.jpg"))); // NOI18N
        btnfav.setToolTipText("");
        cuerpoprincipal.add(btnfav);

        jsalida.setBounds(186, 0, 30, 30);
        jsalida.setBackground(new java.awt.Color(1, 1, 1));

        jsalida.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/consola.png"))); // NOI18N
        jsalida.setToolTipText("");
        jsalida.addActionListener((ActionEvent e) -> {
            // scrollConsola.setVisible(!scrollConsola.isVisible());
            cuerpoprincipal.setVisible(true);
            // navegadorBarra();
            // validate();
        });
        cuerpoprincipal.add(jsalida);

    }

    public LinkedList<String> getHistorial() {
        return this.historial;
    }

    public String getContenido(String ruta) {
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

    public void setContido(String ruta) {
        //String input = this.txtruta1.getText();
        String contenido = getContenido(ruta);
        AnalisisLex_CHTML lexico = new AnalisisLex_CHTML(new BufferedReader(new StringReader(contenido)));
        SintacticoCHTML sintactico = new SintacticoCHTML(lexico);
        try {

            //SE INICIA LA COMPILACION LEXICO Y SINTACTICO
            sintactico.parse();

            ArbolCSJ raiz = sintactico.arbol;

            if (raiz != null) {
                GraficarArbol grafo = new GraficarArbol();
                grafo.recorrrerarbol(raiz.getRaiz(), 0);
                grafo.contador = 0;
                grafo.enlazararbol(raiz.getRaiz(), 0);
                grafo.graficar(grafo.auxdot, "chtml.png");
                InterpreteCHTML chtml = new InterpreteCHTML(this);
                chtml.ejecutarchtml(raiz.getRaiz(), "global");
                this.titulo = chtml.titulo;
                int indice = PantallaInicio.getInstancia().getIndexSelect();
                PantallaInicio.getInstancia().getPestanias().setTitleAt(indice, titulo);
                this.ruta = this.txtruta.getText();
                //this.contenidoCHTML=chtml.contenidoCHTML;
                this.contenidoCHTML = contenido;
                this.contenidoCJS = chtml.contenidoCJS;
                this.contenidoCSS = chtml.contenidoCCS;
                erroes.add(contador, chtml.errores);
                consolas.add(contador,chtml.consola);
                chtml.settearaccion(true);
                chtml.actualizarPanel();
                chtml.settearaccion(false);
                ListaErrores.getListaerror().reiniciarErrores();
            }
        } catch (Exception ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void refactorizar(InterpreteCHTML chtml){
        if(chtml!=null){
            panelHTML.repaint();
        }
    }

    public void mostrarcodigoHTML() {
        panelsalida.removeAll();
        Font f = new Font("Arial", Font.BOLD, 12);
        JTextArea txt = new JTextArea(this.contenidoCHTML);
        JScrollPane scro = new JScrollPane();
        scro.setSize(new Dimension(1280, 200));
        scro.setLocation(0, 0);
        scro.setViewportView(txt);
        txt.setFont(f);
        txt.disable();
        panelsalida.add(scro);
        panelsalida.repaint();
    }

    public void mostrarcodigoCJS() {
        panelsalida.removeAll();
        Font f = new Font("Arial", Font.BOLD, 12);
        JTextArea txt = new JTextArea(this.contenidoCJS);
        JScrollPane scro = new JScrollPane();
        scro.setSize(new Dimension(1280, 200));
        scro.setLocation(0, 0);
        scro.setViewportView(txt);
        txt.setFont(f);
        txt.disable();
        panelsalida.add(scro);
        panelsalida.repaint();
    }

    public void mostrarcodigoCSS() {
        panelsalida.removeAll();
        Font f = new Font("Arial", Font.BOLD, 12);
        JTextArea txt = new JTextArea(this.contenidoCSS);
        JScrollPane scro = new JScrollPane();
        scro.setSize(new Dimension(1280, 200));
        scro.setLocation(0, 0);
        scro.setViewportView(txt);
        txt.setFont(f);
        txt.disable();
        panelsalida.add(scro);
        panelsalida.repaint();
    }

    public void mostrarErrores() {
        panelsalida.removeAll();
        JTable tabla = new JTable();
        DefaultTableModel m = new DefaultTableModel(0, 0);
        String[] header = {"Fila", "Columna", "Lexema", "Tipo", "Descripcion"};
        ListaErrores actual = this.errorActual();
        m.setColumnIdentifiers(header);
        for (ER e : actual.getErrores()) {
            Object[] objt = {e.getFila(), e.getColumna(), e.getLexema(), e.getTipoError(), e.getInformacion()};
            m.addRow(objt);
        }
        tabla.setModel(m);
        JScrollPane scro = new JScrollPane();
        scro.setSize(new Dimension(1280, 200));
        scro.setLocation(0, 0);
        scro.setViewportView(tabla);
        panelsalida.add(scro);
        panelsalida.repaint();
    }
    
    public void mostrarConsola() {
        panelsalida.removeAll();
        JTable tabla = new JTable();
        DefaultTableModel m = new DefaultTableModel(0, 0);
        String[] header = {"Fila", "Columna", "Salida"};
        ListaConsolas actual = this.consolaActual();
        m.setColumnIdentifiers(header);
        for (ER e : actual.getErrores()) {
            Object[] objt = {e.getFila(), e.getColumna(), e.getLexema()};
            m.addRow(objt);
        }
        tabla.setModel(m);
        JScrollPane scro = new JScrollPane();
        scro.setSize(new Dimension(1280, 200));
        scro.setLocation(0, 0);
        scro.setViewportView(tabla);
        panelsalida.add(scro);
        panelsalida.repaint();
    }

    public ListaErrores errorActual() {
        return erroes.get(contador - 1);
    }
    
    public ListaConsolas consolaActual() {
        return consolas.get(contador - 1);
    }

    public final void HabilitarBotonones() {
        if (this.historial.isEmpty()) {
            this.btnadelante.setEnabled(false);
            this.btnatreas.setEnabled(false);
        } else {
            if (this.contador < this.historial.size() - 1) {
                this.btnadelante.setEnabled(true);
            }
            if (this.contador > 0) {
                this.btnatreas.setEnabled(true);
            } else {
                this.btnatreas.setEnabled(false);
            }

        }
    }
}
