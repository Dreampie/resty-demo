package cn.dreampie.resource.store.model;


import cn.dreampie.orm.Model;
import cn.dreampie.orm.annotation.Table;
import com.alibaba.fastjson.annotation.JSONField;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ice on 15-1-22.
 */
@Table(name = "fun_store_init", cached = true)
public class StoreInit extends Model<StoreInit> {
  public final static StoreInit dao = new StoreInit();

  @JSONField(serialize = false)
  public Map<String, StoreInit> getInits() {
    List<StoreInit> inits = findStoreInits();
    Map<String, StoreInit> results = new HashMap<String, StoreInit>();

    for (StoreInit init : inits) {
      results.put(init.<String>get("bar_code"), init);
    }
    return results;
  }

  public List<StoreInit> findStoreInits() {
    String sql = "SELECT * FROM fun_store_init s1 INNER JOIN (SELECT bar_code,max(date) date FROM fun_store_init GROUP BY bar_code) s2 ON s1.bar_code=s2.bar_code AND s1.date =s2.date ORDER BY s1.date DESC";
    return StoreInit.dao.find(sql);
  }
}
