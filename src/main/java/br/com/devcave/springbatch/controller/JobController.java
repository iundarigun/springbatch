package br.com.devcave.springbatch.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("job")
@RequiredArgsConstructor
public class JobController {

    private final JobLauncher jobLauncher;

    @Qualifier("importEmployeeJob")
    private final Job importEmployeeJob;

    @PostMapping
    public HttpEntity<?> job(@RequestBody final String path) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("filePath", path)
                .addString("time", LocalDateTime.now().format(dateTimeFormatter))
                .toJobParameters();

        jobLauncher.run(importEmployeeJob, jobParameters);

        return ResponseEntity.noContent().build();
    }
}
