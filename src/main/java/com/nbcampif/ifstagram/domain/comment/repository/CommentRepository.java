package com.nbcampif.ifstagram.domain.comment.repository;


import com.nbcampif.ifstagram.domain.comment.entity.Comment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {

  Optional<Comment> findById(Long commentId);

  Optional<List<Comment>> findByPostIdAndIsDeletedAndParentCommentId(
      Long postId, Boolean isDeleted, Long parentId
  );

  Optional<Comment> findByIdAndIsDeleted(Long commentId, Boolean isDeleted);

  @Query("SELECT c FROM Comment c WHERE c.postId = :postId AND c.isDeleted = false AND c.parentCommentId = :parentCommentId")
  List<Comment> findAllCommentByPostId(
      @Param("postId") Long postId, @Param("parentCommentId") Long parentCommentId
  );

}
