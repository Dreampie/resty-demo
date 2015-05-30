package cn.dreampie.resource.setting.model;


import cn.dreampie.orm.Model;
import cn.dreampie.orm.annotation.Table;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ice on 15-1-22.
 */
@Table(name = "fun_type", cached = true)
public class Type extends Model<Type> {
  public final static Type dao = new Type();


  public Map<String, Type> getTypes() {
    List<Type> types = Type.dao.findAll();
    Map<String, Type> results = new HashMap<String, Type>();

    for (Type type : types) {
      results.put(type.<String>get("name"), type);
    }
    return results;
  }
}
