package com.nbcampif.ifstagram.domain.image.service;

import com.nbcampif.ifstagram.domain.post.entity.Post;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

public interface PostImageService {

  void createImage(MultipartFile imageFile, Post post);

  String getImage(Long id);

  void updateImage(Post post, MultipartFile imageFile);

}
