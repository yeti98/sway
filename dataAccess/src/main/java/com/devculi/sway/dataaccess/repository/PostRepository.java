package com.devculi.sway.dataaccess.repository;

import com.devculi.sway.dataaccess.entity.Post;
import com.devculi.sway.dataaccess.entity.SwayClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p WHERE lower(p.title) LIKE :keyword")
    List<Post> findPostsByTitleLike(@Param("keyword") String keyword);
}
