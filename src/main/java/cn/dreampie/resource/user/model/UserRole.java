package cn.dreampie.resource.user.model;

import cn.dreampie.orm.Model;
import cn.dreampie.orm.annotation.Table;

/**
 * Created by ice on 15-1-19.
 */
@Table(name = "sec_user_role", cached = true)
public class UserRole extends Model<UserRole> {
  public static final UserRole dao = new UserRole();
}
