package cn.dreampie.resource.order.model;


import cn.dreampie.orm.Model;
import cn.dreampie.orm.annotation.Table;

/**
 * Created by ice on 15-1-22.
 */
@Table(name = "fun_order_product", cached = true)
public class OrderProduct extends Model<OrderProduct> {
  public final static OrderProduct dao = new OrderProduct();
}
