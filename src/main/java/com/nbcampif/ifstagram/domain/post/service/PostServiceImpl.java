package com.nbcampif.ifstagram.domain.post.service;

import com.nbcampif.ifstagram.domain.image.service.PostImageService;
import com.nbcampif.ifstagram.domain.post.dto.PostRequestDto;
import com.nbcampif.ifstagram.domain.post.dto.PostResponseDto;
import com.nbcampif.ifstagram.domain.post.entity.Post;
import com.nbcampif.ifstagram.domain.post.repository.PostQuerydslJpaRepository;
import com.nbcampif.ifstagram.domain.user.model.User;
import com.nbcampif.ifstagram.domain.user.repository.FollowRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Transactional
@Service
public class PostServiceImpl implements PostService {

  private final PostImageService postImageService;

  private final PostQuerydslJpaRepository postQuerydslJpaRepository;
  private final FollowRepository followRepository;

  @Override
  @Transactional
  public void createPost(
      PostRequestDto requestDto, MultipartFile image, User user
  ) {
    Post post = new Post(requestDto, user.getUserId());
    postQuerydslJpaRepository.save(post);

    postImageService.createImage(image, post);
  }

  @Override
  @Transactional(readOnly = true)
  public List<PostResponseDto> getPostList(Pageable pageable) {
    return postQuerydslJpaRepository.findAllPosts()
        .stream()
        .map(p -> new PostResponseDto(p, postImageService.getImage(p.getId())))
        .toList();
  }

  //  @Transactional(readOnly = true)
  @Override
  public PostResponseDto getPost(Long postId) {
    Post post = findPost(postId);

    return new PostResponseDto(post, postImageService.getImage(post.getId()));
  }

  @Override
  @Transactional
  public void updatePost(Long postId, PostRequestDto requestDto, MultipartFile image) {
    Post post = findPost(postId);
    postImageService.updateImage(post, image);

    post.updatePost(requestDto);
  }

  @Override
  @Transactional
  public void deletePost(Long postId) {
    Post post = findPost(postId);

    post.delete();
  }

  @Override
  public List<PostResponseDto> followPost(User user) {
    List<Long> userList = followRepository.findToUserIdByFromUserId(user.getUserId());
    List<Post> posts = postQuerydslJpaRepository.findAllPostsByUserIdList(userList);

    return posts.stream()
        .map(p -> new PostResponseDto(p, postImageService.getImage(p.getId())))
        .toList();
  }

  private Post findPost(Long postId) {
    return postQuerydslJpaRepository.findById(postId)
        .orElseThrow(() -> new IllegalCallerException("일치하는 게시글이 없습니다."));
  }

}
