package com.devculi.sway.controller.mvc;

import com.devculi.sway.interceptor.attr.annotations.ManageClassPage;
import com.devculi.sway.manager.service.interfaces.ILecturerService;
import com.devculi.sway.manager.service.services_impl.LecturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manage-classes")
@ManageClassPage
public class ManageClassPageController extends BaseController{

    @Autowired
    ILecturerService lecturerService;

    @GetMapping
    public ResponseEntity getManagedClasses(Model model){
        return ok(lecturerService.getAllManagedClasses());
    }
}
