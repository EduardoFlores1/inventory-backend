package com.company.inventory.response;

public class ProductResponseRest extends ResponseRest{
	
	ProductResponse productResponse = new ProductResponse();

	public ProductResponse getProductResponse() {
		return productResponse;
	}

	public void setProductResponse(ProductResponse productResponse) {
		this.productResponse = productResponse;
	}
}
