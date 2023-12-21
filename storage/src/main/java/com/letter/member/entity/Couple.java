package com.letter.member.entity;

import com.letter.question.entity.Answer;
import com.letter.question.entity.SelectQuestion;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "YI_COUPLE", schema = "YI")
public class Couple {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CP_ID", nullable = false)
    private Long id;

    @Size(max = 1)
    @NotNull
    @Column(name = "IS_SHOW", nullable = false, length = 1)
    private String isShow;

    @CreatedDate
    @NotNull
    @Column(name = "STARTED_AT", nullable = false)
    private LocalDateTime startedAt;

    @OneToMany(mappedBy = "couple", fetch = FetchType.LAZY)
    private List<SelectQuestion> selectQuestions = new ArrayList<>();

    @OneToMany(mappedBy = "couple", fetch = FetchType.LAZY)
    private List<Member> members = new ArrayList<>();

    @OneToMany(mappedBy = "couple")
    private List<Answer> answers = new ArrayList<>();

}
