package com.devculi.sway.manager.service.services_impl;

import com.devculi.sway.dataaccess.entity.SwaySubmit;
import com.devculi.sway.dataaccess.repository.SwaySubmitRepository;
import com.devculi.sway.manager.service.interfaces.ISubmitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SwaySubmitService implements ISubmitService {
    @Autowired
    SwaySubmitRepository submitRepository;

    @Override
    public SwaySubmit insertSubmit(SwaySubmit swaySubmit) {
        return null;
    }
}
