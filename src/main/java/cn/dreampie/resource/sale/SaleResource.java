package cn.dreampie.resource.sale;

import cn.dreampie.common.util.Maper;
import cn.dreampie.resource.ApiResource;
import cn.dreampie.resource.order.model.OrderDaily;
import cn.dreampie.resource.order.model.OrderDailyProduct;
import cn.dreampie.resource.order.model.OrderShop;
import cn.dreampie.route.annotation.API;
import cn.dreampie.route.annotation.GET;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by wangrenhui on 15/4/8.
 */
@API("/sales")
public class SaleResource extends ApiResource {
  @GET("/dailys")
  public Map daily(String month) {
    if (month == null || "".equals(month)) {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
      month = sdf.format(new Date());
    }
    return Maper.of("month", month, "data", OrderDaily.dao.findBy("date >= ? AND date <= ? ORDER BY date", month + "01", month + "31"));
  }

  @GET("/products")
  public Map products(String month) {
    if (month == null || "".equals(month)) {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
      month = sdf.format(new Date());
    }
    return Maper.of("month", month, "data", OrderDailyProduct.dao.unCache().findMonthly("`order_daily_product`.date >= ? AND `order_daily_product`.date <= ?", month + "01", month + "31"));
  }

  @GET("/monthlys")
  public Map monthly(String month) {
    if (month == null || "".equals(month)) {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
      month = sdf.format(new Date());
    }
    return Maper.of("month", month, "data", OrderShop.dao.findMonthly("`order_shop`.date >= ? AND `order_shop`.date <= ?", month + "01", month + "31"));
  }

  @GET("/shops")
  public Map shop(String month) {
    if (month == null || "".equals(month)) {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
      month = sdf.format(new Date());
    }
    return Maper.of("month", month, "data", OrderShop.dao.findBy("date >= ? AND date <= ? ORDER BY date", month + "01", month + "31"));
  }

}
