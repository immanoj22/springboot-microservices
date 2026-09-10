package microservice.pratice.Quiz.Model.DTO;

import java.util.List;

public class QuizDTO {

    private String Question;
    private List<CategoryDto> categoryList;
    private List<Answers> quizAnswers;

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public List<CategoryDto> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<CategoryDto> categoryList) {
        this.categoryList = categoryList;
    }

    public List<Answers> getQuizAnswers() {
        return quizAnswers;
    }

    public void setQuizAnswers(List<Answers> quizAnswers) {
        this.quizAnswers = quizAnswers;
    }
}
