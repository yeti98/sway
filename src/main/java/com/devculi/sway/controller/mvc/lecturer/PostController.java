package com.devculi.sway.controller.mvc.lecturer;

import com.devculi.sway.business.shared.model.SwayTestModel;
import com.devculi.sway.business.shared.utils.Entity2DTO;
import com.devculi.sway.dataaccess.entity.SwayTest;
import com.devculi.sway.sharedmodel.response.common.PagingResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/manage/posts")
public class PostController {
    @GetMapping
    public String renderHomeworkView(
            Model model, @RequestParam(name = "page", defaultValue = "0") Integer page) {
        return "admin/post/index";
    }



    // Post DETAIL
    @GetMapping("/{id}")
    public String create(Model model, @PathVariable(name = "id") Long id) {
//        SwayTest newSwayTest = testService.getTestByID(id);
//        model.addAttribute("swayTest", Entity2DTO.swayTest2DTO(newSwayTest));
        return "admin/post/detail";
    }
}
