funcion aceptarnotificaciones(){
	Mensaje("Felicidades a aceptado notificaciones, revise su correo proximante");
}

funcion infomazda(){
	Mensaje("Rojo Platinado, perfecto para la noche");	
}

funcion infoMB(){
	Mensaje("Rapido y con mucho estilo");
}

funcion infotoyota(){
	Mensaje("Para sentirse protegido, no hay nada como mi toyota");
}


Dimv correlativo : 1;
funcion Enviar(){
	Mensaje("Mensaje NO." + correlativo +" enviado correctamente");
}

funcion activarConexion(){
	dimv i;
	para (i:0;i<101;++){
		si(i%10==0){
			Mensaje("Cargando" + i+"%");	
		}
	}
	Enviar();
	'aceptarnotificaciones();
	correlativo : correlativo + 1;
}




dimv totalcarros : 5;
dimv contadorcarro:0;
dimv monto:0;
dimv vectorPrecios:{0,0,0,0,0};
'Esta variable debe marcar error cada vez que se usa
dimv carrosdisponibles:5;
dimv montodisponible;

funcion monto1(){
	montodisponible: 250000;  

	Mensaje("Tiene disponible" + montodisponible);
}
funcion monto2(){
	montodisponible:400000;Mensaje("Tiene disponible" + montodisponible);
}


funcion validarprecio(disponible){
	si(disponible < montodisponible){		
		retornar "true";
	}sino{
		retornar "false";
	}
}

funcion validartotalAutos(disponible){
	si(disponible < totalcarros){		
		retornar "true";
	}sino{
		retornar "false";
	}
}

funcion buy_mazda(){
	dimv precio : 25000;		
	si (validarprecio(montodisponible - 25000) == "true" && validartotalAutos(totalcarros - 1)== "true"){						
		montodisponible: montodisponible - 25000;
		vectorPrecios{contadorcarro}: precio;		
		contadorcarro : contadorcarro + 1 ;		
		carrosdisponibles : carrosdisponibles - 1;		
		monto : monto + precio;		
		Mensaje("Compraste un Mazda");
		Mensaje("Unicamente le quedan Q"+montodisponible);
	}sino{
	Mensaje("Unicamente le quedan Q"+montodisponible);
	}
	imprimir("hola");
	Dimv ho = var;
}
funcion buy_toyota(){
	dimv precio : 39000;		
	si (validarprecio(montodisponible - precio)== "true" && validartotalAutos(totalcarros - 1)== "true"){
		montodisponible: montodisponible - 39000;
		vectorPrecios{contadorcarro}: precio;
		contadorcarro : contadorcarro + 1 ;
		carrosdisponibles : carrosdisponibles - 1;
		monto : monto + precio;
		Mensaje("Compraste un Toyota");
		Mensaje("Unicamente le quedan Q"+montodisponible);
	}sino{
	Mensaje("Unicamente le quedan Q"+montodisponible);
	}
}
funcion buy_ford(){
	dimv precio : 55000;	
	si (validarprecio(montodisponible - precio)=="true" && validartotalAutos(totalcarros - 1)=="true"){
		montodisponible: montodisponible - precio;
		vectorPrecios{contadorcarro}: precio;
		contadorcarro : contadorcarro + 1 ;
		carrosdisponibles : carrosdisponibles - 1;
		monto : monto + precio;
		Mensaje("Compraste un Ford");
		Mensaje("Unicamente le quedan Q"+montodisponible);
	}sino{
	Mensaje("Unicamente le quedan Q"+montodisponible);
	}
}
funcion btn_meches(){
	dimv precio : 54000;	
	si (validarprecio(montodisponible - 54000)=="true" && validartotalAutos(totalcarros - 1)=="true"){
		montodisponible: montodisponible - precio;
		vectorPrecios{contadorcarro}: precio;
		contadorcarro : contadorcarro + 1;
		carrosdisponibles : carrosdisponibles - 1;
		monto : monto + precio;
		Mensaje("Compraste un Mercedes Benz");
		Mensaje("Unicamente le quedan Q"+montodisponible);
		imprimir("hola");
	}sino{
	Mensaje("Unicamente le quedan Q"+montodisponible);
	}
}

funcion ordenarburbuja(){
	dimv i;
	dimv j;
	dimv aux;
	para(i : 0;i< vectorPrecios.conteo-1;++){		
        para(j :0;j<vectorPrecios.conteo - 1;++){        	
            si(vectorPrecios{j} > vectorPrecios{j+1}){            	
                 aux : vectorPrecios{j};
                 vectorPrecios{j} : vectorPrecios{j+1};
                 vectorPrecios{j+1} :aux;                 
            }
        }
    }     
    mensaje(vectorPrecios{0});
    mensaje(vectorPrecios{1});
    mensaje(vectorPrecios{2});
    mensaje(vectorPrecios{3});
    mensaje(vectorPrecios{4});    
}
