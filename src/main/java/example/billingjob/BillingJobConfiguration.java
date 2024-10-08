package example.billingjob;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.batch.core.Job;
import org.springframework.jdbc.support.JdbcTransactionManager;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class BillingJobConfiguration {
    @Bean
    public Job job(JobRepository jobRepository, Step step1) {
        String[] requiredKeys = {"input.file"};
        DefaultJobParametersValidator parametersValidator = new DefaultJobParametersValidator();
        parametersValidator.setRequiredKeys(requiredKeys);

        return new JobBuilder("BillingJob", jobRepository)
                .start(step1)
// TODO: figure out how this work
//                .validator(parametersValidator)
                .build();
    }

    @Bean
    public Step step1 (JobRepository jobRepository, JdbcTransactionManager transactionManager) {
        return new StepBuilder("filePreparation", jobRepository)
                .tasklet(new FilePreparationTasklet(), transactionManager)
                .build();
    }
}
