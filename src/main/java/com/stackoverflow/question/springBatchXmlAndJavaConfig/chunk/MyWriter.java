package com.stackoverflow.question.springBatchXmlAndJavaConfig.chunk;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class MyWriter implements ItemWriter<MyLine> {

  private final static Logger LOGGER = LoggerFactory.getLogger(MyWriter.class);

  @Override
  public void write(List<? extends MyLine> items) throws Exception {
    for (MyLine item : items) {
      LOGGER.info(item.toString());
    }

  }
}
