package com.api.reqres.dto.PatchUser;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder

public class PatchUserRequest {

    private String name;
    private String job;
}
