package com.thanhhnguyen23.springbatch.configuration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // provides one or more @Bean methods and may be processed by the Spring Container
@EnableBatchProcessing // base configuration for setting up batch jobs
public class JobConfiguration {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1").tasklet(new Tasklet() {

			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println("Hello world! Batch processing is awesome!!");
				return RepeatStatus.FINISHED;
			}
		}).build();
	}

	/**
	 * Job: job defines the flow between the states
	 * 
	 * Step: step represents an independent unit of processing that makes up a job
	 * 
	 * Chunk: Item reader, processor, and writer
	 */
	
	@Bean
	public Job helloWorldJob() {
		return jobBuilderFactory.get("helloWorldJob").start(step1()).build();
	}
}
