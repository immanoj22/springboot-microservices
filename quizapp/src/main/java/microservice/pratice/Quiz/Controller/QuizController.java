package microservice.pratice.Quiz.Controller;

import microservice.pratice.Quiz.Model.DTO.QuizDTO;
import microservice.pratice.Quiz.Service.QuizService;
import microservice.pratice.Utils.SendResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/quiz")
public class QuizController {

    @Autowired
    QuizService quizService;

    @PostMapping()
    public ResponseEntity<SendResponse> addQuiz(@RequestBody QuizDTO quizDTO){
        SendResponse sendResponse=new SendResponse<QuizDTO>();
        quizService.addQuestion(quizDTO);
        return new ResponseEntity<>(sendResponse, HttpStatus.CREATED);
    }
}
