package com.nbcampif.ifstagram.domain.post.repository;

import com.nbcampif.ifstagram.domain.post.entity.Post;
import com.nbcampif.ifstagram.domain.post.entity.QPost;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
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
  public Page<Post> findAllPosts(Pageable pageable) {
    JPAQuery<Post> jpaQuery = jpaQueryFactory.selectFrom(qPost)
        .where(qPost.deletedAt.isNull())
        .orderBy(qPost.createdAt.desc());

    if (pageable.isPaged()) {
      jpaQuery = jpaQuery.offset(pageable.getOffset()).limit(pageable.getPageSize());
    }

    List<Post> posts = jpaQuery.fetch();

    JPAQuery<Long> totalQuery = jpaQueryFactory.select(Wildcard.count)
        .from(qPost)
        .where(qPost.deletedAt.isNull());

    return PageableExecutionUtils.getPage(posts, pageable, totalQuery::fetchOne);
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
