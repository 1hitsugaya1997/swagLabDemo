package com.api.reqres.dto.CreateUser;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CreateUserResponse {
    private String name;
    private String job;
    private String id;

    @JsonProperty("createdAt")
    private String createdAt;
}