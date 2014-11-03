package com.stackoverflow.question.springBatchXmlAndJavaConfig;

import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.step.job.DefaultJobParametersExtractor;
import org.springframework.stereotype.Component;

/**
 * Pass step context as job parameters to a sub-job
 */
@Component
public class JobStepParametersExtractor extends DefaultJobParametersExtractor {
  public JobStepParametersExtractor() {
    super();
    setUseAllParentParameters(true);
  }

  @Override
  public JobParameters getJobParameters(Job job, StepExecution stepExecution) {
    JobParametersBuilder parametersBuilder = new JobParametersBuilder(super.getJobParameters(job, stepExecution));
    for (Map.Entry<String, Object> entry : stepExecution.getExecutionContext().entrySet()) {
      if (entry.getValue() instanceof String) {
        parametersBuilder.addString(entry.getKey(), (String) entry.getValue());
      }
    }
    return parametersBuilder.toJobParameters();
  }

}
