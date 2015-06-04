package cn.dreampie.resource.user.model;

import cn.dreampie.orm.Model;
import cn.dreampie.orm.annotation.Table;
import com.alibaba.fastjson.annotation.JSONField;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by ice on 15-1-19.
 */
@Table(name = "sec_role", cached = true)
public class Role extends Model<Role> {
  public static final Role dao = new Role();

  public List<Permission> getPermissions() {
    List<Permission> permissions = null;
    if (this.get("permissions") == null && this.get("id") != null) {
      permissions = Permission.dao.findByRole(this.<Long>get("id"));
      this.put("permissions", permissions);
    } else {
      permissions = this.get("permissions");
    }
    return permissions;
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

  public void updatePermissions() {
    Long roleId = this.get("id");
    if (roleId != null) {
      Role role = Role.dao.findFirstBy("id=?", roleId);
      Set<Long> oldPermissionIds = role.getPermissionIds();
      Set<Long> newPermissionIds = this.getPermissionIds();

      newPermissionIds.removeAll(oldPermissionIds);
      oldPermissionIds.removeAll(this.getPermissionIds());
      for (Long id : oldPermissionIds) {
        RolePermission.dao.deleteBy("role_id=? AND permission_id=?", roleId, id);
      }

      for (Long id : newPermissionIds) {
        new RolePermission().set("role_id", roleId).set("permission_id", id).save();
      }
      Permission.dao.purgeCache();
      Role.dao.purgeCache();
    }
  }
}
