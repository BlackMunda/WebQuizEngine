package org.example.quizengine.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class QuizDTO {
    private long id;
    private String title;
    private String text;
    private List<String> options;
}
