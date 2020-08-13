package com.devculi.sway.manager.service.interfaces;

import com.devculi.sway.dataaccess.entity.Lesson;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface ILessonService {
  Lesson getLessonByID(Long id);

  Page<Lesson> getLessonByPage(Integer page);
}
