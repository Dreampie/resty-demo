package cn.dreampie.resource.order.model;


import cn.dreampie.orm.Model;
import cn.dreampie.orm.annotation.Table;
import cn.dreampie.resource.setting.model.Point;

import java.util.List;

/**
 * Created by ice on 15-1-22.
 */
@Table(name = "fun_order", cached = true)
public class Order extends Model<Order> {
  public final static Order dao = new Order();

  public Point getPoint() {
    if (this.get("point") == null) {
      this.put("point", Point.dao.findFirstBy("shop_id=?", this.get("shop_id")));
    }
    return this.get("point");
  }

  public List<OrderProduct> getProducts() {
    if (this.get("products") == null) {
      this.put("products", OrderProduct.dao.findBy("order_id=?", this.get("id")));
    }
    return this.get("products");
  }
}
