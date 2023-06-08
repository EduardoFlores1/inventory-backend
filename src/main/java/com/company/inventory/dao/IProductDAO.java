package com.company.inventory.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.company.inventory.model.Product;

@Repository
public interface IProductDAO extends CrudRepository<Product, Long>{

}
