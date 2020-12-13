package com.marco.scmexc.models.response;

import java.time.LocalDate;

public class CourseResponse {

    public final Long id;
    public final String name;
    public final String code;
    public final String description;
    public final LocalDate dateCreated;

    private CourseResponse(Long id, String name, String code, String description, LocalDate dateCreated) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.description = description;
        this.dateCreated = dateCreated;
    }

    public static CourseResponse of(Long id, String name, String code, String description, LocalDate dateCreated){
        return new CourseResponse(id, name, code, description, dateCreated);
    }
}
