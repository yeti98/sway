package com.devculi.sway.manager.service.services_impl;

import com.devculi.sway.business.shared.request.UpsertPostRequest;
import com.devculi.sway.business.shared.utils.Entity2DTO;
import com.devculi.sway.dataaccess.entity.Post;
import com.devculi.sway.dataaccess.repository.PostRepository;
import com.devculi.sway.manager.service.interfaces.IPostService;
import com.devculi.sway.manager.service.interfaces.IUserService;
import com.devculi.sway.sharedmodel.exceptions.RecordNotFoundException;
import com.devculi.sway.sharedmodel.response.common.PagingResponse;
import com.devculi.sway.utils.PropertyUtils;
import com.devculi.sway.utils.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService implements IPostService {

  @Autowired IUserService userService;

  @Autowired PostRepository postRepository;

  @Value("${site.admin.pagination.limit.post}")
  private Integer PostPerPage;

  @Override
  public PagingResponse getPostByPage(Integer page) {
    Pageable pageable = PageRequest.of(page, PostPerPage, Sort.by("createdAt").descending());
    Page<Post> all = postRepository.findAll(pageable);
    return new PagingResponse<>(
        all.getTotalPages(),
        all.getContent().stream().map(Entity2DTO::post2DTO).collect(Collectors.toList()));
  }

  @Override
  @Transactional
  public Post createPost() {
    Post post = new Post();
    post.setTitle("");
    post.setContents("");
    post.setMenu("Homepage");
    try {
      post.setAuthor(userService.getCurrentUser());
    } catch (Exception e) {
      e.printStackTrace();
    }

    postRepository.save(post);
    return post;
  }

  @Override
  public Post getPostById(Long id) {
    Optional<Post> byId = postRepository.findById(id);
    return byId.orElseThrow(() -> new RecordNotFoundException(Post.class, "id", id.toString()));
  }

  @Override
  public Long deletePostById(Long id) {
    postRepository.deleteById(id);
    return id;
  }

  @Override
  public Post updatePost(UpsertPostRequest upsertPostRequest, Long idPost) {
    Post post = postRepository.findById(idPost).orElse(null);
    String[] nullPropertiesString = PropertyUtils.getNullPropertiesString(upsertPostRequest);
    BeanUtils.copyProperties(upsertPostRequest, post, nullPropertiesString);
    if (post.getSlug() == null) {
      String slug = StringUtils.makeSlug(post.getTitle());
      post.setSlug(slug);
    }
    postRepository.save(post);
    return post;
  }

  @Override
  public List<Post> searchByTitle(String keyword, boolean isIgnoreCase) {
    if (isIgnoreCase) {
      keyword = "%" + keyword.toLowerCase() + "%";
    } else {
      keyword = "%" + keyword + "%";
    }

    return postRepository.findPostsByTitleLike(keyword);
  }
}
