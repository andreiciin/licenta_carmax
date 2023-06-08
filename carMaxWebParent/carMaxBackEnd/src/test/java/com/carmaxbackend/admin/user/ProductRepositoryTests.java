package com.carmaxbackend.admin.user;

import com.carmax.common.entity.Brand;
import com.carmax.common.entity.Category;
import com.carmax.common.entity.Product;
import com.carmaxbackend.admin.product.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class ProductRepositoryTests {

	@Autowired
	private ProductRepository repo;

	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void testCreateProduct() {
//		Brand brand = entityManager.find(Brand.class, 10);
//		Category category = entityManager.find(Category.class, 21);
//
//		Product product = new Product();
//		product.setName("Hankook Ventus Prime 3");
//		product.setAlias("hankook_ventus_prime_3");
//		product.setShortDescription("Short description for Hankook tires");
//		product.setFullDescription("Full description for Hankook tires");
//
//		product.setBrand(brand);
//		product.setCategory(category);
//
//		product.setPrice(1000);
//		product.setCost(600);
//		product.setEnabled(true);
//		product.setInStock(true);
//
//		product.setCreatedTime(new Date());
//		product.setUpdatedTime(new Date());
//
//		Product savedProduct = repo.save(product);
	}

	@Test
	public void testSaveImages() {
//		Integer productId = 1;
//		Product product = repo.findById(productId).get();
//
//		product.setMainImage("mainEnkeiImage.jpg");
//		product.addExtraImage("extraImage1.png");
//		product.addExtraImage("extraImage2.png");
//		product.addExtraImage("extraImage3.png");
//
//		Product savedProduct = repo.save(product);
//
//		assertThat(savedProduct.getImages().size()).isEqualTo(3);
	}

	@Test
	public void testListAllProducts() {
		Iterable<Product> iterableProducts = repo.findAll();

		iterableProducts.forEach(System.out::println);
	}

	@Test
	public void testGetProduct() {
		Integer id = 1;
		Product product = repo.findById(id).get();
		System.out.println(product);

		assertThat(product).isNotNull();
	}
}
