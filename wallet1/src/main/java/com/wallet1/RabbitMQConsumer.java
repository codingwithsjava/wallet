//package com.wallet1;
//
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class RabbitMQConsumer {
//	
//	@Autowired
//	private TransactionRepo repo;
//	
//	@RabbitListener(queues = MQConfig.Queue)
//	public void processmsg(Transaction transaction) {
//		repo.save(transaction);
//	}
//
//}
