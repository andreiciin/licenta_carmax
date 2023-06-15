package com.carmaxbackend.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import com.carmax.common.entity.Country;
import com.carmaxbackend.admin.setting.country.CountryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CountryRepositoryTests {
	
	@Autowired private CountryRepository repo;
	
	@Test
	public void testCreateCountry() {
//		Country country = repo.save(new Country("Romania", "RO"));
//		assertThat(country).isNotNull();
//		assertThat(country.getId()).isGreaterThan(0);
	}
}
