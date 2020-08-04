package com.devculi.sway.config.security;

import com.devculi.sway.manager.service.security.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private CustomAuthenticationProvider authProvider;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(authProvider).eraseCredentials(false);
  }


  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.headers().frameOptions().sameOrigin();
    http.csrf().disable();
    http.authorizeRequests()
        .antMatchers(
            "/",
            "/auth/**",
            "/admin/login",
            "/admin/logout",
            "/login",
            "/logout",
            "/signup",
            "/user_assets/**",
            "/webfonts/**",
            "/fonts/**",
            "/assets/**",
            "/user_assets/**",
            "/AdminLTE/**")
        .permitAll()
        .antMatchers("/info/**", "/homework/**")
        .access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')");

    http.antMatcher("/admin/**")
        .authorizeRequests()
        .anyRequest()
        .hasAnyRole("ROLE_ADMIN")
        .and()
        .formLogin()
        .loginPage("/admin/login")
        .usernameParameter("username")
        .failureUrl("/admin/login?error=true")
        .defaultSuccessUrl("/admin")
        .and()
        .logout()
        .logoutUrl("/admin/login?logout=true")
        .deleteCookies("JSESSIONID")
        .and()
        .exceptionHandling()
        .accessDeniedPage("/error");
//    http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
  }
}
