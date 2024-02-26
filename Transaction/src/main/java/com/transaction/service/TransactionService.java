package com.transaction.service;

import java.util.List;
import java.util.Optional;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.transaction.config.MQConfig;
import com.transaction.entity.Transaction;
import com.transaction.repository.TransactionRepo;
import com.transaction.response.APIResponse;

@Service
public class TransactionService {
	
	@Autowired
	private TransactionRepo transactionRepo;
	
	public Transaction create(Transaction transaction) {
		return transactionRepo.save(transaction);
	}
	
	
	public APIResponse<List<Transaction>> getAll(Transaction transaction) {
		 APIResponse  apiResponse= new APIResponse(200,transactionRepo.findAll());

		return apiResponse;
	}
	
	public APIResponse<Transaction> gettrasaction(String transactionid){
		
 APIResponse  apiResponse= new APIResponse(200,transactionRepo.findBytransactionid(transactionid));

 //transactionRepo.findBytransactionid(transactionid);
		return apiResponse;
	}
	
	

}
