package com.devculi.sway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.devculi.sway.dataaccess.repository")
@EntityScan("com.devculi.sway.dataaccess.entity")
public class SwayApplication {

  public static void main(String[] args) {
    SpringApplication.run(SwayApplication.class, args);
  }
}
