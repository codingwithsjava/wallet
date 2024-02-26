package com.wallet1;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends MongoRepository<Wallet, String> {

	Optional<Wallet> findBysenderWalletId(String senderWalletId);

	Optional<Wallet> findByreceiverWalletId(String receiverWalletId);

}
