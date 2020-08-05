package com.devculi.sway.controller.api.lecturer;

import com.devculi.sway.business.shared.model.SwayClassModel;
import com.devculi.sway.business.shared.utils.Entity2DTO;
import com.devculi.sway.controller.api.BaseController;
import com.devculi.sway.controller.api.admin.AdminController;
import com.devculi.sway.dataaccess.entity.SwayClass;
import com.devculi.sway.dataaccess.entity.SwayUser;
import com.devculi.sway.manager.service.interfaces.ILecturerService;
import com.devculi.sway.sharedmodel.model.UserModel;
import com.devculi.sway.sharedmodel.response.common.PagingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/lecturers")
public class LecturerController extends BaseController {
  @Autowired ILecturerService lecturerService;

  @GetMapping("/classes/{id}")
  ResponseEntity getClassInfo(@PathVariable(name = "id") Long classId) {
    return ok(lecturerService.getClassById(classId));
  }


  //  @GetMapping("classes/{id}/questions")
  //  ResponseEntity getQuestionsInClass(
  //      @PathVariable(name = "id") Long classId,
  //      @RequestParam(name = "qpage", defaultValue = 0) Long qpage) {
  //    return ok(lecturerService.getQuestionInClass(classId, qpage));
  //  }

}
