package cn.dreampie.resource.store.model;


import cn.dreampie.orm.Model;
import cn.dreampie.orm.annotation.Table;

/**
 * Created by ice on 15-1-22.
 */
@Table(name = "fun_gift_monthly", cached = true)
public class GiftMonthly extends Model<GiftMonthly> {
  public final static GiftMonthly dao = new GiftMonthly();
}
