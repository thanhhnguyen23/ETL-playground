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

@Configuration
@EnableBatchProcessing
public class JobConfiguration {
	
	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;


	public Step step1() {
		
		return stepBuilderFactory.get("step1").tasklet(new Tasklet() {
			
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println(">>step 1 \nI'm stepping through a step machine now!\n");
				return RepeatStatus.FINISHED;
			}
		}).build();
	}
	public Step step2() {
		
		return stepBuilderFactory.get("step2").tasklet(new Tasklet() {
			
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println(">>step 2 \nI'm stepping through a step machine now!\n");
				return RepeatStatus.FINISHED;
			}
		}).build();
	}
	public Step step3() {
		
		return stepBuilderFactory.get("step 3").tasklet(new Tasklet() {
			
			@Override
			public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
				System.out.println(">>step 3 \nI'm stepping through a step machine now!\n");
				return RepeatStatus.FINISHED;
			}
		}).build();
	}

	public Step step4() {
		
		return stepBuilderFactory.get("step4")
				.tasklet((contribution,chunkContext) ->{
					System.out.println(">>step 4 \nI'm doing step 4 with Java Lambdas!\n");
					return RepeatStatus.FINISHED;
		}).build();
	}
//	more examples of controlling the flow of states
	@Bean
	public Job transitionJobSimpleNext() {
		return jobBuilderFactory.get("transitionJobNext")
				.start(step1())
				.on("COMPLETED").to(step2())
				.from(step2()).on("COMPLETED").stopAndRestart(step3())
//				.from(step2()).on("COMPLETED").fail()
				.from(step3()).end() // step3 should not executed because step2 has failed
				.build();
	}

//	more granular control of flow
//	@Bean
//	public Job transitionJobSimpleNext() {
//		return jobBuilderFactory.get("transitionJobNext")
//				.start(step1())
//				.on("COMPLETED").to(step2())
//				.from(step2()).on("COMPLETED").to(step3())
//				.from(step3()).end()
//				.build();
//	}

//	simple transition steps
//	@Bean
//	public Job transitionJobSimpleNext() {
//		return jobBuilderFactory.get("transitionJobSimpleNext")
//				.start(step1())
//				.next(step2())
//				.next(step3())
//				.next(step4())
//				.build();
//	}
}
