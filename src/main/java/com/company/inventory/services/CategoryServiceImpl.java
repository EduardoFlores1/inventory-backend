package com.company.inventory.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.inventory.dao.ICategoryDAO;
import com.company.inventory.model.Category;
import com.company.inventory.response.CategoryResponseRest;

@Service
public class CategoryServiceImpl implements ICategoryService {
	
	// Inyectamos la interfaz repository de ICategoryDAO
	@Autowired
	private ICategoryDAO categoryDAO;

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<CategoryResponseRest> search() {

		// Creamos instancia de CategoryResponseRest
		CategoryResponseRest response = new CategoryResponseRest();
		
		// Controlamos toda la operacion en un bloque try catch
		try {
			
			// Guardamos las categorias posteriormente de parsear los datos de .findAll()
			List<Category> category = (List<Category>) categoryDAO.findAll();
			// Seteamos la lista en CategoryResponse desde CategoryResponseRest
			response.getCategoryResponse().setCategory(category);
			// Registramos la metadata de operacion exitosa
			response.setMetadata("Respuesta ok", "00", "Listado Exitoso");

		} catch (Exception e) {
			
			// Si no procede la operacion, se hace saber con la metadata
			response.setMetadata("Respuesta nok", "-1", "Error al consultar");
			e.getStackTrace();
			return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}
		
		// Retorna la respuesta
		return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<CategoryResponseRest> searchById(Long id) {
		
		// Creamos instancia de CategoryResponseRest
		CategoryResponseRest response = new CategoryResponseRest();
		// Creamos una lista de tipo category, a fuera del trycatch en caso se mande vacia
		List<Category> list = new ArrayList<>();

		try {
			
			// .findById() devuelve un optional
			Optional<Category> category = categoryDAO.findById(id);

			
			// Si se encuentra la categoria
			if (category.isPresent()) {
				// Se agrega la categoria y se guarda la lista en CategoryResponse
				list.add(category.get());
				response.getCategoryResponse().setCategory(list);
				// Se establece la metadata, categoria encontrada
				response.setMetadata("Respuesta ok", "00", "Categoria Encontrada");
			} else {
				// Si no se encuetra la categoria se modifica la metadata y se envia una respuesta
				response.setMetadata("Respuesta nok", "-1", "Categoria no encontrada");
				return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			
			// En caso de un error, se modifica la metadata y se manda la respuesta
			response.setMetadata("Respuesta nok", "-1", "Error al consultar por ID");
			e.getStackTrace();
			return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		// Si todo a salido bien se retorna la respuesta
		return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<CategoryResponseRest> save(Category category) {
		
		// Creamos instancia de CategoryResponseRest
		CategoryResponseRest response = new CategoryResponseRest();
		// Creamos una lista de tipo category, a fuera del trycatch en caso se mande vacia
		List<Category> list = new ArrayList<>();

		try {
			
			// Se guarda la categoria y se almacena en una variable
			Category categorySaved = categoryDAO.save(category);
			if (categorySaved != null) {
				// Si la respuesta no es nula, se guarda en la lista y se modifica la metadata
				list.add(categorySaved);
				response.getCategoryResponse().setCategory(list);
				response.setMetadata("Respuesta ok", "00", "Categoria Guardada");
			} else {
				// Si es nula, se modifica la metadata con no guardada y se retorna respuesta de mala consulta
				response.setMetadata("Respuesta nok", "-1", "Categoria no guardada");
				return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.BAD_REQUEST);
			}

		} catch (Exception e) {
			
			// Si hubo un error, se modifica la metadata con error de consulta y se retorna respuesta
			response.setMetadata("Respuesta nok", "-1", "Error al consultar por ID");
			e.getStackTrace();
			return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		// Se retorna respuesta si se guardo una categoria
		return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<CategoryResponseRest> update(Category category, Long id) {
		
		// Creamos instancia de CategoryResponseRest
		CategoryResponseRest response = new CategoryResponseRest();
		// Creamos una lista de tipo category, a fuera del trycatch en caso se mande vacia
		List<Category> list = new ArrayList<>();

		try {
			
			// Se evalua si se encontro una categoria con el id que se paso como argumento
			Optional<Category> categorySearch = categoryDAO.findById(id);

			if (categorySearch.isPresent()) {
				// Se procederá a actualizar la categoría
				categorySearch.get().setName(category.getName());
				categorySearch.get().setDescription(category.getDescription());
				
				// Se guarda la categoria y se almacena en una nueva variable la respuesta
				Category categoryToUpdate = categoryDAO.save(categorySearch.get());

				if (categoryToUpdate != null) {
					
					// Se agrega a la lista y se guarda; se modifica la metadata a enviar
					list.add(categoryToUpdate);
					response.getCategoryResponse().setCategory(list);
					response.setMetadata("Respuesta ok", "00", "Categoria Actualizada");
				} else {
					// Se modifica la data de no actualizada y se encia la respuesta
					response.setMetadata("Respuesta nok", "-1", "Categoria no actualizada");
					return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.BAD_REQUEST);
				}

			} else {
				
				// Al no encotrar el id se establece no encontrada y se retorna respuesta
				response.setMetadata("Respuesta nok", "-1", "Categoria no encontrada");
				return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			
			// Se modifica la metadata con el error y se retorna respuesta
			response.setMetadata("Respuesta nok", "-1", "Error al actualizar");
			e.getStackTrace();
			return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		// Categoria encontrada y actualizada
		return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<CategoryResponseRest> deleteById(Long id) {
		
		// Creamos instancia de CategoryResponseRest
		CategoryResponseRest response = new CategoryResponseRest();

		try {
			// Se elimina la categoria y se establece la metadata
			categoryDAO.deleteById(id);
			response.setMetadata("Respuesta ok", "00", "Registro Eliminado");

		} catch (Exception e) {
			
			// Ante un error se notifica en la metadata y se retorna respuesta
			response.setMetadata("Respuesta nok", "-1", "Error al eliminar");
			e.getStackTrace();
			return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}
		
		// Eliminacion exitosa
		return new ResponseEntity<CategoryResponseRest>(response, HttpStatus.OK);
	}

}
