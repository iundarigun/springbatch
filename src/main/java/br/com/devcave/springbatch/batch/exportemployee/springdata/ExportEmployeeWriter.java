package br.com.devcave.springbatch.batch.exportemployee.springdata;

import br.com.devcave.springbatch.domain.Employee;
import br.com.devcave.springbatch.domain.EmployeeLine;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.FieldExtractor;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@StepScope
public class ExportEmployeeWriter extends FlatFileItemWriter<EmployeeLine> {

    private static final String HEADERS = "id;name;position;startDate;endDate";

    @Value("#{jobParameters[filePath]}")
    private String filePath;

    @Override
    public String doWrite(List<? extends EmployeeLine> items) {
        logger.info("write");
        return super.doWrite(items);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        setResource(new FileSystemResource(filePath));
        setAppendAllowed(true);
        setHeaderCallback(writer -> writer.write(HEADERS));
        setLineAggregator(createLineAggregator());
    }

    private LineAggregator<EmployeeLine> createLineAggregator() {
        DelimitedLineAggregator<EmployeeLine> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(";");

        FieldExtractor<EmployeeLine> fieldExtractor = createFieldExtractor();
        lineAggregator.setFieldExtractor(fieldExtractor);

        return lineAggregator;
    }

    private FieldExtractor<EmployeeLine> createFieldExtractor() {
        BeanWrapperFieldExtractor<EmployeeLine> extractor = new BeanWrapperFieldExtractor<>();
        extractor.setNames(new String[]{"id", "name", "position", "startDate", "endDate"});
        return extractor;
    }
}