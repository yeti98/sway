package com.devculi.sway.controller.api.lecturer;

import com.devculi.sway.annotations.RequireRoleAdmin;
import com.devculi.sway.business.shared.model.LessonModel;
import com.devculi.sway.business.shared.request.UpsertLessonRequest;
import com.devculi.sway.business.shared.utils.Entity2DTO;
import com.devculi.sway.controller.api.BaseController;
import com.devculi.sway.dataaccess.entity.Lesson;
import com.devculi.sway.manager.service.interfaces.ILessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/lessons")
@RequireRoleAdmin
public class RestLessonController extends BaseController {
  @Autowired ILessonService lessonService;

  @GetMapping("/{id}")
  public ResponseEntity getTestByID(@PathVariable(name = "id") Long id) {
    return ok(lessonService.getLessonByID(id));
  }

  @PutMapping("/{id}")
  public LessonModel update(
      @RequestBody UpsertLessonRequest updateHomeworkRequest, @PathVariable(name = "id") Long id)
      throws Exception {
    if (!id.equals(updateHomeworkRequest.getId())) {
      throw new Exception("Id must be exactly");
    }
    Lesson lesson = lessonService.updateLesson(id, updateHomeworkRequest);
    return Entity2DTO.lesson2DTO(lesson);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Long> deleteTestByID(@PathVariable(name = "id") Long id) {
    Long deletedId = lessonService.deleteLessonById(id);
    return ok(deletedId);
  }

  @GetMapping("/search")
  public ResponseEntity<Object> searchByKeyword(
      @RequestParam(name = "query", defaultValue = "") String keyword) {
    if (keyword.length() == 0) {
      return ok(new ArrayList<>());
    }
    List<Lesson> results = lessonService.searchBy(keyword, true);
    return ok(results.stream().map(Entity2DTO::lesson2DTO).collect(Collectors.toList()));
  }
}
