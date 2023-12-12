package com.letter.question.entity;

import com.letter.member.entity.InviteOpponent;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "YI_QUESTION", schema = "YI")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "QSTN_ID", nullable = false)
    private Long id;

    @Size(max = 200)
    @NotNull
    @Column(name = "QSTN", nullable = false, length = 200)
    private String questionContents;

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

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
    private List<InviteOpponent> inviteOpponents = new ArrayList<>();

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
    private List<SelectQuestion> selectQuestions = new ArrayList<>();

}