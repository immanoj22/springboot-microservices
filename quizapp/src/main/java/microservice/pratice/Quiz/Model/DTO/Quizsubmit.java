package microservice.pratice.Quiz.Model.DTO;

public class Quizsubmit {
    private String quiztion;
    private int questionId;

        private String answer;

    public String getQuiztion() {
        return quiztion;
    }

    public void setQuiztion(String quiztion) {
        this.quiztion = quiztion;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
