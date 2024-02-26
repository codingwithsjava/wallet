package com.transaction.entity;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
//@Document(collection = "Wallet")
public class Wallet {
	
	//@Id
	private String senderWalletId;
	
	private String receiverWalletId;
	
	private Long userId; 
	  // Foreign key to associate wallet with user
	private String currency;
	
	private BigDecimal balance;
	
//	@DBRef
//	private List<Transaction> transactions;
	
	

}
