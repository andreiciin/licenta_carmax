package com.carmaxbackend.admin.user;

import com.carmax.common.entity.Role;
import com.carmax.common.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class UserRepositoryTest {

	@Autowired
	private UserRepository repo;

	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void testGetUserByEmail() {
		String email = "user.parolat@gmail.com";
		User user = repo.getUserByEmail(email);
		System.out.println(user.getFirstName());
		assertThat(user).isNotNull();
	}

	@Test
	public void testCountById() {
		Integer id = 37;
		Long countById = repo.countById(id);

		assertThat(countById).isNotNull().isGreaterThan(0);
	}

	@Test
	public void testDisableUser() {
		Integer id = 37;
		repo.updateEnabledStatus(id, false);
	}
}
