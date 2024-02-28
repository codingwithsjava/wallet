package com.wallet1;

import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wallet1.InsufficientBalanceException;
import com.wallet1.Transaction;
import com.wallet1.UserNotFoundException;
import com.wallet1.Wallet;
import com.wallet1.WalletRepository;



@Service
public class WalletService {
	
	@Autowired
	private WalletRepository repository;
	
	@Autowired
	private NotificationServiceClient client;
	
	@Autowired
	private RabbitTemplate template;

	
	
//	@Autowired
//	private TransactionRepo repo;
	
	APIResponse apiResponse=new APIResponse();
	
	 public APIResponse<Wallet> deposit(String senderWalletId, BigDecimal amount,String receiverWalletId,String currency) {
		 Transaction transaction = new Transaction();
		 
	        Optional<Wallet> optionalWallet = repository.findBysenderWalletId(senderWalletId);
	        if (optionalWallet.isPresent()) {
	        	try {
	            Wallet wallet = optionalWallet.get();
	            wallet.setBalance(wallet.getBalance().add(amount));
	            repository.save(wallet);
	           // http://localhost:8090/otp/send-otp?phoneNumber=%2B917489102883&userName=shubham&message=
	            transaction.setTransactionid(transaction.getTransactionid());
	            transaction.setWalletid(senderWalletId);
	            transaction.setAmount(amount);
	            transaction.setSenderWalletId(senderWalletId);
	            transaction.setReceiverWalletId(receiverWalletId);
	            transaction.setTransactiondate( LocalDateTime.now());
	            transaction.setDescription("money deposit success");
	            transaction.setCurrency(currency);
	            transaction.setStatus(TransactionStatus.SUCCESS);
	            transaction.setType(TransactionType.WALLET);
	            transaction.setServiceType(ServiceType.WALLET_DEPOSIT);
	            transaction.setUpdatedDate(new Date());
	            transaction.setReferenceno(UUID.randomUUID().toString());
	            
	           // repo.save(transaction);
	            	
	            template.convertAndSend(MQConfig.Exchange, MQConfig.ROUTING_KEY, transaction);
	            	client.create(transaction);
	            	apiResponse=new  APIResponse(200,transaction);
	            }catch (Exception e) {
	               apiResponse=new APIResponse(409,HttpStatus.INTERNAL_SERVER_ERROR);

				}
	            return apiResponse;
	           //Transaction otp=   client.sendOtp(phoneNumber, username, transaction);
	        } else {

	            apiResponse=new  APIResponse(200,"not deposit");

	     
	    }

		//    storeTransactionInQueue( senderWalletId,  amount,receiverWalletId, currency);
          //  apiResponse=new  APIResponse(200,"not deposit");

	      //  return apiResponse;
	        return apiResponse;
	        

	
	 }

	 public APIResponse<Wallet> todeposit(String senderWalletId, BigDecimal amount,String receiverWalletId,String currency) {
	        Optional<Wallet> optionalWallet = repository.findBysenderWalletId(senderWalletId);
	        Optional<Wallet> optionalWallet1 = repository.findByreceiverWalletId(receiverWalletId);

	        if (optionalWallet.isPresent()&&optionalWallet1.isPresent()) {
	        	try {
	            Wallet wallet = optionalWallet.get();
	            Wallet wallet1 = optionalWallet1.get();

	            wallet.setBalance(wallet.getBalance().subtract(amount));
	            wallet1.setBalance(wallet1.getBalance().add(amount));

	            
	            repository.save(wallet);
	            repository.save(wallet1);
	      
	            Transaction transaction = new Transaction();
	            transaction.setTransactionid(senderWalletId);
	            transaction.setWalletid(senderWalletId);
	            transaction.setAmount(amount);
	            transaction.setSenderWalletId(senderWalletId);
	            transaction.setReceiverWalletId(receiverWalletId);
	            transaction.setTransactiondate( LocalDateTime.now());
	            transaction.setCurrency(currency);
	            transaction.setDescription("money deposit success");
	            transaction.setStatus(TransactionStatus.SUCCESS);
	            transaction.setType(TransactionType.WALLET);
	            transaction.setServiceType(ServiceType.WALLET_DEPOSIT);
	            transaction.setUpdatedDate(new Date());
	            transaction.setReferenceno(UUID.randomUUID().toString());
	            template.convertAndSend(MQConfig.Exchange, MQConfig.ROUTING_KEY, transaction);
            	client.todeposit(transaction);
            	apiResponse=new  APIResponse(200,transaction);
            }catch (Exception e) {
               apiResponse=new APIResponse(409,HttpStatus.INTERNAL_SERVER_ERROR);

			}
            return apiResponse;
           //Transaction otp=   client.sendOtp(phoneNumber, username, transaction);
        } else {

            apiResponse=new  APIResponse(200,"not deposit");

     
    }

	//    storeTransactionInQueue( senderWalletId,  amount,receiverWalletId, currency);
      //  apiResponse=new  APIResponse(200,"not deposit");

      //  return apiResponse;
        return apiResponse;
        


 }

	public APIResponse<Wallet> create(Wallet wallet) {
	
        apiResponse=new  APIResponse(200,repository.save(wallet));

		return  apiResponse;
	}
		
	public APIResponse<List<Wallet>> getAll(Wallet wallet) {
		;
        apiResponse=new  APIResponse(200,repository.findAll());

        return  apiResponse;
	}
	
	public APIResponse<Optional<Wallet>> get(String id){
		;
        apiResponse=new  APIResponse(200,repository.findById(id));

		return apiResponse;
	}
	//@Transactional
	public APIResponse<Wallet> recharge(String senderWalletId,String mobile, BigDecimal amount) {
		 Wallet wallet = repository.findBysenderWalletId(senderWalletId) .orElseThrow(() -> new UserNotFoundException("User not found"));
		  Optional<Wallet> optionalWallet = repository.findBysenderWalletId(senderWalletId);
	        if (optionalWallet.isPresent()) {
	      //  Optional<Wallet> wallet = repository.findBysenderWalletId(senderWalletId);


		 try {
		 BigDecimal currentBalance = wallet.getBalance();
	        if (currentBalance.compareTo(amount) < 0) {
	            throw new InsufficientBalanceException("Insufficient balance");
	        }
	     
	        		Transaction transaction = new  Transaction();
	        BigDecimal updatedBalance = currentBalance.subtract(amount);
	        wallet.setBalance(updatedBalance);
	        repository.save(wallet);
	        transaction.setTransactionid(transaction.getTransactionid());
            transaction.setWalletid(senderWalletId);
            transaction.setAmount(amount);
            transaction.setSenderWalletId(senderWalletId);
            transaction.setReceiverWalletId(transaction.getReceiverWalletId());
            transaction.setTransactiondate( LocalDateTime.now());
            transaction.setDescription("mobile recharge success");
            transaction.setCurrency("indian rupee");
            transaction.setStatus(TransactionStatus.SUCCESS);
            transaction.setType(TransactionType.WALLET);
            transaction.setServiceType(ServiceType.MOBILE_RECHARGE);
            transaction.setUpdatedDate(new Date());
            transaction.setReferenceno(UUID.randomUUID().toString());
            transaction.setMobile(mobile);
	    //   transaction=repo.save(transaction);
	        template.convertAndSend(MQConfig.Exchange, MQConfig.ROUTING_KEY, transaction);
        	client.recharge(transaction);
        	apiResponse=new  APIResponse(200,transaction);
        }catch (Exception e) {
           apiResponse=new APIResponse(409,HttpStatus.INTERNAL_SERVER_ERROR);

		}
         return apiResponse;
         //Transaction otp=   client.sendOtp(phoneNumber, username, transaction);
      } else {

          apiResponse=new  APIResponse(200,"not deposit");

   
  }

	//    storeTransactionInQueue( senderWalletId,  amount,receiverWalletId, currency);
    //  apiResponse=new  APIResponse(200,"not deposit");

    //  return apiResponse;
      return apiResponse;
      


} 

//    storeTransactionInQueue( senderWalletId,  amount,receiverWalletId, currency);
  //  apiResponse=new  APIResponse(200,"not deposit");

  //  return apiResponse;

	public APIResponse<Wallet>  withdraw(String senderWalletId, BigDecimal amount) {
		Transaction transaction = new  Transaction();
		 Wallet wallet = repository.findBysenderWalletId(senderWalletId) .orElseThrow(() -> new UserNotFoundException("User not found"));

		 	try {
	        BigDecimal currentBalance = wallet.getBalance();
	        if (currentBalance.compareTo(amount) < 0) {
	            throw new InsufficientBalanceException("Insufficient balance");
	        }
	     
	        BigDecimal updatedBalance = currentBalance.subtract(amount);
		//	transaction.setTransactionid(id);

	        wallet.setBalance(updatedBalance);
	        transaction.setAmount(amount);
	        transaction.setDescription("money withdraw success");
	        transaction.setTransactiondate(LocalDateTime.now());
	        transaction.setWalletid(wallet.getSenderWalletId());
	        transaction.setCurrency("indian rupee");
	        transaction.setStatus(TransactionStatus.SUCCESS);
            transaction.setType(TransactionType.WALLET);
            transaction.setServiceType(ServiceType.WALLET_WITHDRAW);
            transaction.setReferenceno(UUID.randomUUID().toString());
	       // transaction=repo.save(transaction);
	        repository.save(wallet);
	        template.convertAndSend(MQConfig.Exchange, MQConfig.ROUTING_KEY, transaction);
        	client.withdraw(transaction);
        	apiResponse=new  APIResponse(200,transaction);
        }catch (Exception e) {
           apiResponse=new APIResponse(409,HttpStatus.INTERNAL_SERVER_ERROR);

		}
        return apiResponse;
       //Transaction otp=   client.sendOtp(phoneNumber, username, transaction);
 
    
	    }
		// TODO Auto-generated method stub
		
	}
