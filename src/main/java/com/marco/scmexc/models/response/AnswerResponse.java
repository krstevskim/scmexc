package com.marco.scmexc.models.response;

public class AnswerResponse {

    public String question;
    public String answer;
    public int upVotes;
    public int downVotes;

    public AnswerResponse(String question,String answer,int upVotes,int downVotes) {
        this.question= question;
        this.answer=answer;
        this.upVotes=upVotes;
        this.downVotes=downVotes;
    }

    public static AnswerResponse of(String question,String answer,int upVotes,int downVotes) {
        return  new AnswerResponse(question, answer, upVotes, downVotes);
    }

}
