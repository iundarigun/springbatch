package br.com.devcave.springbatch.batch.exportemployee.manual;

import br.com.devcave.springbatch.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.LongStream;

@StepScope
@Component
@RequiredArgsConstructor
public class EmployeeRangePartition implements Partitioner {

    private final EmployeeRepository employeeRepository;

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {
        long total = employeeRepository.count();
        long pages = (total / gridSize) + ((total % gridSize == 0) ? 0 : 1);

        Map<String, ExecutionContext> partitionMap = new HashMap<>();
        LongStream.range(0, pages).boxed()
                .map(page -> partitionMap.put("partition"+page, createExecutionContext(page)));

        return partitionMap;
    }

    private ExecutionContext createExecutionContext(final Long page){
        ExecutionContext executionContext = new ExecutionContext();
        executionContext.put("page", page.intValue());
        return executionContext;
    }
}

