package com.everis.bank.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.everis.bank.domain.AccountBank;
import com.everis.bank.dto.ReportBalance;
import com.everis.bank.service.AccountBankService;

import io.swagger.annotations.ApiOperation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/account-banks")
public class AccountBankController {

	
	@Autowired
	private AccountBankService AccountBankServ;
	

	
	@ApiOperation(value = "save a account bank", notes="save object account")
	@PostMapping
	public Mono<AccountBank> saveAccountBank(@RequestBody @Valid AccountBank accountBank) {
		return AccountBankServ.save(accountBank);
	}
	
	
	@ApiOperation(value = "Find a Account bank", notes="Find a bank by id account")
	@GetMapping("/{productId}")
	public Mono<AccountBank> findById(@PathVariable(name = "productId") String productId) {
		return AccountBankServ.findById(productId);
	}
	
	
	@ApiOperation(value = "return list all", notes="select all Bank")
	@GetMapping("/list")
	public Flux<AccountBank> ListCountBank() {
		return AccountBankServ.findAll();
	}
	
	
	@GetMapping("/reports/{clientId}")
	@ApiOperation(value = "List balance for client", notes="Generate balance by client Id ")
	public Flux<ReportBalance> reportProductsBalance(@PathVariable String clientId) {
		return AccountBankServ.reportSaldo(clientId);
	}
	
	
	
}
