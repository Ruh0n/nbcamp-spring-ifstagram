package com.nbcampif.ifstagram.domain.report.entity;


import com.nbcampif.ifstagram.global.entity.Timestamped;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@RequiredArgsConstructor
@Table(name = "reports")
public class Report extends Timestamped {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String content;

  @Column
  private Long fromUserId;

  @Column
  private Long toUserId;

  public Report(String content, Long toUserId, Long fromUserId) {
    this.content = content;
    this.toUserId = toUserId;
    this.fromUserId = fromUserId;
  }

}
