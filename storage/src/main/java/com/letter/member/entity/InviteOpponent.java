package com.letter.member.entity;

import com.letter.question.entity.Question;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "YI_INVITE_OPPONENT", schema = "YI")
public class InviteOpponent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INVT_ID", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MBR_ID", nullable = false)
    private Member member;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "QSTN_ID", nullable = false)
    private Question question;

    @Size(max = 200)
    @NotNull
    @Column(name = "LINK_KEY", nullable = false, length = 200)
    private String linkKey;

    @NotNull
    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;

}