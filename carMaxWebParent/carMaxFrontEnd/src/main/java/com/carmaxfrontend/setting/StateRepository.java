package com.carmaxfrontend.setting;

import com.carmax.common.entity.Country;
import com.carmax.common.entity.State;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StateRepository extends CrudRepository<State, Integer> {

	public List<State> findByCountryOrderByNameAsc(Country country);
}
