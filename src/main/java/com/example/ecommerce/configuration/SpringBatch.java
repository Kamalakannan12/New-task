package com.example.ecommerce.configuration;

import com.example.ecommerce.entity.UserDetails;

import com.example.ecommerce.repository.UserRepository;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableBatchProcessing
public class SpringBatch {

    // READER

    @Bean
    public FlatFileItemReader<UserDetails> reader(UserFieldSetMapper mapper) {

        FlatFileItemReader<UserDetails> reader =
                new FlatFileItemReader<>();

        // CSV FILE LOCATION FROM DEVICE

        reader.setResource(
                new FileSystemResource("D:/csv/batch.csv")
        );

        // skip header

        reader.setLinesToSkip(1);

        // tokenizer

        DelimitedLineTokenizer tokenizer =
                new DelimitedLineTokenizer();

        tokenizer.setDelimiter(",");

        tokenizer.setNames(
                "address",
                "email",
                "name",
                "phone",
                "password"
        );

        // line mapper

        DefaultLineMapper<UserDetails> lineMapper =
                new DefaultLineMapper<>();

        lineMapper.setLineTokenizer(tokenizer);

        lineMapper.setFieldSetMapper(mapper);

        reader.setLineMapper(lineMapper);

        return reader;
    }

    // WRITER

    @Bean
    public JpaItemWriter<UserDetails> writer(
            EntityManagerFactory entityManagerFactory
    ) {

        JpaItemWriter<UserDetails> writer =
                new JpaItemWriter<>();

        writer.setEntityManagerFactory(entityManagerFactory);

        return writer;
    }

    // STEP

    @Bean
    public Step step1(JobRepository jobRepository,
                      PlatformTransactionManager transactionManager,
                      FlatFileItemReader<UserDetails> reader,
                      Processor processor,
                      JpaItemWriter<UserDetails> writer) {

        return new StepBuilder("step1", jobRepository)
                .<UserDetails, UserDetails>chunk(10, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    // JOB
    @Bean
    public Job importUserJob(JobRepository jobRepository,
                             Step step1) {

        return new JobBuilder("importUserJob", jobRepository)
                .start(step1)
                .build();
    }
}