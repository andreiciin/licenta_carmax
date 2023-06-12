package com.carmaxfrontend;

import com.carmax.common.entity.Category;
import com.carmaxfrontend.category.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CategoryTest {
//	@Autowired
//	private CategoryRepository repo;
//
//	@Test
//	public void testListEnabledCategories() {
//		List<Category> categories = repo.findAllEnabled();
//		categories.forEach(category -> {
//			System.out.println(category.getName() + " (" + category.isEnabled() + ")");
//		});
//	}
}
