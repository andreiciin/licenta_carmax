package com.carmaxfrontend.customer;

import com.carmax.common.entity.Country;
import com.carmax.common.entity.Customer;
import com.carmaxfrontend.setting.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

	@Autowired
	private CountryRepository countryRepo;
	@Autowired private CustomerRepository customerRepo;

	public List<Country> listAllCountries() {
		return countryRepo.findAllByOrderByNameAsc();
	}

	public boolean isEmailUnique(String email) {
		Customer customer = customerRepo.findByEmail(email);
		return customer == null;
	}
}
