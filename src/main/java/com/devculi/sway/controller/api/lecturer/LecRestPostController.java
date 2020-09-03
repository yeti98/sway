package com.devculi.sway.controller.api.lecturer;

import com.devculi.sway.annotations.RequireRoleAdmin;
import com.devculi.sway.business.shared.model.PostModel;
import com.devculi.sway.business.shared.request.UpsertPostRequest;
import com.devculi.sway.business.shared.utils.Entity2DTO;
import com.devculi.sway.controller.api.RestBaseController;
import com.devculi.sway.dataaccess.entity.Post;
import com.devculi.sway.manager.service.interfaces.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequireRoleAdmin
public class LecRestPostController extends RestBaseController {

  @Autowired IPostService postService;

  @PostMapping
  public PostModel createPost(@RequestBody UpsertPostRequest upsertPostRequest) {
    Post post = postService.createPost(upsertPostRequest);
    return Entity2DTO.post2DTO(post);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity deletePostById(@PathVariable(name = "id") Long id) {
    Long deleteId = postService.deletePostById(id);
    return ok(deleteId);
  }

  @GetMapping("/{id}")
  public PostModel getPostById(@PathVariable(name = "id") Long id) {
    Post postById = postService.getPostById(id);
    return Entity2DTO.post2DTO(postById);
  }

  @PutMapping("/{id}")
  public ResponseEntity<PostModel> editPost(
      @RequestBody UpsertPostRequest upsertPostRequest, @PathVariable(name = "id") Long id) {
    Post post = postService.updatePost(upsertPostRequest, id);
    return ok(Entity2DTO.post2DTO(post));
  }
}
