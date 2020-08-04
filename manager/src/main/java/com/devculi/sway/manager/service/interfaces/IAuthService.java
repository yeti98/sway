package com.devculi.sway.manager.service.interfaces;

import com.devculi.sway.dataaccess.entity.SwayUser;
import com.devculi.sway.sharedmodel.model.AuthenticationModel;
import org.springframework.stereotype.Service;

@Service
public interface IAuthService {
    SwayUser login(String username, String password, String loginType);

    AuthenticationModel createAuthenticationModelForUser(SwayUser user);
}
