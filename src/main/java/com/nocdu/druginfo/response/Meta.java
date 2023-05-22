package com.nocdu.druginfo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class Meta {
    private int total_count;
    @JsonProperty(namespace = "is_end")
    private boolean is_end;
}