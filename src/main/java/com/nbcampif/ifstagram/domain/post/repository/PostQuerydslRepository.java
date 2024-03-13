package com.nbcampif.ifstagram.domain.post.repository;

import com.nbcampif.ifstagram.domain.post.entity.Post;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostQuerydslRepository {

  List<Post> findAllPosts();

  Page<Post> findAllPosts(Pageable pageable);

  List<Post> findAllPostsByUserIdList(List<Long> userList);

}
