package com.api.reqres.dto.Support;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class Support {
    private String url;
    private String text;
}
