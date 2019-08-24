package br.com.devcave.springbatch.batch.exportemployee.manual;

import br.com.devcave.springbatch.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.LongStream;

@Slf4j
@StepScope
@Component
@RequiredArgsConstructor
public class EmployeeRangePartition implements Partitioner {

    private final EmployeeRepository employeeRepository;

    @Override
    public Map<String, ExecutionContext> partition(final int gridSize) {
        log.info("partition, gridSize={}", gridSize);
        long total = employeeRepository.count();
        long pages = (total / gridSize) + ((total % gridSize == 0) ? 0 : 1);

        Map<String, ExecutionContext> partitionMap = new HashMap<>();
        LongStream.range(0, pages).boxed()
                .map(page -> partitionMap.put("partition"+page, createExecutionContext(page, gridSize)));

        return partitionMap;
    }

    private ExecutionContext createExecutionContext(final Long page, final int gridSize){
        ExecutionContext executionContext = new ExecutionContext();
        executionContext.put("page", page.intValue());
        executionContext.put("size", gridSize);
        return executionContext;
    }
}

