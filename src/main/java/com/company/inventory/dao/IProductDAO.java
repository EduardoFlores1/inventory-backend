package com.company.inventory.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.company.inventory.model.Product;

@Repository
public interface IProductDAO extends CrudRepository<Product, Long>{
	
	@Query("SELECT p FROM Product p WHERE p.name LIKE %?1%")
	List<Product> findByNameLike(String name);
	
	List<Product> findByNameContainingIgnoreCase(String name);
}
