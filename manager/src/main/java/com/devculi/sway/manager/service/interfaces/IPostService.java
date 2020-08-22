package com.devculi.sway.manager.service.interfaces;

import com.devculi.sway.business.shared.model.PostModel;
import com.devculi.sway.business.shared.request.UpsertPostRequest;
import com.devculi.sway.dataaccess.entity.Post;
import com.devculi.sway.sharedmodel.response.common.PagingResponse;
import org.springframework.stereotype.Service;

@Service
public interface IPostService {

    PagingResponse getPostByPage(Integer page);

    PostModel createPost(UpsertPostRequest upsertPostRequest);

    Long deletePostById(Long id);

    PostModel updatePost(UpsertPostRequest upsertPostRequest, Long idPost);
}
