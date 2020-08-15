package com.devculi.sway.manager.service.interfaces;

import com.devculi.sway.business.shared.request.UpsertLessonRequest;
import com.devculi.sway.dataaccess.entity.Lesson;
import com.devculi.sway.sharedmodel.response.common.PagingResponse;
import org.springframework.stereotype.Service;

@Service
public interface ILessonService {
  Lesson getLessonByID(Long id);

  PagingResponse getLessonByPage(Integer page);

  Lesson createLesson();

  Lesson updateLesson(Long id, UpsertLessonRequest updateHomeworkRequest);

  Long deleteLessonById(Long id);
}
