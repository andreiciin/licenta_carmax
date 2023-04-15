package com.carmaxbackend.admin.user;

import com.carmax.common.entity.Category;
import com.carmaxbackend.admin.category.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class CategoryRepositoryTest {

	@Autowired
	private CategoryRepository repo;

	@Test
	public void testCreateSubCategory() {
//		Category parent = new Category(1);
//		System.out.println(parent.getName());
//
//		Set<Category> children = parent.getChildren();
//		for (Category subcategory : children) {
//			System.out.println(subcategory.getName());
//		}
//
//		assertThat(children.size()).isGreaterThan(0);
	}

	@Test
	public void testListRootCategories() {
		List<Category> rootCategories = repo.findRootCategories();
		rootCategories.forEach(cat -> System.out.println(cat.getName()));
	}
}
