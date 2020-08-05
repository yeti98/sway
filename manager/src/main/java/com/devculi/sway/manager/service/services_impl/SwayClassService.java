package com.devculi.sway.manager.service.services_impl;

import com.devculi.sway.dataaccess.entity.SwayClass;
import com.devculi.sway.dataaccess.repository.SwayClassRepository;
import com.devculi.sway.manager.service.interfaces.IClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SwayClassService implements IClassService {
  @Autowired SwayClassRepository classRepository;

  @Override
  public Page<SwayClass> getClassByPage(Pageable pageable) {
    return classRepository.findAll(pageable);
  }
}
