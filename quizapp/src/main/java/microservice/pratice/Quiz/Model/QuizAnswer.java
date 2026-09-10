package microservice.pratice.Quiz.Model;

import jakarta.persistence.*;

@Entity
public class QuizAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer question_id;

    private String answer;


    @ManyToOne
    @JoinColumn(name = "quiz_question_id")
    private QuizQuestion quizQuestion;

    public Integer getQuestion_id() {
        return question_id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setQuestion_id(Integer question_id) {
        this.question_id = question_id;
    }


}
