package cn.dreampie.resource.user.model;

import cn.dreampie.orm.Model;
import cn.dreampie.orm.annotation.Table;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by ice on 15-1-19.
 */
@Table(name = "sec_user", cached = true)
public class User extends Model<User> {
  public final static User dao = new User();

  public Long getRoleId() {
    if (this.get("role_id") == null && this.get("id") != null) {
      String sql = "SELECT user_role.role_id FROM sec_user_role user_role WHERE user_role.user_id=?";
      this.put("role_id", queryFirst(sql, this.get("id")));
    }
    return this.get("role_id");
  }

  public Set<String> getPermissions() {
    Long role_id = getRoleId();
    if (this.get("permissions") == null && role_id != null) {
      String sql = "SELECT permission.value FROM sec_permission permission,sec_role_permission role_permission WHERE permission.id = role_permission.permission_id AND role_permission.role_id = ? ORDER BY permission.id";
      this.put("permissions", query(sql, role_id));
    }
    return new HashSet<String>((List<String>) this.get("permissions"));
  }
}
