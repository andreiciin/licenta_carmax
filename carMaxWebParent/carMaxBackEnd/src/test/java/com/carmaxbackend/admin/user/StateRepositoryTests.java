package com.carmaxbackend.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import com.carmax.common.entity.Country;
import com.carmax.common.entity.State;
import com.carmaxbackend.admin.setting.state.StateRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;


@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class StateRepositoryTests {

	@Autowired private StateRepository repo;
	@Autowired private TestEntityManager entityManager;
	
	@Test
	public void testCreateStatesInRomania() {
//		Integer countryId = 1;
//		Country country = entityManager.find(Country.class, countryId);
//
//		State state = repo.save(new State("Braila", country));
//
//		assertThat(state).isNotNull();
//		assertThat(state.getId()).isGreaterThan(0);
	}
}
