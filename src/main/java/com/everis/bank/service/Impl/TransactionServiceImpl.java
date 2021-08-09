package com.everis.bank.service.Impl;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.everis.bank.domain.Client;
import com.everis.bank.domain.Transactions;
import com.everis.bank.repository.TransactionRepository;
import com.everis.bank.service.TransactionsService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TransactionServiceImpl implements TransactionsService{
	
	TransactionRepository tranrepo ;


	@Override
	public Flux<Transactions> ListMovements(String idClient) {
		
		return WebClient.builder().baseUrl("http://localhost:8009/client/client/").build()
				.get().uri(idClient).retrieve().bodyToMono(Client.class).log().flux()
				.defaultIfEmpty(new Client())
				.flatMap(c -> {
					if(c.getId() == null) {
						return Mono.error(new InterruptedException("El cliente no existe"));
					}
					return Flux.just(c);
				})	
				.flatMap(c -> {
					return tranrepo.ListtransactionforClient(c.getId());
					
				});
	}

	
	
}
