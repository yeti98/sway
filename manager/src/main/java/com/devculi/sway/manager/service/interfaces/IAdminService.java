package com.devculi.sway.manager.service.interfaces;

import com.devculi.sway.dataaccess.entity.SwayClass;
import com.devculi.sway.dataaccess.entity.SwayUser;
import com.devculi.sway.sharedmodel.request.UpsertUserRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface IAdminService {

  Page<SwayUser> getUsers(Integer page);

  SwayUser insertNewUser(UpsertUserRequest insertUserRequest);

  SwayUser deleteUserByID(Long userID) throws Exception;

  SwayUser updateUser(Long userID, UpsertUserRequest updateUserRequest);

  Page<SwayClass> getClasses(Integer page);

  String randomPassword();
}
