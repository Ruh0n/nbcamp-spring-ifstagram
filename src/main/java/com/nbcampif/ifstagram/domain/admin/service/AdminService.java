package com.nbcampif.ifstagram.domain.admin.service;

import com.nbcampif.ifstagram.domain.admin.dto.LoginRequestDto;
import com.nbcampif.ifstagram.domain.admin.dto.UserForceUpdateRequestDto;
import com.nbcampif.ifstagram.domain.post.dto.PostRequestDto;
import com.nbcampif.ifstagram.domain.post.dto.PostResponseDto;
import com.nbcampif.ifstagram.domain.user.dto.ReportReponseDto;
import com.nbcampif.ifstagram.domain.user.dto.UserResponseDto;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

public interface AdminService {

  void login(LoginRequestDto requestDto, HttpServletResponse response);

  @PostConstruct
  void createAdminAccount();

  UserResponseDto searchUser(Long userId);

  List<ReportReponseDto> searchReport(Long reportId);

  List<PostResponseDto> getDeletedPost();

  @Transactional
  void updateUser(Long userId, UserForceUpdateRequestDto requestDto);

  @Transactional
  void updatePost(Long postId, PostRequestDto requestDto, MultipartFile image);

  @Transactional
  void deletePost(Long postId);

}
