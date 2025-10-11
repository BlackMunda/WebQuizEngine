package org.example.quizengine.quiz.service;

import org.example.quizengine.quiz.dto.Answers;
import org.example.quizengine.quiz.entity.AppUser;
import org.example.quizengine.quiz.service.mapper.MyMapper;
import org.example.quizengine.quiz.dto.Feedback;
import org.example.quizengine.quiz.dto.QuizDTO;
import org.example.quizengine.quiz.entity.Quiz;
import org.example.quizengine.quiz.repository.QuizRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class QuizService {
    private final QuizRepository quizRepo;
    private final MyMapper mapper;

    public QuizService(QuizRepository quizRepo, MyMapper mapper){
        this.quizRepo = quizRepo;
        this.mapper = mapper;
    }

    public QuizDTO getQuizById(long id){
        return mapper.turnIntoDTO(quizRepo.findById(id).orElseThrow(), QuizDTO.class);
    }

    public List<QuizDTO> getAllQuizzes(){
        return StreamSupport.stream(quizRepo.findAll().spliterator(), false)
                .map(quiz -> mapper.turnIntoDTO(quiz, QuizDTO.class)).toList();
    }

    public QuizDTO createQuiz(AppUser user, Quiz quiz){
        quiz.setUser(user);
        quizRepo.save(quiz);
        return mapper.turnIntoDTO(quiz, QuizDTO.class);
    }

    public Feedback solveThisQuiz(long id, Answers answer){
        Quiz quiz = quizRepo.findById(id).orElseThrow();

        List<Integer> submitted = Arrays.asList(answer.getAnswers());
        List<Integer> correct = quiz.getCorrectOption();

        if (correct.size() == submitted.size() && correct.containsAll(submitted)) {
            return new Feedback(true, "Congratulations, you are right");
        }
        System.out.println(quiz.getCorrectOption());
        System.out.println(Arrays.asList(answer.getAnswers()));
        return new Feedback(false, "Wrong answer! Please, try again");
    }

    public boolean existsById(long id){
            return quizRepo.existsById(id);
    }

    @Transactional
    public void deleteQuizById(AppUser user, long id){
        Quiz quiz = quizRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Quiz not found"));

        if (quiz.getUser().getId() != user.getId()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "It's not your quiz, bitch!");
        }

        quizRepo.deleteById(id);
    }
}
