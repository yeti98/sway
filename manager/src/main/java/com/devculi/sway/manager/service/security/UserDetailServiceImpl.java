package com.devculi.sway.manager.service.security;

import com.devculi.sway.dataaccess.entity.SwayUser;
import com.devculi.sway.dataaccess.repository.SwayUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

  @Autowired
  SwayUserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) {
    Optional<SwayUser> optionalUser = userRepository.getByUsername(username);
    try {
      return optionalUser
          .map(CustomUserDetails::new)
          .orElseThrow(
              () ->
                  new Exception("Sai tk mk"));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public UserDetails loadUserByUUID(Long uuid) {
    try {
      return userRepository.findById(uuid)
          .map(CustomUserDetails::new)
              .orElseThrow(
                      () ->
                              new Exception("Sai tk mk"));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }
}
