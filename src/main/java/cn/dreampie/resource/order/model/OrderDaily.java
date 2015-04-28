package cn.dreampie.resource.order.model;

import cn.dreampie.orm.Model;
import cn.dreampie.orm.annotation.Table;

/**
 * Created by wangrenhui on 15/3/7.
 */
@Table(name = "fun_order_daily", cached = true)
public class OrderDaily extends Model<OrderDaily> {
  public static OrderDaily dao = new OrderDaily();

}
