package com.letter.member.entity;

import com.letter.question.entity.SelectQuestion;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "YI_COUPLE", schema = "YI")
public class Couple {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CP_ID", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INVT_MBR_ID", nullable = false)
    private Member invitedMember;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "RCVD_MBR_ID", nullable = false)
    private Member receivedMember;

    @Size(max = 1)
    @NotNull
    @Column(name = "IS_SHOW", nullable = false, length = 1)
    private String isShow;

    @NotNull
    @Column(name = "STARTED_AT", nullable = false)
    private LocalDateTime startedAt;

    @OneToMany(mappedBy = "couple", fetch = FetchType.LAZY)
    private List<SelectQuestion> selectQuestions = new ArrayList<>();

}