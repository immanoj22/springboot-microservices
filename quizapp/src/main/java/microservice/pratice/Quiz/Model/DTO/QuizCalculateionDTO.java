package microservice.pratice.Quiz.Model.DTO;

import microservice.pratice.Quiz.Model.QuizQuestion;

import java.util.List;

public class QuizCalculateionDTO {
    private int correctCount;

    private int errorCount;

    private List<QuizQuestion> errorQuestion;

    public int getCorrectCount() {
        return correctCount;
    }

    public void setCorrectCount(int correctCount) {
        this.correctCount = correctCount;
    }

    public int getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(int errorCount) {
        this.errorCount = errorCount;
    }

    public List<QuizQuestion> getErrorQuestion() {
        return errorQuestion;
    }

    public void setErrorQuestion(List<QuizQuestion> errorQuestion) {
        this.errorQuestion = errorQuestion;
    }
}
