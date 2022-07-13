package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.entity.Address;
import com.example.demo.entity.Company;
import com.example.demo.entity.Employee;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.CompanyRepository;
import com.example.demo.repository.EmployeeRepository;

@SpringBootTest
class Internship2022ApplicationTests {
	
	final AddressRepository addressRepository;
	
	final EmployeeRepository employeeRepository;
	
	final CompanyRepository companyRepository;
	
	@Autowired
	public Internship2022ApplicationTests(AddressRepository addressRepository, EmployeeRepository employeeRepository,
			CompanyRepository companyRepository) {
		this.addressRepository = addressRepository;
		this.employeeRepository = employeeRepository;
		this.companyRepository = companyRepository;
	}

	@Test
	@Transactional
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
	
	@Test
	@Transactional
	void testOneToManyBiderectional() {
		final Address address = Address.builder()
				.country("Romania")
				.town("Timisoara")
				.street("Torontalului")
				.number(69)
				.build();
		
		final Employee employee1 = Employee.builder()
				.firstName("Ion")
				.lastname("Ionescu")
				.age(45)
				.build();
		employee1.setAddress(address);
		
		final Employee employee2 = Employee.builder()
				.firstName("Ion2")
				.lastname("Ionescu2")
				.age(45)
				.build();
		employee2.setAddress(address);
		
		final Company company = Company.builder()
				.country("Romania")
				.build();
		company.addEmployee(employee1);
		company.addEmployee(employee2);
		
		companyRepository.save(company);
		
		assertThat(companyRepository.findAll().size() == 1);
		assertThat(employeeRepository.findAll().size() == 2);
		assertThat(addressRepository.findAll().size() == 1);
		
//		final Employee repoEmployee = employeeRepository.findAll().get(0);
//		final Address repoAddress = addressRepository.findAll().get(0);
//		
//		assertThat(repoEmployee.getAddress() != null);
//		assertThat(repoEmployee.getId() == employee.getId());
//		assertThat(address.getId() == repoEmployee.getAddress().getId());
//		assertThat(repoEmployee.getFirstName().equals("Ion"));
//		assertThat(repoEmployee.getLastname().equals("Ionescu"));
//		assertThat(repoEmployee.getAge() == 45);
//		
//		assertThat(repoAddress.getEmployee() != null);
//		assertThat(repoAddress.getId() == address.getId());
//		assertThat(employee.getId() == repoAddress.getEmployee().getId());
//		assertThat(repoAddress.getCountry().equals("Romania"));
//		assertThat(repoAddress.getTown().equals("Timisoara"));
//		assertThat(repoAddress.getStreet().equals("Torontalului"));
//		assertThat(repoAddress.getNumber() == 69);
	}

}
