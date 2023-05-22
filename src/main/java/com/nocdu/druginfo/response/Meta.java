package com.nocdu.druginfo.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class Meta {
    private int total_count;
    private boolean is_end;
}