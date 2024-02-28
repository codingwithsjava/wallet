package com.transaction.config;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.transaction.entity.Transaction;
import com.transaction.repository.TransactionRepo;

@Component
public class TransactionQueueConsumer {
	
	@Autowired
	private TransactionRepo repo;
	
	@Autowired
	private RestTemplate template;
	public static boolean isRabbitMQListenerActive = true;

	// Setter method for the flag
	public void setRabbitMQListenerActive(boolean isActive) {
	    this.isRabbitMQListenerActive = isActive;
	}

	// RabbitMQ listener method
	@RabbitListener(queues = MQConfig.Queue)
	public void processTransaction(Transaction transaction) {
	    // Process the transaction in the transaction service only if the flag is set to false
	    if (isRabbitMQListenerActive) {
	        System.out.println("Received transaction from queue in transaction service: " + transaction);
	        repo.save(transaction);
	     //  isRabbitMQListenerActive=false;
	    }
	}
}
