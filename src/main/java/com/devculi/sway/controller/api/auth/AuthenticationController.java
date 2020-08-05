package com.devculi.sway.controller.api.auth;

import com.devculi.sway.business.shared.factory.SwayFactory;
import com.devculi.sway.controller.api.BaseController;
import com.devculi.sway.dataaccess.entity.SwayUser;
import com.devculi.sway.manager.service.interfaces.IAuthService;
import com.devculi.sway.manager.service.security.CustomUserDetails;
import com.devculi.sway.sharedmodel.request.AuthenticateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
public class AuthenticationController extends BaseController {
  @Autowired IAuthService authService;

//  @PostMapping("/login")
//  @Transactional(readOnly = true)
//  public Authentication login(AuthenticateRequest authenticateRequest) {
//    SwayFactory.getAuthValidator().validateLogin(authenticateRequest);
//
//    SwayUser swayUser =
//        authService.login(
//            authenticateRequest.getUsername(), authenticateRequest.getPassword(), "NORMAL");
//
//    CustomUserDetails userDetails = new CustomUserDetails(swayUser);
//    UsernamePasswordAuthenticationToken authentication =
//        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//    return authentication;
//    return null;
//  }
}
