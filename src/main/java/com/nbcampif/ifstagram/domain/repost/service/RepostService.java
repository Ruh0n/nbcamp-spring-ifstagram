package com.nbcampif.ifstagram.domain.repost.service;

import com.nbcampif.ifstagram.domain.user.model.User;
import java.io.IOException;
import org.springframework.transaction.annotation.Transactional;

public interface RepostService {

  @Transactional
  void createRepost(
      Long postId, User user
  ) throws IOException;

}
