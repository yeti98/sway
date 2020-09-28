package com.devculi.sway.manager.service.interfaces;

import com.devculi.sway.business.shared.request.UpsertPostRequest;
import com.devculi.sway.dataaccess.entity.Post;
import com.devculi.sway.dataaccess.entity.SwayClass;
import com.devculi.sway.sharedmodel.response.common.PagingResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IPostService {

  PagingResponse getPostByPage(Integer page);

  Post createPost();

  Post getPostById(Long id);

  Post getPostBySlug(String slug);

  Long deletePostById(Long id);

  Post updatePost(UpsertPostRequest upsertPostRequest, Long idPost);

  List<Post> searchByTitle(String keyword, boolean isIgnoreCase);
}
