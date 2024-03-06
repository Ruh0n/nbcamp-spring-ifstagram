package com.nbcampif.ifstagram.domain.image.service;

import com.nbcampif.ifstagram.domain.image.entity.PostImage;
import com.nbcampif.ifstagram.domain.image.repository.PostImageRepository;
import com.nbcampif.ifstagram.domain.post.entity.Post;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j(topic = "PostImageService")
@RequiredArgsConstructor
@Transactional
@Service
public class PostImageServiceImpl implements PostImageService {

  @Value("${upload.path}")
  private String uploadPath;

  private final PostImageRepository postImageRepository;


  @Override
  public void createImage(MultipartFile imageFile, Post post) {
    PostImage image = getPostImage(imageFile, post);

    postImageRepository.save(image);
  }


  @Override
  @Transactional(readOnly = true)
  public String getImage(Long id) {
    PostImage image = postImageRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("게시물 이미지가 존재하지 않습니다"));

    return image.getFilePath();
  }

  @Override
  public void updateImage(Post post, MultipartFile imageFile) {
    PostImage image = getPostImage(imageFile, post);
    PostImage postImage = postImageRepository.findByPostId(post.getId())
        .orElseThrow(() -> new IllegalArgumentException("게시물 이미지가 존재하지 않습니다"));

    postImage.updatePostImage(image);
  }

  private PostImage getPostImage(MultipartFile file, Post post) {
    if (file == null) {
      return PostImage.builder()
          .fileName(null)
          .saveFileName(null)
          .contentType(null)
          .filePath(null)
          .post(post)
          .build();
    }

    String originalFilename = file.getOriginalFilename();
    String savedFilename = createSaveFileName(originalFilename);

    try {
      file.transferTo(new File(getFullPath(savedFilename)));
    } catch (IOException e) {
      log.error("파일 저장 실패", e);
    }

    return PostImage.builder()
        .fileName(originalFilename)
        .saveFileName(savedFilename)
        .contentType(file.getContentType())
        .filePath(uploadPath + savedFilename)
        .post(post)
        .build();
  }

  private String createSaveFileName(String originalFilename) {
    return UUID.randomUUID() + "." + extractExt(originalFilename);
  }

  private String extractExt(String originalFilename) {
    return originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
  }

  private String getFullPath(String filename) {
    return uploadPath + filename;
  }

}
