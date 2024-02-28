	package com.transaction.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.transaction.config.MQConfig;
import com.transaction.entity.Transaction;
import com.transaction.entity.Wallet;
import com.transaction.exception1.InsufficientBalanceException;
import com.transaction.exception1.UserNotFoundException;
import com.transaction.repository.WalletRepository;
import com.transaction.response.APIResponse;
import com.transaction.service.TransactionService;
import com.transaction.service.WalletService;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
	
	@Autowired
	private WalletService service;
	
	@Autowired
	private TransactionService service2;
	
	
	@GetMapping("/get")
	public String healthcheck() {
		return "this is transaction health check up";
	}
	@GetMapping("/all")
	public ResponseEntity<List<Transaction>> getAll(@RequestBody Transaction transaction){
		
		
		 return new ResponseEntity(service2.getAll(transaction),HttpStatus.OK);
	}
	@GetMapping("/transaction/{id}")
	public ResponseEntity<Transaction> gettrasaction(@PathVariable ("id") String transactionid) {
		
		//Transaction t=service2.gettrasaction(transactionid);
		 return new ResponseEntity(service2.gettrasaction(transactionid),HttpStatus.OK);
	}
	@PostMapping("/create")
	public ResponseEntity<String> create(@RequestBody Transaction wallet) {
		System.out.println("hello this is trans");

		return new ResponseEntity( service2.deposite(wallet),HttpStatus.OK);
	}
	
	@PostMapping("/deposit")
	public ResponseEntity<String> todeposit(@RequestBody Transaction wallet) {
		System.out.println("hello this is trans");

		return new ResponseEntity( service2.todeposite(wallet),HttpStatus.OK);
	}
	
	@PostMapping("/recharge")
	public ResponseEntity<String> recharge(@RequestBody Transaction wallet) {
		System.out.println("hello this is trans");

		return new ResponseEntity( service2.recharge(wallet),HttpStatus.OK);
	}
	
	@PostMapping("/withdraw")
	public ResponseEntity<String> withdraw(@RequestBody Transaction wallet) {
		System.out.println("hello this is trans");

		return new ResponseEntity( service2.withdraw(wallet),HttpStatus.OK);
	}
	
//	@GetMapping("/{id}")
//	public Optional<Wallet> get(@PathVariable ("id") String id) {
//		return service.get(id);
//	}
	
//	@GetMapping("/all")
//	public ResponseEntity<List<Wallet>> getAll(@RequestBody Wallet wallet){
//		
//		 return new ResponseEntity(service.getAll(wallet),HttpStatus.OK);
//	}
}

//@PostMapping("/create")
//public Wallet create(@RequestBody Wallet wallet) {
//	return service.create(wallet);
//}
//	  @PostMapping("/deposit")
//	    public ResponseEntity<APIResponse<Wallet>> deposit(@RequestParam String senderWalletId, @RequestParam BigDecimal amount,@RequestParam String receiverWalletId,@RequestParam  String currency) {
//	        APIResponse<Wallet> wallet= service.deposit(senderWalletId, amount,receiverWalletId,currency);
//	        return ResponseEntity.ok(wallet);
//	    }
//	   @PostMapping("/todeposit")
//	    public ResponseEntity<APIResponse<Wallet>> todeposit(@RequestParam String senderWalletId, @RequestParam BigDecimal amount,@RequestParam String receiverWalletId,@RequestParam  String currency) {
//	        APIResponse<Wallet> wallet= service.todeposit(senderWalletId, amount,receiverWalletId,currency);
//	        return ResponseEntity.ok(wallet);
//	    }
//	   @PostMapping("/recharge")
//	    public ResponseEntity<String> recharge(@RequestParam String id, @RequestParam BigDecimal amount) {
//	      
//		return new ResponseEntity(service.recharge(id, amount), HttpStatus.OK);
//	    }
//	   @PostMapping("/withdraw")
//	    public ResponseEntity<String> withdraw(@RequestParam String id, @RequestParam BigDecimal amount) {	        
//		 
//	    		return new ResponseEntity(service.withdraw(id, amount), HttpStatus.OK);
//
//}
//	    }


