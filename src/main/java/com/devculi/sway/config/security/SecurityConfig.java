package com.devculi.sway.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Configuration
@EnableWebSecurity
@Order(1)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired private CustomAuthenticationProvider authProvider;

  @Bean
  public JwtAuthenticationFilter jwtAuthenticationFilter() {
    return new JwtAuthenticationFilter();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(authProvider).eraseCredentials(false);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    //    http.headers().frameOptions().sameOrigin();
    //    http.csrf().disable();
    http.authorizeRequests()
        .antMatchers(
            "/",
            "/auth/**",
            "/admin",
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
        .permitAll();

    http.authorizeRequests()
        .antMatchers("/admin/**")
        .access("hasAnyRole('ROLE_ADMIN')");

    http.authorizeRequests()
        .antMatchers("/info**", "/homework**")
        .authenticated()
        .and()
        .formLogin()
        .loginPage("/login")
        .successHandler(new MyAuthenticationSuccessHandler())
        .usernameParameter("username")
        .loginProcessingUrl("/login")
        .failureUrl("/login?error=true")
//        .defaultSuccessUrl("/", true)
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

    http.addFilterBefore(jwtAuthenticationFilter() , UsernamePasswordAuthenticationFilter.class);
  }

//  @Bean
//  public AuthenticationSuccessHandler refererSuccessHandler() {
//    SavedRequestAwareAuthenticationSuccessHandler handler = new SavedRequestAwareAuthenticationSuccessHandler();
//    handler.setUseReferer(true);
//    return handler;
//  }

  static class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
      super.onAuthenticationSuccess(request, response, authentication);
    }
  }
}
