package com.company.inventory.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.inventory.dao.ICategoryDAO;
import com.company.inventory.dao.IProductDAO;
import com.company.inventory.model.Category;
import com.company.inventory.model.Product;
import com.company.inventory.response.ProductResponseRest;
import com.company.inventory.util.Util;

@Service
public class ProductServiceImpl implements IProductService{

	private ICategoryDAO categoryDAO;
	private IProductDAO productDAO;
	
	public ProductServiceImpl(ICategoryDAO categoryDAO, IProductDAO productDAO) {
		super();
		this.categoryDAO = categoryDAO;
		this.productDAO = productDAO;
	}
	
	@Override
	@Transactional
	public ResponseEntity<ProductResponseRest> save(Product product, Long categoryId) {
		
		ProductResponseRest response = new ProductResponseRest();
		List<Product> list = new ArrayList<>();
		
		try {
			
			// search category to set in the product object
			Optional<Category> category = categoryDAO.findById(categoryId);
			
			if(category.isPresent()) {
				product.setCategory(category.get());
			}else {
				response.setMetadata("Respuesta nok", "-1", "Categoria no encontrada asociada al producto");
				return new ResponseEntity<ProductResponseRest>(response, HttpStatus.NOT_FOUND);
			}
			
			// save product
			Product productSaved = productDAO.save(product);
			
			if(productSaved != null) {
				list.add(productSaved);
				response.getProductResponse().setProducts(list);
				response.setMetadata("Respuesta ok", "00", "Producto guardado");
			}else {
				response.setMetadata("Respuesta nok", "-1", "Producto no guardado");
				return new ResponseEntity<ProductResponseRest>(response, HttpStatus.BAD_REQUEST);
			}
			
			
		} catch (Exception e) {
			e.getStackTrace();
			response.setMetadata("Respuesta nok", "-1", "Error al guardar producto");
			return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK);
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<ProductResponseRest> searchById(Long id) {
		
		ProductResponseRest response = new ProductResponseRest();
		List<Product> list = new ArrayList<>();
		
		try {
			
			// search product by id
			Optional<Product> product = productDAO.findById(id);
			
			if(product.isPresent()) {
				
				byte [] imageDescompressed = Util.decompressZLib(product.get().getPicture());
				product.get().setPicture(imageDescompressed);
				list.add(product.get());
				response.getProductResponse().setProducts(list);
				response.setMetadata("Respuesta ok", "00", "Producto encontrado");
				
			}else {
				response.setMetadata("Respuesta nok", "-1", "Producto no encontrado");
				return new ResponseEntity<ProductResponseRest>(response, HttpStatus.NOT_FOUND);
			}
			
			
		} catch (Exception e) {
			e.getStackTrace();
			response.setMetadata("Respuesta nok", "-1", "Error al obtener producto");
			return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK);
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<ProductResponseRest> searchByName(String name) {
		
		ProductResponseRest response = new ProductResponseRest();
		List<Product> list = new ArrayList<>();
		List<Product> listAux = new ArrayList<>();
		
		try {
			
			listAux = productDAO.findByNameLike(name);
			
			if(listAux.size() > 0) {
				
				listAux.stream().forEach((p) -> {
					byte[] imageDescompressed = Util.decompressZLib(p.getPicture());
					p.setPicture(imageDescompressed);
					list.add(p);
				});
				
				response.getProductResponse().setProducts(list);
				response.setMetadata("Respuesta ok", "00", "Productos encontrados");
				
			}else {
				response.setMetadata("Respuesta nok", "-1", "Producto no encontrado");
				return new ResponseEntity<ProductResponseRest>(response, HttpStatus.NOT_FOUND);
			}
			
			
		} catch (Exception e) {
			e.getStackTrace();
			response.setMetadata("Respuesta nok", "-1", "Error al buscar producto");
			return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<ProductResponseRest> deleteById(Long id) {
		ProductResponseRest response = new ProductResponseRest();
		
		try {
			
			// delete product by id
			productDAO.deleteById(id);
			response.setMetadata("Respuesta ok", "00", "Producto Eliminado");
			
		} catch (Exception e) {
			e.getStackTrace();
			response.setMetadata("Respuesta nok", "-1", "Error al eliminar producto");
			return new ResponseEntity<ProductResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<ProductResponseRest>(response, HttpStatus.OK);
	}

}
