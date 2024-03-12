package com.nbcampif.ifstagram.domain.user.repository;

import com.nbcampif.ifstagram.domain.user.UserRole;
import com.nbcampif.ifstagram.domain.user.repository.entity.QUserEntity;
import com.nbcampif.ifstagram.domain.user.repository.entity.UserEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class UserQuerydslRepositoryImpl implements UserQuerydslRepository {

  private final JPAQueryFactory jpaQueryFactory;
  private static final QUserEntity qUser = QUserEntity.userEntity;

  @Override
  public Optional<UserEntity> findUserByEmail(String email) {
    return Optional.ofNullable(jpaQueryFactory.selectFrom(qUser)
        .where(qUser.email.eq(email)
            .and(qUser.deletedAt.isNull())
            .and(qUser.email.isNotNull()))
        .fetchOne());
  }

  @Override
  public Optional<UserEntity> findUserById(Long userId) {
    return Optional.ofNullable(jpaQueryFactory.selectFrom(qUser)
        .where(qUser.userId.eq(userId))
        .fetchOne());
  }

  @Override
  public boolean existsByRole(UserRole userRole) {
    return !jpaQueryFactory.selectFrom(qUser)
        .where(qUser.role.eq(userRole))
        .fetch()
        .isEmpty();
  }

}
