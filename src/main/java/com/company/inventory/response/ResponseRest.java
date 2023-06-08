package com.company.inventory.response;

import java.util.ArrayList;
import java.util.HashMap;

// Esta clase pojo almacenara la cabecera de la respuesta del servicio rest
public class ResponseRest {
	
	// Unico atributo ArrayList de tipo hashMap
	private ArrayList<HashMap<String, String>> metadata = new ArrayList<>();

	// El get no se altera
	public ArrayList<HashMap<String, String>> getMetadata() {
		return metadata;
	}

	// Se modifica para setear el tipo, el codigo y la fecha
	public void setMetadata(String type, String code, String date) {
		HashMap<String, String> map = new HashMap<String, String>();
		
		// Agregamos la cabecera y el cuerpo al hashMap creado
		map.put("type", type);
		map.put("code", code);
		map.put("date", date);
		
		// Agregamos el hashMap al ArrayList metadata
		metadata.add(map);
	}
	
	
	
}
