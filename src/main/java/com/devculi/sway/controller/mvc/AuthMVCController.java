package com.devculi.sway.controller.mvc;

import com.devculi.sway.config.security.CustomAuthenticationProvider;
import com.devculi.sway.controller.api.auth.AuthenticationController;
import com.devculi.sway.dataaccess.entity.SwayUser;
import com.devculi.sway.manager.service.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class AuthMVCController {
  @Autowired AuthenticationController authenticationController;
  @Autowired CustomAuthenticationProvider authManager;

  @GetMapping("/login")
  public String renderLoginView(Model model) {
    model.addAttribute("pageTitle", "Đăng nhập");
    return "login";
  }

  @GetMapping("/logout")
  public String renderLogoutView(Model model) {
    model.addAttribute("pageTitle", "Đăng xuất");
    return "redirect:/login?logout";
  }

  //    @PostMapping("/login")
  //    public String login(HttpServletRequest request, Model model, AuthenticateRequest
  // authRequest){
  //        try {
  //      System.out.println("REQUEST"+ request.toString());
  //            UsernamePasswordAuthenticationToken authReq
  //                    = new UsernamePasswordAuthenticationToken(authRequest.getUsername(),
  // authRequest.getPassword());
  //            Authentication auth = authManager.authenticate(authReq);
  //
  //            SecurityContext sc = SecurityContextHolder.getContext();
  //            sc.setAuthentication(auth);
  //            HttpSession session = request.getSession(true);
  //            session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, sc);
  //            UsernamePasswordAuthenticationToken authentication =
  // (UsernamePasswordAuthenticationToken) authenticationController.login(authRequest);
  //            authentication.setDetails(new
  // WebAuthenticationDetailsSource().buildDetails(request));
  //            SecurityContextHolder.getContext().setAuthentication(authentication);
  //
  //            HttpSession session = request.getSession(true);
  ////
  // session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
  // SecurityContextHolder.getContext());
  ////            System.out.println(session.getAttribute("SPRING_SECURITY_CONTEXT_KEY"));
  //            System.out.println("AUTHENTICATION" +
  // SecurityContextHolder.getContext().getAuthentication());
  //            UserDetails principal = (UserDetails)
  // SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  //            System.out.println(
  //                    "PRINCIPAL" + principal);
  //            System.out.println(principal.getUsername());
  //            return "redirect:/";
  //        } catch (Exception e){
  //            e.printStackTrace();
  //        }
  //        return "redirect:/login?error";
  //    }

  @GetMapping("/info")
  public String welcome(Model model) {
    System.out.println(
        "AUTHENTICATION:\t" + SecurityContextHolder.getContext().getAuthentication());
    //        System.out.println(SecurityContextHolder.getContext().getAuthentication());

    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principal instanceof UserDetails) {
//      SwayUser user =
//              ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
//                      .getUser();
      String username = ((UserDetails) principal).getUsername();
      model.addAttribute("user", username);

    } else {
      String username = principal.toString();
      model.addAttribute("user", username);
    }
    return "info";
  }
}
