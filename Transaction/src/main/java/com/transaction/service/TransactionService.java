package com.transaction.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.transaction.config.MQConfig;
import com.transaction.config.TransactionQueueConsumer;
import com.transaction.entity.ServiceType;
import com.transaction.entity.Transaction;
import com.transaction.entity.TransactionStatus;
import com.transaction.entity.TransactionType;
import com.transaction.repository.TransactionRepo;
import com.transaction.response.APIResponse;


@Service
public class TransactionService {
	
	@Autowired
	private TransactionRepo transactionRepo;
	
	@Autowired
	private RabbitTemplate template;
	

	
	public ResponseEntity<String> deposite(Transaction transaction) {
		try {
		     
		           // http://localhost:8090/otp/send-otp?phoneNumber=%2B917489102883&userName=shubham&message=
		            transaction.setTransactionid(transaction.getTransactionid());
		            transaction.setWalletid(transaction.getSenderWalletId());
		            transaction.setAmount(transaction.getAmount());
		            transaction.setSenderWalletId(transaction.getSenderWalletId());
		            transaction.setReceiverWalletId(transaction.getReceiverWalletId());
		            transaction.setTransactiondate( LocalDateTime.now());
		            transaction.setDescription("money deposit success");
		            transaction.setCurrency(transaction.getCurrency());
		            transaction.setStatus(TransactionStatus.SUCCESS);
		            transaction.setType(TransactionType.WALLET);
		            transaction.setServiceType(ServiceType.WALLET_DEPOSIT);
		            transaction.setUpdatedDate(new Date());
		            transaction.setReferenceno(UUID.randomUUID().toString());
		            System.out.println("data saved");
			
		            if (TransactionQueueConsumer.isRabbitMQListenerActive) {
		                // If RabbitMQ listener is active, send the transaction to RabbitMQ
		                template.convertAndSend(MQConfig.Exchange, MQConfig.ROUTING_KEY, transaction);
		                System.out.println("if");
		                TransactionQueueConsumer.isRabbitMQListenerActive=false;
		                
		            } else {
		                // If RabbitMQ listener is not active, directly save the transaction to the database
		                transactionRepo.save(transaction);
		                System.out.println("else");
		                TransactionQueueConsumer.isRabbitMQListenerActive=false;

		            }
		return new ResponseEntity<>("transaction sucess",HttpStatus.OK);
		}catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>("trnsaction errror",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	public ResponseEntity<String> withdraw(Transaction transaction) {
		try {
		     
		           // http://localhost:8090/otp/send-otp?phoneNumber=%2B917489102883&userName=shubham&message=
		            transaction.setTransactionid(transaction.getTransactionid());
		            transaction.setWalletid(transaction.getSenderWalletId());
		            transaction.setAmount(transaction.getAmount());
		            transaction.setSenderWalletId(transaction.getSenderWalletId());
		            transaction.setReceiverWalletId(transaction.getReceiverWalletId());
		            transaction.setTransactiondate( LocalDateTime.now());
		            transaction.setDescription("money withdraw success");
		            transaction.setCurrency(transaction.getCurrency());
		            transaction.setStatus(TransactionStatus.SUCCESS);
		            transaction.setType(TransactionType.WALLET);
		            transaction.setServiceType(ServiceType.WALLET_WITHDRAW);
		            transaction.setUpdatedDate(new Date());
		            transaction.setReferenceno(UUID.randomUUID().toString());
		            System.out.println("data saved");
			
		            if (TransactionQueueConsumer.isRabbitMQListenerActive) {
		                // If RabbitMQ listener is active, send the transaction to RabbitMQ
		                template.convertAndSend(MQConfig.Exchange, MQConfig.ROUTING_KEY, transaction);
		                System.out.println("if");
		                TransactionQueueConsumer.isRabbitMQListenerActive=false;

		            } else {
		                // If RabbitMQ listener is not active, directly save the transaction to the database
		                transactionRepo.save(transaction);
		                System.out.println("else");
		                TransactionQueueConsumer.isRabbitMQListenerActive=false;

		            }
		return new ResponseEntity<>("transaction sucess",HttpStatus.OK);
		}catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>("trnsaction errror",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public ResponseEntity<String> todeposite(Transaction transaction) {
		try {
		     
		           // http://localhost:8090/otp/send-otp?phoneNumber=%2B917489102883&userName=shubham&message=
		            transaction.setTransactionid(transaction.getTransactionid());
		            transaction.setWalletid(transaction.getSenderWalletId());
		            transaction.setAmount(transaction.getAmount());
		            transaction.setSenderWalletId(transaction.getSenderWalletId());
		            transaction.setReceiverWalletId(transaction.getReceiverWalletId());
		            transaction.setTransactiondate( LocalDateTime.now());
		            transaction.setDescription("money deposit success");
		            transaction.setCurrency(transaction.getCurrency());
		            transaction.setStatus(TransactionStatus.SUCCESS);
		            transaction.setType(TransactionType.WALLET);
		            transaction.setServiceType(ServiceType.WALLET_DEPOSIT);
		            transaction.setUpdatedDate(new Date());
		            transaction.setReferenceno(UUID.randomUUID().toString());
		            System.out.println("data saved");
			
		            if (TransactionQueueConsumer.isRabbitMQListenerActive) {
		                // If RabbitMQ listener is active, send the transaction to RabbitMQ
		                template.convertAndSend(MQConfig.Exchange, MQConfig.ROUTING_KEY, transaction);
		                System.out.println("if");
		                TransactionQueueConsumer.isRabbitMQListenerActive=false;

		            } else {
		                // If RabbitMQ listener is not active, directly save the transaction to the database
		                transactionRepo.save(transaction);
		                System.out.println("else");
		                TransactionQueueConsumer.isRabbitMQListenerActive=false;

		            }
		return new ResponseEntity<>("transaction sucess",HttpStatus.OK);
		}catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>("trnsaction errror",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	public ResponseEntity<String> recharge(Transaction transaction) {
		try {
		     
		           // http://localhost:8090/otp/send-otp?phoneNumber=%2B917489102883&userName=shubham&message=
			 transaction.setTransactionid(transaction.getTransactionid());
	            transaction.setWalletid(transaction.getSenderWalletId());
	            transaction.setAmount(transaction.getAmount());
	            transaction.setSenderWalletId(transaction.getSenderWalletId());
	            transaction.setReceiverWalletId(transaction.getReceiverWalletId());
	            transaction.setTransactiondate( LocalDateTime.now());
	            transaction.setDescription("mobile recharge success");
	            transaction.setCurrency(transaction.getCurrency());
	            transaction.setStatus(TransactionStatus.SUCCESS);
	            transaction.setType(TransactionType.WALLET);
	            transaction.setServiceType(ServiceType.MOBILE_RECHARGE);
	            transaction.setUpdatedDate(new Date());
	            transaction.setReferenceno(UUID.randomUUID().toString());
		            System.out.println("data saved");
			
		            if (TransactionQueueConsumer.isRabbitMQListenerActive) {
		                // If RabbitMQ listener is active, send the transaction to RabbitMQ
		                template.convertAndSend(MQConfig.Exchange, MQConfig.ROUTING_KEY, transaction);
		                System.out.println("if");
		                TransactionQueueConsumer.isRabbitMQListenerActive=false;

		            } else {
		                // If RabbitMQ listener is not active, directly save the transaction to the database
		                transactionRepo.save(transaction);
		                System.out.println("else");
		                TransactionQueueConsumer.isRabbitMQListenerActive=false;

		            }
		return new ResponseEntity<>("transaction sucess",HttpStatus.OK);
		}catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>("trnsaction errror",HttpStatus.INTERNAL_SERVER_ERROR);
		}
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
