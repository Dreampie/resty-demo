package cn.dreampie.resource.user.model;

import cn.dreampie.orm.Model;
import cn.dreampie.orm.annotation.Table;

/**
 * Created by ice on 15-1-19.
 */
@Table(name = "sec_role_permission", cached = true)
public class RolePermission extends Model<RolePermission> {
  public static final RolePermission dao = new RolePermission();

}
