package com.nbcampif.ifstagram.domain.post.service;

import com.nbcampif.ifstagram.domain.post.dto.PostRequestDto;
import com.nbcampif.ifstagram.domain.post.dto.PostResponseDto;
import com.nbcampif.ifstagram.domain.user.model.User;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface PostService {

  void createPost(
      PostRequestDto requestDto,
      MultipartFile image,
      User user
  );

  List<PostResponseDto> getPostList(Pageable pageable);

  PostResponseDto getPost(Long postId);

  void updatePost(Long postId, PostRequestDto requestDto, MultipartFile image);

  void deletePost(Long postId);

  List<PostResponseDto> followPost(User user);

}
