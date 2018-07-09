/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EDD.CSJ;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author danielvega
 */
public class GraficarArbol {

    public GraficarArbol() {
    }

    public String auxdot = "digraph prueba{\nnode [shape=box];\n style = filled;\n ";
    public int contador = 0;

    public void recorrrerarbol(nodo root, int padre) {
        if (root != null) {
            if (root.getHijos() != null) {
                if (root.getHijos().size() > 0 && root.getNombre() != null) {
                    //      System.out.println(root.toString() + "<-->" + root.getLexema());
                    auxdot += "nodo" + String.valueOf(padre) + "[label=\"" + root.getNombre().replace("\"","") + "\" color=green,style=filled, rank=same];\n";
                    for (int x = 0; x < root.getHijos().size(); x++) {
                        contador++;
                        recorrrerarbol(root.getHijos().get(x), contador);
                    }
                } else {
                    auxdot += "nodo" + String.valueOf(contador) + "[label=\"" + root.getNombre().replace("\"","") + "\"color=thistle2, style = filled, rank=same];\n";
                }
            } else {
                if (root.getNombre() != null) {
                    auxdot += "nodo" + String.valueOf(contador) + "[label=\"" + root.getNombre().replace("\"","") + "\"color=thistle2, style = filled, rank=same];\n";
                }
            }
        }
    }

    public void enlazararbol(nodo root, int padre) {
        if (root != null) {
            if (root.getHijos() != null) {
                if (root.getHijos().size() > 0 && root.getNombre() != null) {
                    //      System.out.println(root.toString() + "<-->" + root.getLexema());
                    for (int x = 0; x < root.getHijos().size(); x++) {
                        contador++;
                        auxdot += "nodo" + String.valueOf(padre) + "->nodo" + String.valueOf(contador) + ";\n";
                        enlazararbol(root.getHijos().get(x), contador);
                    }
                } else {
                    //auxdot += "nodo" + String.valueOf(padre) + "->nodo" + String.valueOf(contador) + ";\n";

                }
            } else {
                if (root.getNombre() != null) {
                   // auxdot += "nodo" + String.valueOf(padre) + "->nodo" + String.valueOf(contador) + ";\n";
                }
            }
        }
    }

    public void graficar(String contenido, String nombre) throws IOException {
        contenido += "}";
        String ruta = "/home/danielvega/Escritorio/ast.dot";
        File archivo = new File(ruta);
        BufferedWriter bw;
        if (archivo.exists()) {
            vaciarfichero(ruta);
            bw = new BufferedWriter(new FileWriter(archivo, true));//el true es para que no pise el archivo
            bw.write("");
            bw.write(contenido);
        } else {
            vaciarfichero(ruta);
            bw = new BufferedWriter(new FileWriter(archivo));
            bw.write("");
            bw.write(contenido);
        }
        auxdot = "digraph prueba{\n ";
        bw.close();
        try {
            String cmd = "dot -Tpng /home/danielvega/Escritorio/ast.dot -o /home/danielvega/Escritorio/"+nombre+";"; //Comando de apagado en linux
            Runtime.getRuntime().exec(cmd);
            /*try{ Thread.sleep(1000); }
            catch(InterruptedException e ) 
            { System.out.println("Thread Interrupted"); }
            cmd = "gvfs-open /home/danielvega/Escritorio/ast.png";
            Runtime.getRuntime().exec(cmd);*/

        } catch (IOException ioe) {
            System.out.println(ioe);
        }
        contador = 0;
    }

    public void vaciarfichero(String ruta) throws IOException {
        File archivo = new File(ruta);
        archivo.delete();
        try {
            archivo.createNewFile();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }

}
