package Gramaticas.CJS;
import Errores.ListaErrores;
import java_cup.runtime.Symbol;
import Errores.ER;
%%
Comentario      =["\'"][/] [^*]+ [/]"\'"
Comentario2     =["\'"] [^\n]* [\n]

digito = [0-9]
Numero= {digito}+
letra =[a-zñA-ZÑ]
iden ={letra}+({letra}|{Numero}|"_")*
Dec ={Numero}"."({Numero})+
chain = "\""(.[^"\""]*)"\"" 
//%state COMENT_MULTI

%cupsym sym
%class AnalisisLex_CJS
%cup
%public
%unicode
%public
%ignorecase
%line
%column
%char
%{

ListaErrores errores = ListaErrores.getListaerror();
%}
%%

"dimv"			{
					System.out.println("Se reconocio reservada "+yytext());	
					return new Symbol(sym.rdimv,yychar,yyline,yytext());
				}
"setelemento"	{
					System.out.println("Se reconocio reservada "+yytext());	
					return new Symbol(sym.setelemento,yychar,yyline,yytext());
				}
"obtener"		{
					System.out.println("Se reconocio reservada "+yytext());	
					return new Symbol(sym.obtener,yychar,yyline,yytext());
				}
"observador"	{
					System.out.println("Se reconocio reservada "+yytext());	
					return new Symbol(sym.observador,yychar,yyline,yytext());
				}
"documento"		{
					System.out.println("Se reconocio reservada "+yytext());	
					return new Symbol(sym.documento,yychar,yyline,yytext());
				}
"mensaje"		{
					System.out.println("Se reconocio reservada "+yytext());	
					return new Symbol(sym.mensaje,yychar,yyline,yytext());
				}
"retornar"		{
					System.out.println("Se reconocio reservada "+yytext());	
					return new Symbol(sym.retornar,yychar,yyline,yytext());
				}
"funcion"		{
					System.out.println("Se reconocio reservada "+yytext());	
					return new Symbol(sym.funcion,yychar,yyline,yytext());
				}
"imprimir"		{
					System.out.println("Se reconocio etiqueta "+yytext());	
					return new Symbol(sym.imprimir,yychar,yyline,yytext());
				}
"detener"		{
					System.out.println("Se reconocio etiqueta "+yytext());	
					return new Symbol(sym.detener,yychar,yyline,yytext());
				}
"mientras"		{
					System.out.println("Se reconocio reservada "+yytext());	
					return new Symbol(sym.mientras,yychar,yyline,yytext());
				}
"para"			{
					System.out.println("Se reconocio reservada "+yytext());	
					return new Symbol(sym.para,yychar,yyline,yytext());
				}
"defecto"		{
					System.out.println("Se reconocio reservada "+yytext());	
					return new Symbol(sym.defecto,yychar,yyline,yytext());
				}
"caso"			{
					System.out.println("Se reconocio reservada "+yytext());	
					return new Symbol(sym.caso,yychar,yyline,yytext());
				}
"selecciona"	{
					System.out.println("Se reconocio reservada "+yytext());	
					return new Symbol(sym.selecciona,yychar,yyline,yytext());
				}
"sino"			{
					System.out.println("Se reconocio reservada "+yytext());	
					return new Symbol(sym.sino,yychar,yyline,yytext());
				}
"si"		{
					System.out.println("Se reconocio reservada "+yytext());	
					return new Symbol(sym.si,yychar,yyline,yytext());
				}
"atexto"		{
					System.out.println("Se reconocio reservada "+yytext());	
					return new Symbol(sym.atexto,yychar,yyline,yytext());
				}
"conteo"		{
					System.out.println("Se reconocio reservada "+yytext());	
					return new Symbol(sym.rconteo,yychar,yyline,yytext());
				}
"listo"			{
					System.out.println("Se reconocio reservada "+yytext());	
					return new Symbol(sym.rlisto,yychar,yyline,yytext());
				}
"modificado"	{
					System.out.println("Se reconocio reservada "+yytext());	
					return new Symbol(sym.rmodificado,yychar,yyline,yytext());
				}
"cliqueado"		{
					System.out.println("Se reconocio reservada "+yytext());	
					return new Symbol(sym.rcliqueado,yychar,yyline,yytext());
				}



"&&"			{
					System.out.println("Se reconocio signo "+yytext());	
					return new Symbol(sym.and,yychar,yyline,yytext());
				}
"||"			{
					System.out.println("Se reconocio signo "+yytext());	
					return new Symbol(sym.or,yychar,yyline,yytext());
				}
"=="			{
					System.out.println("Se reconocio signo "+yytext());	
					return new Symbol(sym.igualacion,yychar,yyline,yytext());
				}
"<="			{
					System.out.println("Se reconocio signo "+yytext());	
					return new Symbol(sym.menorigual,yychar,yyline,yytext());
				}
">="			{
					System.out.println("Se reconocio signo "+yytext());	
					return new Symbol(sym.mayorigual,yychar,yyline,yytext());
				}	
"!="			{
					System.out.println("Se reconocio signo "+yytext());	
					return new Symbol(sym.diferente,yychar,yyline,yytext());
				}										
"!"				{
					System.out.println("Se reconocio signo "+yytext());	
					return new Symbol(sym.not,yychar,yyline,yytext());
				}
"%"				{
					System.out.println("Se reconocio signo "+yytext());	
					return new Symbol(sym.mod,yychar,yyline,yytext());
				}
"^"				{
					System.out.println("Se reconocio signo "+yytext());	
					return new Symbol(sym.pot,yychar,yyline,yytext());
				}
"/"				{
					System.out.println("Se reconocio signo "+yytext());	
					return new Symbol(sym.div,yychar,yyline,yytext());
				}
"*"				{
					System.out.println("Se reconocio signo "+yytext());	
					return new Symbol(sym.por,yychar,yyline,yytext());
				}
"+"				{
					System.out.println("Se reconocio signo "+yytext());	
					return new Symbol(sym.mas,yychar,yyline,yytext());
				}
"-"				{
					System.out.println("Se reconocio signo "+yytext());	
					return new Symbol(sym.menos,yychar,yyline,yytext());
				}
"++"			{
					System.out.println("Se reconocio signo "+yytext());	
					return new Symbol(sym.incrementar,yychar,yyline,yytext());
				}
"--"			{
					System.out.println("Se reconocio signo "+yytext());	
					return new Symbol(sym.decrementar,yychar,yyline,yytext());
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
[","]           { 
					System.out.println("Se reconocio signo "+yytext());	
					return new Symbol(sym.coma,yychar,yyline,yytext());
				}
["."]           { 
					System.out.println("Se reconocio signo "+yytext());	
					return new Symbol(sym.punto,yychar,yyline,yytext());
				}
[":"]           { 
					System.out.println("Se reconocio signo "+yytext());	
					return new Symbol(sym.dpuntos,yychar,yyline,yytext());
				}				
["("]           { 
					System.out.println("Se reconocio signo "+yytext());	
					return new Symbol(sym.aparen,yychar,yyline,yytext());
				}
[")"]           { 
					System.out.println("Se reconocio signo "+yytext());	
					return new Symbol(sym.cparen,yychar,yyline,yytext());
				}
["{"]           { 
					System.out.println("Se reconocio signo "+yytext());	
					return new Symbol(sym.allave,yychar,yyline,yytext());
				}
["}"]           { 
					System.out.println("Se reconocio signo "+yytext());	
					return new Symbol(sym.cllave,yychar,yyline,yytext());
				}				
["\'"]          { 
					System.out.println("Se reconocio signo "+yytext());	
					return new Symbol(sym.comillasimple,yychar,yyline,yytext());
				}	
["\""]          { 
					System.out.println("Se reconocio signo "+yytext());	
					return new Symbol(sym.comillas,yychar,yyline,yytext());
				}			
/*"true"          { 
					System.out.println("Se reconocio boolean "+yytext());	
					return new Symbol(sym.rtrue,yychar,yyline,yytext());
				}
"false"          { 
					System.out.println("Se reconocio boolean "+yytext());	
					return new Symbol(sym.rfalse,yychar,yyline,yytext());
				}		*/						
{Numero}        { 
					System.out.println("Se reconocio numero "+yytext());	
					return new Symbol(sym.num,yychar,yyline,yytext());
				}
{iden}			{ 
					/*String cadaux = yytext().toLowerCase().replace("\"","");
					if(cadaux.equals("false")){
						System.out.println("Se reconocio boolean "+yytext());	
						return new Symbol(sym.rtrue,yychar,yyline,yytext());
					}else if(cadaux.equals("true")){
						System.out.println("Se reconocio boolean "+yytext());	
						return new Symbol(sym.rtrue,yychar,yyline,yytext());
					}*///else{
						System.out.println("Se reconocio identificador "+yytext());	
						return new Symbol(sym.iden,yychar,yyline, new String(yytext()));	
					//}
					
				}
{chain}			{ 
					String cadaux = yytext().toLowerCase().replace("\"","");
					if(cadaux.equals("false")){
						System.out.println("Se reconocio boolean "+yytext());	
						return new Symbol(sym.rfalse,yychar,yyline,yytext());
					}else if(cadaux.equals("true")){
						System.out.println("Se reconocio boolean "+yytext());	
						return new Symbol(sym.rtrue,yychar,yyline,yytext());
					}else{
						String valor = cadaux.toString();
						String[] vec = valor.split("/");
			            if (vec.length == 3) {
			                String date = vec[vec.length - 1];
			                valor = date;
			                vec = valor.split(":");
			                if (vec.length == 3) {
			                    date = vec[vec.length - 1];
			                    if (date.length() > 2) {
			                        System.out.println("Se reconocio cadena "+yytext());	
									return new Symbol(sym.cadena,yychar,yyline, new String(yytext()));
			                    }
			                        System.out.println("Se reconocio datetime"+yytext());	
									return new Symbol(sym.dateti,yychar,yyline, new String(yytext()));
			                } else {
			                    System.out.println("Se reconocio date"+yytext());	
									return new Symbol(sym.date,yychar,yyline, new String(yytext()));
			                }
			            }
						System.out.println("Se reconocio cadena "+yytext());	
						return new Symbol(sym.cadena,yychar,yyline, new String(yytext()));
					}
				}
{Dec}			{ 
					System.out.println("Se reconocio decimal "+yytext());	
					return new Symbol(sym.decimal,yychar,yyline, yytext());
				}				

{Comentario}    { /* Se ignoran */
					/*if(yytext().toLowerCase().equals("'false'")){
						return new Symbol(sym.rfalse,yychar,yyline,yytext());
					}else if(yytext().toLowerCase().equals("'true'")){
						return new Symbol(sym.rtrue,yychar,yyline,yytext());
					}*/

				}
{Comentario2}    { /* Se ignoran */}

[ \t\r\f\n]+    { /* Se ignoran */ yychar = 0;}
.               { System.out.println("Error lexico: "+yytext());
				errores.agregarerror(new ER(yyline,yycolumn, yytext(), 1, ""
                                + "Caracter no conocido"));
				}
