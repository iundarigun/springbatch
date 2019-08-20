package br.com.devcave.springbatch.domain;

import lombok.Data;

@Data
public class EmployeeLine {
    private Long id;

    private String name;

    private String position;

    private String startDate;

    private String endDate;
}
