package br.com.fiap.handsonspringbatch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class BatchConfiguration {

    @Bean
    public Job processarPerson (JobRepository jobRepository,Step step){


        return new JobBuilder("processarPerson",jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
    }

    @Bean
    public Step step (JobRepository jobRepository,
                      PlatformTransactionManager platformTransactionManager,
                      ItemReader<Person> itemReader,
                      ItemWriter<Person> itemWriter,
                      ItemProcessor<Person,Person> itemProcessor){


        return new StepBuilder("step",jobRepository)
                .<Person,Person>chunk(20,platformTransactionManager)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();
    }

    @Bean
    public ItemReader<Person> itemReader (){
        BeanWrapperFieldSetMapper<Person> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Person.class);

        return new FlatFileItemReaderBuilder<Person>()
                .name("personItemReader")
                .resource(new ClassPathResource("people.csv"))
                .delimited()
                .names("name","streetName","number","city","country","email","phoneNumber")
                .fieldSetMapper(fieldSetMapper)
                .build();

    }

    @Bean
    public ItemWriter<Person> itemWriter (DataSource dataSource){
        return new JdbcBatchItemWriterBuilder<Person>()
                .dataSource(dataSource)
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO person "
                        + "(name,streetName,number,city,country,email,phoneNumber, create_date_time) "
                        + "VALUES (:name,:streetName,:number,:city,:country,:email,:phoneNumber, :create_date_time)")
                .build();

    }

    @Bean
    public ItemProcessor<Person,Person> itemProcessor (){


        return new PersonProcessor();
    }




}
