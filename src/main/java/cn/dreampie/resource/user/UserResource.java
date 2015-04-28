package cn.dreampie.resource.user;

import cn.dreampie.resource.ApiResource;
import cn.dreampie.resource.user.model.User;
import cn.dreampie.route.core.annotation.API;
import cn.dreampie.route.core.annotation.GET;

import java.util.List;

/**
 * Created by ice on 15-1-19.
 */
@API("/users")
public class UserResource extends ApiResource {

  @GET
  public List<User> users() {
    return User.dao.findAll();
  }
}
