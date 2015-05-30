package cn.dreampie.resource.setting.model;


import cn.dreampie.orm.Model;
import cn.dreampie.orm.annotation.Table;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ice on 15-1-22.
 */
@Table(name = "fun_point", cached = true)
public class Point extends Model<Point> {
  public final static Point dao = new Point();


  public Map<Integer, Point> getPoints() {
    List<Point> points = dao.findAll();

    Map<Integer, Point> results = new HashMap<Integer, Point>();
    for (Point point : points) {
      results.put(point.<Integer>get("shop_id"), point);
    }
    return results;
  }
}
