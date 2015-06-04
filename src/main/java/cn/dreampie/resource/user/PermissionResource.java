package cn.dreampie.resource.user;

import cn.dreampie.resource.ApiResource;
import cn.dreampie.resource.user.model.Permission;
import cn.dreampie.route.core.annotation.API;
import cn.dreampie.route.core.annotation.GET;

import java.util.List;

/**
 * Created by ice on 15-1-19.
 */
@API("/permissions")
public class PermissionResource extends ApiResource {

  @GET
  public List<Permission> permissions() {
    return Permission.dao.findBy("deleted_at IS NULL");
  }

}
