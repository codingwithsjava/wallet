package com.wallet1;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;




@RestController
@RequestMapping("/wallet")
public class WalletController {
	
	@Autowired
	private WalletService service;
	


	

//	private TransactionService service2;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@PostMapping("/create")
	public APIResponse<Wallet> create(@RequestBody Wallet wallet) {
		return service.create(wallet);
	}
	
	@GetMapping("/transaction/{id}")
	public List get(@PathVariable ("id") String id) {
		String TRANSACTIONS = restTemplate.exchange("http://localhost:8888/transaction/transaction/"+id,HttpMethod.GET,null,String.class).getBody();
		List list=new ArrayList<>();
		JsonParser springParser = JsonParserFactory.getJsonParser();
		Map < String, Object > map = springParser.parseMap(TRANSACTIONS);
		list.add(map);


		return list;
	}
	@PostMapping("/transaction/create")
	public String put(@RequestBody Transaction wallet) {
	
	    return "data create succes";
	}
	@GetMapping("/{id}")
	public APIResponse<Optional<Wallet>> getwallet(@PathVariable ("id") String id) {
		return service.get(id);
	}
	//senderWalletId

	@GetMapping("/all")
	public APIResponse<List<Wallet>> getAll(@RequestBody Wallet wallet){
		 return service.getAll(wallet);
	}
	   @PostMapping("/deposit")
	    public ResponseEntity<APIResponse<Wallet>> deposit(@RequestParam String senderWalletId, @RequestParam BigDecimal amount,@RequestParam String receiverWalletId,@RequestParam  String currency ) {
	        APIResponse<Wallet> wallet= service.deposit(senderWalletId, amount,receiverWalletId,currency);
	        
	        return ResponseEntity.ok(wallet);
	    }
	   @PostMapping("/todeposit")
	    public ResponseEntity<APIResponse<Wallet>> todeposit(@RequestParam String senderWalletId, @RequestParam BigDecimal amount,@RequestParam String receiverWalletId,@RequestParam  String currency) {
	        APIResponse<Wallet> wallet= service.todeposit(senderWalletId, amount,receiverWalletId,currency);
	        return ResponseEntity.ok(wallet);
	    }

	   @PostMapping("/recharge")
	   public ResponseEntity<APIResponse<Wallet>>recharge(@RequestParam String id, @RequestParam BigDecimal amount){
		     APIResponse<Wallet> wallet=  service.recharge(id, amount);
			return ResponseEntity.ok(wallet);
		}
	   @PostMapping("/withdraw")
	   public ResponseEntity<APIResponse<Wallet>> withdraw(@RequestParam String id, @RequestParam BigDecimal amount){
		     APIResponse<Wallet> wallet=  service.withdraw(id, amount);
			return ResponseEntity.ok(wallet);
		}
	   
	   
//		@GetMapping("/transaction/{id}")
//		public Optional<Transaction> gettransaction(@PathVariable ("id") String id) {
//			return service2.gettransaction(id);
//		}
	   
	   
//		@GetMapping("/transaction")
//		public List<Transaction> getAll(@RequestBody Transaction transaction){
//			 return service2.getAll(transaction);
//		}
//	   @PostMapping("/withdraw")
//	    public ResponseEntity<String> withdraw(@RequestParam String id, @RequestParam BigDecimal amount) {
//	        // Validate input
//	        
//		   try {
//			   Transaction transaction=new Transaction();
//			   transaction.setTransactionid(id);
//			   transaction.setAmount(amount);
//			   transaction.setDescription("money withdraw succesfully");
//			   transaction.setTransactiondate(LocalDateTime.now());
//	            service.withdraw(id, amount);
//	            return ResponseEntity.ok("money withdraw successfully");
//	        } catch (InsufficientBalanceException e) {
//	            return ResponseEntity.badRequest().body("Insufficient balance");
//	        } catch (UserNotFoundException e) {
//	            return ResponseEntity.badRequest().body("User not found");
//	        }
//	    }
	    }


