package com.devculi.sway.controller.api.lecturer;

import com.devculi.sway.business.shared.model.SwayTestModel;
import com.devculi.sway.business.shared.utils.Entity2DTO;
import com.devculi.sway.controller.api.BaseController;
import com.devculi.sway.dataaccess.entity.Question;
import com.devculi.sway.dataaccess.entity.SwayTest;
import com.devculi.sway.dataaccess.entity.enums.TestType;
import com.devculi.sway.manager.service.interfaces.ISwayTestService;
import com.devculi.sway.manager.service.services_impl.LecturerService;
import com.devculi.sway.sharedmodel.response.common.PagingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tests")
public class RestTestController extends BaseController {
  @Autowired ISwayTestService swayTestService;
  @Autowired LecturerService lecturerService;

  @GetMapping("/{id}")
  public ResponseEntity getTestByID(@PathVariable(name = "id") Long id) {
    return ok(swayTestService.getTestByID(id));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity deleteTestByID(@PathVariable(name = "id") Long id) {
    return ok(swayTestService.deleteTestByID(id));
  }

  @GetMapping("/")
  public PagingResponse<SwayTestModel> getTestByPage(
      @RequestParam(name = "page", defaultValue = "0") Integer page) {
    Page<SwayTest> homeworks = lecturerService.getHomeworkByPage(page);
    int totalPages = homeworks.getTotalPages();
    return new PagingResponse<>(
            totalPages,
            homeworks.getContent().stream().map(Entity2DTO::swayTest2DTO).collect(Collectors.toList()));
  }

  @GetMapping("/create")
  public SwayTest createTest(@RequestParam(name = "testType", defaultValue = "HOMEWORK") TestType testType) {
    return swayTestService.createTestByType(testType);
  }
}
