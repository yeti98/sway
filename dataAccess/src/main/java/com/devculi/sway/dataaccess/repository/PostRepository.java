package com.devculi.sway.dataaccess.repository;

import com.devculi.sway.dataaccess.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {
}
