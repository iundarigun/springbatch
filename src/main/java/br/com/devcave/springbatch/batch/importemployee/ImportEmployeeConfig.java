package br.com.devcave.springbatch.batch.importemployee;

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
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.retry.backoff.BackOffPolicy;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Slf4j
@Configuration
public class ImportEmployeeConfig {

    public static final String IMPORT_EMPLOYEE_JOB = "importEmployeeJob";

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(15);

        return taskExecutor;
    }


    @Bean
    public Step importEmployeeStep(final StepBuilderFactory stepBuilderFactory,
                                   final ImportEmployeeReader reader,
                                   final ImportEmployeeWriter writer,
                                   final ImportEmployeeProcessor processor,
                                   final TaskExecutor taskExecutor) {
        return stepBuilderFactory.get("importEmployeeStep")
                .<EmployeeLine, Employee>chunk(10)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .faultTolerant()
                .retry(Exception.class)
                .retryLimit(10)
                .backOffPolicy(backOffPolicy())
                .taskExecutor(taskExecutor)
                .throttleLimit(4)
                .build();
    }

    @Bean
    public Job importEmployeeJob(final JobBuilderFactory jobBuilderFactory,
                                 final Step importEmployeeStep) {
        return jobBuilderFactory.get(IMPORT_EMPLOYEE_JOB)
                .incrementer(new RunIdIncrementer())
                .flow(importEmployeeStep)
                .end()
                .build();
    }

    private BackOffPolicy backOffPolicy() {
        FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
        fixedBackOffPolicy.setBackOffPeriod(5000);
        return fixedBackOffPolicy;
    }


}
