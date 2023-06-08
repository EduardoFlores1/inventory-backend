package com.company.inventory.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;

// Con esta anotacion especificamos que esta clase es una entidad de nuestra BD
@Entity
// Con @table especificamos el nombre de nuestra tabla
@Table(name = "category")
public class Category implements Serializable{

	private static final long serialVersionUID = 842760071184800781L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Size(min = 3, max = 70, message = "Name debe contener min 3 y max 70 caractres")
	@Column(name = "name", nullable = false, length = 70)
	private String name;
	
	@Size(min = 3, max = 150, message = "Description debe contener min 3 y max 150 caracteres")
	@Column(name = "description", nullable = false, length = 150)
	private String description;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
