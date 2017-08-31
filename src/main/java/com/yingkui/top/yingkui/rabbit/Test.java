/*package com.yingkui.top.yingkui.rabbit;

import java.util.Random;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.rabbitmq.client.Channel;

@Configuration
public class Test {

	@Bean
	public ConnectionFactory connectionFactory() {  
		 CachingConnectionFactory connectionFactory = new CachingConnectionFactory();  
	        connectionFactory.setAddresses("10.253.0.201:5672");  
	        connectionFactory.setUsername("admin");  
	        connectionFactory.setPassword("admin");  
	        connectionFactory.setVirtualHost("/");  
	        connectionFactory.setPublisherConfirms(true); //必须要设置  
	        return connectionFactory;  
	}  
	
	 @Bean  
	    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)  
	    //必须是prototype类型  
	    public RabbitTemplate rabbitTemplate() {  
	        RabbitTemplate template = new RabbitTemplate(connectionFactory());  
	        return template;  
	    }  
	 @Bean  
	    public Queue queue() {  
	        return new Queue("helloQueue", true); //队列持久  
	  
	    } 
	 
	 @Bean  
	    public SimpleMessageListenerContainer messageContainer() {  
	        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory());  
	        container.setQueues(queue());  
	        container.setExposeListenerChannel(true);  
	        container.setMaxConcurrentConsumers(1);  
	        container.setConcurrentConsumers(1);  
	        container.setAcknowledgeMode(AcknowledgeMode.MANUAL); //设置确认模式手工确认  
	        container.setMessageListener(new ChannelAwareMessageListener() {  
	  
	         
				public void onMessage(Message message, Channel channel)
						throws Exception {
					// TODO Auto-generated method stub
					 byte[] body = message.getBody();  
		                System.out.println("receive msg : " + new String(body));  
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
				}  
	        });  
	        return container;  
	    }  
	
}
*/