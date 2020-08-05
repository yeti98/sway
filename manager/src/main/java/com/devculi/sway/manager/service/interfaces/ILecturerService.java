package com.devculi.sway.manager.service.interfaces;

import com.devculi.sway.dataaccess.entity.SwayClass;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface ILecturerService {
  SwayClass getClassById(Long classId);

  Page<SwayClass> getClassesByPage(Long page);
}
