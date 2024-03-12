package com.nbcampif.ifstagram.domain.user.repository;

import com.nbcampif.ifstagram.domain.user.UserRole;
import com.nbcampif.ifstagram.domain.user.repository.entity.UserEntity;
import java.util.Optional;

public interface UserQuerydslRepository {

  Optional<UserEntity> findUserByEmail(String email);

  Optional<UserEntity> findUserById(Long userId);

  boolean existsByRole(UserRole userRole);

}
