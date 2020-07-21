package com.devculi.sway.manager.service.interfaces;

import com.devculi.sway.dataaccess.entity.SwayUser;
import org.springframework.stereotype.Service;

@Service
public interface IStudentService {
  SwayUser getStudentByID(Long id);
}
