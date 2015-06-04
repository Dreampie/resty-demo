package cn.dreampie.resource.user;

import cn.dreampie.resource.ApiResource;
import cn.dreampie.resource.user.model.Role;
import cn.dreampie.route.core.annotation.API;
import cn.dreampie.route.core.annotation.GET;

import java.util.List;

/**
 * Created by ice on 15-1-19.
 */
@API("/roles")
public class RoleResource extends ApiResource {

  @GET
  public List<Role> roles(){
    return Role.dao.findBy("deleted_at IS NULL");
  }

  @GET("/:id")
  public Role permissions(int id){
    return Role.dao.findFirstBy("id=?",id);
  }

}
