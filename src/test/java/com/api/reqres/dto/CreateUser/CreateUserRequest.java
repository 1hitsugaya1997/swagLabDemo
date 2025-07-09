package com.api.reqres.dto.CreateUser;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CreateUserRequest {
    private String name;
    private String job;
}