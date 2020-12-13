package com.marco.scmexc.models.response;

import org.apache.catalina.User;

public class UserResponse {
    public final String username;
    public final String firstName;
    public final String lastName;
    public final String email;

    private UserResponse(String username, String firstName, String lastName, String email) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public static UserResponse of(String username, String firstName, String lastName, String email) {
        return new UserResponse(username, firstName, lastName, email);
    }
}
