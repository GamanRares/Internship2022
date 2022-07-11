package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.entity.Address;
import com.example.demo.entity.Employee;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.EmployeeRepository;

@SpringBootTest
class Internship2022ApplicationTests {
	
	final AddressRepository addressRepository;
	
	final EmployeeRepository employeeRepository;
	
	@Autowired
	public Internship2022ApplicationTests(AddressRepository addressRepository, EmployeeRepository employeeRepository) {
		this.addressRepository = addressRepository;
		this.employeeRepository = employeeRepository;
	}

	@Test
	void testOneToOneBiderectional() {
		final Address address = Address.builder()
				.country("Romania")
				.town("Timisoara")
				.street("Torontalului")
				.number(69)
				.build();
		
		final Employee employee = Employee.builder()
				.firstName("Ion")
				.lastname("Ionescu")
				.age(45)
				.build();
		employee.setAddress(address);
		
		employeeRepository.save(employee);
		
		assertThat(employeeRepository.findAll().size() == 1);
		assertThat(addressRepository.findAll().size() == 1);
		
		final Employee repoEmployee = employeeRepository.findAll().get(0);
		final Address repoAddress = addressRepository.findAll().get(0);
		
		assertThat(repoEmployee.getAddress() != null);
		assertThat(repoEmployee.getId() == employee.getId());
		assertThat(address.getId() == repoEmployee.getAddress().getId());
		assertThat(repoEmployee.getFirstName().equals("Ion"));
		assertThat(repoEmployee.getLastname().equals("Ionescu"));
		assertThat(repoEmployee.getAge() == 45);
		
		assertThat(repoAddress.getEmployee() != null);
		assertThat(repoAddress.getId() == address.getId());
		assertThat(employee.getId() == repoAddress.getEmployee().getId());
		assertThat(repoAddress.getCountry().equals("Romania"));
		assertThat(repoAddress.getTown().equals("Timisoara"));
		assertThat(repoAddress.getStreet().equals("Torontalului"));
		assertThat(repoAddress.getNumber() == 69);
	}

}
