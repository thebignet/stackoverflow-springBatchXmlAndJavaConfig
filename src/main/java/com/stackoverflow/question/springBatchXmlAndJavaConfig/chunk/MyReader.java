package com.stackoverflow.question.springBatchXmlAndJavaConfig.chunk;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.FileSystemResource;

public class MyReader extends FlatFileItemReader<MyLine> {
  public MyReader(String inputFilePath) {
    DefaultLineMapper<MyLine> lineMapper = new DefaultLineMapper<MyLine>();
    DelimitedLineTokenizer myLineTokenizer = new DelimitedLineTokenizer();
    myLineTokenizer.setNames(new String[] {"key", "value"});
    lineMapper.setLineTokenizer(myLineTokenizer);
    BeanWrapperFieldSetMapper<MyLine> mapper = new BeanWrapperFieldSetMapper<MyLine>();
    mapper.setTargetType(MyLine.class);
    lineMapper.setFieldSetMapper(mapper);
    setLineMapper(lineMapper);
    if (inputFilePath != null) {
      setResource(new FileSystemResource(inputFilePath));
    }
  }

}
