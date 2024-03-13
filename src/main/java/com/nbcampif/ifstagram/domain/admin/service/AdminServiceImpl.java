package com.nbcampif.ifstagram.domain.admin.service;

import com.nbcampif.ifstagram.domain.admin.dto.LoginRequestDto;
import com.nbcampif.ifstagram.domain.admin.dto.UserForceUpdateRequestDto;
import com.nbcampif.ifstagram.domain.image.service.PostImageService;
import com.nbcampif.ifstagram.domain.post.dto.PostRequestDto;
import com.nbcampif.ifstagram.domain.post.dto.PostResponseDto;
import com.nbcampif.ifstagram.domain.post.entity.Post;
import com.nbcampif.ifstagram.domain.post.repository.PostQuerydslJpaRepository;
import com.nbcampif.ifstagram.domain.report.Entity.Report;
import com.nbcampif.ifstagram.domain.report.repository.ReportRepository;
import com.nbcampif.ifstagram.domain.user.UserRole;
import com.nbcampif.ifstagram.domain.user.dto.ReportReponseDto;
import com.nbcampif.ifstagram.domain.user.dto.UserResponseDto;
import com.nbcampif.ifstagram.domain.user.model.User;
import com.nbcampif.ifstagram.domain.user.repository.UserRepository;
import com.nbcampif.ifstagram.global.exception.NotFoundUserException;
import com.nbcampif.ifstagram.global.exception.PermissionNotException;
import com.nbcampif.ifstagram.global.jwt.JwtTokenProvider;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminServiceImpl implements AdminService {

  private final PostImageService postImageService;
  private final PostQuerydslJpaRepository postQuerydslJpaRepository;
  private final ReportRepository reportRepository;
  private final UserRepository userRepository;
  private final JwtTokenProvider jwtTokenProvider;

  @Value("${admin.email}")
  private String adminEmail;

  @Value("${admin.password}")
  private String verifyPassword;

  @Override
  public void login(LoginRequestDto requestDto, HttpServletResponse response) {
    String password = requestDto.getPassword();
    User user = userRepository.findByEmail(requestDto.getEmail())
        .orElseThrow(() -> new NotFoundUserException("해당 유저는 존재하지 않습니다."));
    if (!verifyPassword.equals(password) || !user.getRole().equals(UserRole.ADMIN)) {
      throw new PermissionNotException("허용되지 않은 권한입니다.");
    }

    String accessToken = jwtTokenProvider.generateAccessToken(user.getUserId(), user.getRole()
        .getAuthority());
    String refreshToken = jwtTokenProvider.generateRefreshToken(user.getUserId(), user.getRole()
        .getAuthority());

    jwtTokenProvider.addAccessTokenToCookie(accessToken, response);
  }

  @Override
  @PostConstruct
  public void createAdminAccount() {
    boolean existsAdmin = userRepository.existsByRole(UserRole.ADMIN);
    if (!existsAdmin) {
      User admin = new User(1L, adminEmail, "admin", UUID.randomUUID()
          .toString(), null, UserRole.ADMIN);
      userRepository.createUser(admin);
    }
  }

  @Override
  public UserResponseDto searchUser(Long userId) {
    User user = userRepository.findUser(userId)
        .orElseThrow(() -> new NotFoundUserException("해당 유저는 존재하지 않습니다."));

    return UserResponseDto.of(user);
  }

  @Override
  public List<ReportReponseDto> searchReport(Long reportId) {
    List<Report> reportList = reportRepository.findAllByToUserId(reportId);

    return reportList.stream()
        .map(r -> new ReportReponseDto(r.getContent(), r.getFromUserId(), r.getToUserId()))
        .toList();
  }

  @Override
  public List<PostResponseDto> getDeletedPost() {
    return postQuerydslJpaRepository.findAllPosts()
        .stream()
        .map(e -> new PostResponseDto(e, postImageService.getImage(e.getId())))
        .toList();
  }

  @Override
  public void updateUser(Long userId, UserForceUpdateRequestDto requestDto) {
    User user = userRepository.findUser(userId)
        .orElseThrow(() -> new NotFoundUserException("해당 유저는 존재하지 않습니다."));

    userRepository.forceUpdateUser(requestDto, user);
  }

  @Override
  public void updatePost(Long postId, PostRequestDto requestDto, MultipartFile image) {

    Post post = postQuerydslJpaRepository.findById(postId)
        .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
    postImageService.updateImage(post, image);
    post.updatePost(requestDto);
  }

  @Override
  public void deletePost(Long postId) {
    Post post = postQuerydslJpaRepository.findById(postId)
        .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));
    post.delete();
  }

}
