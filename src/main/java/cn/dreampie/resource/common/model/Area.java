package cn.dreampie.resource.common.model;

import cn.dreampie.orm.Model;
import cn.dreampie.orm.annotation.Table;

/**
 * Created by ice on 15-1-22.
 */
@Table(name = "com_area", cached = true)
public class Area extends Model<Area> {
  public final static Area dao = new Area();
}
