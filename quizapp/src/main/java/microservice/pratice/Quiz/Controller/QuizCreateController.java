package microservice.pratice.Quiz.controller;

import microservice.pratice.Quiz.Model.DTO.QuizCalculateionDTO;
import microservice.pratice.Quiz.Model.DTO.QuizQuestionOptionDTO;
import microservice.pratice.Quiz.Model.DTO.Quizsubmit;
import microservice.pratice.Quiz.Service.QuizcreatinSerivce;
import microservice.pratice.Utils.SendResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/quizs")
public class QuizCreateController {

    @Autowired
    QuizcreatinSerivce quizcreatinSerivce;

    @GetMapping
    public ResponseEntity<SendResponse<List<QuizQuestionOptionDTO>>> createQuiz(@RequestParam("category") String cat,
                                                         @RequestParam("count") int count)
    {
        SendResponse<List<QuizQuestionOptionDTO>> sendResponse=new SendResponse<>();
        sendResponse.setMessage("Fetched succesfulyy");
        sendResponse.setData(quizcreatinSerivce.getQuestion(cat,count));
        sendResponse.setStatusCode(HttpStatus.ACCEPTED);

        return new ResponseEntity<>(sendResponse,sendResponse.getStatusCode());
    }

    @PostMapping("/submit")
    public  ResponseEntity<SendResponse> submit(@RequestBody List<Quizsubmit> quizsubmits){
        SendResponse<QuizCalculateionDTO> sendResponse=new SendResponse<>();
        sendResponse.setMessage("submited succesfulyy");
        sendResponse.setData(quizcreatinSerivce.calculatetheScore(quizsubmits));
        sendResponse.setStatusCode(HttpStatus.ACCEPTED);

        return new ResponseEntity<>(sendResponse,sendResponse.getStatusCode());
    }
}
