package com.mobilise.task.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;

@Getter
@Setter
public class AuthorRequest {
    private String firstName;
    private String middleName;
    @Email
    private String email;
    private String lastName;
    private String bio;

}
