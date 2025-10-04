package org.example.quizengine.quiz.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.quizengine.quiz.dto.Answers;
import org.example.quizengine.quiz.dto.Feedback;
import org.example.quizengine.quiz.entity.Quiz;
import org.example.quizengine.quiz.dto.QuizDTO;
import org.example.quizengine.quiz.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
public class QuizController implements CommandLineRunner {
    private final QuizService quizService;

    @Autowired
    public QuizController(QuizService quizService){
        this.quizService = quizService;
    }

    @GetMapping("/api/quizzes/{id}")
    public QuizDTO getQuiz(@PathVariable long id) {
        return quizService.getQuizById(id);
    }

    @GetMapping("/api/quizzes")
    public List<QuizDTO> getQuizzes(){
        return quizService.getAllQuizzes();
    }

    @PostMapping("/api/quizzes")
    public QuizDTO postAnswer(@Valid @RequestBody Quiz postedQuiz) {
        return quizService.createQuiz(postedQuiz);
    }

    @PostMapping("/api/quizzes/{id}/solve")
    public Feedback solveQuiz(@PathVariable long id,@Valid @RequestBody Answers answers){
        return quizService.solveThisQuiz(id, answers);
    }

    @Override
    public void run(String... args){
        System.out.println("done");
    }

}
