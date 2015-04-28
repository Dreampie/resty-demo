package cn.dreampie.resource.order.model;


import cn.dreampie.orm.Model;
import cn.dreampie.orm.annotation.Table;

import java.util.List;

/**
 * Created by ice on 15-1-22.
 */
@Table(name = "fun_order_daily_product", cached = true)
public class OrderDailyProduct extends Model<OrderDailyProduct> {
  public final static OrderDailyProduct dao = new OrderDailyProduct();

  public List<OrderDailyProduct> findMonthly(String where, Object... paras) {
    String sql = "SELECT `order_daily_product`.bar_code,`order_daily_product`.product_name,`order_daily_product`.product_price," +
        "SUM(`order_daily_product`.product_count) count,SUM(`order_daily_product`.total_pay) total_pay" +
        " FROM fun_order_daily_product `order_daily_product` LEFT JOIN fun_order_daily `order_daily` " +
        "ON(`order_daily_product`.order_daily_id=`order_daily`.id) WHERE "
        + where + " GROUP BY `order_daily_product`.bar_code";
    return dao.find(sql, paras);
  }
}
