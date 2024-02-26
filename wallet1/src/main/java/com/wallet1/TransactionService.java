package com.wallet1;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wallet1.Transaction;
import com.wallet1.TransactionRepo;

@Service
public class TransactionService {
	
	@Autowired
	private TransactionRepo transactionRepo;
	
	public Transaction create(Transaction transaction) {
		return transactionRepo.save(transaction);
	}
	
	public List<Transaction> getAll(Transaction transaction) {
		return transactionRepo.findAll();
	}
	
	public Optional<Transaction> gettransaction(String id){
		return transactionRepo.findById(id);
	}
	
	

}
