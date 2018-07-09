/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaz;

import Errores.ListaErrores;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

/**
 *
 * @author danielvega
 */
public class PantallaInicio extends javax.swing.JFrame {

    ArrayList<String> pestanas;
    ArrayList<Pagina> paginas;
    
    
    private static PantallaInicio unique;
    
    public static PantallaInicio getInstancia(){
        if(unique==null){
            unique = new PantallaInicio();
        }
        return unique;
    }
    
    public int getIndexSelect(){
        return pestanias.getSelectedIndex();
    }
    
    public JTabbedPane getPestanias(){
        return this.pestanias;
    }
    private PantallaInicio() {
        initComponents();
        this.setExtendedState(MAXIMIZED_BOTH);
        this.paginas = new ArrayList<>();
        JPanel pan = new JPanel();
        pan.setName("+");    
        this.pestanias.add(pan);
        pestanas= new ArrayList<>();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu8 = new javax.swing.JMenu();
        jMenu9 = new javax.swing.JMenu();
        pestanias = new javax.swing.JTabbedPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jarchivo = new javax.swing.JMenu();
        jnuevo = new javax.swing.JMenu();
        jcodigo = new javax.swing.JMenu();
        jsalida = new javax.swing.JMenu();
        jcjs = new javax.swing.JMenu();
        jchtml = new javax.swing.JMenu();
        jcss = new javax.swing.JMenu();
        jMenu7 = new javax.swing.JMenu();

        jMenu8.setText("File");
        jMenuBar2.add(jMenu8);

        jMenu9.setText("Edit");
        jMenuBar2.add(jMenu9);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("USAC-WEB");
        setBackground(new java.awt.Color(254, 254, 254));

        pestanias.setBackground(new java.awt.Color(250, 250, 250));
        pestanias.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pestaniasMouseClicked(evt);
            }
        });

        jMenuBar1.setBackground(new java.awt.Color(254, 254, 254));
        jMenuBar1.setForeground(new java.awt.Color(253, 251, 251));

        jarchivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/archivo.png"))); // NOI18N
        jarchivo.setToolTipText("");

        jnuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/nuevo.png"))); // NOI18N
        jnuevo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jnuevoMouseClicked(evt);
            }
        });
        jnuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jnuevoActionPerformed(evt);
            }
        });
        jarchivo.add(jnuevo);

        jMenuBar1.add(jarchivo);

        jcodigo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/salida.jpg"))); // NOI18N
        jcodigo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jcodigoMouseClicked(evt);
            }
        });

        jsalida.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/consola.png"))); // NOI18N
        jsalida.setText("Consola Salida");
        jsalida.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jsalidaMouseClicked(evt);
            }
        });
        jcodigo.add(jsalida);

        jcjs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/js.png"))); // NOI18N
        jcjs.setText("Codigo CJS");
        jcjs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jcjsMouseClicked(evt);
            }
        });
        jcodigo.add(jcjs);

        jchtml.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/html.png"))); // NOI18N
        jchtml.setText("Codigo CHTML");
        jchtml.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jchtmlMouseClicked(evt);
            }
        });
        jcodigo.add(jchtml);

        jcss.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/css.jpg"))); // NOI18N
        jcss.setText("Codigo CCSS");
        jcss.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jcssMouseClicked(evt);
            }
        });
        jcodigo.add(jcss);

        jMenu7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/errores.jpg"))); // NOI18N
        jMenu7.setText("Consola Errores");
        jMenu7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu7MouseClicked(evt);
            }
        });
        jcodigo.add(jMenu7);

        jMenuBar1.add(jcodigo);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pestanias, javax.swing.GroupLayout.PREFERRED_SIZE, 1336, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pestanias, javax.swing.GroupLayout.DEFAULT_SIZE, 674, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jnuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jnuevoActionPerformed
        // TODO add your handling code here:
   
    }//GEN-LAST:event_jnuevoActionPerformed

    private void jnuevoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jnuevoMouseClicked
 
            Pagina pes = new Pagina();
            this.pestanias.add("nueva",pes.cuerpoprincipal);
            this.paginas.add(pes);
            
    }//GEN-LAST:event_jnuevoMouseClicked

    private void pestaniasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pestaniasMouseClicked
        // TODO add your handling code here:
      /*  JPanel pan = (JPanel) this.pestanias.getSelectedComponent();
        if(pan.getName().equals("+")){
            JScrollPane page= new JScrollPane();
            page.setName("nueva");            
            this.pestanias.add(page);
            //this.pestanas.add(page);
            
        }*/
    }//GEN-LAST:event_pestaniasMouseClicked

    private void jcodigoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jcodigoMouseClicked
    
    }//GEN-LAST:event_jcodigoMouseClicked
    public void nuevoContenido(JScrollPane page, String titulo, String ruta){
        //JScrollPane pan = (JScrollPane) this.pestanias.getSelectedComponent();
        int index = this.pestanias.getSelectedIndex();
        if(index>=0){
           this.pestanas.add(ruta);
           if(index>0){
           this.pestanas.remove(index);
           }
           this.pestanias.addTab(titulo, page);
        }
    }
    private void nuevoContenido(Pagina page){
        JPanel pan = (JPanel) this.pestanias.getSelectedComponent();
        if(pan!=null){
            int index = this.pestanias.getSelectedIndex();
            //this.pestanas.add(e)
        }
    }
    private void jMenu7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu7MouseClicked
        int indice = this.pestanias.getSelectedIndex();
        if(indice>0){
            Pagina pag = paginas.get(indice-1);
            if(pag!=null){
                pag.mostrarErrores();
            }
        }
    }//GEN-LAST:event_jMenu7MouseClicked

    private void jcjsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jcjsMouseClicked
        // TODO add your handling code here:
        int indice = this.pestanias.getSelectedIndex();
        if(indice>0){
            Pagina pag = paginas.get(indice-1);
            if(pag!=null){
                pag.panelsalida.removeAll();
                pag.mostrarcodigoCJS();
                pag.panelsalida.validate();
            }
        }
    }//GEN-LAST:event_jcjsMouseClicked

    private void jchtmlMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jchtmlMouseClicked
        // TODO add your handling code here:
        int indice = this.pestanias.getSelectedIndex();
        if(indice>0){
            Pagina pag = paginas.get(indice-1);
            if(pag!=null){
                //System.out.println(pag.contenidoCHTML);
                pag.panelsalida.removeAll();
                pag.mostrarcodigoHTML();
                pag.panelsalida.validate();
            }
        }
    }//GEN-LAST:event_jchtmlMouseClicked

    private void jcssMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jcssMouseClicked
        // TODO add your handling code here:
        int indice = this.pestanias.getSelectedIndex();
        if(indice>0){
            Pagina pag = paginas.get(indice-1);
            if(pag!=null){
                pag.panelsalida.removeAll();
                pag.mostrarcodigoCSS();
                pag.panelsalida.validate();
            }
        }
    }//GEN-LAST:event_jcssMouseClicked

    private void jsalidaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jsalidaMouseClicked
        // TODO add your handling code here:
        int indice = this.pestanias.getSelectedIndex();
        if(indice>0){
            Pagina pag = paginas.get(indice-1);
            if(pag!=null){
                pag.mostrarConsola();
            }
        }
    }//GEN-LAST:event_jsalidaMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PantallaInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PantallaInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PantallaInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PantallaInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PantallaInicio().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu7;
    private javax.swing.JMenu jMenu8;
    private javax.swing.JMenu jMenu9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenu jarchivo;
    private javax.swing.JMenu jchtml;
    private javax.swing.JMenu jcjs;
    private javax.swing.JMenu jcodigo;
    private javax.swing.JMenu jcss;
    private javax.swing.JMenu jnuevo;
    private javax.swing.JMenu jsalida;
    public javax.swing.JTabbedPane pestanias;
    // End of variables declaration//GEN-END:variables
}
