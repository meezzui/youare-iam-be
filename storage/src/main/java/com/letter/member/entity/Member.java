package com.letter.member.entity;

import com.letter.question.entity.Answer;
import com.letter.question.entity.RegisterQuestion;
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
@Table(name = "YI_MEMBER", schema = "YI")
public class Member {

    @Id
    @Size(max = 30)
    @Column(name = "MBR_ID", nullable = false, length = 30)
    private String id;

    @Size(max = 100)
    @NotNull
    @Column(name = "REFRESH_TOKEN", nullable = false, length = 100)
    private String refreshToken;

    @Size(max = 50)
    @NotNull
    @Column(name = "MEDIA_SE", nullable = false, length = 50)
    private String mediaSeparator;

    @Size(max = 50)
    @NotNull
    @Column(name = "MBR_NM", nullable = false, length = 50)
    private String name;

    @Size(max = 200)
    @NotNull
    @Column(name = "MBR_EML", nullable = false, length = 200)
    private String email;

    @ManyToOne
    @JoinColumn(name = "CP_ID")
    private Couple couple;

    @CreatedDate
    @NotNull
    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "MODIFIED_AT")
    private LocalDateTime modifiedAt;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<Answer> answers = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<InviteOpponent> inviteOpponents = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<RegisterQuestion> registerQuestions = new ArrayList<>();

}
