package cn.dreampie.resource.user.model;

import cn.dreampie.orm.Model;
import cn.dreampie.orm.annotation.Table;

import java.util.List;

/**
 * Created by ice on 15-1-19.
 */
@Table(name = "sec_permission", cached = true)
public class Permission extends Model<Permission> {
  public static final Permission dao = new Permission();

  public List<Permission> findByRole(Long roleId) {
    String sql = "SELECT permission.id,permission.name,permission.value FROM sec_role_permission role_permission,sec_permission permission WHERE role_permission.permission_id=permission.id AND role_permission.role_id=?";
    return find(sql, roleId);
  }
}
