package com.api.reqres.dto.SingleUser;

import com.api.reqres.dto.Support.Support;
import com.api.reqres.dto.User;
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
