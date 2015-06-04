package cn.dreampie.resource.order.service;

import cn.dreampie.orm.transaction.Transaction;

import java.util.Date;

/**
 * Created by wangrenhui on 15/2/7.
 */
public interface OrderService {

  @Transaction
  public void orderScan(Date start, Date end);

  @Transaction
  public void shopDaily(String date);

  @Transaction
  public void orderDaily(String date);
}
