package com.marco.scmexc.api;

import com.marco.scmexc.models.requests.AnswerRequest;
import com.marco.scmexc.models.response.AnswerResponse;
import com.marco.scmexc.models.response.ResponseMessage;
import com.marco.scmexc.services.AnswerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AnswerController {

    private final AnswerService service;

    public AnswerController(AnswerService service) {
        this.service = service;
    }

    @PostMapping("/api/question/addAnswer")
    public ResponseEntity<ResponseMessage> addAnswer(@RequestBody AnswerRequest request) {
        this.service.addAnswer(request);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Your answer has been added!!!"));
    }

    @GetMapping("/api/question/{id}/getAnswers")
    public ResponseEntity<List<AnswerResponse>> getAnswerByQuestion(@PathVariable Long id) {
        List<AnswerResponse> answerResponses = this.service.getAllByQuestion(id).stream()
                .map(answer -> {
                   return AnswerResponse.of(answer.getQuestion().getDescription(), answer.getAnswer(), answer.getUpVotes(), answer.getDownVotes());
                }).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(answerResponses);
    }

}
