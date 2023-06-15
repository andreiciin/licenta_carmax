package com.carmaxfrontend;

import com.carmax.common.entity.Country;
import com.carmax.common.entity.Customer;
import com.carmaxfrontend.customer.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.Date;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class CustomerTest {

	@Autowired
	private CustomerRepository repo;
	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void testCreateCustomer1() {
//		Integer countryId = 234; // USA
//		Country country = entityManager.find(Country.class, countryId);
//
//		Customer customer = new Customer();
//		customer.setCountry(country);
//		customer.setFirstName("Marian");
//		customer.setLastName("Manea");
//		customer.setPassword("password123");
//		customer.setEmail("marian.manea@gmail.com");
//		customer.setPhoneNumber("0786732109");
//		customer.setAddressLine1("Aleea Dorului 12");
//		customer.setCity("Bucuresti");
//		customer.setState("Bucuresti");
//		customer.setPostalCode("410234");
//		customer.setCreatedTime(new Date());
//
//		Customer savedCustomer = repo.save(customer);
//
//		assertThat(savedCustomer).isNotNull();
//		assertThat(savedCustomer.getId()).isGreaterThan(0);
	}
}