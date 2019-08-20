package br.com.devcave.springbatch.repository;

import br.com.devcave.springbatch.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
