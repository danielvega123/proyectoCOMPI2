package Gramaticas.CHTML;

import Errores.ListaErrores;
import java_cup.runtime.Symbol;
import Errores.ER;
%%
Comentario      ="<"[/][/][-] [^*]+ [-][/][/]">"
//Comentario2     =[/][/] [^\n]* [\n]

digito = [0-9]
Numero= {digito}+
letra =[a-zñA-ZÑ]
iden ={letra}+({letra}|{Numero}|"_")*
//parrafo = (numero|letra|[/]|\+|-|\*|\/)*

chain = "\""(.[^"\""]*)"\"" 

//%state COMENT_MULTI

%cupsym sym
%class AnalisisLex_CHTML
%cup
%public
%unicode
%public
%line
%ignorecase
%column
%char
%{

ListaErrores errores = ListaErrores.getListaerror();
%}
%%

"CHTML"			{
					System.out.println("Se reconocio etiqueta "+yytext());	
					return new Symbol(sym.echtml,yychar,yyline,yytext());
				}
"FIN"			{
					System.out.println("Se reconocio etiqueta "+yytext());	
					return new Symbol(sym.efin,yychar,yyline,yytext());
				}
"ENCABEZADO"	{
					System.out.println("Se reconocio etiqueta "+yytext());	
					return new Symbol(sym.eencabezado,yychar,yyline,yytext());
				}
"CJS"			{
					System.out.println("Se reconocio etiqueta "+yytext());	
					return new Symbol(sym.ecjs,yychar,yyline,yytext());
				}
"ruta"			{
					System.out.println("Se reconocio propiedad "+yytext());	
					return new Symbol(sym.pruta,yychar,yyline,yytext());
				}				
"CCSS"			{
					System.out.println("Se reconocio etiqueta "+yytext());	
					return new Symbol(sym.eccss,yychar,yyline,yytext());
				}
"CUERPO"		{
					System.out.println("Se reconocio etiqueta "+yytext());	
					return new Symbol(sym.ecuerpo,yychar,yyline,yytext());
				}
"fondo"			{
					System.out.println("Se reconocio propiedad "+yytext());	
					return new Symbol(sym.efondo,yychar,yyline,yytext());
				}
"PANEL"			{
					System.out.println("Se reconocio etiqueta "+yytext());	
					return new Symbol(sym.epanel,yychar,yyline,yytext());
				}
"TITULO"		{
					System.out.println("Se reconocio etiqueta "+yytext());	
					return new Symbol(sym.etitulo,yychar,yyline,yytext());
				}
"TEXTO"			{
					System.out.println("Se reconocio etiqueta "+yytext());	
					return new Symbol(sym.etexto,yychar,yyline,yytext());
				}
"CAJA_TEXTO"	{
					System.out.println("Se reconocio etiqueta "+yytext());	
					return new Symbol(sym.ecajatexto,yychar,yyline,yytext());
				}
"click"			{
					System.out.println("Se reconocio propiedad "+yytext());	
					return new Symbol(sym.eclick,yychar,yyline,yytext());
				}
"IMAGEN"		{
					System.out.println("Se reconocio etiqueta "+yytext());	
					return new Symbol(sym.eimagen,yychar,yyline,yytext());
				}
"BOTON"			{
					System.out.println("Se reconocio etiqueta "+yytext());	
					return new Symbol(sym.eboton,yychar,yyline,yytext());
				}
"ENLACE"		{
					System.out.println("Se reconocio etiqueta "+yytext());	
					return new Symbol(sym.eenlace,yychar,yyline,yytext());
				}
"TABLA"			{
					System.out.println("Se reconocio etiqueta "+yytext());	
					return new Symbol(sym.etabla,yychar,yyline,yytext());
				}
"FIL_T"			{
					System.out.println("Se reconocio etiqueta "+yytext());	
					return new Symbol(sym.efila,yychar,yyline,yytext());
				}
"CB"			{
					System.out.println("Se reconocio etiqueta "+yytext());	
					return new Symbol(sym.eceldaencabezado,yychar,yyline,yytext());
				}
"CT"			{
					System.out.println("Se reconocio etiqueta "+yytext());	
					return new Symbol(sym.ecelda,yychar,yyline,yytext());
				}
"TEXTO_A"		{
					System.out.println("Se reconocio etiqueta "+yytext());	
					return new Symbol(sym.eareatexto,yychar,yyline,yytext());
				}
"CAJA"			{
					System.out.println("Se reconocio etiqueta "+yytext());	
					return new Symbol(sym.ecajaopciones,yychar,yyline,yytext());
				}
"OPCION"			{
					System.out.println("Se reconocio etiqueta "+yytext());	
					return new Symbol(sym.eopcion,yychar,yyline,yytext());
				}
"valor"			{
					System.out.println("Se reconocio propiedad "+yytext());	
					return new Symbol(sym.evalor,yychar,yyline,yytext());
				}
"SPINNER"		{
					System.out.println("Se reconocio etiqueta "+yytext());	
					return new Symbol(sym.econtador,yychar,yyline,yytext());
				}
"SALTO"			{
					System.out.println("Se reconocio etiqueta "+yytext());	
					return new Symbol(sym.esalto,yychar,yyline,yytext());
				}
"id"			{
					System.out.println("Se reconocio propiedad "+yytext());	
					return new Symbol(sym.pid,yychar,yyline,yytext());
				}
"grupo"			{
					System.out.println("Se reconocio propiedad "+yytext());	
					return new Symbol(sym.pgrupo,yychar,yyline,yytext());
				}
"alto"			{
					System.out.println("Se reconocio propiedad "+yytext());	
					return new Symbol(sym.palto,yychar,yyline,yytext());
				}
"ancho"			{
					System.out.println("Se reconocio propiedad "+yytext());	
					return new Symbol(sym.pancho,yychar,yyline,yytext());
				}
"alineado"		{
					System.out.println("Se reconocio propiedad "+yytext());	
					return new Symbol(sym.palineado,yychar,yyline,yytext());
				}
"izquierda"		{
					System.out.println("Se reconocio propiedad "+yytext());	
					return new Symbol(sym.pizquierda,yychar,yyline,yytext());
				}
"derecha"		{
					System.out.println("Se reconocio propiedad "+yytext());	
					return new Symbol(sym.pderecha,yychar,yyline,yytext());
				}
"centrado"			{
					System.out.println("Se reconocio propiedad "+yytext());	
					return new Symbol(sym.pcentrado,yychar,yyline,yytext());
				}
"-"				{
					System.out.println("Se reconocio signo "+yytext());	
					return new Symbol(sym.menos,yychar,yyline,yytext());
				}
"<"				{
					System.out.println("Se reconocio signo "+yytext());	
					return new Symbol(sym.menor,yychar,yyline,yytext());
				}
">"				{
					System.out.println("Se reconocio signo "+yytext());	
					return new Symbol(sym.mayor,yychar,yyline,yytext());
				}

"="             { 
					System.out.println("Se reconocio signo "+yytext());	
					return new Symbol(sym.igual,yychar,yyline,yytext());
				}
[";"]           { 
					System.out.println("Se reconocio signo "+yytext());	
					return new Symbol(sym.pcoma,yychar,yyline,yytext());
				}
["("]           { 
					System.out.println("Se reconocio signo "+yytext());	
					return new Symbol(sym.aparen,yychar,yyline,yytext());
				}
[")"]           { 
					System.out.println("Se reconocio signo "+yytext());	
					return new Symbol(sym.cparen,yychar,yyline,yytext());
				}
/*{parrafo}		{ 
					System.out.println("Se reconocio parrafo "+yytext());	
					return new Symbol(sym.rparrafo,yychar,yyline, yytext());
				}*/
{Numero}        { 
					System.out.println("Se reconocio numero "+yytext());	
					return new Symbol(sym.num,yychar,yyline,yytext());
				}
{iden}			{ 
					System.out.println("Se reconocio identificador "+yytext());	
					return new Symbol(sym.iden,yychar,yyline, new String(yytext()));
				}
{chain}			{ 
					System.out.println("Se reconocio cadena "+yytext());	
					return new Symbol(sym.cadena,yychar,yyline, yytext());
				}

{Comentario}    { /* Se ignoran */}

[ \t\r\f\n]+    { /* Se ignoran */ yychar = 0;}
.               { System.out.println("Error lexico: "+yytext());
errores.agregarerror(new ER(yyline,yycolumn, yytext(), 1, ""
                                + "Caracter no conocido"));}
