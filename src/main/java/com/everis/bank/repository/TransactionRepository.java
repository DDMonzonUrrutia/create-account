package com.everis.bank.repository;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.everis.bank.domain.Transactions;

import reactor.core.publisher.Flux;


public interface TransactionRepository   extends ReactiveMongoRepository<Transactions, String>{

	@Query("{'account.client.id' : ?0 }")	
	public Flux<Transactions> ListtransactionforClient( String idClient);
	
}
