package com.devculi.sway.manager.service.security;

import com.devculi.sway.dataaccess.entity.SwayUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {

  private static final long serialVersionUID = 1L;
  private SwayUser user;

  public CustomUserDetails(SwayUser user) {
    this.user = user;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
    //    List<GrantedAuthority> authorities = new ArrayList<>();
    //    for (Role role : user.getRoles()) {
    //      authorities.add(new SimpleGrantedAuthority(role.getName()));
    //      Collection<Privilege> privileges = role.getPrivileges();
    //      if (Optional.of(privileges).isPresent()) {
    //        role.getPrivileges().stream()
    //            .map(p -> new SimpleGrantedAuthority(p.getName()))
    //            .forEach(authorities::add);
    //      }
    //    }
    //    return authorities;
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getUsername();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return !user.getStatus();
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  public SwayUser getUser() {
    return user;
  }

  public void setUser(SwayUser user) {
    this.user = user;
  }
}
