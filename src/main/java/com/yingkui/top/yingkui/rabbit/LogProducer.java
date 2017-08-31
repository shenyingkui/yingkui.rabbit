package com.yingkui.top.yingkui.rabbit;

import java.util.Date;
import java.util.UUID;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class LogProducer implements RabbitTemplate.ConfirmCallback {

	public static final String EXCHANGE   = "spring-boot-exchange";  
	public static final String ROUTINGKEY = "spring-boot-routingKey";  
	 RabbitTemplate rabbitTemplate;
	 
	 public LogProducer(RabbitTemplate rabbitTemplate){
		  this.rabbitTemplate=rabbitTemplate;
	        this.rabbitTemplate.setConfirmCallback(this);
	 }
	 
	 @Autowired
	 AmqpTemplate amqpTemplate;
	
	/*@Scheduled(fixedDelay=3000)//每3s执行1次
	public void send(){
		 rabbitTemplate.convertAndSend("my-queue","dddd");
	}
*/
	@Scheduled(fixedDelay=10000)//每3s执行1次
	public void send2() throws Exception {
		
		final Object sendMsg = "hello1 " + new Date();
//	        System.out.println("Sender1 : " + sendMsg);
	        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString()); 
	        System.out.println("发送消息 = " + sendMsg+"//==="+ correlationData.getId());
	        this.rabbitTemplate.convertAndSend(EXCHANGE,ROUTINGKEY, sendMsg,correlationData);
	}

	public void confirm(CorrelationData correlationData, boolean arg1, String arg2) {
		 System.out.println("-------------------------------------");
		// TODO Auto-generated method stub
		if(arg1){
			System.out.println("消费成功");
		}else{
			System.out.println("消费失败");
		}
	
		 System.out.println("callbakck confirm: " + correlationData.getId());
		 System.out.println("-------------------------------------");
		 System.out.println("");
		 System.out.println("");
		 System.out.println("");
		 System.out.println("");
		 System.out.println("");
	}
	
}
