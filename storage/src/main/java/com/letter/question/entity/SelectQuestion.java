package com.letter.question.entity;

import com.letter.member.entity.Couple;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "YI_SELECT_QUESTION", schema = "YI")
public class SelectQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SEL_QSTN_ID", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CP_ID")
    private Couple couple;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "QSTN_ID")
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REG_QSTN_ID")
    private RegisterQuestion registerQuestion;

    @Size(max = 1)
    @NotNull
    @Column(name = "IS_SHOW", nullable = false, length = 1)
    private String isShow;

    @NotNull
    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "selectQuestion")
    private List<Answer> answer = new ArrayList<>();

}