package com.yingkui.top.yingkui.rabbit;


import java.util.Random;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory.CacheMode;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import com.rabbitmq.client.Channel;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class App 
{
	
	public static final String EXCHANGE   = "spring-boot-exchange";  
	public static final String ROUTINGKEY = "spring-boot-routingKey";  
    
	
	public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        SpringApplication.run(App.class, args);
    }
    
   
    final static String queueName = "helloQueue";

    @Bean
    public Queue helloQueue() {
        return new Queue(queueName,true);
    }
    
    @Bean
    public DirectExchange defaultExchange() {
        return new DirectExchange(EXCHANGE);
    }
    
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(helloQueue()).to(defaultExchange()).with(ROUTINGKEY);
    }
    @Bean  
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE) 
    public RabbitTemplate rabbitTemplate() {  
        RabbitTemplate template = new RabbitTemplate(connectionFactory());  
        template.setMandatory(true);
        return template;  
    }  
    @Bean  
    public ConnectionFactory connectionFactory() {  
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();  
        connectionFactory.setAddresses("10.253.0.201:5672");  
        connectionFactory.setUsername("admin");  
        connectionFactory.setPassword("admin");  
        connectionFactory.setVirtualHost("/");  
        connectionFactory.setPublisherConfirms(true); //必须要设置  
        connectionFactory.setCacheMode(CacheMode.CHANNEL);
        connectionFactory.setChannelCacheSize(100);
        return connectionFactory;  
    }  
    @Bean
    SimpleMessageListenerContainer container() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory());
//        container.setConnectionFactory(connectionFactory());
//        container.setQueueNames(queueName);
        container.setQueues(helloQueue());
        container.setExposeListenerChannel(true);
        container.setMaxConcurrentConsumers(1);
        container.setConcurrentConsumers(1);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        
        container.setMessageListener(new ChannelAwareMessageListener() {  
        	  
            public void onMessage(Message message, Channel channel) throws Exception { 
          
                byte[] body = message.getBody(); 
                Random e = new Random();
                System.out.println("接受消息 : " + new String(body));  
       		 int k =e.nextInt(50);
       		 System.out.println(message.getMessageProperties().getDeliveryTag());
       		 if(k%2==0){
       			 System.out.println("-////////////////");
       			 System.out.println("本次确认消费");
       			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
       			
       		 }else{
       			 System.out.println("+////////");
       			 System.out.println("本次确认未消费---");
       			channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
//       			
       		 }
            }  
        });  
        return container;
    }

   /* @Bean
    MessageListenerAdapter listenerAdapter(Consumer receiver) {
         return new MessageListenerAdapter(receiver,"receiveQueue");
    }*/
  /*  
    @Bean
    public Queue userQueue() {
        return new Queue("user");
    }
    
    //===============以下是验证topic Exchange的队列==========
    @Bean
    public Queue queueMessage() {
        return new Queue("topic.message");
    }

    @Bean
    public Queue queueMessages() {
        return new Queue("topic.messages");
    }*/
  //===============以上是验证topic Exchange的队列==========
    
    
    //===============以下是验证Fanout Exchange的队列==========
   /* @Bean
    public Queue AMessage() {
        return new Queue("fanout.A");
    }

    @Bean
    public Queue BMessage() {
        return new Queue("fanout.B");
    }

    @Bean
    public Queue CMessage() {
        return new Queue("fanout.C");
    }*/
    //===============以上是验证Fanout Exchange的队列==========
    

  /*  @Bean
    TopicExchange exchange() {
        return new TopicExchange("exchange");
    }
    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanoutExchange");
    }*/

    /**
     * 将队列topic.message与exchange绑定，binding_key为topic.message,就是完全匹配
     * @param queueMessage
     * @param exchange
     * @return
     */
    /*@Bean
    Binding bindingExchangeMessage(Queue queueMessage, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessage).to(exchange).with("topic.message");
    }*/

    /**
     * 将队列topic.messages与exchange绑定，binding_key为topic.#,模糊匹配
     * @param queueMessage
     * @param exchange
     * @return
     */
   /* @Bean
    Binding bindingExchangeMessages(Queue queueMessages, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessages).to(exchange).with("topic.#");
    }
    
    @Bean
    Binding bindingExchangeA(Queue AMessage,FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(AMessage).to(fanoutExchange);
    }

    @Bean
    Binding bindingExchangeB(Queue BMessage, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(BMessage).to(fanoutExchange);
    }

    @Bean
    Binding bindingExchangeC(Queue CMessage, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(CMessage).to(fanoutExchange);
    }
    */
    
    @Bean
    public Queue queue(){
    	return new Queue("my-queue");
    }
}
