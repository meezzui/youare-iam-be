package com.letter.question.entity;

import com.letter.member.entity.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "YI_ANSWER", schema = "YI")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ANS_ID", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MBR_ID")
    private Member member;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "SEL_QSTN_ID", nullable = false)
    private SelectQuestion selectQuestion;

    @Size(max = 1000)
    @NotNull
    @Column(name = "ANS", nullable = false, length = 1000)
    private String answerContents;

    @Size(max = 1)
    @NotNull
    @Column(name = "IS_SHOW", nullable = false, length = 1)
    private String isShow;

    @CreatedDate
    @NotNull
    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "MODIFIED_AT")
    private LocalDateTime modifiedAt;

}