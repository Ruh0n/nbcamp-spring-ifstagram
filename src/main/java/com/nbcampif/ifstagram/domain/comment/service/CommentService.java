package com.nbcampif.ifstagram.domain.comment.service;

import com.nbcampif.ifstagram.domain.comment.dto.CommentRequestDto;
import com.nbcampif.ifstagram.domain.comment.dto.CommentResponseDto;
import com.nbcampif.ifstagram.domain.user.model.User;
import com.nbcampif.ifstagram.global.response.CommonResponse;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface CommentService {

  //    private final PostRepository postRepository; // post repository 생성 후 수정
  ResponseEntity<CommonResponse<List<CommentResponseDto>>> createComment(
      CommentRequestDto requestDto,
      Long postId,
      User user
  );

  ResponseEntity<CommonResponse<List<CommentResponseDto>>> createReplyComment(
      CommentRequestDto requestDto,
      Long postId,
      Long commentId,
      User user
  );

  ResponseEntity<CommonResponse<List<CommentResponseDto>>> getComment(Long postId);

  ResponseEntity<CommonResponse<List<CommentResponseDto>>> updateComment(
      CommentRequestDto requestDto,
      Long commentId,
      Long postId
  );

  ResponseEntity<CommonResponse<Void>> deleteComment(Long commentId);

}
