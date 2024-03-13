package com.nbcampif.ifstagram.domain.like.service;

import com.nbcampif.ifstagram.domain.like.entity.Like;
import com.nbcampif.ifstagram.domain.like.repository.LikeRepository;
import com.nbcampif.ifstagram.domain.post.entity.Post;
import com.nbcampif.ifstagram.domain.post.repository.PostQuerydslJpaRepository;
import com.nbcampif.ifstagram.domain.user.model.User;
import com.nbcampif.ifstagram.domain.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class LikeServiceImpl implements LikeService {

  private final PostQuerydslJpaRepository postQuerydslJpaRepository;
  private final LikeRepository likeRepository;
  private final UserRepository userRepository;

  @Override
  public Long countLike(Long postId, User user) {
    Post postSeq = postQuerydslJpaRepository.findById(postId).orElseThrow(()
        -> new IllegalCallerException("일치하는 게시글이 없습니다."));
    User userInfo = userRepository.findUser(user.getUserId()).orElseThrow(()
        -> new IllegalCallerException("일치하는 유저 없음"));
    Optional<Like> likeInfo = likeRepository.findByUserIdAndPostId(userInfo.getUserId(), postSeq.getId());

    long nextLikeCount;
    Long previousLikeCount = postQuerydslJpaRepository.findById(postId)
        .orElseThrow(() -> new EntityNotFoundException("해당 게시글이 존재하지 않습니다."))
        .getLikeCount();

    if (likeInfo.isEmpty()) {
      likeRepository.save(new Like(userInfo.getUserId(), postId));
      postQuerydslJpaRepository.upCount(postId);
      nextLikeCount = previousLikeCount + 1;
    } else {
      likeRepository.delete(likeInfo.get());
      postQuerydslJpaRepository.downCount(postId);
      nextLikeCount = previousLikeCount - 1;
    }

    return nextLikeCount;
  }

}
