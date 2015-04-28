package cn.dreampie.resource;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

/**
 * Created by Dreampie on 15/4/28.
 */
public class DailyJob implements Job {
  public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
    System.out.println("hi," + new Date());
  }
}
