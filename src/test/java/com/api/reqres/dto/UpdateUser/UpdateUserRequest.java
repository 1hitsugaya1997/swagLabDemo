package com.api.reqres.dto.UpdateUser;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder

public class UpdateUserRequest {

    private String name;
    private String job;
}
