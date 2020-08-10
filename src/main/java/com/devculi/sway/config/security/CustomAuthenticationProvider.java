package com.devculi.sway.config.security;

import com.devculi.sway.dataaccess.entity.SwayUser;
import com.devculi.sway.dataaccess.repository.SwayUserRepository;
import com.devculi.sway.manager.service.security.CustomUserDetails;
import com.devculi.sway.utils.security.Protector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.Optional;

@Configuration
public class CustomAuthenticationProvider implements AuthenticationProvider {
  @Autowired SwayUserRepository userRepository;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {

    String username = authentication.getName();
    String password = authentication.getCredentials().toString();

    Optional<SwayUser> userByUsername = userRepository.getByUsername(username);
    if (userByUsername.isPresent()) {
      SwayUser user = userByUsername.get();
      if (!user.getStatus()) return  null; // Locked user
      if (Protector.isMatch(password, user.getPassword(), user.getSaltValue())) {
        CustomUserDetails userDetails = new CustomUserDetails(user);
        return new UsernamePasswordAuthenticationToken(
            userDetails, null, userDetails.getAuthorities());
      }
    }
    return null;
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }
}
