package com.transaction.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.transaction.entity.Wallet;
@Repository
public interface WalletRepository extends MongoRepository<Wallet, String> {
	Optional<Wallet> findBysenderWalletId(String senderWalletId);
	
	
	

	Optional<Wallet> findByreceiverWalletId(String receiverWalletId);

}
