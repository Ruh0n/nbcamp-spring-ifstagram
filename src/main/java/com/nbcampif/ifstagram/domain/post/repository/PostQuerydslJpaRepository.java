package com.nbcampif.ifstagram.domain.post.repository;

import com.nbcampif.ifstagram.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostQuerydslJpaRepository extends JpaRepository<Post, Long>,
    PostQuerydslRepository {

  @Modifying
  @Query("update Post p set p.likeCount = p.likeCount + 1 WHERE p.id = :postId")
  void upCount(@Param("postId") Long postId);

  @Modifying
  @Query("update Post p set p.likeCount = p.likeCount - 1 WHERE p.id = :postId")
  void downCount(@Param("postId") Long postId);

}
