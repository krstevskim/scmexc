package com.marco.scmexc.models.response;

import java.time.LocalDate;
import java.time.ZonedDateTime;

public class MaterialResponse {

    public Long id;
    public String title;
    public String createdBy;
    public ZonedDateTime dateCreated;
    public boolean published;
    public String description;
    public String approvedBy;
    public Integer upVotes;
    public Integer downVotes;


    public MaterialResponse(Long id, String title, String createdBy, ZonedDateTime dateCreated, boolean published,
                            String description, String approvedBy, Integer upVotes, Integer downVotes) {
        this.id = id;
        this.title = title;
        this.createdBy = createdBy;
        this.dateCreated = dateCreated;
        this.published = published;
        this.description = description;
        this.approvedBy = approvedBy;
        this.upVotes = upVotes;
        this.downVotes = downVotes;
    }

    public static MaterialResponse of(Long id, String title, String createdBy, ZonedDateTime dateCreated, boolean published,
                                      String description, String approvedBy, Integer upVotes, Integer downVotes){
        return new MaterialResponse(id,title,createdBy,dateCreated,published,description,approvedBy,upVotes,downVotes);
    }
}
