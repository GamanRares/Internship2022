package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import org.springframework.transaction.annotation.EnableTransactionManagement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Entity
@AllArgsConstructor
@Builder
@Data
@EnableTransactionManagement
public class Address {

	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "country")
	private String country;

	@Column(name = "town")
	private String town;

	@Column(name = "street")
	private String street;

	@Column(name = "number")
	private int number;

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	private Employee employee;

	public Address() {
	}

}
