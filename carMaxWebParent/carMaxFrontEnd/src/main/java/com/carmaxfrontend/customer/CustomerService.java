package com.carmaxfrontend.customer;

import com.carmax.common.entity.Country;
import com.carmax.common.entity.Customer;
import com.carmaxfrontend.setting.CountryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import net.bytebuddy.utility.RandomString;
@Service
@Transactional
public class CustomerService {

	@Autowired private CountryRepository countryRepo;
	@Autowired private CustomerRepository customerRepo;
	@Autowired PasswordEncoder passwordEncoder;

	public List<Country> listAllCountries() {
		return countryRepo.findAllByOrderByNameAsc();
	}

	public boolean isEmailUnique(String email) {
		Customer customer = customerRepo.findByEmail(email);
		return customer == null;
	}

	public void registerCustomer(Customer customer) {
		encodePassword(customer);
		customer.setEnabled(false);
		customer.setCreatedTime(new Date());

		String randomCode = RandomString.make(64);
		customer.setVerificationCode(randomCode);

		customerRepo.save(customer);

	}

	private void encodePassword(Customer customer) {
		String encodedPassword = passwordEncoder.encode(customer.getPassword());
		customer.setPassword(encodedPassword);
	}

	public boolean verify(String verificationCode) {
		Customer customer = customerRepo.findByVerificationCode(verificationCode);

		if (customer == null || customer.isEnabled()) {
			return false;
		} else {
			customerRepo.enable(customer.getId());
			return true;
		}
	}
}

