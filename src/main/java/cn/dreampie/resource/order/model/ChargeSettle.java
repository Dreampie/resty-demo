package cn.dreampie.resource.order.model;


import cn.dreampie.orm.Model;
import cn.dreampie.orm.annotation.Table;

/**
 * Created by ice on 15-1-22.
 */
@Table(name = "fun_charge_settle", cached = true)
public class ChargeSettle extends Model<ChargeSettle> {
  public final static ChargeSettle dao = new ChargeSettle();
}
