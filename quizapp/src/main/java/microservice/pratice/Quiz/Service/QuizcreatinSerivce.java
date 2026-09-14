package microservice.pratice.Quiz.Service;

import microservice.pratice.Quiz.Exception.QuizNotHasenough;
import microservice.pratice.Quiz.Model.DTO.QuizCalculateionDTO;
import microservice.pratice.Quiz.Model.DTO.QuizQuestionOptionDTO;
import microservice.pratice.Quiz.Model.DTO.Quizsubmit;
import microservice.pratice.Quiz.Model.QuizAnswer;
import microservice.pratice.Quiz.Model.QuizQuestion;
import microservice.pratice.Quiz.Repository.QuizQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class QuizcreatinSerivce {

    @Autowired
    QuizQuestionRepository quizQuestionRepository;
    public List<QuizQuestionOptionDTO> getQuestion(String cat, int count) {
        int quizCount=quizQuestionRepository.getCategoryCount(cat);

        if(quizCount<count){
            throw new QuizNotHasenough("Try later not enough question found");
        }
        PageRequest pageable= PageRequest.of(0,count);
        List<QuizQuestion> quizQuestions=quizQuestionRepository.findWithCategory(cat,pageable);

        List<QuizQuestionOptionDTO> quizQuestionOptionDTOS=
                IntStream.range(0,quizQuestions.size()).
                        mapToObj(i->{
                            QuizQuestion quizQuestion=quizQuestions.get(i);

                            QuizQuestionOptionDTO quiz=new QuizQuestionOptionDTO();
                            quiz.setSoNo(i+1);
                            quiz.setQuestionId(quizQuestion.getQuestion_id());
                            quiz.setQuestion(quizQuestion.getQuizQuestion());
                            return quiz;
                        }).
                toList();

        System.out.println("quizCount"+quizCount);
        return quizQuestionOptionDTOS;
    }

    public QuizCalculateionDTO calculatetheScore(
            List<Quizsubmit> quizsubmits) {

        int correctcount = 0;

        int total = quizsubmits.size();

        // Get all submitted question IDs
        List<Integer> questionIds = quizsubmits.stream()
                .map(Quizsubmit::getQuestionId)
                .toList();

        // Fetch questions from database
        List<QuizQuestion> quizQuestions =
                quizQuestionRepository.findAllById(questionIds);

        // Convert List to Map
        Map<Integer, QuizQuestion> questionMap =
                quizQuestions.stream()
                        .collect(Collectors.toMap(
                                QuizQuestion::getQuestion_id,
                                q -> q
                        ));

        List<QuizQuestion> errorquestion = new ArrayList<>();

        for (Quizsubmit submit : quizsubmits) {

            QuizQuestion question =
                    questionMap.get(submit.getQuestionId());

            if (question == null) {
                continue;
            }

            Set<QuizAnswer> answers = question.getAnswers();
            boolean correct = answers.stream()
                    .anyMatch(a ->
                            a.getAnswer().equalsIgnoreCase(submit.getAnswer())
                    );


            if (correct) {
                correctcount++;
            } else {
                errorquestion.add(question);
            }
        }

        int errors = total - correctcount;

        QuizCalculateionDTO dto = new QuizCalculateionDTO();

        dto.setCorrectCount(correctcount);
        dto.setErrorCount(errors);
        dto.setErrorQuestion(errorquestion);

        return dto;
    }

}
