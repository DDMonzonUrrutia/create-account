package com.everis.bank.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.everis.bank.domain.Transactions;
import com.everis.bank.dto.OperationDto;
import com.everis.bank.service.OperationService;

import io.swagger.annotations.ApiOperation;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/transaction")
public class TransactionController {

	@Autowired
	OperationService operation ;
	
	
	@ApiOperation(value = "Deposite a account", notes="increment to balance")
	@PostMapping(value = "/deposite")
	public Mono<Transactions> deposite(@RequestBody @Valid OperationDto dto){
		
		return operation.deposit(dto);
	}
	
	@ApiOperation(value = "retirement a transaction", notes="Discount from balance")
	@PostMapping(value = "/retirement")
	public Mono<Transactions> retirement(@RequestBody @Valid OperationDto dto){
		
		return operation.retirement(dto);
	}

}
