package com.devculi.sway.manager.service.interfaces;

import com.devculi.sway.dataaccess.entity.SwayClass;
import com.devculi.sway.dataaccess.entity.SwayTest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ILecturerService {

  Page<SwayTest> getHomeworkByPage(Integer page);

  Page<SwayTest> getTestOnlineByPage(Integer page);

  List<SwayClass> getLeadingClasses();
}
