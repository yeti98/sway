package com.devculi.sway.manager.service.services_impl;

import com.devculi.sway.dataaccess.entity.SwayClass;
import com.devculi.sway.dataaccess.entity.SwaySubmit;
import com.devculi.sway.dataaccess.entity.SwayUser;
import com.devculi.sway.dataaccess.repository.SwaySubmitRepository;
import com.devculi.sway.manager.service.interfaces.ISubmitService;
import com.devculi.sway.manager.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SwaySubmitService implements ISubmitService {
    @Autowired
    SwaySubmitRepository submitRepository;
    @Autowired
    IUserService userService;

    @Override
    public SwaySubmit insertSubmit(SwaySubmit swaySubmit) {
        return null;
    }

    @Override
    public SwaySubmit findLastSubmit(SwayClass swayClass) {
      try {
          SwayUser currentUser = userService.getCurrentUser();
          return submitRepository.getFirstBySwayClassAndSwayUserOrderByCreatedAtDesc(swayClass, currentUser);
      } catch (Exception e) {
        e.printStackTrace();
      }
      return null;
    }
}
