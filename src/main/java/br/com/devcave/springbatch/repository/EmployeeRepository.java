package br.com.devcave.springbatch.repository;

import br.com.devcave.springbatch.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    <S extends Employee> List<S> saveAll(Iterable<S> entities);
}
