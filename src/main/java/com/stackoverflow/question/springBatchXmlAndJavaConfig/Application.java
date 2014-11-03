package com.stackoverflow.question.springBatchXmlAndJavaConfig;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableBatchProcessing
public class Application {

  public static void main(String[] args) {
    System.setProperty("spring.batch.job.enabled", "false");
    SpringApplication.run(Application.class, args);
  }
}
