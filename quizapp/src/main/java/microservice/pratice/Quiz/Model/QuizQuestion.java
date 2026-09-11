package microservice.pratice.Quiz.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
public class QuizQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer question_id;

    private String quizQuestion;

    @OneToMany(mappedBy = "quizQuestion", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<QuizCategory> category = new LinkedHashSet<>();

    @OneToMany(mappedBy = "quizQuestion", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<QuizAnswer> answers = new LinkedHashSet<>();


    public String getQuizQuestion() {
        return quizQuestion;
    }

    public void setQuizQuestion(String quizQuestion) {
        this.quizQuestion = quizQuestion;
    }

    public Set<QuizCategory> getCategory() {
        return category;
    }

    public void setCategory(Set<QuizCategory> category) {
        this.category = category;
    }

    public Set<QuizAnswer> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<QuizAnswer> answers) {
        this.answers = answers;
    }

    public Integer getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(Integer question_id) {
        this.question_id = question_id;
    }
}
