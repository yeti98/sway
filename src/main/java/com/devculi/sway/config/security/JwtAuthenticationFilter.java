package com.devculi.sway.config.security;

import com.devculi.sway.dataaccess.entity.SwayUser;
import com.devculi.sway.dataaccess.repository.SwayUserRepository;
import com.devculi.sway.manager.service.security.CustomUserDetails;
import com.devculi.sway.manager.service.security.JwtService;
import com.devculi.sway.sharedmodel.exceptions.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

  @Autowired
  SwayUserRepository userRepository;

  private String getJwtFromRequest(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return "";
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    try {
      String jwt = getJwtFromRequest(request);
      if (StringUtils.hasText(jwt) && !JwtService.isTokenExpired(jwt)) {
        String username = JwtService.getUsernameFromToken(jwt);
        if (StringUtils.hasText(username)) {
          Optional<SwayUser> userOptional = userRepository.getByUsername(username);
          userOptional.ifPresent(user -> {
            CustomUserDetails userDetails = new CustomUserDetails(user);
            Objects.requireNonNull(userDetails);
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
          });
          throw new RecordNotFoundException(SwayUser.class, "username", username);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    filterChain.doFilter(request, response);
  }
}
