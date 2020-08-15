package com.devculi.sway.manager.service.interfaces;

import com.devculi.sway.business.shared.model.CourseModel;
import com.devculi.sway.business.shared.request.UpsertCourseRequest;
import com.devculi.sway.dataaccess.entity.Course;
import com.devculi.sway.sharedmodel.response.common.PagingResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ICourseService {
  PagingResponse<CourseModel> getCourseByPage(Integer page);

  Course createCourse();

  Course getCourseById(Long id);

  Course updateCourse(Long id, UpsertCourseRequest updateHomeworkRequest);

  Long deleteCourseById(Long id);

  List<Course> searchBy(String keyword, boolean isIgnoreCase);
}
