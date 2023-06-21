package com.carmaxbackend.admin.user;

import com.carmax.common.entity.History;
import com.carmax.common.entity.Product;
import com.carmaxbackend.admin.history.HistoryRepository;
import com.carmaxbackend.admin.product.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class HistoryRepositoryTests {

	@Autowired
	private HistoryRepository repo;

	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void testCreateHistory() {
//		Product product1 = entityManager.find(Product.class, 3);
//		Product product2 = entityManager.find(Product.class, 33);
//
//		History history = new History();
//
//		history.setVehicle("Skoda Fabia");
//		history.setMileage(181329);
//		history.setService("Mecanic Iulian - Constanta");
//		history.setFullDescription("Schimbare distributie");
//
//		Date date = new GregorianCalendar(2023, Calendar.MAY, 5).getTime();
//		history.setCreatedTime(date);
//		Date updatedDate = new GregorianCalendar(2023, Calendar.JUNE, 20).getTime();
//		history.setUpdatedTime(updatedDate);
//
//		history.setEnabled(true);
//
//		Set<Product> products = new HashSet<>();
//		products.add(product1);
//		products.add(product2);
//		history.setProducts(products);
//
//		History savedHistory = repo.save(history);
	}
}
