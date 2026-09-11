package microservice.pratice.Quiz.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class QuizCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer questionCategory_id;

    private String category;


    @ManyToOne
    @JoinColumn(name = "quiz_question_id")
    @JsonBackReference
    private QuizQuestion quizQuestion;

    public Integer getQuestionCategory_id() {
        return questionCategory_id;
    }

    public void setQuestionCategory_id(Integer questionCategory_id) {
        this.questionCategory_id = questionCategory_id;
    }

    public QuizQuestion getQuizQuestion() {
        return quizQuestion;
    }

    public void setQuizQuestion(QuizQuestion quizQuestion) {
        this.quizQuestion = quizQuestion;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
