package cn.dreampie.resource.setting.model;


import cn.dreampie.orm.Model;
import cn.dreampie.orm.annotation.Table;
import com.alibaba.fastjson.annotation.JSONField;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ice on 15-1-22.
 */
@Table(name = "fun_product", cached = true)
public class Product extends Model<Product> {
  public final static Product dao = new Product();

  @JSONField(serialize = false)
  public Map<String, Product> getProducts() {
    List<Product> products = findProducts();
    Map<String, Product> results = new HashMap<String, Product>();

    for (Product product : products) {
      results.put(product.getStr("bar_code"), product);
    }
    return results;
  }

  public List<Product> findProducts() {
    String sql = "SELECT * FROM fun_product p1 INNER JOIN (SELECT bar_code,max(date) date FROM fun_product GROUP BY bar_code) p2 ON p1.bar_code=p2.bar_code AND p1.date =p2.date ORDER BY p1.date DESC";
    return Product.dao.find(sql);
  }
}
