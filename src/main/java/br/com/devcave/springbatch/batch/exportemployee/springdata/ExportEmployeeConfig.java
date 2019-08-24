package br.com.devcave.springbatch.batch.exportemployee.springdata;

import br.com.devcave.springbatch.domain.Employee;
import br.com.devcave.springbatch.domain.EmployeeLine;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Slf4j
@Configuration
public class ExportEmployeeConfig {

    public static final String EXPORT_EMPLOYEE_JOB = "exportEmployeeJob";

    @Bean
    public Step exportEmployeeStep(final StepBuilderFactory stepBuilderFactory,
                                   final ExportEmployeeReader reader,
                                   final ExportEmployeeWriter writer,
                                   final ExportEmployeeProcessor processor,
                                   final TaskExecutor taskExecutor) {
        return stepBuilderFactory.get("exportEmployeeStep")
                .<Employee, EmployeeLine>chunk(5)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .taskExecutor(taskExecutor)
                .throttleLimit(4)
                .build();
    }

    @Bean
    public Job exportEmployeeJob(final JobBuilderFactory jobBuilderFactory,
                                 final Step exportEmployeeStep) {
        return jobBuilderFactory.get(EXPORT_EMPLOYEE_JOB)
                .incrementer(new RunIdIncrementer())
                .flow(exportEmployeeStep)
                .end()
                .build();
    }

}
