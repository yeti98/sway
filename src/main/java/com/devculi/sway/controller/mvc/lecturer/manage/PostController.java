package com.devculi.sway.controller.mvc.lecturer.manage;

import com.devculi.sway.business.shared.model.PostModel;
import com.devculi.sway.business.shared.utils.Entity2DTO;
import com.devculi.sway.dataaccess.entity.Post;
import com.devculi.sway.interceptor.attr.annotations.ManagePostsPage;
import com.devculi.sway.manager.service.interfaces.IPostService;
import com.devculi.sway.sharedmodel.response.common.PagingResponse;
import com.devculi.sway.utils.GsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/manage/posts")
@ManagePostsPage
public class PostController {
  @Autowired IPostService postService;

  @GetMapping
  public String renderPostView(
      Model model, @RequestParam(name = "page", defaultValue = "0") Integer page) {
    PagingResponse<PostModel> PostByPage = postService.getPostByPage(page);
    model.addAttribute("totalPages", PostByPage.getTotalPage());
    model.addAttribute("posts", PostByPage.getContent());
    model.addAttribute("current", page);
    return "admin/post/index";
  }

  // Post DETAIL
  @GetMapping("/create")
  public String create(Model model) {

    return "admin/post/detail";
  }

  @GetMapping("/{id}")
  public String edit(Model model, @PathVariable(name = "id") Long id) {
    Post postById = postService.getPostById(id);
    model.addAttribute("editPost", GsonUtils.toJson(Entity2DTO.post2DTO(postById)));
    return "admin/post/detail";
  }
}
