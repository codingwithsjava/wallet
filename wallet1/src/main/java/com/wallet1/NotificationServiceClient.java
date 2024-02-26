package com.wallet1;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;



@FeignClient(name = "TRANSACTION-SERVICE")
public interface NotificationServiceClient {
	
	@PostMapping("/transaction/create")
	public Transaction create(@RequestBody Transaction wallet) ;

	
	@PostMapping("otp/send-otp")
	public OtpResponseDto sendOtp(@RequestParam  String phoneNumber ,@RequestParam String userName,@RequestParam String message);
}