package com.letter.question.service;

import com.letter.question.dto.QuestionResponse;
import com.letter.question.repository.QuestionCustomRepositoryImpl;
import com.letter.question.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final QuestionCustomRepositoryImpl questionCustomRepository;

    public ResponseEntity<List<QuestionResponse.QuestionList>> getQuestionsList() {
        return ResponseEntity.ok(questionCustomRepository.findAll());
    }

}
