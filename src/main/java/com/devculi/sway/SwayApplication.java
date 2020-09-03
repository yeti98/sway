package com.devculi.sway;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.devculi.sway.dataaccess.repository")
@EntityScan("com.devculi.sway.dataaccess.entity")
public class SwayApplication implements CommandLineRunner {

  public static void main(String[] args) {
    SpringApplication.run(SwayApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {}
}
