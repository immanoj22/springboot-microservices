package microservice.pratice.Quiz.Exception;

public class QuizNotHasenough extends RuntimeException{
    public QuizNotHasenough(String message){
        super(message);
    }
}
