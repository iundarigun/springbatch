package br.com.devcave.springbatch.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeLine {
    private Long id;

    private String name;

    private String position;

    private String startDate;

    private String endDate;
}
