package com.nbcampif.ifstagram.domain.comment.controller;


import com.nbcampif.ifstagram.domain.comment.dto.CommentRequestDto;
import com.nbcampif.ifstagram.domain.comment.dto.CommentResponseDto;
import com.nbcampif.ifstagram.domain.comment.service.CommentService;
import com.nbcampif.ifstagram.domain.user.model.User;
import com.nbcampif.ifstagram.global.response.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments/post")
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "댓글 생성", description = "댓글 생성")
    @PostMapping("/{postId}")
    public ResponseEntity<CommonResponse<List<CommentResponseDto>>> createComment(
            @PathVariable Long postId,
            @AuthenticationPrincipal User user,
            @RequestBody CommentRequestDto requestDto) {
        return commentService.createComment(requestDto, postId, user);
    }

    @Operation(summary = "대댓글 생성", description = "대댓글 생성")
    @PostMapping("/{postId}/reply/{commentId}")
    public ResponseEntity<CommonResponse<List<CommentResponseDto>>> createReplyComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @AuthenticationPrincipal User user,
            @RequestBody CommentRequestDto requestDto) {
        return commentService.createReplyComment(requestDto, postId, commentId, user);
    }

    @Operation(summary = "댓글 조회", description = "댓글 조회")
    @GetMapping("/{postId}")
    public ResponseEntity<CommonResponse<List<CommentResponseDto>>> getComment(
            @PathVariable Long postId) {
        return commentService.getComment(postId);
    }

    @Operation(summary = "댓글 수정", description = "댓글 수정")
    @PutMapping("/{postId}/{commentId}")
    public ResponseEntity<CommonResponse<List<CommentResponseDto>>> updateComment(
            @PathVariable Long commentId,
            @PathVariable Long postId,
            @AuthenticationPrincipal User user,
            @RequestBody CommentRequestDto requestDto) {
        return commentService.updateComment(requestDto, commentId, postId);
    }

  @Operation(summary = "댓글 삭제", description = "댓글 삭제")
  @DeleteMapping("{commentId}")
  public ResponseEntity<CommonResponse<Void>> deleteComment(
      @PathVariable Long commentId
  ) {
    return commentService.deleteComment(commentId);
  }


}
