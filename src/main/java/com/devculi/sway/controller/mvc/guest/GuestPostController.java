package com.devculi.sway.controller.mvc.guest;

import com.devculi.sway.business.shared.utils.Entity2DTO;
import com.devculi.sway.dataaccess.entity.Post;
import com.devculi.sway.manager.service.interfaces.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bai-viet")
public class GuestPostController {
    @Autowired
    IPostService postService;

    @GetMapping("/{slug}")
    public String viewPost(Model model, @PathVariable(name = "slug") String slug) {
        Post post = postService.getPostBySlug(slug);
        model.addAttribute("pageTitle", post.getTitle());
        model.addAttribute("post", Entity2DTO.post2DTO(post));

        return "guest/bai-viet";
    }
}
