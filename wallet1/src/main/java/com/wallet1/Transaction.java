package com.wallet1;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@NoArgsConstructor
@AllArgsConstructor
@Data
public class Transaction {
	
	
	private String transactionid;
	
	private String walletid;
	
	//@CreatedDate
	private LocalDateTime transactiondate;
	

	private BigDecimal amount;
	
	private String description;
	

    private String senderWalletId;
    
    private String receiverWalletId;
    
    private String currency;
    
	private TransactionType type;
	
    private TransactionStatus status;

    private ServiceType serviceType;

	 private String Referenceno;
	 

	

   // @LastModifiedDate
	private Date updatedDate;
	
//	@DBRef
//	private Wallet wallet;


	

	

}
