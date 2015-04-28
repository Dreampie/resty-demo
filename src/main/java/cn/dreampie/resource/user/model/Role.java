package cn.dreampie.resource.user.model;

import cn.dreampie.orm.Model;
import cn.dreampie.orm.annotation.Table;

/**
 * Created by ice on 15-1-19.
 */
@Table(name = "sec_role", cached = true)
public class Role extends Model<Role> {
  public static final Role dao = new Role();
}
