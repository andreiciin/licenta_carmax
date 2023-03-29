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
	public void testCreateUserWithOneRole() {
		Role adminRole = entityManager.find(Role.class, 57);
		User someUser = new User("someEmail@cm.com", "somePassword", "Some", "User");
		someUser.addRole(adminRole);
		User savedUser = repo.save(someUser);
		assertThat(savedUser.getId()).isGreaterThan(0);
	}

	@Test
	public void testCreateUserWithTwoRoles() {
		Role roleEditor = new Role(58);
		Role roleAssistant = new Role(59);
		User firstUser = new User("mrFirst@yahoo.com", "firstsPassword", "Mr", "First");
		firstUser.addRole(roleEditor);
		firstUser.addRole(roleAssistant);
		User savedUser = repo.save(firstUser);
		assertThat(savedUser.getId()).isGreaterThan(0);
	}

	@Test
	public void testListAllUsers() {
		Iterable<User> listUsers = repo.findAll();
		for (User listUser : listUsers) {
			System.out.println(listUser);
		}
	}

	@Test
	public void testGetUserById() {
		User userSelected = repo.findById(37).get();
		System.out.println(userSelected);
		assertThat(userSelected).isNotNull();
	}

	@Test
	public void testUpdateUserDetails() {
		User userSelected = repo.findById(37).get();
		userSelected.setEnabled(true);
		userSelected.setEmail("changedEmail@gmail.com");

		repo.save(userSelected);
	}

	@Test
	public void testUpdateUserRoles() {
		User userSelected = repo.findById(40).get();
		Role roleEditor = new Role(58);
		Role roleSales= new Role(59);
		userSelected.getRoles().remove(roleEditor);
		userSelected.addRole(roleSales);

		repo.save(userSelected);
	}

	@Test
	public void testDeleteUser() {
		Integer userId = 40;
		repo.deleteById(userId);
	}
}
