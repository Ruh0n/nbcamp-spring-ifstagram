package com.nbcampif.ifstagram.domain.repost.service;

import com.nbcampif.ifstagram.domain.post.entity.Post;
import com.nbcampif.ifstagram.domain.post.repository.PostQuerydslJpaRepository;
import com.nbcampif.ifstagram.domain.repost.entity.Repost;
import com.nbcampif.ifstagram.domain.repost.repository.RepostRepository;
import com.nbcampif.ifstagram.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RepostServiceImpl implements RepostService {

  private final RepostRepository repostRepository;
  private final PostQuerydslJpaRepository postQuerydslJpaRepository;

  @Override
  @Transactional
  public void createRepost(
      Long postId, User user
  ) {
    // 원본 게시글 정보 가져옴
    Post post = postQuerydslJpaRepository.findById(postId)
        .orElseThrow(() -> new IllegalCallerException("일치하는 게시글이 없습니다."));

    Post savePost = new Post(post, user.getUserId());

    // 원본 게시글 새롭게 저장
    Post savePostInfo = postQuerydslJpaRepository.save(savePost);

    // 원본 게시글을 새롭게 저장 repost id를 가져온 post id로 저장

    Repost repost = new Repost(savePostInfo, user);

    repostRepository.save(repost);
  }

}
