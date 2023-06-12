package com.carmaxfrontend.category;

import com.carmax.common.entity.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, Integer> {

	@Query("SELECT c FROM Category c WHERE c.enabled = true ORDER BY c.name ASC")
	public List<Category> findAllEnabled();
}
