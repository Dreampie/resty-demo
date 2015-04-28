package cn.dreampie.resource.store.model;


import cn.dreampie.orm.Model;
import cn.dreampie.orm.annotation.Table;

/**
 * Created by ice on 15-1-22.
 */
@Table(name = "fun_gift", cached = true)
public class Gift extends Model<Gift> {
  public final static Gift dao = new Gift();
}
