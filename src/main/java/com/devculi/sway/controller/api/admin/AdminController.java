package com.devculi.sway.controller.api.admin;

import com.devculi.sway.business.shared.factory.SwayFactory;
import com.devculi.sway.business.shared.model.SwayClassModel;
import com.devculi.sway.business.shared.utils.Entity2DTO;
import com.devculi.sway.dataaccess.entity.SwayClass;
import com.devculi.sway.dataaccess.entity.SwayUser;
import com.devculi.sway.manager.service.interfaces.IAdminService;
import com.devculi.sway.manager.service.interfaces.IAuthService;
import com.devculi.sway.sharedmodel.model.AuthenticationModel;
import com.devculi.sway.sharedmodel.model.UserModel;
import com.devculi.sway.sharedmodel.request.UpsertUserRequest;
import com.devculi.sway.sharedmodel.response.common.PagingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
public class AdminController {
  @Autowired IAdminService adminService;
  @Autowired IAuthService authService;

  @GetMapping("/users")
  public PagingResponse<UserModel> getUsersByPage(
      @RequestParam(name = "page", defaultValue = "0") Integer page) {
    Page<SwayUser> userByPage = adminService.getUsers(page);
    return new PagingResponse<>(
        userByPage.getTotalPages(),
        userByPage.getContent().stream().map(Entity2DTO::user2DTO).collect(Collectors.toList()));
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
      return Entity2DTO.user2DTO(user);
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

  public PagingResponse<SwayClassModel> getClassByPage(@RequestParam(name = "page", defaultValue = "0") Integer page) {
    Page<SwayClass> classByPage = adminService.getClasses(page);
    return new PagingResponse<>(
            classByPage.getTotalPages(),
            classByPage.getContent().stream().map(Entity2DTO::class2DTO).collect(Collectors.toList()));
  }
}
