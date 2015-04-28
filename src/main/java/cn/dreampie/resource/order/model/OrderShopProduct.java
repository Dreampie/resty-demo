package cn.dreampie.resource.order.model;


import cn.dreampie.orm.Model;
import cn.dreampie.orm.annotation.Table;

/**
 * Created by ice on 15-1-22.
 */
@Table(name = "fun_order_shop_product", cached = true)
public class OrderShopProduct extends Model<OrderShopProduct> {
  public final static OrderShopProduct dao = new OrderShopProduct();
}
