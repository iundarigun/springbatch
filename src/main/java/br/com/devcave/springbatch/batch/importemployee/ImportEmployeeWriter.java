package br.com.devcave.springbatch.batch.importemployee;

import br.com.devcave.springbatch.domain.Employee;
import br.com.devcave.springbatch.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@StepScope
@RequiredArgsConstructor
public class ImportEmployeeWriter implements ItemWriter<Employee> {

    private final EmployeeRepository employeeRepository;

    @Override
    public void write(List<? extends Employee> items) throws Exception {
        log.info("write, thread={}", Thread.currentThread().getName());
        employeeRepository.saveAll(items.stream().map(this::process).collect(Collectors.toList()));
        log.info("write final, thread={}", Thread.currentThread().getName());
    }

    private Employee process(final Employee importedEmployee) {
        if (importedEmployee.getId() != null) {
            Employee employee = employeeRepository.findById(importedEmployee.getId()).orElse(null);
            if (employee == null) {
                log.warn("the id is not on database");
                importedEmployee.setId(null);
                return importedEmployee;
            }
            employee.setName(importedEmployee.getName());
            employee.setPosition(importedEmployee.getPosition());
            employee.setStartDate(importedEmployee.getStartDate());
            employee.setEndDate(importedEmployee.getEndDate());
            return employee;
        }
        return importedEmployee;
    }
}
