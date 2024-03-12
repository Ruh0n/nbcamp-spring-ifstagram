package com.nbcampif.ifstagram.domain.user.repository;

import com.nbcampif.ifstagram.domain.admin.dto.UserForceUpdateRequestDto;
import com.nbcampif.ifstagram.domain.user.UserRole;
import com.nbcampif.ifstagram.domain.user.dto.UserUpdateRequestDto;
import com.nbcampif.ifstagram.domain.user.model.User;
import com.nbcampif.ifstagram.domain.user.repository.entity.UserEntity;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {

  private final UserQuerydslJpaRepository repository;

  @Override
  public void createUser(User user) {
    UserEntity userEntity = UserEntity.fromModel(user);
    repository.save(userEntity);
  }

  @Override
  public Optional<User> findUser(Long userId) {
    return repository.findUserById(userId).map(UserEntity::toModel);
  }

  @Override
  public User findUserOrElseThrow(Long userId) {
    return findUser(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
  }

  @Override
  public User findByEmailOrElseThrow(String email) {
    return repository.findUserByEmail(email)
        .orElseThrow(() -> new EntityNotFoundException("User not found"))
        .toModel();
  }

  @Override
  public Optional<User> findByEmail(String email) {
    return repository.findUserByEmail(email).map(UserEntity::toModel);
  }

  @Override
  public boolean existsByRole(UserRole userRole) {
    return repository.existsByRole(userRole);
  }

  @Override
  public User updateUser(UserUpdateRequestDto requestDto, User savedUser) {
    UserEntity userEntity = repository.findUserByEmail(savedUser.getEmail()).orElseThrow();
    userEntity.update(requestDto);
    return repository.save(userEntity).toModel();
  }

  @Override
  public void forceUpdateUser(UserForceUpdateRequestDto requestDto, User savedUser) {
    UserEntity userEntity = repository.findUserById(savedUser.getUserId()).orElseThrow();
    userEntity.update(requestDto);
    userEntity.toModel();
  }

  @Override
  public void updateReportedCount(User reportedUser) {
    UserEntity user = repository.findUserById(reportedUser.getUserId())
        .orElseThrow(() -> new IllegalArgumentException("유저가 없습니다."));
    user.updateReportedCount();
  }

}
