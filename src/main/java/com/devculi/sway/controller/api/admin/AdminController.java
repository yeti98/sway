package com.devculi.sway.controller.api.admin;

import com.devculi.sway.business.shared.factory.SwayFactory;
import com.devculi.sway.dataaccess.entity.SwayUser;
import com.devculi.sway.manager.service.interfaces.IAdminService;
import com.devculi.sway.manager.service.interfaces.IAuthService;
import com.devculi.sway.sharedmodel.model.AuthenticationModel;
import com.devculi.sway.sharedmodel.model.UserModel;
import com.devculi.sway.sharedmodel.request.UpsertUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
  @Autowired IAdminService adminService;
  @Autowired IAuthService authService;

  @GetMapping("/users")
  public List<UserModel> getUsersByPage(
      @RequestParam(name = "page", defaultValue = "0") Long page) {
    List<SwayUser> users = adminService.getUsers(page);
    return null;
  }

  @PostMapping("/users")
  @Transactional(rollbackFor = Exception.class)
  public AuthenticationModel insertUser(@RequestBody UpsertUserRequest insertUserRequest) {
    SwayFactory.getUserValidation().validateInsertUser(insertUserRequest);
    SwayUser user = adminService.insertNewUser(insertUserRequest);
    return authService.createAuthenticationModelForUser(user);
  }

  @DeleteMapping("/users/{id}")
  public UserModel deleteUser(@PathVariable(name = "id") Long userID) {
    try {
      SwayUser user = adminService.deleteUserByID(userID);
      return SwayFactory.getModelMapper().user2DTO(user);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  @PutMapping("/users/{id}")
  public SwayUser updateUser(
      @PathVariable(name = "id") Long userID, @RequestBody UpsertUserRequest updateUserRequest) {
    SwayFactory.getUserValidation().validateUpdateUser(updateUserRequest);
    SwayUser user = adminService.updateUser(userID, updateUserRequest);
    return null;
  }
}
