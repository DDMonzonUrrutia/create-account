package com.everis.bank.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor

public  @Data  class ProductBank {
	
	private String id;
	private String description;
	private TypeProductBank typeProductBank;
	private Integer code;
	public ProductBank(String description, TypeProductBank typeProductBank, Integer code) {
		super();
		this.description = description;
		this.typeProductBank = typeProductBank;
		this.code = code;
	}
	
	
	
	
}
