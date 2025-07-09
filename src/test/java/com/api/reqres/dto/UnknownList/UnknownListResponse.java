package com.api.reqres.dto.UnknownList;

import com.api.reqres.dto.Support.Support;
import com.api.reqres.dto.Unknown.UnknownResource;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UnknownListResponse {

    private int page;

    @JsonProperty("per_page")
    private int perPage;

    private int total;

    @JsonProperty("total_pages")
    private int totalPages;

    private List<UnknownResource> data;
    private Support support;
}
