package org.learning.springbatchdemo.config;

import org.learning.springbatchdemo.entity.MaleOscarWinners;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class SpringBatchConfig {
    @Bean
    public Job job(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
                   ItemReader<MaleOscarWinners> maleOscarWinnersItemReader,
                   ItemProcessor<MaleOscarWinners,MaleOscarWinners> maleOscarWinnersItemProcessor,
                   ItemWriter<MaleOscarWinners> maleOscarWinnersItemWriter){
        Step step = stepBuilderFactory.get("ETL-File-Load")
                .<MaleOscarWinners,MaleOscarWinners>chunk(20)
                .reader(maleOscarWinnersItemReader)
                .processor(maleOscarWinnersItemProcessor)
                .writer(maleOscarWinnersItemWriter)
                .exceptionHandler((context, throwable) ->throwable.printStackTrace())
                .build();
        return jobBuilderFactory.get("ETL-Load")
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
    }
    @Bean
    public FlatFileItemReader<MaleOscarWinners> maleOscarWinnersFlatFileItemReader(@Value("${input}")Resource resource){
        FlatFileItemReader<MaleOscarWinners> oscarWinnersFileItemReader = new FlatFileItemReader<>();
        oscarWinnersFileItemReader.setResource(resource);
        oscarWinnersFileItemReader.setName("CSV-Reader");
        oscarWinnersFileItemReader.setLinesToSkip(1);
        oscarWinnersFileItemReader.setLineMapper(lineMapper());
        return oscarWinnersFileItemReader;
    }
    @Bean
    public LineMapper<MaleOscarWinners> lineMapper() {
        DefaultLineMapper<MaleOscarWinners> defaultLineMapper =  new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("id","year","age","name","movie");
        BeanWrapperFieldSetMapper fieldSetMapper = new BeanWrapperFieldSetMapper();
        fieldSetMapper.setTargetType(MaleOscarWinners.class);
        defaultLineMapper.setLineTokenizer(lineTokenizer);
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);
        return defaultLineMapper;
    }
}
