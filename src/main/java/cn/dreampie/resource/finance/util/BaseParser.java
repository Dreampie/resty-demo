package cn.dreampie.resource.finance.util;


import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.*;


/**
 * Created by wangrenhui on 15/4/2.
 */
public class BaseParser {
  private File file;

  public BaseParser(File file) {
    this.file = file;
  }

  public BaseParser(String filePath) {
    file = new File(filePath);
  }


  public Iterable<CSVRecord> parse() {
    Reader reader;
    Iterable<CSVRecord> records = null;

    try {
      reader = new FileReader(file);
      records = CSVFormat.EXCEL.withHeader().parse(reader);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return records;
  }

}


