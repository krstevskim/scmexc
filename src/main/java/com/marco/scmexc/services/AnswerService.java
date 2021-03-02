package com.marco.scmexc.services;
import com.marco.scmexc.models.domain.Answer;
import com.marco.scmexc.models.domain.Question;
import com.marco.scmexc.models.domain.SmxUser;
import com.marco.scmexc.models.requests.AnswerRequest;
import com.marco.scmexc.repository.AnswerRepository;
import com.marco.scmexc.repository.QuestionRepository;
import com.marco.scmexc.repository.SmxUserRepository;
import com.marco.scmexc.security.UserPrincipal;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final SmxUserRepository userRepository;

    public AnswerService(AnswerRepository answerRepository, QuestionRepository questionRepository, SmxUserRepository userRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
        this.userRepository = userRepository;
    }

    public List<Answer> getAllByQuestion(Long questionID) {
        return this.answerRepository.findAllByQuestion_Id(questionID).stream().sorted(Comparator.comparing(Answer::getDatePosted)).collect(Collectors.toList());
    }

    public Answer addAnswer(AnswerRequest request, Long userID){
        //excepetion to add
        Question question = this.questionRepository.findById(request.questionID).orElse(null);
        SmxUser user = this.userRepository.findById(userID).orElse(null);
        Answer answer = new Answer();
        answer.setAnswer(request.answer);
        answer.setUpVotes(0);
        answer.setDownVotes(0);
        answer.setQuestion(question);
        answer.setAnsweredBy(user);
        answer.setDatePosted(ZonedDateTime.now());
        return this.answerRepository.save(answer);
    }
    public boolean incUpVotes(Long answerID,Long userID) {
        SmxUser user = this.userRepository.findById(userID).orElse(null);
        Answer answer = this.answerRepository.findById(answerID).orElse(null);
        if(!answer.getUpVotedBy().contains(user)){
            int upVotes = answer.getUpVotes()+1;
            answer.getUpVotedBy().add(user);
            answer.setUpVotes(upVotes);
            this.answerRepository.save(answer);
            return true;
        }
        return false;
    }

    public boolean incDownVotes(Long answerID,Long userID) {
        SmxUser user = this.userRepository.findById(userID).orElse(null);
        Answer answer = this.answerRepository.findById(answerID).orElse(null);
        if(!answer.getDownVotedBy().contains(user)){
            int downVotes = answer.getDownVotes()+1;
            answer.getDownVotedBy().add(user);
            answer.setDownVotes(downVotes);
            this.answerRepository.save(answer);
            return true;
        }
        return false;
    }
    public Answer deleteAnswerByID(Long answerID) {
        Answer answer = this.answerRepository.findById(answerID).orElse(null);
        this.answerRepository.delete(answer);
        return answer;
    }
    public Answer editAnswerByID(AnswerRequest request) {
        Answer answer = this.answerRepository.findById(request.answerID).orElse(null);
        answer.setAnswer(request.answer);
        return this.answerRepository.save(answer);
    }

    public Answer viewAnswerByID(Long answerID) {
        Answer answer = this.answerRepository.findById(answerID).orElse(null);
        return answer;
    }

    public Answer selectUseFullAnswer(AnswerRequest request) {
        Question question = this.questionRepository.findById(request.questionID).orElse(null);
        Answer correctAnswer = this.answerRepository.findById(request.answerID).orElse(null);
        question.setUsefulAnswer(correctAnswer);
        this.questionRepository.save(question);
        return correctAnswer;
    }

    public Answer getCorrectAnswer(Long questionID) {
        Question question = this.questionRepository.findById(questionID).orElse(null);
        if(question.getUsefulAnswer() != null) {
            Answer answer = answerRepository.findById(question.getUsefulAnswer().getId()).orElse(null);
            return answer;
        }
        return null;
    }

}
