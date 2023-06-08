package com.company.inventory.response;

// Clase pojo, extendemos de ResponseRest para que muestre primero la metadata
public class CategoryResponseRest extends ResponseRest{
	
	// Instanciamos CategoryResponse para que muestre la lista despues de la metadata
	CategoryResponse categoryResponse = new CategoryResponse();

	public CategoryResponse getCategoryResponse() {
		return categoryResponse;
	}

	public void setCategoryResponse(CategoryResponse categoryResponse) {
		this.categoryResponse = categoryResponse;
	}
}
