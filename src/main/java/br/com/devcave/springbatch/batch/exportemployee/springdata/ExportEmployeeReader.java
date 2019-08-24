package br.com.devcave.springbatch.batch.exportemployee.springdata;

import br.com.devcave.springbatch.domain.Employee;
import br.com.devcave.springbatch.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@StepScope
@RequiredArgsConstructor
public class ExportEmployeeReader extends RepositoryItemReader<Employee> {

    private final EmployeeRepository employeeRepository;

    @Override
    public void afterPropertiesSet() throws Exception {
        setRepository(employeeRepository);
        setMethodName("findByEnabled");

        List parameters = new ArrayList();
        parameters.add(Boolean.TRUE);
        setArguments(parameters);

        Map<String, Sort.Direction> sort = new HashMap<String, Sort.Direction>();
        sort.put("id", Sort.Direction.ASC);
        setSort(sort);
    }
}
