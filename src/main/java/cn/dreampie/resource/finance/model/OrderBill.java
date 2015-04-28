package cn.dreampie.resource.finance.model;


import cn.dreampie.orm.Model;
import cn.dreampie.orm.annotation.Table;

/**
 * Created by ice on 15-1-22.
 */
@Table(name = "fun_order_bill", cached = true)
public class OrderBill extends Model<OrderBill> {
  public final static OrderBill dao = new OrderBill();
}
