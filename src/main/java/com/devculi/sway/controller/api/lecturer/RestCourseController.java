package com.devculi.sway.controller.api.lecturer;

import com.devculi.sway.annotations.RequireRoleAdmin;
import com.devculi.sway.business.shared.model.CourseModel;
import com.devculi.sway.business.shared.request.UpsertCourseRequest;
import com.devculi.sway.business.shared.utils.Entity2DTO;
import com.devculi.sway.controller.api.BaseController;
import com.devculi.sway.dataaccess.entity.Course;
import com.devculi.sway.dataaccess.entity.Lesson;
import com.devculi.sway.manager.service.interfaces.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/courses")
@RequireRoleAdmin
public class RestCourseController extends BaseController {
  @Autowired ICourseService courseService;

  @GetMapping("/{id}")
  public ResponseEntity getTestByID(@PathVariable(name = "id") Long id) {
    return ok(courseService.getCourseById(id));
  }

  @PutMapping("/{id}")
  public ResponseEntity<CourseModel> update(
      @RequestBody UpsertCourseRequest upsertCourseRequest, @PathVariable(name = "id") Long id)
      throws Exception {
    if (!id.equals(upsertCourseRequest.getId())) {
      throw new Exception("Id must be exactly");
    }
    Course course = courseService.updateCourse(id, upsertCourseRequest);
    return ok(Entity2DTO.course2DTO(course));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity deleteTestByID(@PathVariable(name = "id") Long id) {
    Long deletedId = courseService.deleteCourseById(id);
    return ok(deletedId);
  }

  @GetMapping("/search")
  public ResponseEntity<Object> searchByKeyword(
          @RequestParam(name = "query", defaultValue = "") String keyword) {
    if (keyword.length() == 0) {
      return ok(new ArrayList<>());
    }
    List<Course> results = courseService.searchBy(keyword, true);
    return ok(results.stream().map(Entity2DTO::course2DTO).collect(Collectors.toList()));
  }
}
