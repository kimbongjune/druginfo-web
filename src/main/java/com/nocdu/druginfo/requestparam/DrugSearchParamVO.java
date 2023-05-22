package com.nocdu.druginfo.requestparam;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Data
@ToString
public class DrugSearchParamVO {
    private String query;
    private int page;
    private int limitPageSize = 15;
    private String shape;
    private String dosageForm;
    private String printFront;
    private String printBack;
    private String colorClass;
    private String line;
}
