package com.marco.scmexc.api;
import com.marco.scmexc.models.domain.Answer;
import com.marco.scmexc.models.domain.Question;
import com.marco.scmexc.models.requests.AnswerRequest;
import com.marco.scmexc.models.response.AnswerResponse;
import com.marco.scmexc.models.response.ResponseMessage;
import com.marco.scmexc.security.CurrentUser;
import com.marco.scmexc.security.UserPrincipal;
import com.marco.scmexc.services.AnswerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/answers")
public class AnswerController {

    private final AnswerService service;

    public AnswerController(AnswerService service) {
        this.service = service;
    }

    @PostMapping("/addAnswer")
    public ResponseEntity<ResponseMessage> addAnswer(@CurrentUser UserPrincipal user,@RequestBody AnswerRequest request) {
        this.service.addAnswer(request,user.getId());
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Your answer has been added!!!"));
    }

    @GetMapping("/{questionID}/getAnswers")
    public ResponseEntity<List<AnswerResponse>> getAnswerByQuestion(@PathVariable Long questionID) {
        List<AnswerResponse> answerResponses = this.service.getAllByQuestion(questionID).stream()
                .map(answer -> {
                   return AnswerResponse.of(answer.getQuestion().getDescription(), answer.getAnswer(), answer.getUpVotes(), answer.getDownVotes(),answer.getAnsweredBy().getEmail(),answer.getDatePosted());
                }).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(answerResponses);
    }

    @PostMapping("/{answerID}/incUpVotes")
    public ResponseEntity<AnswerResponse> incUpVotes(@PathVariable Long answerID,@CurrentUser UserPrincipal userPrincipal) {
        Answer answer = this.service.incUpVotes(answerID,userPrincipal.getId());
        AnswerResponse response=AnswerResponse.of(answer.getQuestion().getDescription(), answer.getAnswer(), answer.getUpVotes(), answer.getDownVotes(),answer.getAnsweredBy().getEmail(),answer.getDatePosted());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/{answerID}/incDownVotes")
    public ResponseEntity<AnswerResponse> incDownVotes(@PathVariable Long answerID, @CurrentUser UserPrincipal userPrincipal) {
        Answer answer = this.service.incDownVotes(answerID, userPrincipal.getId());
        AnswerResponse response=AnswerResponse.of(answer.getQuestion().getDescription(), answer.getAnswer(), answer.getUpVotes(), answer.getDownVotes(),answer.getAnsweredBy().getEmail(),answer.getDatePosted());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/delete/{answerID}")
    public AnswerResponse deleteAnswer(@PathVariable Long answerID) {
        Answer answer = this.service.deleteAnswerByID(answerID);
        return AnswerResponse.of(answer.getQuestion().getDescription(), answer.getAnswer(), answer.getUpVotes(), answer.getDownVotes(),answer.getAnsweredBy().getEmail(),answer.getDatePosted());
    }

    @PostMapping("/edit")
    public AnswerResponse editAnswer(@RequestBody AnswerRequest request){
        Answer answer = this.service.editAnswerByID(request);
        return AnswerResponse.of(answer.getQuestion().getDescription(), answer.getAnswer(), answer.getUpVotes(), answer.getDownVotes(),answer.getAnsweredBy().getEmail(),answer.getDatePosted());
    }

    @GetMapping("/{answerID}/getAnswer")
    public AnswerResponse viewAnswer(@PathVariable Long answerID){
        Answer answer = this.service.viewAnswerByID(answerID);
        return AnswerResponse.of(answer.getQuestion().getDescription(), answer.getAnswer(), answer.getUpVotes(), answer.getDownVotes(),answer.getAnsweredBy().getEmail(),answer.getDatePosted());
    }

    @PostMapping("/selectAnswer")
    public AnswerResponse selectUseFullAnswer(@RequestBody AnswerRequest request) {
        Answer answer = this.service.selectUseFullAnswer(request);
        return AnswerResponse.of(answer.getQuestion().getDescription(), answer.getAnswer(), answer.getUpVotes(), answer.getDownVotes(),answer.getAnsweredBy().getEmail(),answer.getDatePosted());
    }

    @GetMapping("/{questionID}/getCorrectAnswer")
    public AnswerResponse getCorrectAnswer(@PathVariable Long questionID) {
        Answer answer = this.service.getCorrectAnswer(questionID);
        return AnswerResponse.of(answer.getQuestion().getDescription(), answer.getAnswer(), answer.getUpVotes(), answer.getDownVotes(),answer.getAnsweredBy().getEmail(),answer.getDatePosted());

    }
}