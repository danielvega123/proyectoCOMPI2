package Gramaticas.CSS;

import Errores.ListaErrores;
import java_cup.runtime.Symbol;
import Errores.ER;
%%
Comentario      =[/]["*"] [^*]+ ["*"][/]
Comentario2     =[/][/] [^\n]* [\n]

digito = [0-9]
Numero= {digito}+
letra =[a-zñA-ZÑ]
iden ={letra}+({letra}|{Numero}|"_")*
//chain = ["\""] (~["\n","\r"])* ["\""]
Dec ={Numero}"."({Numero})+
chain = "\""(.[^"\""]*)"\"" 
//%state COMENT_MULTI

%cupsym sym
%class AnalisisLex_CCSS
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

"id"			{
					System.out.println("Se reconocio reservada "+yytext());	
					return new Symbol(sym.rid,yychar,yyline,yytext());
				}
"texto"	{
					System.out.println("Se reconocio reservada "+yytext());	
					return new Symbol(sym.rtexto,yychar,yyline,yytext());
				}
"negrilla"		{
					System.out.println("Se reconocio reservada "+yytext());	
					return new Symbol(sym.rnegrilla,yychar,yyline,yytext());
				}
"cursiva"		{
					System.out.println("Se reconocio propiedad "+yytext());	
					return new Symbol(sym.rcursiva,yychar,yyline,yytext());
				}				
"mayuscula"		{
					System.out.println("Se reconocio reservada "+yytext());	
					return new Symbol(sym.rmayuscula,yychar,yyline,yytext());
				}
"minuscula"		{
					System.out.println("Se reconocio reservada "+yytext());	
					return new Symbol(sym.rminuscula,yychar,yyline,yytext());
				}
"capital-t"		{
					System.out.println("Se reconocio propiedad "+yytext());	
					return new Symbol(sym.rcapital,yychar,yyline,yytext());
				}
"formato"		{
					System.out.println("Se reconocio reservada "+yytext());	
					return new Symbol(sym.rformato,yychar,yyline,yytext());
				}
"letra"			{
					System.out.println("Se reconocio reservada "+yytext());	
					return new Symbol(sym.rletra,yychar,yyline,yytext());
				}
"tamtex"		{
					System.out.println("Se reconocio reservada "+yytext());	
					return new Symbol(sym.rtamtex,yychar,yyline,yytext());
				}
"fondoelemento"	{
					System.out.println("Se reconocio reservada "+yytext());	
					return new Symbol(sym.rfondoele,yychar,yyline,yytext());
				}
"autoredimension" {
					System.out.println("Se reconocio propiedad "+yytext());	
					return new Symbol(sym.rautore,yychar,yyline,yytext());
				}
"visible"		{
					System.out.println("Se reconocio reservada "+yytext());	
					return new Symbol(sym.rvisible,yychar,yyline,yytext());
				}
"borde"			{
					System.out.println("Se reconocio reservada "+yytext());	
					return new Symbol(sym.rborde,yychar,yyline,yytext());
				}
"opaque"		{
					System.out.println("Se reconocio reservada "+yytext());	
					return new Symbol(sym.ropaque,yychar,yyline,yytext());
				}
"colortext"		{
					System.out.println("Se reconocio reservada "+yytext());	
					return new Symbol(sym.rcolortext,yychar,yyline,yytext());
				}
"grupo"			{
					System.out.println("Se reconocio reservada "+yytext());	
					return new Symbol(sym.rgrupo,yychar,yyline,yytext());
				}
"izquierda"		{
					System.out.println("Se reconocio propiedad "+yytext());	
					return new Symbol(sym.rizquierda,yychar,yyline,yytext());
				}
"derecha"		{
					System.out.println("Se reconocio propiedad "+yytext());	
					return new Symbol(sym.rderecha,yychar,yyline,yytext());
				}
"centrado"		{
					System.out.println("Se reconocio propiedad "+yytext());	
					return new Symbol(sym.rcentrado,yychar,yyline,yytext());
				}
"justificado"	{
					System.out.println("Se reconocio propiedad "+yytext());	
					return new Symbol(sym.rjustificado,yychar,yyline,yytext());
				}
"alineado"		{
					System.out.println("Se reconocio propiedad "+yytext());	
					return new Symbol(sym.ralineado,yychar,yyline,yytext());
				}
"horizontal"	{
					System.out.println("Se reconocio propiedad "+yytext());	
					return new Symbol(sym.rhorizontal,yychar,yyline,yytext());
				}
"vertical"		{
					System.out.println("Se reconocio propiedad "+yytext());	
					return new Symbol(sym.rvertical,yychar,yyline,yytext());
				}
"["				{
					System.out.println("Se reconocio signo "+yytext());	
					return new Symbol(sym.acorche,yychar,yyline,yytext());
				}
"]"				{
					System.out.println("Se reconocio signo "+yytext());	
					return new Symbol(sym.ccorche,yychar,yyline,yytext());
				}

"-"				{
					System.out.println("Se reconocio signo "+yytext());	
					return new Symbol(sym.menos,yychar,yyline,yytext());
				}
"+"				{
					System.out.println("Se reconocio signo "+yytext());	
					return new Symbol(sym.mas,yychar,yyline,yytext());
				}
"*"				{
					System.out.println("Se reconocio signo "+yytext());	
					return new Symbol(sym.por,yychar,yyline,yytext());
				}
"/"				{
					System.out.println("Se reconocio signo "+yytext());	
					return new Symbol(sym.div,yychar,yyline,yytext());
				}
":"				{
					System.out.println("Se reconocio signo "+yytext());	
					return new Symbol(sym.dpuntos,yychar,yyline,yytext());
				}
"="             { 
					System.out.println("Se reconocio signo "+yytext());	
					return new Symbol(sym.igual,yychar,yyline,yytext());
				}
[";"]           { 
					System.out.println("Se reconocio signo "+yytext());	
					return new Symbol(sym.pcoma,yychar,yyline,yytext());
				}
[","]           { 
					System.out.println("Se reconocio signo "+yytext());	
					return new Symbol(sym.coma,yychar,yyline,yytext());
				}
["("]           { 
					System.out.println("Se reconocio signo "+yytext());	
					return new Symbol(sym.aparen,yychar,yyline,yytext());
				}
[")"]           { 
					System.out.println("Se reconocio signo "+yytext());	
					return new Symbol(sym.cparen,yychar,yyline,yytext());
				}
"true"          { 
					System.out.println("Se reconocio signo "+yytext());	
					return new Symbol(sym.rtrue,yychar,yyline,yytext());
				}
"false"         { 
					System.out.println("Se reconocio signo "+yytext());	
					return new Symbol(sym.rfalse,yychar,yyline,yytext());
				}
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
{Dec}			{ 
					System.out.println("Se reconocio decimal "+yytext());	
					return new Symbol(sym.decimal,yychar,yyline, yytext());
				}	

{Comentario}    { /* Se ignoran */}
{Comentario2}   { /* Se ignoran */}

[ \t\r\f\n]+    { /* Se ignoran */ yychar = 0;}
.               { System.out.println("Error lexico: "+yytext());
errores.agregarerror(new ER(yyline,yycolumn, yytext(), 1, ""
                                + "Caracter no conocido"));}
