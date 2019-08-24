package br.com.devcave.springbatch.batch.exportemployee.manual;

import br.com.devcave.springbatch.domain.Employee;
import br.com.devcave.springbatch.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@StepScope
@Component
@RequiredArgsConstructor
public class ExportManualEmployeeReader implements ItemReader<Employee> {

    @Value("#{stepExecutionContext['page']}")
    private Integer page;

    @Value("#{stepExecutionContext['size']}")
    private Integer size;

    private final EmployeeRepository employeeRepository;

    private List<Employee> results = null;
    private int index = 0;

    @Override
    public Employee read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (results == null){
            results = employeeRepository.findByEnabled(true, PageRequest.of(page, 10)).getContent();
        }
        Employee result = null;
        if (index < results.size()) {
            result = results.get(index);
            index++;
        }
        return result;
    }
}
