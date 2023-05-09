package com.company.inventory.response;

public class CategoryResponseRest extends ResponseRest{
	
	CategoryResponse categoryResponse = new CategoryResponse();

	public CategoryResponse getCategoryResponse() {
		return categoryResponse;
	}

	public void setCategoryResponse(CategoryResponse categoryResponse) {
		this.categoryResponse = categoryResponse;
	}
}
