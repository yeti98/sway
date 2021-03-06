package com.devculi.sway.controller.api.lecturer;

import com.devculi.sway.annotations.RequireRoleLecturer;
import com.devculi.sway.business.shared.utils.Entity2DTO;
import com.devculi.sway.controller.api.RestBaseController;
import com.devculi.sway.dataaccess.entity.SwayUser;
import com.devculi.sway.manager.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
@RequireRoleLecturer
public class LecRestUserController extends RestBaseController {
  @Autowired IUserService userService;

  @GetMapping("/search")
  public ResponseEntity<Object> searchByUsername(
      @RequestParam(name = "username", defaultValue = "") String keyword,
      @RequestParam(name = "role", defaultValue = "all") String role) {
    if (keyword.length() == 0) {
      return ok(new ArrayList<>());
    }
    System.out.println(role);
    System.out.println("\n\n\n\n\n\n");
    SwayUser user = null;
    if ("all".equalsIgnoreCase(role)) {
      user = userService.searchByUsername(keyword, true);
    } else {
      user = userService.searchByUsername(keyword, true, role.toUpperCase());
    }
    if (user == null) {
      return ok(new ArrayList<>());
    }
    return ok(Entity2DTO.user2DTO(user));
  }

  @GetMapping("/search/byKey")
  public ResponseEntity searchByKeyword(
      @RequestParam(name = "query", defaultValue = "") String keyword) {
    if (keyword.length() == 0) {
      return ok(new ArrayList<>());
    }
    List<SwayUser> results = userService.searchBy(keyword, true);
    return ok(results.stream().map(Entity2DTO::user2DTO).collect(Collectors.toList()));
  }
}
