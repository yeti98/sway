package com.devculi.sway.controller.api.lecturer;

import com.devculi.sway.annotations.RequireRoleAdmin;
import com.devculi.sway.business.shared.model.SwayClassModel;
import com.devculi.sway.business.shared.request.UpsertClassRequest;
import com.devculi.sway.business.shared.utils.Entity2DTO;
import com.devculi.sway.controller.api.BaseController;
import com.devculi.sway.dataaccess.entity.SwayClass;
import com.devculi.sway.manager.service.interfaces.IClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/classes")
@RequireRoleAdmin
public class RestClassController extends BaseController {
  @Autowired IClassService classService;

  @GetMapping("/{id}")
  public ResponseEntity getClassById(@PathVariable(name = "id") Long id) {
    return ok(classService.getClassById(id));
  }

  @PutMapping("/{id}")
  public ResponseEntity<SwayClassModel> update(
      @RequestBody UpsertClassRequest upsertClassRequest, @PathVariable(name = "id") Long id)
      throws Exception {
    if (!id.equals(upsertClassRequest.getId())) {
      throw new Exception("Id must be exactly");
    }
    SwayClass swayClass = classService.updateClass(id, upsertClassRequest);
    return ok(Entity2DTO.class2DTO(swayClass));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity deleteTestByID(@PathVariable(name = "id") Long id) {
    Long deletedId = classService.deleteClassById(id);
    return ok(deletedId);
  }
}
