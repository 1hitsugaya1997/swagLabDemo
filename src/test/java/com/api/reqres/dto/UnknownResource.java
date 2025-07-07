package com.api.reqres.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UnknownResource {

    private int id;
    private String name;
    private int year;
    private String color;

    @JsonProperty("pantone_value")
    private String pantoneValue;
}
