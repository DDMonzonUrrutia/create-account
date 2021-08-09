package com.everis.bank.service.Impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.everis.bank.domain.Transactions;
import com.everis.bank.dto.OperationDto;
import com.everis.bank.repository.AccountBankRepository;
import com.everis.bank.repository.TransactionRepository;
import com.everis.bank.service.OperationService;

import reactor.core.publisher.Mono;

@Service
public class OperationServiceImpl implements OperationService {

	@Autowired
	AccountBankRepository accrepo;
	@Autowired
	TransactionRepository tranrepo;

	@Override
	public Mono<Transactions> retirement(OperationDto dto) {
		System.out.println("2 - Retiro");
		
		return accrepo.findByNumcount(dto.getAccountOrig()).flatMap(account -> {
				
			if((account.getBalance()-dto.getAmount()) >=0) {
				account.setBalance(account.getBalance() - dto.getAmount());
				return accrepo.save(account);
				
			}

			return Mono.error(new InterruptedException(" monto del retiro excede el saldo actual"));
		}).flatMap(account -> {

			Transactions tran = new Transactions(account,dto.getAccountOrig(), account.getNumcount(), "2 ", new Date(),
					dto.getAmount());
			
			return tranrepo.save(tran);
		});
	}

	@Override
	public Mono<Transactions> deposit(OperationDto dto)   {
		
		System.out.println("1 - Deposito");
		return accrepo.findByNumcount(dto.getAccountOrig()).flatMap(account -> {
			if((account.getBalance()-dto.getAmount()) < 0) {
				return Mono.error(new InterruptedException(" monto del deposito excede el saldo"));
			}	
			account.setBalance(account.getBalance() - dto.getAmount());
			return accrepo.save(account);

			
		}).flatMap(acc ->{
		return accrepo.findByNumcount(dto.getAccountDest());
		}) 
		
		
		.flatMap(account -> {
		
			account.setBalance(account.getBalance() + dto.getAmount());
			return accrepo.save(account);

		}).flatMap(account -> {

			Transactions tran = new Transactions(account,dto.getAccountOrig(), account.getNumcount(), "1 ", new Date(),
					dto.getAmount());
		
			return tranrepo.save(tran); 
		});

	}

}
