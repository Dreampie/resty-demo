package cn.dreampie.resource.order;

import cn.dreampie.common.util.Maper;
import cn.dreampie.resource.ApiResource;
import cn.dreampie.resource.order.model.Order;
import cn.dreampie.resource.order.model.OrderDailyProduct;
import cn.dreampie.route.annotation.API;
import cn.dreampie.route.annotation.GET;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by ice on 15-1-22.
 */
@API("/orders")
public class OrderResource extends ApiResource {

  @GET
  public Map list(String start, String end, Integer pageNum, Integer shopId, Integer state, String code) {
    String where = "1=1";
    List<Object> paras = new ArrayList<Object>();

    if (shopId != null) {
      where += " AND shop_id=?";
      paras.add(shopId);
    }

    if (code != null && !code.isEmpty()) {
      where += " AND code=?";
      paras.add(code);
    } else {
      if (start != null) {
        where += " AND date>=?";
        paras.add(start);
      }
      if (end != null) {
        where += " AND date<=?";
        paras.add(end);
      }
      if (state != null) {
        where += " AND state=?";
        paras.add(state);
      }
      where += " ORDER BY id DESC";
    }

    if (pageNum == null) {
      pageNum = 1;
    }
    return Maper.of("start", start, "end", end, "data", Order.dao.paginateBy(pageNum, 100, where, paras.toArray()));
  }

  @GET("/products")
  public Map products(String month) {
    if (month == null || "".equals(month)) {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
      month = sdf.format(new Date());
    }
    return Maper.of("month", month, "data", OrderDailyProduct.dao.findMonthly("`order_daily_product`.date >= ? AND `order_daily_product`.date <= ?", month + "01", month + "31"));
  }
}
