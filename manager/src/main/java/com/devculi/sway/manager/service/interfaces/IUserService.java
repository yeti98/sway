package com.devculi.sway.manager.service.interfaces;

import com.devculi.sway.dataaccess.entity.SwayClass;
import com.devculi.sway.dataaccess.entity.SwayUser;
import com.devculi.sway.sharedmodel.request.UpsertUserRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IUserService {
  SwayUser findUserByUsername(String username);

  SwayUser register(UpsertUserRequest insertUserRequest) throws Exception;

  SwayUser deleteUserByID(Long userID) throws Exception;

  Page<SwayUser> getUserByPage(Pageable pageable);

  SwayUser updateUser(Long id, UpsertUserRequest upsertUserRequest);

  String randomPassword();

  SwayUser getUserById(Long tID);

  SwayUser searchByUsername(String keyword, boolean isIgnoreCase);

  SwayUser getCurrentUser() throws Exception;

  List<SwayClass> getJoinedClasses();

  List<SwayUser> searchBy(String keyword, boolean isIgnoreCase);
}
