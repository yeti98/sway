package com.devculi.sway.manager.service.services_impl;

import com.devculi.sway.dataaccess.entity.SwayUser;
import com.devculi.sway.dataaccess.repository.SwayUserRepository;
import com.devculi.sway.manager.service.interfaces.IAuthService;
import com.devculi.sway.sharedmodel.exceptions.RecordNotFoundException;
import com.devculi.sway.sharedmodel.model.AuthenticationModel;
import com.devculi.sway.sharedmodel.model.UserModel;
import com.devculi.sway.utils.security.JWTUtils;
import com.devculi.sway.utils.security.Protector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.devculi.sway.sharedmodel.enums.LoginType.NORMAL;

@Service
public class AuthService implements IAuthService {
  @Autowired SwayUserRepository userRepository;

  public AuthenticationModel login(String username, String password, String loginType) {
    Optional<SwayUser> userByUsername = userRepository.getUserByUsername(username);
    if (userByUsername.isPresent()) {
      SwayUser user = userByUsername.get();
      if (Protector.isMatch(password, user.getPassword(), user.getSaltValue())) {
        if (loginType.equals(NORMAL.getType())) {
          return createIdentity(user);
        } else {
          // TODO: continue check
        }
      }
    }
    throw new RecordNotFoundException(SwayUser.class, username);
  }

  @Override
  public AuthenticationModel createAuthenticationModelForUser(SwayUser user) {
    if (user != null) return createIdentity(user);
    return null;
  }

  private AuthenticationModel createIdentity(SwayUser user) {
    AuthenticationModel authenticationModel = new AuthenticationModel();
    authenticationModel.setUserModel(
        new UserModel(
            user.getId(),
            user.getName(),
            user.getUsername(),
            user.getAvatar(),
            user.getDescription(),
            user.getStatus(),
            user.getType()));
    authenticationModel.setToken(JWTUtils.generateToken(user.getUsername()));
    authenticationModel.setRefreshToken(JWTUtils.generateToken(user.getUsername()));
    return authenticationModel;
  }
}
