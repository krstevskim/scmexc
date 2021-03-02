package com.marco.scmexc.models.response;

import java.time.ZonedDateTime;

public class AnswerResponse {

    public String question;
    public String answer;
    public int upVotes;
    public int downVotes;
    public String answeredBy;
    public ZonedDateTime time;

    public AnswerResponse(String question,String answer,int upVotes,int downVotes,String answeredBy,ZonedDateTime time) {
        this.question= question;
        this.answer=answer;
        this.upVotes=upVotes;
        this.downVotes=downVotes;
        this.answeredBy=answeredBy;
        this.time=time;

    }

    public static AnswerResponse of(String question,String answer,int upVotes,int downVotes,String createdBy,ZonedDateTime time) {
        return  new AnswerResponse(question, answer, upVotes, downVotes,createdBy,time);
    }

}
