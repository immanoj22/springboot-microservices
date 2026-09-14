package microservice.pratice.Quiz.Model.DTO;

public class QuizQuestionOptionDTO {
    private Integer SoNo;
    private String question;

    private int questionId;

    public Integer getSoNo() {
        return SoNo;
    }

    public void setSoNo(Integer soNo) {
        SoNo = soNo;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }
}
