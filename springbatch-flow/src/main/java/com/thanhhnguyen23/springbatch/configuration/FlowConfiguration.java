package com.thanhhnguyen23.springbatch.configuration;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class FlowConfiguration {
	// TODO -- learn more about flows
	// TODO -- learn more about how to manage flows
	@Autowired
	public StepBuilderFactory stepBuilderFactory;
	
	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1")
				.tasklet((contribution, chunkContext) ->{
					System.out.println(">> Step 1 from inside flow \n");
					return RepeatStatus.FINISHED;
				}).build();
	}
	@Bean
	public Step step2() {
		return stepBuilderFactory.get("step2")
				.tasklet((contribution, chunkContext) ->{
					System.out.println(">> Step 2 from inside flow \n");
					return RepeatStatus.FINISHED;
				}).build();
	}
	@Bean
	public Flow foo() {
		FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("foo");
		
		flowBuilder.start(step1())
			.next(step2())
			.end();
		
		return flowBuilder.build();
	}


}
