package com.devculi.sway.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Order(2)
@EnableWebSecurity
public class UserSecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http.authorizeRequests().antMatchers("/info/**", "/homework/**")
        .access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
        .and()
        .formLogin()
        .loginPage("/login")
        .usernameParameter("username")
        .loginProcessingUrl("/login")
        .failureUrl("/login?error=true")
        .defaultSuccessUrl("/", true)
        .and()
        .logout()
        .permitAll()
        .logoutSuccessUrl("/login?logout")
        .and()
        .logout()
        .logoutUrl("/logout")
        .logoutSuccessUrl("/login?logout=true")
        .deleteCookies("JSESSIONID")
        .and()
        .csrf()
        .disable();
  }
}
