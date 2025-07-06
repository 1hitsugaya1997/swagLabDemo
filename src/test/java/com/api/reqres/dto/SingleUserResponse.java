package com.api.reqres.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class SingleUserResponse {
    private User data;
    private Support support;
}
