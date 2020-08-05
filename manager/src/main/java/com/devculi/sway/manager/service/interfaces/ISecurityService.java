package com.devculi.sway.manager.service.interfaces;

import com.devculi.sway.dataaccess.entity.SwayUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public interface ISecurityService {
    void setSecurityContext(UserDetails userDetails);

    SwayUser getCurrentUser();
}
