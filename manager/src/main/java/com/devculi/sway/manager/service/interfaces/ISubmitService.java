package com.devculi.sway.manager.service.interfaces;

import com.devculi.sway.dataaccess.entity.SwaySubmit;
import org.springframework.stereotype.Service;

@Service
public interface ISubmitService {
  public SwaySubmit insertSubmit(SwaySubmit swaySubmit);
}
