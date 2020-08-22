package com.devculi.sway.manager.service.services_impl;

import com.devculi.sway.dataaccess.entity.*;
import com.devculi.sway.dataaccess.repository.SwayUserRepository;
import com.devculi.sway.manager.service.interfaces.IUserService;
import com.devculi.sway.manager.service.security.CustomUserDetails;
import com.devculi.sway.sharedmodel.exceptions.RecordNotFoundException;
import com.devculi.sway.sharedmodel.request.UpsertUserRequest;
import com.devculi.sway.utils.PropertyUtils;
import com.devculi.sway.utils.security.Protector;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {
  @Autowired SwayUserRepository userRepository;

  @Value("${randomPasswordLength}")
  String len;

  @Override
  public SwayUser findUserByUsername(String username) {
    Optional<SwayUser> userByUsername = userRepository.getByUsername(username);
    return userByUsername.orElse(null);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public SwayUser register(UpsertUserRequest insertUserRequest) throws Exception {
    SwayUser user = new SwayUser();
    user.setUsername(insertUserRequest.getUsername());
    user.setName(insertUserRequest.getName());
    user.setAvatar(insertUserRequest.getAvatar());
    user.setDescription(insertUserRequest.getDescription());
    user.setStatus(insertUserRequest.getStatus());
    user.setRole(insertUserRequest.getRole());
    user.setType(insertUserRequest.getType());
    String saltValue = Protector.generateSalt();
    String password = Protector.encrypt(insertUserRequest.getPassword(), saltValue);
    if (password == null) {
      throw new Exception("Can't encode password");
    }
    user.setSaltValue(saltValue);
    user.setPassword(password);
    userRepository.save(user);
    return user;
  }

  @Override
  public SwayUser deleteUserByID(Long userID) throws Exception {
    Optional<SwayUser> userByID = userRepository.findById(userID);
    if (!userByID.isPresent()) {
      throw new Exception("User not exist");
    }
    SwayUser user = userByID.get();
    userRepository.delete(user);
    return user;
  }

  @Override
  public Page<SwayUser> getUserByPage(Pageable pageable) {
    return userRepository.findAll(pageable);
  }

  @Override
  public SwayUser updateUser(Long id, UpsertUserRequest upsertUserRequest) {
    SwayUser user = userRepository.findById(id).orElse(null);
    String[] nullPropertiesString = PropertyUtils.getNullPropertiesString(upsertUserRequest);
    BeanUtils.copyProperties(upsertUserRequest, user, nullPropertiesString);
    userRepository.save(user);
    return user;
  }

  @Override
  public String randomPassword() {
    return Protector.generatePassword(Integer.parseInt(len));
  }

  @Override
  public SwayUser getUserById(Long id) {
    Optional<SwayUser> byId = userRepository.findById(id);
    return byId.orElseThrow(() -> new RecordNotFoundException(SwayUser.class, "id", id.toString()));
  }

  @Override
  public SwayUser searchByUsername(String username, boolean b) {
    return userRepository.findByUsername(username);
  }

  @Override
  public SwayUser getCurrentUser() throws Exception {
    SwayUser user = getCustomUserDetailsFromContext().getUser();
    if (user == null) throw new Exception("User is null");
    return user;
  }

  @Override
  public List<SwayClass> getJoinedClasses() {
    SwayUser currentUser = null;
    try {
      currentUser = getCurrentUser();
    } catch (Exception e) {
      e.printStackTrace();
    }
    assert currentUser != null;

    Optional<SwayUser> userOptional = userRepository.getByUsername(currentUser.getUsername());
    if (userOptional.isPresent()) {
      SwayUser user = userOptional.get();
      return user.getJoinedClasses();
    }
    return new ArrayList<>();
  }

  @Override
  public List<SwayUser> searchBy(String keyword, boolean isIgnoreCase) {
    if (isIgnoreCase) {
      keyword = "%" + keyword.toLowerCase() + "%";
    } else {
      keyword = "%" + keyword + "%";
    }
    return userRepository.findByUsernameOrNameLike(keyword);
  }

  private boolean isAnonymousAuthenticationToken(Authentication authentication) {
    return authentication instanceof AnonymousAuthenticationToken;
  }

  private CustomUserDetails getCustomUserDetailsFromContext() throws Exception {
    Authentication authentication = getAuthentication();
    if (isAnonymousAuthenticationToken(authentication)) {
      throw new Exception("Anonymous User");
    }
    return (CustomUserDetails) authentication.getPrincipal();
  }

  private Authentication getAuthentication() {
    return SecurityContextHolder.getContext().getAuthentication();
  }
}
