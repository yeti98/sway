package com.devculi.sway.manager.service.services_impl;

import com.devculi.sway.business.shared.model.CourseModel;
import com.devculi.sway.business.shared.model.LessonModel;
import com.devculi.sway.business.shared.request.UpsertCourseRequest;
import com.devculi.sway.business.shared.utils.Entity2DTO;
import com.devculi.sway.dataaccess.entity.Course;
import com.devculi.sway.dataaccess.entity.Lesson;
import com.devculi.sway.dataaccess.repository.CourseRepository;
import com.devculi.sway.manager.service.interfaces.ICourseService;
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
public class CourseService implements ICourseService {
  @Autowired CourseRepository courseRepository;

  @Autowired LessonService lessonService;

  @Value("${site.admin.pagination.limit.course}")
  private Integer CoursePerPage;

  @Override
  public PagingResponse<CourseModel> getCourseByPage(Integer page) {
    Pageable pageable = PageRequest.of(page, CoursePerPage, Sort.by("createdAt").descending());
    Page<Course> all = courseRepository.findByActive(true, pageable);
    return new PagingResponse<>(
        all.getTotalPages(),
        all.getContent().stream().map(Entity2DTO::course2DTO).collect(Collectors.toList()));
  }

  @Override
  public Course createCourse() {
    Course course = new Course();
    course.setActive(false);
    courseRepository.save(course);
    return course;
  }

  @Override
  public Course getCourseById(Long id) {
    Optional<Course> byId = courseRepository.findById(id);
    return byId.orElseThrow(() -> new RecordNotFoundException(Course.class, "id", id.toString()));
  }

  @Override
  public Course updateCourse(Long id, UpsertCourseRequest updateCourseRequest) {
    Course courseById = getCourseById(id);
    Set<String> nullProperties = PropertyUtils.getNullProperties(updateCourseRequest);

    String courseName = updateCourseRequest.getName();
    String courseId = updateCourseRequest.getCourseId();

    if (!nullProperties.contains("name")) {
      courseById.setName(courseName);
      // first time update
      if (!courseById.isActive()) {
        courseById.setActive(true);
      }
    }
    if (!nullProperties.contains("courseId")) {
      courseById.setCourseId(courseId);
    }
    List<Lesson> lessons = new ArrayList<>();
    List<Long> lessonIdList =
        updateCourseRequest.getLessons().stream()
            .map(LessonModel::getId)
            .collect(Collectors.toList());
    lessonIdList.forEach(
        tID -> {
          Lesson lessonByID = lessonService.getLessonByID(tID);
          if (!lessons.contains(lessonByID)) {
            lessons.add(lessonByID);
          }
        });
    courseById.setLessons(lessons);
    courseRepository.save(courseById);
    return courseById;
  }

  @Override
  public Long deleteCourseById(Long id) {
    Course courseByID = getCourseById(id);
    Long deletedId = courseByID.getId();
    courseRepository.delete(courseByID);
    return deletedId;
  }

  @Override
  public List<Course> searchBy(String keyword, boolean isIgnoreCase) {
    if (isIgnoreCase) {
      keyword = "%" + keyword.toLowerCase() + "%";
    } else {
      keyword = "%" + keyword + "%";
    }
    return courseRepository.findByCourseIdLike(keyword);
  }
}
