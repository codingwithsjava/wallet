//package com.wallet1;
//
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;
//
//@Component
//public class TransactionQueueConsumer {
//
//    @RabbitListener(queues = MQConfig.Queue)
//    public void processTransaction(String transaction) {
//        // Process the transaction in the transaction service
//        System.out.println("Received transaction from queue in transaction service: " + transaction);
//    }
//}
