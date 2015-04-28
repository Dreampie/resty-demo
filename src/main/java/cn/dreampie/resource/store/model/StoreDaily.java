package cn.dreampie.resource.store.model;


import cn.dreampie.orm.Model;
import cn.dreampie.orm.annotation.Table;

import java.util.List;

/**
 * Created by ice on 15-1-22.
 */
@Table(name = "fun_store_daily", cached = true)
public class StoreDaily extends Model<StoreDaily> {
  public final static StoreDaily dao = new StoreDaily();

  public List<StoreDaily> findMonthly(String where, Object... paras) {
    String sql = "SELECT SUM(`store_daily`.in_count) in_count,SUM(`store_daily`.out_count) out_count," +
        "SUM(`store_daily`.in_stock) in_stock,SUM(`store_daily`.out_stock) out_stock, `store_daily`.bar_code," +
        "`store_daily`.cost,`store_daily`.product_name, `store_daily`.date FROM fun_store_daily `store_daily` " +
        "WHERE " + where + " GROUP BY `store_daily`.bar_code";
    return dao.find(sql, paras);
  }
}
