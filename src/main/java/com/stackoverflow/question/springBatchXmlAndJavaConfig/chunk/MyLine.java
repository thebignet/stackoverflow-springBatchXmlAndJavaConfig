package com.stackoverflow.question.springBatchXmlAndJavaConfig.chunk;

public class MyLine {
  private String key;
  private String label;

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  @Override
  public String toString() {
    return "MyLine{" + "key='" + key + '\'' + ", label='" + label + '\'' + '}';
  }
}
