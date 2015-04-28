package cn.dreampie.resource;

import cn.dreampie.resource.user.model.User;
import cn.dreampie.route.core.annotation.API;
import cn.dreampie.route.core.annotation.DELETE;
import cn.dreampie.route.core.annotation.GET;
import cn.dreampie.route.core.annotation.POST;
import cn.dreampie.security.Principal;
import cn.dreampie.security.Subject;


/**
 * Created by ice on 15-1-16.
 */
@API("/sessions")
public class SessionResource extends ApiResource {

  @GET
  public User get() {
    Principal<User> principal = Subject.getPrincipal();
    if (principal != null)
      return principal.getModel();
    else
      return null;
  }


  @POST
  public User login(String username, String password, boolean rememberMe) {
    Subject.login(username, password, rememberMe);
    return (User) Subject.getPrincipal().getModel();
  }


  @DELETE
  public boolean logout() {
    Subject.logout();
    return true;
  }
}
