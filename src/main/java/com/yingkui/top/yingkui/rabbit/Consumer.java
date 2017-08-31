package com.yingkui.top.yingkui.rabbit;

import java.util.Random;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;



public class Consumer  {

//	 @RabbitListener(queues = "my-queue")
	    public void receiveQueue(String text) {
	       System.out.println(text);
	    }
	    
	    
//	    @RabbitListener(queues = "helloQueue")
	    public void handleMessage(Message message,Channel channel) throws Exception {
	    	System.out.println("==============================");
	     byte[] body =	message.getBody();
		 System.out.println("接受到消息：" + new String(body));
		
		 Random e = new Random();
		 int k =e.nextInt(50);
		 System.out.println(" 结果："+k);
		 if(k%2==0){
			 System.out.println("基数");
			 channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		 }else{
			 System.out.println("偶数");
			 channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
		 }
		System.out.println("==============================");
			
	    }
	
		

//	 @RabbitListener(queues = "helloQueue")
	    public void process(String text,Channel channel) {
		 System.out.println("接受到消息：" + text);
		
		/* SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		 container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
		 container.setMessageListener(new ChannelAwareMessageListener() {
			
			public void onMessage(Message message, Channel channel) throws Exception {
				// TODO Auto-generated method stub
				 byte[] body = message.getBody();  
				 System.out.println("接受到消息：" +new String(body));
				 channel.basicAck(message.getMessageProperties().getDeliveryTag(), false); //确认消息成功消费  
			}
		});
		*/
	    }
	 
	 
}
