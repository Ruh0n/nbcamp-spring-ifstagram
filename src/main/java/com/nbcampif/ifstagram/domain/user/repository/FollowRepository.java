package com.nbcampif.ifstagram.domain.user.repository;

import com.nbcampif.ifstagram.domain.user.model.Follow;
import com.nbcampif.ifstagram.domain.user.repository.entity.FollowEntity;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class FollowRepository {

  private final FollowJpaRepository followJpaRepository;

  public Follow saveFollow(Long fromUserId, Long toUserId) {
    return followJpaRepository.save(FollowEntity.of(fromUserId, toUserId)).toModel();
  }

  public Optional<Follow> findByFromUserIdAndToUserId(Long fromUserId, Long toUserId) {
    return followJpaRepository.findByFromUserIdAndToUserId(fromUserId, toUserId)
        .map(FollowEntity::toModel);
  }

  public void deleteFollow(Follow savedFollow) {
    followJpaRepository.delete(FollowEntity.of(savedFollow));
  }

  public List<Long> findToUserIdByFromUserId(Long userId) {
    return followJpaRepository.findAllToUserIdByFromUserId(userId);
  }
}
