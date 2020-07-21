package com.devculi.sway.manager.service.interfaces;

import com.devculi.sway.dataaccess.entity.SwayTest;
import org.springframework.stereotype.Service;

@Service
public interface ISwayTestService {
  SwayTest getTestByID(Long id);

  SwayTest deleteTestByID(Long id);
}
