/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EDD.CSJ.Interprete;

import EDD.CSJ.nodo;
import EDDTS.*;
import Errores.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author danielvega
 */
public class InterpreteCSS {

    HashMap<String, Decoradores> css;
    ListaErrores errores;
    String name;
    int valor;

    public InterpreteCSS() {
        this.css = new HashMap<>();
        this.errores=ListaErrores.getListaerror();
        /*this.css.put("ID", new Decoradores());
        this.css.put("GRUPO", new Decoradores());*/
    }
    
    public HashMap<String,Decoradores> getCSS(){
        return this.css;
    }

    private void Insertar(String nombre) {
        if (this.css.containsKey(nombre)) {
            //ERROR
        } else {
            this.css.put(nombre, new Decoradores());
            Decoradores aux = this.css.get(nombre);
            aux.insertarNuevo("ID");
            aux.insertarNuevo("GRUPO");
        }
    }

    private void nuevoID(String nombre) {
        Decoradores auxcss = this.css.get(name);
        if (auxcss != null) {
            ListaCSS auxi = auxcss.obtenersubconjunto("ID");
            if (auxi != null) {
                auxi.insertarHijo(nombre);
            }
        }
    }

    private void nuevoGR(String nombre) {
        Decoradores auxcss = this.css.get(name);
        if (auxcss != null) {
            ListaCSS auxi = auxcss.obtenersubconjunto("GRUPO");
            if (auxi != null) {
                auxi.insertarHijo(nombre);
            }
        }
    }

    private void insertarValorPropiedad(String propiedad, String grupo, Object val) {
        Decoradores auxcss = this.css.get(name);
        if (auxcss != null) {
            ListaCSS auxi;
            if (valor == 0) {
                auxi = auxcss.obtenersubconjunto("GRUPO");
                if (auxi != null) {
                    CSS cs = auxi.existeNombre(grupo);
                    if (cs != null) {
                        ArrayList<Object> prop = cs.getPropiedad(propiedad);
                        if (prop != null) {
                            prop.add(val);
                        }
                    }
                }
            } else if (valor == 1) {
                auxi = auxcss.obtenersubconjunto("ID");
                if (auxi != null) {
                    CSS cs = auxi.existeNombre(grupo);
                    if (cs != null) {
                        ArrayList<Object> prop = cs.getPropiedad(propiedad);
                        if (prop != null) {
                            prop.add(val);
                        }
                    }
                }
            }
        }
    }

    private void insertarPropiedad(String propiedad, String grupo) {
        Decoradores auxcss = this.css.get(name);
        if (auxcss != null) {
            ListaCSS auxi;
            if (valor == 0) {
                auxi = auxcss.obtenersubconjunto("GRUPO");
                if (auxi != null) {
                    CSS cs = auxi.existeNombre(grupo);
                    if (cs != null) {
                        cs.insertarNuevo(propiedad);
                    }
                }
            } else if (valor == 1) {
                auxi = auxcss.obtenersubconjunto("ID");
                if (auxi != null) {
                    CSS cs = auxi.existeNombre(grupo);
                    if (cs != null) {
                        cs.insertarNuevo(propiedad);
                    }
                }
            }
        }
    }

    public void ejecutarCSS(nodo raiz) {
        if (raiz != null) {
            if (raiz.getNombre().equals("INI")) {
                for (nodo s : raiz.getHijos()) {
                    switch (s.getNombre()) {
                        case "ID":
                            recorrerID(s);
                            break;
                    }
                }
                for (nodo s : raiz.getHijos()) {
                    switch (s.getNombre()) {
                        case "ID":
                            recorrerPropiedades(s);
                            break;
                    }
                }
            }
        }
    }

    private void recorrerPropiedades(nodo raiz) {
        if (raiz != null) {
            nodo ide = raiz.getHijos().get(0);
            nodo propiedades = raiz.getHijos().get(1);
            name = ide.getNombre();
            String nombre = "";
            Insertar(ide.getNombre());
            for (nodo s : propiedades.getHijos()) {
                switch (s.getNombre()) {
                    case "grupo_refererenciado":
                        nombre = s.getHijos().get(0).getNombre();
                        valor = 0;
                        break;
                    case "id_refererenciado":
                        nombre = s.getHijos().get(0).getNombre();
                        valor = 1;
                        break;
                    case "formato":
                        valoressinEXPR(s, nombre, "formato");
                        break;
                    case "letra":
                        valoresconEXPR(s, nombre, "letra");
                        break;
                    case "tamtex":
                        valoresconEXPR(s, nombre, "tamtex");
                        break;
                    case "fondoelemento":
                         valoresconEXPR(s, nombre, "fondoelemento");
                        break;
                    case "colottext":
                         valoresconEXPR(s, nombre, "colortext");
                        break;
                    case "texto":
                         valoresconEXPR(s, nombre, "texto");
                        break;
                    case "alineado":
                        valoressinEXPR(s, nombre, "alineado");
                        break;
                    case "autoredimension":
                        autoredimensio(s, nombre, "autoredimension");
                        break;
                    case "visible":
                         valoresconEXPR(s, nombre, "visible");
                        break;
                    case "opaco":
                         valoresconEXPR(s, nombre, "opaco");
                        break;
                    case "borde":
                         valoresconEXPR(s, nombre, "borde");
                        break;
                }
            }
        }
    }
    
    private void valoressinEXPR(nodo raiz, String grupo, String propiedad){
        if(raiz!=null){
            for(nodo s: raiz.getHijos()){
                insertarValorPropiedad(propiedad, grupo, s.getNombre());
            }
        }
    }
    
    private void valoresconEXPR(nodo raiz, String grupo, String propiedad){
        if(raiz!=null){
            for(nodo s: raiz.getHijos()){
                insertarValorPropiedad(propiedad, grupo, ejecutarExpresion(s, ""));
            }
        }
    }
    
    private void autoredimensio(nodo raiz, String grupo, String propiedad){
        if(raiz!=null){
            insertarValorPropiedad(propiedad, grupo, ejecutarExpresion(raiz.getHijos().get(0), grupo));
            insertarValorPropiedad(propiedad, grupo, raiz.getHijos().get(1).getNombre());
        }
    }

    private void recorrerID(nodo raiz) {
        if (raiz != null) {
            nodo ide = raiz.getHijos().get(0);
            nodo propiedades = raiz.getHijos().get(1);
            name = ide.getNombre();
            String nombre = "";
            Insertar(ide.getNombre());
            for (nodo s : propiedades.getHijos()) {
                switch (s.getNombre()) {
                    case "grupo_refererenciado":
                        nombre = s.getHijos().get(0).getNombre();
                        nuevoGR(nombre);
                        valor = 0;
                        break;
                    case "id_refererenciado":
                        nombre = s.getHijos().get(0).getNombre();
                        nuevoID(nombre);
                        valor = 1;
                        break;
                    case "formato":
                        insertarPropiedad("formato", nombre);
                        break;
                    case "letra":
                        insertarPropiedad("letra", nombre);
                        break;
                    case "tamtex":
                        insertarPropiedad("tamtex", nombre);
                        break;
                    case "fondoelemento":
                        insertarPropiedad("fondoelemento", nombre);
                        break;
                    case "colottext":
                        insertarPropiedad("colortext", nombre);
                        break;
                    case "texto":
                        insertarPropiedad("texto", nombre);
                        break;
                    case "alineado":
                        insertarPropiedad("alineado", nombre);
                        break;
                    case "autoredimension":
                        insertarPropiedad("autoredimension", nombre);
                        break;
                    case "visible":
                        insertarPropiedad("visible", nombre);
                        break;
                    case "opaco":
                        insertarPropiedad("opaco", nombre);
                        break;
                    case "borde":
                        insertarPropiedad("borde", nombre);
                        break;
                }
            }
        }
    }
    
    private Object ejecutarExpresion(nodo raiz, String ambito) {
        if (raiz != null) {
            int hijos = raiz.getHijos().size();
            if (hijos == 2) {
                switch (raiz.getNombre()) {
                    case "+":
                        return ejecutarSuma(raiz.getHijos().get(0), raiz.getHijos().get(1), ambito);
                    case "-":
                        return ejecutarResta(raiz.getHijos().get(0), raiz.getHijos().get(1), ambito);
                    case "*":
                        return ejecutarMulti(raiz.getHijos().get(0), raiz.getHijos().get(1), ambito);
                    case "/":
                        return ejecutarDivi(raiz.getHijos().get(0), raiz.getHijos().get(1), ambito);
                }
                
            } else if (hijos == 1) {
                switch (raiz.getNombre()) {
                    case "num":
                        return Integer.parseInt(raiz.getHijos().get(0).getNombre());
                    case "decimal":
                        return Double.parseDouble(raiz.getHijos().get(0).getNombre());
                      case "false":
                        return false;
                    case "true":
                        return true;
                    case "cadena":
                        return raiz.getHijos().get(0).getNombre().replace("\"", "");
                   

                }
            } 
        }
        return null;
    }
    
      private Object ejecutarSuma(nodo izq, nodo der, String ambito) {
        Object val1 = ejecutarExpresion(izq, ambito);
        Object val2 = ejecutarExpresion(der, ambito);
        int tipo1 = tipoObjeto(val1);
        int tipo2 = tipoObjeto(val2);

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
        }
        return null;
    }

    private Object ejecutarResta(nodo izq, nodo der, String ambito) {
        Object val1 = ejecutarExpresion(izq, ambito);
        Object val2 = ejecutarExpresion(der, ambito);
        int tipo1 = tipoObjeto(val1);
        int tipo2 = tipoObjeto(val2);

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
        }
        return null;
    }

    private Object ejecutarMulti(nodo izq, nodo der, String ambito) {
        Object val1 = ejecutarExpresion(izq, ambito);
        Object val2 = ejecutarExpresion(der, ambito);
        int tipo1 = tipoObjeto(val1);
        int tipo2 = tipoObjeto(val2);

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
        }
        return null;
    }

    private Object ejecutarDivi(nodo izq, nodo der, String ambito) {
        Object val1 = ejecutarExpresion(izq, ambito);
        Object val2 = ejecutarExpresion(der, ambito);
        int tipo1 = tipoObjeto(val1);
        int tipo2 = tipoObjeto(val2);

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
        }
        return null;
    }
    
     private Object ejecutarOR(nodo izq, nodo der, String ambito) {
        Object valee1 = ejecutarExpresion(izq, ambito);
        Object valee2 = ejecutarExpresion(der, ambito);

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
            return false;
        }
    }

    private Object ejecutarAND(nodo izq, nodo der, String ambito) {
        Object valee1 = ejecutarExpresion(izq, ambito);
        Object valee2 = ejecutarExpresion(der, ambito);

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
            return false;
        }
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
        }
        return 0;
    }

     private String TipoString(int tipo) {
        switch (tipo) {
            case 1:
                return "Texto";
            case 2:
            case 3:
                return "Numerico";
            case 4:
                return "Booleano";
        }
        return "";
    }

}
