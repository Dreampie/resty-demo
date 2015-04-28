package cn.dreampie.resource.finance.model;


import cn.dreampie.orm.Model;
import cn.dreampie.orm.annotation.Table;

/**
 * Created by ice on 15-1-22.
 */
@Table(name = "fun_order_file", cached = true)
public class OrderFile extends Model<OrderFile> {
  public final static OrderFile dao = new OrderFile();
}
