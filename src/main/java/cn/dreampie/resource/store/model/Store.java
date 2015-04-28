package cn.dreampie.resource.store.model;


import cn.dreampie.orm.Model;
import cn.dreampie.orm.annotation.Table;

/**
 * Created by ice on 15-1-22.
 */
@Table(name = "fun_store", cached = true)
public class Store extends Model<Store> {
  public final static Store dao = new Store();
}
