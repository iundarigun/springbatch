package br.com.devcave.springbatch.batch.importemployee;

import br.com.devcave.springbatch.domain.EmployeeLine;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

@Component
@StepScope
public class ImportEmployeeReader extends FlatFileItemReader<EmployeeLine> {
    private static final String[] NAMES = {"id", "name", "position", "startDate", "endDate"};

    @Value("#{jobParameters[filePath]}")
    private String filePath;

    @Override
    public void afterPropertiesSet() throws Exception {
        setResource(new FileSystemResource(filePath));
        setLinesToSkip(1);
        setLineMapper(lineMapper());
    }

    private LineMapper<EmployeeLine> lineMapper() {

        final DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(";");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames(NAMES);

        final BeanWrapperFieldSetMapper<EmployeeLine> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(EmployeeLine.class);

        final DefaultLineMapper<EmployeeLine> defaultLineMapper = new DefaultLineMapper<>();
        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);

        return defaultLineMapper;
    }
}
