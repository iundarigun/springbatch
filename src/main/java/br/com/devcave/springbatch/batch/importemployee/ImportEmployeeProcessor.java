package br.com.devcave.springbatch.batch.importemployee;

import br.com.devcave.springbatch.domain.Employee;
import br.com.devcave.springbatch.domain.EmployeeLine;
import br.com.devcave.springbatch.domain.Position;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Slf4j
@Component
public class ImportEmployeeProcessor implements ItemProcessor<EmployeeLine, Employee> {

    @Override
    public Employee process(final EmployeeLine item) throws Exception {
        // Uncomment this part to simulate retry
//        if (new Random().nextBoolean()){
//            throw new RuntimeException();
//        }
        try {

            return Employee.builder()
                    .id(item.getId())
                    .name(item.getName())
                    .position(Position.valueOf(item.getPosition()))
                    .startDate(parseString(item.getStartDate()))
                    .endDate(parseString(item.getEndDate()))
                    .build();
        } catch (Exception e) {
            log.error("Error processing item: " + item, e);
        }
        return null;
    }

    private LocalDate parseString(final String date) {
        if (StringUtils.isEmpty(date)) {
            return null;
        }
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
