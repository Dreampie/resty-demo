package cn.dreampie.resource.order.model;

import cn.dreampie.orm.Model;
import cn.dreampie.orm.annotation.Table;

import java.util.List;

/**
 * Created by wangrenhui on 15/3/7.
 */
@Table(name = "fun_order_shop", cached = true)
public class OrderShop extends Model<OrderShop> {
  public static OrderShop dao = new OrderShop();

  public List<OrderShopProduct> getProducts() {
    if (this.get("products") == null && this.get("id") != null) {
      this.put("products", OrderShopProduct.dao.findBy("order_shop_id=?", this.get("id")));
    }
    return this.get("products");
  }

  public List<OrderShop> findMonthly(String where, Object... paras) {
    String sql = "SELECT SUM(`order_shop`.order_count) order_count,SUM(`order_shop`.count) count,SUM(`order_shop`.product_count) product_count, SUM(`order_shop`.sales) sales,SUM(`order_shop`.cost) cost" +
        ", SUM(`order_shop`.packing) packing,SUM(`order_shop`.profit) profit, SUM(`order_shop`.freight)freight,SUM(`order_shop`.point)point, SUM(`order_shop`.tax) tax" +
        ", SUM(`order_shop`.sales_profit) sales_profit, SUM(`order_shop`.sales_profit)/SUM(`order_shop`.sales)*10000 sales_profit_rate, SUM(`order_shop`.amortization) amortization" +
        ", SUM(`order_shop`.net_profit) net_profit, SUM(`order_shop`.net_profit)/SUM(`order_shop`.sales)*10000 net_profit_rate, `order_shop`.shop_id," +
        " `order_shop`.shop_name, `order_shop`.date FROM fun_order_shop `order_shop` WHERE " + where + " GROUP BY `order_shop`.shop_id";
    return dao.find(sql, paras);
  }

}
