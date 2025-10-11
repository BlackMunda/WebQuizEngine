package org.example.quizengine.quiz.controller;

import jakarta.validation.Valid;
import org.example.quizengine.quiz.dto.Answers;
import org.example.quizengine.quiz.dto.Feedback;
import org.example.quizengine.quiz.entity.AppUser;
import org.example.quizengine.quiz.entity.Quiz;
import org.example.quizengine.quiz.dto.QuizDTO;
import org.example.quizengine.quiz.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


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
    public QuizDTO postAnswer(@AuthenticationPrincipal AppUser user, @Valid @RequestBody Quiz postedQuiz) {
        return quizService.createQuiz(user, postedQuiz);
    }

    @PostMapping("/api/quizzes/{id}/solve")
    public Feedback solveQuiz(@PathVariable long id,@Valid @RequestBody Answers answers){
        return quizService.solveThisQuiz(id, answers);
    }

    @Transactional
    @DeleteMapping("/api/quizzes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteQuiz(@AuthenticationPrincipal AppUser user, @PathVariable long id){
        if (!quizService.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "quiz doesnt exist!!");
        }
        quizService.deleteQuizById(user, id);
    }

    @Override
    public void run(String... args){
        System.out.println("done");
    }
}