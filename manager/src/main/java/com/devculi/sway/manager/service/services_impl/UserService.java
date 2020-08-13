package com.devculi.sway.manager.service.services_impl;

import com.devculi.sway.dataaccess.entity.Question;
import com.devculi.sway.dataaccess.entity.SwayUser;
import com.devculi.sway.dataaccess.repository.SwayUserRepository;
import com.devculi.sway.manager.service.interfaces.IUserService;
import com.devculi.sway.sharedmodel.request.UpsertUserRequest;
import com.devculi.sway.utils.PropertyUtils;
import com.devculi.sway.utils.security.Protector;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
