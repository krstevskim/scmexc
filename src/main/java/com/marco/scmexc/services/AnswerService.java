package com.marco.scmexc.services;

import com.marco.scmexc.models.domain.Answer;
import com.marco.scmexc.models.domain.Question;
import com.marco.scmexc.models.requests.AnswerRequest;
import com.marco.scmexc.repository.AnswerRepository;
import com.marco.scmexc.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerService {

    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;

    public AnswerService(AnswerRepository answerRepository, QuestionRepository questionRepository) {
        this.answerRepository = answerRepository;
        this.questionRepository = questionRepository;
    }

    public List<Answer> getAllByQuestion(Long questionID) {
        return this.answerRepository.findAllByQuestion_Id(questionID);
    }

    public Answer addAnswer(AnswerRequest request){
        //excepetion todo
        Question question = this.questionRepository.findById(request.questionID).orElse(null);
        Answer answer = new Answer();
        answer.setAnswer(request.answer);
        answer.setUpVotes(0);
        answer.setDownVotes(0);
        answer.setQuestion(question);
        return this.answerRepository.save(answer);
    }
}
