package org.example.quizengine.quiz.service;

import org.example.quizengine.quiz.dto.Answers;
import org.example.quizengine.quiz.service.mapper.MyMapper;
import org.example.quizengine.quiz.dto.Feedback;
import org.example.quizengine.quiz.dto.QuizDTO;
import org.example.quizengine.quiz.entity.Quiz;
import org.example.quizengine.quiz.repository.QuizRepository;
import org.springframework.stereotype.Service;

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

    public QuizDTO createQuiz(Quiz quiz){
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
}
