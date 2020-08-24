package com.devculi.sway.manager.service.interfaces;

import com.devculi.sway.business.shared.model.PostModel;
import com.devculi.sway.business.shared.request.UpsertPostRequest;
import com.devculi.sway.dataaccess.entity.Post;
import com.devculi.sway.sharedmodel.response.common.PagingResponse;
import org.springframework.stereotype.Service;

@Service
public interface IPostService {

    PagingResponse getPostByPage(Integer page);

    Post createPost(UpsertPostRequest upsertPostRequest);

    Post getPostById(Long id);

    Long deletePostById(Long id);

    Post updatePost(UpsertPostRequest upsertPostRequest, Long idPost);
}
