package com.everis.bank.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.everis.bank.domain.AccountBank;
import com.everis.bank.domain.Client;
import com.everis.bank.domain.ProductBank;
import com.everis.bank.dto.ReportBalance;
import com.everis.bank.repository.AccountBankRepository;
import com.everis.bank.service.AccountBankService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AccountBankServiceImpl implements AccountBankService {

	@Autowired
	AccountBankRepository accbankrep;
   //Reporta saldo
	@Override
	public Flux<ReportBalance> reportSaldo(String idCliente) {
		return WebClient.builder().baseUrl("http://localhost:8009/client/client/").build().get().uri(idCliente)
				.retrieve().bodyToMono(Client.class).log().flatMapMany(cli -> {
					return accbankrep.findByClient(cli);
				}).flatMap(clPro -> {
					return Flux.just(new ReportBalance(clPro.getNumcount(), clPro.getBalance()));
				});
	}

	// registro de cuentas bancarias
	@Override
	public Mono<AccountBank> save(AccountBank accountBank) {
		return WebClient.builder().baseUrl("http://localhost:8081/clients/").build().get()
				.uri(accountBank.getClient().getId()).retrieve().bodyToMono(Client.class).log().flatMap(cl -> {
					accountBank.setClient(cl);
					return WebClient.builder().baseUrl("http://localhost:8009/productbank/productbank/").build().get()
							.uri(accountBank.getProduct().getId()).retrieve().bodyToMono(ProductBank.class).log();
				}).flatMap(sa -> {
					accountBank.setProduct(sa);
					return ValidCant(accountBank).count();

				})

				.flatMap(count -> {
					// valida tipo Cliente Personal
					if (accountBank.getClient().getType().getValtip() == 1) {
						System.out.println("el codigo es ");
						System.out.println(count);
						if (accountBank.getProduct().getCodigo() == 1 || accountBank.getProduct().getCodigo() == 2
								|| accountBank.getProduct().getCodigo() == 3) {
							if (count > 0) {

								String error = "el cliente ya cuenta con una cuenta tipo "
										+ accountBank.getProduct().getDescription();
								return Mono.error(new InterruptedException(error));

							}
							return accbankrep.save(accountBank);
                        
						}
						String error = "el cliente debe no puede tener asignado esta cuenta "
								+ accountBank.getProduct().getDescription();
						return Mono.error(new InterruptedException(error));

						// valida tipo Cliente Empresarial
					} else if (accountBank.getClient().getType().getValtip() == 2) {
						if (accountBank.getProduct().getCodigo() == 1 || accountBank.getProduct().getCodigo() == 3) {
							String error = " no puede acceder a estas cuentas "
									+ accountBank.getProduct().getDescription();
							return Mono.error(new InterruptedException(error));

						} else if (accountBank.getProduct().getCodigo() == 2) {
							return accbankrep.save(accountBank);
						}

							// Person bank 
					} else if (accountBank.getClient().getType().getValtip() == 3) {
						if (accountBank.getProduct().getCodigo() == 4) {
							//limite de inicio de monto de 500
							if (accountBank.getBalance() > 0) {

								String error = "el cliente ya cuenta con una cuenta tipo "
										+ accountBank.getProduct().getDescription();
								return Mono.error(new InterruptedException(error));

							}
							return accbankrep.save(accountBank);

						}
						
					}		

					return accbankrep.save(accountBank);

				});
	}

	public Flux<AccountBank> ValidCant(AccountBank acc) {
		return accbankrep.findQuery(acc.getClient().getId(), acc.getClient().getType().getValtip(),
				acc.getProduct().getCodigo());
	}

	@Override
	public Flux<AccountBank> findAll() {
		return this.accbankrep.findAll();
	}

	@Override
	public Mono<AccountBank> findById(String id) {
		return this.accbankrep.findById(id);
	}

	@Override
	public Mono<Void> deletexId(String id) {
		return this.accbankrep.deleteById(id);
	}

	@Override
	public Mono<Void> delete(AccountBank accountBank) {
		return this.accbankrep.delete(accountBank);
	}

}
