package com.company.inventory.response;

import java.util.List;

import com.company.inventory.model.Category;

// Clase pojo para insertar una lista de categorias
public class CategoryResponse {
	
	// unico metodo list<Category>
	private List<Category> category;

	public List<Category> getCategory() {
		return category;
	}

	public void setCategory(List<Category> category) {
		this.category = category;
	}
}
