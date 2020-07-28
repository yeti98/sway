package com.devculi.sway.manager.service.interfaces;

import com.devculi.sway.dataaccess.entity.SwayClass;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ILecturerService {
  SwayClass getClassById(Long classId);

  List<SwayClass> getAllManagedClasses();
}
