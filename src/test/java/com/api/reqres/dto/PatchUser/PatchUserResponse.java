package com.api.reqres.dto.PatchUser;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder

public class PatchUserResponse {

    private String name;
    private String job;
    private String updatedAt;
}
