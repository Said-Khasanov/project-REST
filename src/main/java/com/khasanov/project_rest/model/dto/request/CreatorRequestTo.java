package com.khasanov.project_rest.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreatorRequestTo {
    private String login;
    private String password;
    private String firstname;
    private String lastname;
}
