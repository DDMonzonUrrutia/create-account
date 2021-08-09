package com.everis.bank.service;

import com.everis.bank.domain.Transactions;
import com.everis.bank.dto.OperationDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TransactionsService {


	public Flux<Transactions> ListMovements(String idClient);

	
}
