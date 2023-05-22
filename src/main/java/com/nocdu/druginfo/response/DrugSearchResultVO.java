package com.nocdu.druginfo.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
public class DrugSearchResultVO {
    private Meta meta;
    private List<Documents> documents;

}