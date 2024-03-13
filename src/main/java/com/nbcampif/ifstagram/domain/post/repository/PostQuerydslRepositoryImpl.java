package com.nbcampif.ifstagram.domain.post.repository;

import com.nbcampif.ifstagram.domain.post.entity.Post;
import com.nbcampif.ifstagram.domain.post.entity.QPost;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class PostQuerydslRepositoryImpl implements PostQuerydslRepository {

  private static final QPost qPost = QPost.post;
  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public List<Post> findAllPosts() {
    Page<Post> posts = this.findAllPosts(Pageable.unpaged());

    return posts.hasContent() ? posts.getContent() : Collections.emptyList();
  }

  @Override
  public Page<Post> findAllPosts(Pageable pageRequest) {
    List<Post> posts = jpaQueryFactory.selectFrom(qPost)
        .where(qPost.deletedAt.isNull())
        .orderBy(qPost.createdAt.desc())
        .offset(pageRequest.getOffset())
        .limit(pageRequest.getPageSize())
        .fetch();

    long total = jpaQueryFactory.select(qPost.count())
        .from(qPost)
        .where(qPost.deletedAt.isNull())
        .fetchFirst();

    return new PageImpl<>(posts, pageRequest, total);
  }

  @Override
  public List<Post> findAllPostsByUserIdList(List<Long> userList) {
    return jpaQueryFactory.selectFrom(qPost)
        .where(qPost.userId.in(userList))
        .where(qPost.deletedAt.isNull())
        .orderBy(qPost.createdAt.desc())
        .fetch();
  }

}
