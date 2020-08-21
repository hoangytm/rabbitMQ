package com.javainuse;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.amqp.core.Message;

import static com.javainuse.producer.SendMessageController.X_TENANT_CODE;


@SpringBootApplication
public class EmployeeRegistrationApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeRegistrationApplication.class, args);
    }


    @Bean(name = "rabbitListenerContainerFactory")
    public SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory(
            SimpleRabbitListenerContainerFactoryConfigurer configurer,
            ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        factory.setAdviceChain(new MDCAdvice());
        return factory;
    }

    public static class MDCAdvice implements MethodInterceptor {

        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            // pre process
            System.out.println("I'm coming 1st");
            Message message = (Message) invocation.getArguments()[1];
            String tenantCode = message.getMessageProperties().getHeaders().get(X_TENANT_CODE).toString();
            System.out.println("please set tenant code "+tenantCode);
            try {
                return invocation.proceed();
            } finally {
                // post process
            }
        }

    }
}
