package com.devculi.sway.manager.service.interfaces;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public interface ISecurityService {
    void setSecurityContext(UserDetails userDetails);
}
