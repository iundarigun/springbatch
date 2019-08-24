package br.com.devcave.springbatch.batch.exportemployee.springdata;

import br.com.devcave.springbatch.domain.Employee;
import br.com.devcave.springbatch.domain.EmployeeLine;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@StepScope
@Component
public class ExportEmployeeProcessor implements ItemProcessor<Employee, EmployeeLine> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public EmployeeLine process(Employee item) throws Exception {
        return EmployeeLine.builder()
                .id(item.getId())
                .name(item.getName())
                .position(item.getPosition().name())
                .startDate(formatDate(item.getStartDate()))
                .endDate(formatDate(item.getEndDate()))
                .build();
    }

    private String formatDate(final LocalDate date) {
        if (date == null) {
            return null;
        }
        return date.format(formatter);
    }
}
