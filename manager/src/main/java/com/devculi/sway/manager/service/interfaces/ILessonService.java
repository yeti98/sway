package com.devculi.sway.manager.service.interfaces;

import com.devculi.sway.business.shared.request.UpsertLessonRequest;
import com.devculi.sway.dataaccess.entity.Lesson;
import com.devculi.sway.dataaccess.entity.SwayTest;
import com.devculi.sway.dataaccess.entity.SwayUser;
import com.devculi.sway.sharedmodel.response.common.PagingResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ILessonService {
  Lesson getLessonByID(Long id);

  Lesson getLessonBySlug(String slug);

  PagingResponse getLessonByPage(Integer page);

  Lesson createLesson();

  Lesson updateLesson(Long id, UpsertLessonRequest updateHomeworkRequest);

  Long deleteLessonById(Long id);

  List<Lesson> searchBy(String keyword, boolean isIgnoreCase);

    boolean isPassedLesson(SwayUser swayUser, Lesson lesson);
}
