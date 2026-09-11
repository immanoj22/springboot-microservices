package microservice.pratice.Quiz.Service;

import microservice.pratice.Quiz.Exception.QuizException;
import microservice.pratice.Quiz.Model.DTO.QuizDTO;
import microservice.pratice.Quiz.Model.Mapper.QuizMapper;
import microservice.pratice.Quiz.Model.QuizCategory;
import microservice.pratice.Quiz.Model.QuizQuestion;
import microservice.pratice.Quiz.Repository.QuizCategoryRepository;
import microservice.pratice.Quiz.Repository.QuizQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuizService {

    @Autowired
    QuizQuestionRepository quizQuestionRepository;

    @Autowired
    QuizCategoryRepository quizCategoryRepository;

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

    public Object getDetails() {
        List<QuizQuestion> quizQuestion=quizQuestionRepository.findAllDetails();
        return quizQuestion;
    }

    public Object getDetailsByCategory(String category) {
        System.out.println("category"+category);
        List<QuizCategory> quizCategories=quizCategoryRepository.findAllDetailsByCategory(category);
        System.out.println("### Found " + quizCategories.size() + " categories for input: [" + category + "]");
        return quizCategories;
    }
}
