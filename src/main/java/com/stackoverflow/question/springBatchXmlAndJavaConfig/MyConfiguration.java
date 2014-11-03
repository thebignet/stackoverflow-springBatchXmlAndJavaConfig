package com.stackoverflow.question.springBatchXmlAndJavaConfig;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@Configuration
@ImportResource("job.xml")
public class MyConfiguration {
  private final static Logger LOGGER = LoggerFactory.getLogger(MyConfiguration.class);

  @Bean
  public JobExecution launchMyJob(JobLauncher jobLauncher, Job myJob) throws JobParametersInvalidException,
      JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
    return jobLauncher.run(myJob, new JobParametersBuilder().toJobParameters());
  }

  @Bean
  /** Crée un partitioner pour traiter séquentiellement chaque fichier en entrée. */
  public Partitioner filePartitioner() throws IOException {
    final List<File> inputFiles = new ArrayList<File>();
    for (File file : FileUtils.listFiles(new ClassPathResource("myFiles").getFile(), new String[] {"csv"}, false)) {
      inputFiles.add(file);
    }
    Collections.sort(inputFiles);
    LOGGER.info("There are " + inputFiles.size() + " files to parse");
    return new Partitioner() {
      @Override
      public Map<String, ExecutionContext> partition(int gridsize) {
        Map<String, ExecutionContext> map = new HashMap<String, ExecutionContext>();
        int i = 0;
        for (File inputFile : inputFiles) {
          ExecutionContext context = new ExecutionContext();
          context.putString("inputFilePath", inputFile.getAbsolutePath());
          map.put("partition" + (i++), context);
        }
        return map;
      }
    };
  }

  @Bean
  public DataSource dataSource() {
    EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder();
    return embeddedDatabaseBuilder.addScript("classpath:org/springframework/batch/core/schema-drop-hsqldb.sql")
        .addScript("classpath:org/springframework/batch/core/schema-hsqldb.sql").setType(EmbeddedDatabaseType.HSQL).build();
  }
}
