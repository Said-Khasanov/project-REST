package com.khasanov.project_rest.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreatorResponseTo {
    private Long id;
    private String login;
    private String password;
    private String firstname;
    private String lastname;
}
