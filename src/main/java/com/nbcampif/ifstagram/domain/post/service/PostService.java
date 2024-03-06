package com.nbcampif.ifstagram.domain.post.service;

import com.nbcampif.ifstagram.domain.post.dto.PostRequestDto;
import com.nbcampif.ifstagram.domain.post.dto.PostResponseDto;
import com.nbcampif.ifstagram.domain.user.model.User;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

public interface PostService {

  @Transactional
  void createPost(
      PostRequestDto requestDto,
      MultipartFile image,
      User user
  );

  @Transactional(readOnly = true)
  List<PostResponseDto> getPostList();

  //  @Transactional(readOnly = true)
  PostResponseDto getPost(Long postId);

  @Transactional
  void updatePost(Long postId, PostRequestDto requestDto, MultipartFile image);

  @Transactional
  void deletePost(Long postId);

  List<PostResponseDto> followPost(User user);

}
