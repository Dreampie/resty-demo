package cn.dreampie.resource.store;

import cn.dreampie.common.util.Maper;
import cn.dreampie.resource.ApiResource;
import cn.dreampie.resource.store.model.StoreMonthly;
import cn.dreampie.route.annotation.API;
import cn.dreampie.route.annotation.GET;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by ice on 15-1-22.
 */
@API("/stores")
public class StoreResource extends ApiResource {

  @GET("/monthlys")
  public Map monthly(String month) {
    if (month == null || "".equals(month)) {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
      month = sdf.format(new Date());
    }
    return Maper.of("month", month, "data", StoreMonthly.dao.findBy("date = ? ", month));
  }

}
