package com.devculi.sway.controller.api.lecturer;


import com.devculi.sway.annotations.RequireRoleAdmin;
import com.devculi.sway.business.shared.model.PostModel;
import com.devculi.sway.business.shared.request.UpsertPostRequest;
import com.devculi.sway.manager.service.interfaces.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequireRoleAdmin
public class RestPostController {

    @Autowired
    IPostService postService;



    @GetMapping
    public PostModel createPost(@RequestBody UpsertPostRequest upsertPostRequest){

        System.out.println(upsertPostRequest.getTitle());
        System.out.println(upsertPostRequest.getContents());
        return postService.createPost(upsertPostRequest);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletePostById(@PathVariable(name = "id") Long id){
        Long deleteId = postService.deletePostById(id);
        return ResponseEntity.ok(deleteId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostModel> getEditPost(@RequestBody UpsertPostRequest upsertPostRequest,@PathVariable(name = "id") Long id){
        return ResponseEntity.ok(postService.updatePost(upsertPostRequest,id));

    }
}
