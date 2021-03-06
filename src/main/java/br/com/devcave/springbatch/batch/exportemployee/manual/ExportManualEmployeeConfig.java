package br.com.devcave.springbatch.batch.exportemployee.manual;

import br.com.devcave.springbatch.batch.exportemployee.springdata.ExportEmployeeProcessor;
import br.com.devcave.springbatch.batch.exportemployee.springdata.ExportEmployeeReader;
import br.com.devcave.springbatch.batch.exportemployee.springdata.ExportEmployeeWriter;
import br.com.devcave.springbatch.domain.Employee;
import br.com.devcave.springbatch.domain.EmployeeLine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.partition.PartitionHandler;
import org.springframework.batch.core.partition.StepExecutionSplitter;
import org.springframework.batch.core.partition.support.TaskExecutorPartitionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;

import java.util.Collection;

// TODO it is not working
@Slf4j
@Configuration
public class ExportManualEmployeeConfig {

    public static final String EXPORT_MANUAL_EMPLOYEE_JOB = "exportManualEmployeeJob";

    @Bean
    @JobScope
    public Step exportMasterEmployeeStep(final StepBuilderFactory stepBuilderFactory,
                                         final EmployeeRangePartition employeeRangePartition,
                                         final Step exportManualEmployeeStep,
                                         final TaskExecutor taskExecutor) {
        return stepBuilderFactory.get("exportMasterEmployeeStep")
                .partitioner("employeeRangePartition", employeeRangePartition)
                .gridSize(10)
                .step(exportManualEmployeeStep)
                .taskExecutor(taskExecutor)
                .build();
    }

    @Bean
    @JobScope
    public Step exportManualEmployeeStep(final StepBuilderFactory stepBuilderFactory,
                                         final ExportManualEmployeeReader exportManualEmployeeReader,
                                         final ExportEmployeeWriter exportEmployeeWriter,
                                         final ExportEmployeeProcessor exportEmployeeProcessor) {
        return stepBuilderFactory.get("exportManualEmployeeStep")
                .<Employee, EmployeeLine>chunk(10)
                .reader(exportManualEmployeeReader)
                .processor(exportEmployeeProcessor)
                .writer(exportEmployeeWriter)
                .build();
    }


    @Bean
    public Job exportManualEmployeeJob(final JobBuilderFactory jobBuilderFactory,
                                       final Step exportMasterEmployeeStep) {
        return jobBuilderFactory.get(EXPORT_MANUAL_EMPLOYEE_JOB)
                .incrementer(new RunIdIncrementer())
                .start(exportMasterEmployeeStep)
                .build();
    }

}
