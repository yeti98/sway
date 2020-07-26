package com.devculi.sway.config.security;

import com.devculi.sway.manager.service.security.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  UserDetailServiceImpl userDetailServiceImpl;
  @Bean
  public JwtAuthenticationFilter jwtAuthenticationFilter() {
    return new JwtAuthenticationFilter();
  }

  @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }


  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailServiceImpl);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.headers().frameOptions().sameOrigin();
    http.formLogin()
        .loginPage("/login")
        .usernameParameter("username")
        .defaultSuccessUrl("/", true)
        .and()
        .logout()
        .permitAll()
        .logoutSuccessUrl("/login?logout");
    http.authorizeRequests()
        .antMatchers(
            "/",
            "/auth/**",
            "/login",
            "/signup",
            "/user_assets/**",
            "/webfonts/**",
            "/fonts/**",
            "/user_assets/**")
        .permitAll()
        .antMatchers("/admin/**")
        .access("hasRole('ADMIN')")
        .anyRequest()
        .authenticated();
  }
}
