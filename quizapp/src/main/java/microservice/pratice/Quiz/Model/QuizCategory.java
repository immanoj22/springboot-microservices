package microservice.pratice.Quiz.Model;

import jakarta.persistence.*;

@Entity
public class QuizCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer question_id;

    private String category;


    @ManyToOne
    @JoinColumn(name = "quiz_question_id")
    private QuizQuestion quizQuestion;

    public Integer getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(Integer question_id) {
        this.question_id = question_id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
