package cn.dreampie.resource.store.model;


import cn.dreampie.orm.Model;
import cn.dreampie.orm.annotation.Table;

/**
 * Created by ice on 15-1-22.
 */
@Table(name = "fun_store_monthly", cached = true)
public class StoreMonthly extends Model<StoreMonthly> {
  public final static StoreMonthly dao = new StoreMonthly();
}
