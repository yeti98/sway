package com.devculi.sway.controller.api.lecturer;

import com.devculi.sway.annotations.RequireRoleAdmin;
import com.devculi.sway.business.shared.model.SwayTestModel;
import com.devculi.sway.business.shared.request.UpsertTestRequest;
import com.devculi.sway.business.shared.utils.Entity2DTO;
import com.devculi.sway.controller.api.BaseController;
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
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tests")
@RequireRoleAdmin
public class RestTestController extends BaseController {
  @Autowired ISwayTestService swayTestService;
  @Autowired LecturerService lecturerService;

  @GetMapping("/{id}")
  public ResponseEntity getTestByID(@PathVariable(name = "id") Long id) {
    return ok(swayTestService.getTestByID(id));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity deleteTestByID(@PathVariable(name = "id") Long id) {
    SwayTest swayTest = swayTestService.deleteTestByID(id);
    return ok(swayTest.getId());
  }

  @GetMapping
  public PagingResponse<SwayTestModel> getTestByPage(
      @RequestParam(name = "testType", defaultValue = "HOMEWORK") TestType testType,
      @RequestParam(name = "page", defaultValue = "0") Integer page) {

    Page<SwayTest> res = null;
    switch (testType) {
      case HOMEWORK:
        {
          res = lecturerService.getHomeworkByPage(page);
          break;
        }
      case TEST_ONLINE:
        {
          res = lecturerService.getTestonlineByPage(page);
          break;
        }
    }
    int totalPages = res.getTotalPages();
    return new PagingResponse<>(
        totalPages,
        res.getContent().stream().map(Entity2DTO::swayTest2DTO).collect(Collectors.toList()));
  }

  @GetMapping("/create")
  public SwayTest createTest(
      @RequestParam(name = "testType", defaultValue = "HOMEWORK") TestType testType) {
    return swayTestService.createTestByType(testType);
  }

  @GetMapping("/search")
  public ResponseEntity searchByKeyword(
      @RequestParam(name = "query", defaultValue = "") String keyword,
      @RequestParam(name = "type") String testType) {
    if (keyword.length() == 0) {
      return ok(new ArrayList<>());
    }
    List<SwayTest> results = swayTestService.searchBy(keyword, testType, true);
    return ok(results.stream().map(Entity2DTO::swayTest2DTO).collect(Collectors.toList()));
  }

  @PutMapping("/{id}")
  public SwayTestModel update(
      @RequestBody UpsertTestRequest updateHomeworkRequest, @PathVariable(name = "id") Long id)
      throws Exception {
    if (!id.equals(updateHomeworkRequest.getId())) {
      throw new Exception("Id must be exactly");
    }
    SwayTest swayTest = swayTestService.updateHomeWork(id, updateHomeworkRequest);
    return Entity2DTO.swayTest2DTO(swayTest);
  }
}
