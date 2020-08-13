package com.devculi.sway.manager.service.services_impl;

import com.devculi.sway.dataaccess.entity.Lesson;
import com.devculi.sway.dataaccess.entity.SwayTest;
import com.devculi.sway.dataaccess.entity.enums.TestType;
import com.devculi.sway.dataaccess.repository.LessonRepository;
import com.devculi.sway.manager.service.interfaces.ILessonService;
import com.devculi.sway.sharedmodel.exceptions.RecordNotFoundException;
import com.devculi.sway.sharedmodel.response.common.PagingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LessonService implements ILessonService {
  @Autowired LessonRepository lessonRepository;

  @Value("${site.admin.pagination.limit.lesson}")
  private Integer LessonPerPage;

  @Override
  public Lesson getLessonByID(Long id) {
    Optional<Lesson> byId = lessonRepository.findById(id);
    return byId.orElseThrow(() -> new RecordNotFoundException(Lesson.class, "id", id.toString()));
  }


    @Override
    public Page<Lesson> getLessonByPage(Integer page) {
        Pageable pageable = PageRequest.of(page, LessonPerPage, Sort.by("createdAt").descending());
        return lessonRepository.findAll(pageable);
    }
}
