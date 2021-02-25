package com.marco.scmexc.models.response;

import com.marco.scmexc.models.domain.Type;

import java.time.ZonedDateTime;

public class ItemResponse {
    public String name;
    public String question;
    public String url;
    public Type type;
    public ZonedDateTime timePosted;
    public long itemID;


    public ItemResponse(String name,String url,Type type,ZonedDateTime timePosted,String question,long itemID){
        this.name=name;
        this.url=url;
        this.type=type;
        this.timePosted=timePosted;
        this.question=question;
        this.itemID= itemID;
    }

    public static ItemResponse of(String name,String url,Type type,ZonedDateTime timePosted,String question,long itemID){
        return new ItemResponse(name, url, type,timePosted, question,itemID);
    }
}
