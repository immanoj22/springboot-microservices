package microservice.pratice.Quiz.controller;

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
        sendResponse.setData(quizService.addQuestion(quizDTO));
        sendResponse.setMessage("Question added succesfully");
        sendResponse.setStatusCode(HttpStatus.CREATED);
        return new ResponseEntity<>(sendResponse,sendResponse.getStatusCode() );
    }

    @GetMapping
    public ResponseEntity<SendResponse> getDetails(){
        SendResponse sendResponse=new SendResponse<>();
        sendResponse.setData(quizService.getDetails());
        sendResponse.setStatusCode(HttpStatus.OK);
        return new ResponseEntity<>(sendResponse,sendResponse.getStatusCode());
    }

    @GetMapping("/{category}")
    public ResponseEntity<SendResponse> getByCategory(@PathVariable("category") String category){
        SendResponse sendResponse=new SendResponse<>();
        sendResponse.setData(quizService.getDetailsByCategory(category));
        sendResponse.setStatusCode(HttpStatus.OK);
        return new ResponseEntity<>(sendResponse,sendResponse.getStatusCode());
    }
}
