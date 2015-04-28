package cn.dreampie.resource.store.model;


import cn.dreampie.orm.Model;
import cn.dreampie.orm.annotation.Table;

import java.util.List;

/**
 * Created by ice on 15-1-22.
 */
@Table(name = "fun_gift_daily", cached = true)
public class GiftDaily extends Model<GiftDaily> {
  public final static GiftDaily dao = new GiftDaily();

  public List<GiftDaily> findMonthly(String where, Object... paras) {
    String sql = "SELECT SUM(`gift_daily`.out_count) out_count,SUM(`gift_daily`.out_stock) out_stock, `gift_daily`.bar_code," +
        "`gift_daily`.cost,`gift_daily`.product_name, `gift_daily`.date FROM fun_gift_daily `gift_daily` " +
        "WHERE " + where + " GROUP BY `gift_daily`.bar_code";
    return dao.find(sql, paras);
  }
}
