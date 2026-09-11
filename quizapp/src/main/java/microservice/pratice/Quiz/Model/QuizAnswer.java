package microservice.pratice.Quiz.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class QuizAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer questionAnswer_id;

    private String answer;


    @ManyToOne
    @JoinColumn(name = "quiz_question_id")
    @JsonBackReference
    private QuizQuestion quizQuestion;


    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getQuestionAnswer_id() {
        return questionAnswer_id;
    }

    public void setQuestionAnswer_id(Integer questionAnswer_id) {
        this.questionAnswer_id = questionAnswer_id;
    }

    public QuizQuestion getQuizQuestion() {
        return quizQuestion;
    }

    public void setQuizQuestion(QuizQuestion quizQuestion) {
        this.quizQuestion = quizQuestion;
    }
}
