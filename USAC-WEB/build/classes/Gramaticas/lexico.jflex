package cliente_gk_hk;

import java_cup.runtime.Symbol;
%%
Comentario      ="/"[*] [^*]+ [*]"/"
Comentario2     =[/][/] [^\n]* [\n]
digito = [0-9]
Numero= {digito}+
letra =[a-zñA-ZÑ]
chara = "\'" {letra} "\'"
iden ={letra}+({letra}|{Numero}|"."|"_")*
chain ="\""{letra}+({letra}|{Numero}|"."|"_"|" ")*"\""
Dec ={Numero}"."({Numero})+

%cupsym sym
%class analisis_lexico
%cup
%public
%unicode
%public
%line
%char
%ignorecase
%{
%}
%%

"\""             { return new Symbol(sym.comilla,yychar,yyline);}
"+"             { return new Symbol(sym.mas,yychar,yyline);}
"-"             { return new Symbol(sym.menos,yychar,yyline);}
"*"             { return new Symbol(sym.por,yychar,yyline);}
"/"             { return new Symbol(sym.div,yychar,yyline);}
"\'mod\'"             { return new Symbol(sym.mod,yychar,yyline);}
"\'pot\'"             { return new Symbol(sym.pot,yychar,yyline);}
"\'sqrt\'"             { return new Symbol(sym.sqrt,yychar,yyline);}
"="             { return new Symbol(sym.igual,yychar,yyline);}
[";"]             { return new Symbol(sym.comilla,yychar,yyline);}
["("]             { return new Symbol(sym.aparen,yychar,yyline);}
[")"]             { return new Symbol(sym.cparen,yychar,yyline);}
["%"]             { return new Symbol(sym.modular,yychar,yyline);}
">="             { return new Symbol(sym.mayorigual,yychar,yyline);}
"<="             { return new Symbol(sym.menorigual,yychar,yyline);}
"=="             { return new Symbol(sym.igualdad,yychar,yyline);}
["!="]             { return new Symbol(sym.diferente,yychar,yyline);}
[">"]             { return new Symbol(sym.mayor,yychar,yyline);}
["<"]             { return new Symbol(sym.menor,yychar,yyline);}
"||"             { return new Symbol(sym.or,yychar,yyline);}
"&&"             { return new Symbol(sym.and,yychar,yyline);}
{Numero}           { return new Symbol(sym.num, yychar,yyline,new String(yytext()));}
{iden}	{ return new Symbol(sym.variables, yychar,yyline,new String(yytext()));}
{chain}	{ return new Symbol(sym.palabra, yychar,yyline,new String(yytext()));}
{Dec}	{ return new Symbol(sym.decimal, yychar,yyline,new String(yytext()));}
{chara}	{ return new Symbol(sym.caracter, yychar,yyline,new String(yytext()));}
{Comentario}    { /* Se ignoran */}
{Comentario2}   { /* Se ignoran */}
[ \t\r\f\n]+    { /* Se ignoran */}
.               { System.out.println("Error lexico: "+yytext());}
