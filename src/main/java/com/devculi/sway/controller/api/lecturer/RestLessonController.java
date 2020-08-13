package com.devculi.sway.controller.api.lecturer;

import com.devculi.sway.annotations.RequireRoleAdmin;
import com.devculi.sway.controller.api.BaseController;
import com.devculi.sway.manager.service.interfaces.ILessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lessons")
@RequireRoleAdmin
public class RestLessonController extends BaseController {
    @Autowired
    ILessonService lessonService;
    @GetMapping("/{id}")
    public ResponseEntity getTestByID(@PathVariable(name = "id") Long id) {
        return ok(lessonService.getLessonByID(id));
    }

}
