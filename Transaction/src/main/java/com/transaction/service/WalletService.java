package com.transaction.service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.transaction.entity.ServiceType;
import com.transaction.entity.Transaction;
import com.transaction.entity.TransactionStatus;
import com.transaction.entity.TransactionType;
import com.transaction.entity.Wallet;
import com.transaction.exception1.InsufficientBalanceException;
import com.transaction.exception1.UserNotFoundException;
import com.transaction.repository.TransactionRepo;
import com.transaction.repository.WalletRepository;
import com.transaction.response.APIResponse;




@Service
public class WalletService {
	
	@Autowired
	private WalletRepository repository;

	
	
	@Autowired
	private TransactionRepo repo;
	APIResponse apiResponse =new APIResponse();
	
	 public APIResponse<Wallet> deposit(String senderWalletId, BigDecimal amount,String receiverWalletId,String currency) {
	        Optional<Wallet> optionalWallet = repository.findBysenderWalletId(senderWalletId);
	        if (optionalWallet.isPresent()) {
	            Wallet wallet = optionalWallet.get();
	            wallet.setBalance(wallet.getBalance().add(amount));
	            repository.save(wallet);
	      
	            Transaction transaction = new Transaction();
	            transaction.setWalletid(senderWalletId);
	            transaction.setAmount(amount);
	            transaction.setSenderWalletId(senderWalletId);
	            transaction.setReceiverWalletId(receiverWalletId);
	            transaction.setTransactiondate(LocalDateTime.now());
	            transaction.setDescription("money deposit success");
	            transaction.setCurrency(currency);
	            transaction.setStatus(TransactionStatus.SUCCESS);
	            transaction.setType(TransactionType.WALLET);
	            transaction.setServiceType(ServiceType.WALLET_DEPOSIT);
	            transaction.setReferenceno(UUID.randomUUID().toString());
	            repo.save(transaction);
	            apiResponse=new  APIResponse(200,transaction);
	        } else {
	            apiResponse=new  APIResponse(200,"not deposit");

	           return apiResponse;
	    }
	        return apiResponse;
	}
	 public APIResponse<Wallet> todeposit(String senderWalletId, BigDecimal amount,String receiverWalletId,String currency) {
	        Optional<Wallet> optionalWallet = repository.findBysenderWalletId(senderWalletId);
	        Optional<Wallet> optionalWallet1 = repository.findByreceiverWalletId(receiverWalletId);

	        if (optionalWallet.isPresent()&&optionalWallet1.isPresent()) {
	            Wallet wallet = optionalWallet.get();
	            Wallet wallet1 = optionalWallet1.get();

	            wallet.setBalance(wallet.getBalance().subtract(amount));
	            wallet1.setBalance(wallet1.getBalance().add(amount));

	            
	            repository.save(wallet);
	            repository.save(wallet1);
	      
	            Transaction transaction = new Transaction();
	            transaction.setWalletid(senderWalletId);
	            transaction.setAmount(amount);
	            transaction.setCurrency(currency);
	            transaction.setSenderWalletId(senderWalletId);
	            transaction.setReceiverWalletId(receiverWalletId);
	            transaction.setTransactiondate(LocalDateTime.now());
	            transaction.setDescription("money deposit success");
	            transaction.setStatus(TransactionStatus.SUCCESS);
	            transaction.setType(TransactionType.WALLET);
	            transaction.setServiceType(ServiceType.WALLET_DEPOSIT);
	            transaction.setReferenceno(UUID.randomUUID().toString());
	            	            repo.save(transaction);
	            apiResponse=new  APIResponse(200,transaction);
	        } else {
	            apiResponse=new  APIResponse(409,"not deposit");

	           return apiResponse;
	    }
	        return apiResponse;
	}


	public Wallet create(Wallet wallet) {
	
		return repository.save(wallet);
	}
		
	public APIResponse<List<Wallet>> getAll(Wallet wallet) {
	 APIResponse  apiResponse= new APIResponse(200,repository.findAll());

	return apiResponse;
	}
	
	public Optional<Wallet> get(String id){
		return repository.findById(id);
	}
	//@Transactional
	public APIResponse recharge(String id, BigDecimal amount) {
		Transaction wallet1 = new  Transaction();
		 Wallet wallet = repository.findById(id)
	                .orElseThrow(() -> new UserNotFoundException("User not found"));

	        BigDecimal currentBalance = wallet.getBalance();
	        if (currentBalance.compareTo(amount) < 0) {
	            throw new InsufficientBalanceException("Insufficient balance");
	        }

	     
	        BigDecimal updatedBalance = currentBalance.subtract(amount);
	        wallet.setBalance(updatedBalance);
	        wallet1.setAmount(amount);
	        wallet1.setDescription("mobile recharge success");
	        wallet1.setTransactiondate(LocalDateTime.now());
	        wallet1.setUpdatedDate(new Date());
	        wallet1.setWalletid(wallet.getSenderWalletId());
	        wallet1.setStatus(TransactionStatus.SUCCESS);
	        wallet1.setType(TransactionType.WALLET);
	        wallet1.setServiceType(ServiceType.MOBILE_RECHARGE);
	        wallet1.setReferenceno(UUID.randomUUID().toString());
	        wallet1=repo.save(wallet1);
	        repository.save(wallet);
	        APIResponse  apiResponse= new APIResponse(200,wallet1);
	        return  apiResponse;
 
	    }
	
	public APIResponse withdraw(String id, BigDecimal amount) {
		Transaction wallet1 = new  Transaction();
		 Wallet wallet = repository.findById(id)
	                .orElseThrow(() -> new UserNotFoundException("User not found"));

	        BigDecimal currentBalance = wallet.getBalance();
	        if (currentBalance.compareTo(amount) < 0) {
	            throw new InsufficientBalanceException("Insufficient balance");
	        }
	     try {
	        BigDecimal updatedBalance = currentBalance.subtract(amount);
	        wallet.setBalance(updatedBalance);
	        wallet1.setAmount(amount);
	        wallet1.setDescription("money withdraw success");
	        wallet1.setTransactiondate(LocalDateTime.now());
	        wallet1.setWalletid(wallet.getSenderWalletId());
	        wallet1.setStatus(TransactionStatus.SUCCESS);
	        wallet1.setType(TransactionType.WALLET);
	        wallet1.setServiceType(ServiceType.WALLET_WITHDRAW);
	        wallet1.setReferenceno(UUID.randomUUID().toString());
	        wallet1=repo.save(wallet1);
	        repository.save(wallet);
	        APIResponse  apiResponse= new APIResponse(200,wallet1);

            return apiResponse;

	       } catch (InsufficientBalanceException e) {
	         APIResponse apiResponse = new APIResponse();
	         apiResponse.setSuccess(400);
	         apiResponse.setData("Insufficient balance");
	         return apiResponse;
	     } catch (UserNotFoundException e) {
	         APIResponse apiResponse = new APIResponse();
         apiResponse.setSuccess(400);
	         apiResponse.setData("User not found");
	         return apiResponse;
	     }
	    }
		// TODO Auto-generated method stub
		
	}
