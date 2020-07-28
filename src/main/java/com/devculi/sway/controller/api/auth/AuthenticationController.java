package com.devculi.sway.controller.api.auth;

import com.devculi.sway.business.shared.factory.SwayFactory;
import com.devculi.sway.controller.mvc.BaseController;
import com.devculi.sway.manager.service.interfaces.IAuthService;
import com.devculi.sway.sharedmodel.request.AuthenticateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController extends BaseController {
  @Autowired IAuthService authService;

  @GetMapping("/login")
  @Transactional(readOnly = true)
  public ResponseEntity login(AuthenticateRequest authenticateRequest) {
    SwayFactory.getAuthValidator().validateLogin(authenticateRequest);
    return ok(
        authService.login(
            authenticateRequest.getUsername(), authenticateRequest.getPassword(), "NORMAL"));
  }
}
