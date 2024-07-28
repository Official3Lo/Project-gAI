package com.example.springboot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@SpringBootApplication
public class GAIApp {
    @Value("${openai.key}")
    private String openAiApiKey;

    public static void main(String[] args) {
        SpringApplication.run(GAIApp.class, args);
        System.out.println("========================\nSIT© Programming Inc.\n========================");
    }

    //header of OpenAI API
    @Bean
    public RestTemplate restTemplate(){
        RestTemplate restTemplate=new RestTemplate();
        restTemplate.getInterceptors().add(((request,body,execution) -> {
            request.getHeaders().add("Authorization","Bearer "+openAiApiKey);
            return execution.execute(request, body);
        }));
        return restTemplate;
    }
    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            System.out.println("Inspecting the beans provided by SpringBoot:");
            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }

        };
    }

}


/*
{ json postman
openai- "prompt": "essay on global warming"
GAI- "question, clear": "hello"
}
 */


/*Default
package com.example.springboot;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GAIApp {

    public static void main(String[] args) {
        SpringApplication.run(GAIApp.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            System.out.println("Inspecting the beans provided by SpringBoot:");
            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }

        };
    }

}

 */