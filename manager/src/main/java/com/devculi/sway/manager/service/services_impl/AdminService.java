package com.devculi.sway.manager.service.services_impl;

import com.devculi.sway.dataaccess.entity.SwayUser;
import com.devculi.sway.manager.service.interfaces.IAdminService;
import com.devculi.sway.manager.service.interfaces.IClassService;
import com.devculi.sway.manager.service.interfaces.IUserService;
import com.devculi.sway.sharedmodel.exceptions.ExistedRecordException;
import com.devculi.sway.sharedmodel.request.UpsertUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminService implements IAdminService {

  @Autowired IUserService userService;
  @Autowired IClassService classService;

  @Value("${site.admin.pagination.limit.user}")
  private Integer UserPerPage;

  @Override
  public Page<SwayUser> getUsers(Integer page) {
    Pageable pageable = PageRequest.of(page, UserPerPage, Sort.by("createdAt").descending());
    return userService.getUserByPage(pageable);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public SwayUser insertNewUser(UpsertUserRequest insertUserRequest) {
    try {
      String username = insertUserRequest.getUsername();
      SwayUser swayUser = userService.findUserByUsername(username);
      if (swayUser != null) {
        System.out.println("Throw exception");
        throw new ExistedRecordException(SwayUser.class, "username", username);
      }

      return userService.register(insertUserRequest);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public SwayUser deleteUserByID(Long userID) throws Exception {
    return userService.deleteUserByID(userID);
  }

  @Override
  public SwayUser updateUser(Long userID, UpsertUserRequest updateUserRequest) {
    return userService.updateUser(userID, updateUserRequest);
  }

  @Override
  public String randomPassword() {
    return userService.randomPassword();
  }
}
