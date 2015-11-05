package cn.dreampie.resource.user;

import cn.dreampie.orm.transaction.Transaction;
import cn.dreampie.resource.ApiResource;
import cn.dreampie.resource.user.model.Role;
import cn.dreampie.resource.user.model.User;
import cn.dreampie.route.core.annotation.*;
import cn.dreampie.security.DefaultPasswordService;

import java.util.Date;
import java.util.List;

/**
 * Created by ice on 15-1-19.
 */
@API("/users")
public class UserResource extends ApiResource {

  @GET
  public List<User> users() {
    return User.dao.findBy("deleted_at IS NULL");
  }

  @GET("/:id")
  public User get(int id) {
    User user = User.dao.findFirstBy("id=?", id);
    if (user != null) {
      user.remove("password");
    }
    return user;
  }

  @POST
  public boolean save(User user) {
    String password = user.get("password");
    user.set("password", DefaultPasswordService.instance().crypto(password));
//    Role role = user.<Role>get("role");
//    if (role.get("id") == null) {
//      role.save();
//    }
//    role.updatePermissions();
    return user.save();
  }

  @PUT
  @Transaction
  public boolean update(User user) {
    Role role = user.<Role>get("role");
    role.updatePermissions();
    return user.update();
  }


  @DELETE("/:id")
  public boolean delete(int id) {
    return User.dao.updateColsBy("deleted_at", "id=?", new Date(), id);
  }
}
