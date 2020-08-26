package com.devculi.sway.manager.service.interfaces;

import com.devculi.sway.dataaccess.entity.SwayClass;
import com.devculi.sway.dataaccess.entity.SwaySubmit;
import org.springframework.stereotype.Service;

@Service
public interface ISubmitService {
  SwaySubmit insertSubmit(SwaySubmit swaySubmit);

  SwaySubmit findLastSubmit(SwayClass swayClass);
}
