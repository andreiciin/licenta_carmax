package com.carmaxfrontend.setting;

import com.carmax.common.entity.Country;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CountryRepository extends CrudRepository<Country, Integer> {
	public List<Country> findAllByOrderByNameAsc();

	@Query("SELECT c FROM Country c WHERE c.code = ?1")
	public Country findByCode(String code);
}
