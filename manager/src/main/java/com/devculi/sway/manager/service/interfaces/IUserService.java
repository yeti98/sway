package com.devculi.sway.manager.service.interfaces;

import com.devculi.sway.dataaccess.entity.SwayUser;
import com.devculi.sway.sharedmodel.request.UpsertUserRequest;
import org.springframework.stereotype.Service;

@Service
public interface IUserService {
  SwayUser findUserByUsername(String username);

  SwayUser register(UpsertUserRequest insertUserRequest) throws Exception;

  SwayUser deleteUserByID(Long userID) throws Exception;
}
