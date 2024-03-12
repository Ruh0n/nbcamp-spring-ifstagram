package com.nbcampif.ifstagram.domain.user.repository;

import com.nbcampif.ifstagram.domain.admin.dto.UserForceUpdateRequestDto;
import com.nbcampif.ifstagram.domain.user.UserRole;
import com.nbcampif.ifstagram.domain.user.dto.UserUpdateRequestDto;
import com.nbcampif.ifstagram.domain.user.model.User;
import java.util.Optional;

public interface UserRepository {

  void createUser(User user);

  Optional<User> findUser(Long userId);

  User findUserOrElseThrow(Long userId);

  User findByEmailOrElseThrow(String email);

  Optional<User> findByEmail(String email);

  boolean existsByRole(UserRole userRole);

  User updateUser(UserUpdateRequestDto requestDto, User savedUser);

  void forceUpdateUser(UserForceUpdateRequestDto requestDto, User savedUser);

  void updateReportedCount(User reportedUser);

}
