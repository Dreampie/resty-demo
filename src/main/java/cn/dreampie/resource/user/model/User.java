package cn.dreampie.resource.user.model;

import cn.dreampie.orm.Model;
import cn.dreampie.orm.annotation.Table;
import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by ice on 15-1-19.
 */
@Table(name = "sec_user", cached = true)
public class User extends Model<User> {
  public final static User dao = new User();

  public boolean save() {
    boolean result;
    if (this.getRole() == null) {
      throw new IllegalArgumentException("必须设置角色");
    }
    this.set("full_name",this.get("first_name")+"."+this.get("last_name")).set("providername", "shengmu").set("created_at",new Date());
    if (super.save()) {
      UserRole ur = new UserRole().set("user_id", this.get("id")).set("role_id", this.getRole().get("id"));
      result = ur.save();
    } else {
      result = false;
    }
    return result;
  }

  public Role getRole() {
    Role role;
    if (this.get("role") == null && this.get("id") != null) {
      String sql = "SELECT role.id,role.name FROM sec_role role,sec_user_role user_role WHERE role.id=user_role.role_id AND user_role.user_id=?";
      role = Role.dao.findFirst(sql, this.get("id"));
      this.put("role", role);
    } else {
      role = this.get("role");
    }
    return role;
  }

  public List<Permission> getPermissions() {
    Long role_id = getRole().<Long>get("id");
    List<Permission> permissions;
    if (this.get("permissions") == null && role_id != null) {
      permissions = Permission.dao.findByRole(role_id);
      this.put("permissions", permissions);
    } else {
      permissions = this.get("permissions");
    }
    return permissions;
  }

  @JSONField(serialize = false)
  public Set<String> getPermissionValues() {
    List<Permission> permissions = getPermissions();
    Set<String> permissionValues = null;
    if (permissions != null) {
      permissionValues = new HashSet<String>();
      for (Permission permission : permissions) {
        permissionValues.add(permission.<String>get("value"));
      }
    }
    return permissionValues;
  }


  @JSONField(serialize = false)
  public Set<Long> getPermissionIds() {
    List<Permission> permissions = getPermissions();
    Set<Long> permissionIds = null;
    if (permissions != null) {
      permissionIds = new HashSet<Long>();
      for (Permission permission : permissions) {
        permissionIds.add(permission.<Long>get("id"));
      }
    }
    return permissionIds;
  }
}
