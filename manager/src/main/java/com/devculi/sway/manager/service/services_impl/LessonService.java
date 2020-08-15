package com.devculi.sway.manager.service.services_impl;

import com.devculi.sway.business.shared.model.LessonModel;
import com.devculi.sway.business.shared.model.SwayTestModel;
import com.devculi.sway.business.shared.request.UpsertLessonRequest;
import com.devculi.sway.business.shared.utils.Entity2DTO;
import com.devculi.sway.dataaccess.entity.Lesson;
import com.devculi.sway.dataaccess.entity.SwayTest;
import com.devculi.sway.dataaccess.repository.LessonRepository;
import com.devculi.sway.manager.service.interfaces.ILessonService;
import com.devculi.sway.manager.service.interfaces.ISwayTestService;
import com.devculi.sway.sharedmodel.exceptions.RecordNotFoundException;
import com.devculi.sway.sharedmodel.response.common.PagingResponse;
import com.devculi.sway.utils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LessonService implements ILessonService {
  @Autowired LessonRepository lessonRepository;
  @Autowired ISwayTestService testService;

  @Value("${site.admin.pagination.limit.lesson}")
  private Integer LessonPerPage;

  @Override
  public Lesson getLessonByID(Long id) {
    Optional<Lesson> byId = lessonRepository.findById(id);
    return byId.orElseThrow(() -> new RecordNotFoundException(Lesson.class, "id", id.toString()));
  }

  @Override
  public PagingResponse<LessonModel> getLessonByPage(Integer page) {
    Pageable pageable = PageRequest.of(page, LessonPerPage, Sort.by("createdAt").descending());
    Page<Lesson> all = lessonRepository.findAll(pageable);
    return new PagingResponse<>(
        all.getTotalPages(),
        all.getContent().stream().map(Entity2DTO::lesson2Model).collect(Collectors.toList()));
  }

  @Override
  public Lesson createLesson() {
    Lesson lesson = new Lesson();
    lessonRepository.save(lesson);
    return lesson;
  }

  @Override
  public Lesson updateLesson(Long id, UpsertLessonRequest updateHomeworkRequest) {
    Lesson lessonByID = getLessonByID(id);
    Set<String> nullProperties = PropertyUtils.getNullProperties(updateHomeworkRequest);

    String lessonName = updateHomeworkRequest.getName();
    String lessonId = updateHomeworkRequest.getLessonId();

    if (!nullProperties.contains("name")) {
      lessonByID.setName(lessonName);
    }
    if (!nullProperties.contains("lessonId")) {
      lessonByID.setLessonId(lessonId);
    }
    List<SwayTest> tests = new ArrayList<>();
    List<Long> testIdList =
        updateHomeworkRequest.getTests().stream()
            .map(SwayTestModel::getId)
            .collect(Collectors.toList());
    testIdList.forEach(
        tID -> {
          SwayTest testById = testService.getTestByID(tID);
          if (!tests.contains(testById)) {
            tests.add(testById);
          }
        });
    lessonByID.setTests(tests);
    lessonRepository.save(lessonByID);
    return lessonByID;
  }

  @Override
  public Long deleteLessonById(Long id) {
    Lesson lessonByID = getLessonByID(id);
    Long deletedId = lessonByID.getId();
    lessonRepository.delete(lessonByID);
    return deletedId;
  }
}
