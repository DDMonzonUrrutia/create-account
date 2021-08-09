package com.everis.bank.domain;

import org.springframework.data.annotation.Id;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor

public @Data class TypeClient {

	@Id
	private String id ;
	private String name;
	private Integer valtip ;
	public TypeClient(String name, Integer valtip) {
		super();
		this.name = name;
		this.valtip = valtip;
	}
	

	
}