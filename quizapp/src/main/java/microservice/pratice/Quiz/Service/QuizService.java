package microservice.pratice.Quiz.Service;

import microservice.pratice.Quiz.Exception.QuizException;
import microservice.pratice.Quiz.Model.DTO.QuizDTO;
import microservice.pratice.Quiz.Model.DTO.QuizMapper;
import microservice.pratice.Quiz.Model.QuizQuestion;
import microservice.pratice.Quiz.Repository.QuizQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuizService {

    @Autowired
    QuizQuestionRepository quizQuestionRepository;

    public QuizDTO addQuestion(QuizDTO quizDTO) {
        QuizMapper quizMapper=new QuizMapper();
        QuizQuestion quizQuestion=quizMapper.requestToEntity(quizDTO);

        try{
            quizQuestionRepository.save(quizQuestion);
        }catch (Exception exception){
            throw new QuizException("error happened");
        }
        return quizMapper.entityToResponse(quizQuestion);
    }
}
