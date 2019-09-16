package com.thanhhnguyen23.springbatch.configuration;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

public class FlowFirstConfiguration {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;


	public Step myStep() {
		return stepBuilderFactory.get("myStep")
//-------------------------------------------------------------------------------- 
				/*
				 * lambdas example
				 */
				.tasklet((contribution, chunkContext) -> {
					System.out.println("myStep was executed in flowFirstConfig");
					System.out.println("inside FlowFirst");
					return RepeatStatus.FINISHED;
				}).build();

		/*
		 * inner class example
		 * 
		 * .tasklet(new Tasklet() {
		 * 
		 * @Override public RepeatStatus execute(StepContribution contribution,
		 * ChunkContext chunkContext) throws Exception { return RepeatStatus.FINISHED; }
		 * }).build();
		 */
//-------------------------------------------------------------------------------- 
	}

	@Bean
	public Job flowFirstJob(Flow flow) {

		return jobBuilderFactory.get("flowFirstJob")
//				.start(flow)
//				.next(myStep())
				
				.start(myStep())
				.on("COMPLETED").to(flow)
				.end()
				.build();
	}

}
