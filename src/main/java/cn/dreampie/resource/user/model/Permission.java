package cn.dreampie.resource.user.model;

import cn.dreampie.orm.Model;
import cn.dreampie.orm.annotation.Table;

/**
 * Created by ice on 15-1-19.
 */
@Table(name = "sec_permission", cached = true)
public class Permission extends Model<Permission> {
  public static final Permission dao = new Permission();
}
