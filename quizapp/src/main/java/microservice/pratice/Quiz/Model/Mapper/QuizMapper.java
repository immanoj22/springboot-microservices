package microservice.pratice.Quiz.Model.Mapper;

import microservice.pratice.Quiz.Model.DTO.Answers;
import microservice.pratice.Quiz.Model.DTO.CategoryDto;
import microservice.pratice.Quiz.Model.DTO.QuizDTO;
import microservice.pratice.Quiz.Model.QuizAnswer;
import microservice.pratice.Quiz.Model.QuizCategory;
import microservice.pratice.Quiz.Model.QuizQuestion;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class QuizMapper {

    public QuizQuestion requestToEntity(QuizDTO quizDTO) {

        QuizQuestion quizQuestion = new QuizQuestion();
        quizQuestion.setQuizQuestion(quizDTO.getQuestion());

        Set<QuizAnswer> answers = quizDTO.getQuizAnswers().stream()
                .map(a -> {
                    QuizAnswer quizAnswer = new QuizAnswer();
                    quizAnswer.setAnswer(a.getAnswers());
                    quizAnswer.setQuizQuestion(quizQuestion); // ✅ set owning side
                    return quizAnswer;
                })
                .collect(Collectors.toSet());
        quizQuestion.setAnswers(answers);

        Set<QuizCategory> categories = quizDTO.getCategoryList().stream()
                .map(a -> {
                    QuizCategory quizCategory = new QuizCategory();
                    quizCategory.setCategory(a.getCategory());
                    quizCategory.setQuizQuestion(quizQuestion); // ✅ set owning side
                    return quizCategory;
                })
                .collect(Collectors.toSet());
        quizQuestion.setCategory(categories);

        return quizQuestion;
    }

    public QuizDTO entityToResponse(QuizQuestion quizQuestion) {

        QuizDTO quizDTO = new QuizDTO();
        quizDTO.setQuestion(quizQuestion.getQuizQuestion());

        List<Answers> answers = quizQuestion.getAnswers().stream()
                .map(qa -> {
                    Answers a = new Answers();
                    a.setAnswers(qa.getAnswer());
                    return a;
                })
                .collect(Collectors.toList());
        quizDTO.setQuizAnswers(answers);

        List<CategoryDto> categories = quizQuestion.getCategory().stream()
                .map(qc -> {
                    CategoryDto c = new CategoryDto();
                    c.setCategory(qc.getCategory());
                    return c;
                })
                .collect(Collectors.toList());
        quizDTO.setCategoryList(categories);

        return quizDTO;
    }
}
