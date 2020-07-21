package com.devculi.sway.manager.service.interfaces;

import com.devculi.sway.dataaccess.entity.SwayUser;
import com.devculi.sway.sharedmodel.request.UpsertUserRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IAdminService {

  List<SwayUser> getUsers(Long page);

  SwayUser insertNewUser(UpsertUserRequest insertUserRequest);

  SwayUser deleteUserByID(Long userID) throws Exception;

  SwayUser updateUser(Long userID, UpsertUserRequest updateUserRequest);
}
