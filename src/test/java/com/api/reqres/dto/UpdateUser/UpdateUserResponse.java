package com.api.reqres.dto.UpdateUser;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder

public class UpdateUserResponse {

    private String name;
    private String job;
    private String updatedAt;
}
