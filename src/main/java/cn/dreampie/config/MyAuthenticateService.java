package cn.dreampie.config;

import cn.dreampie.resource.user.model.Permission;
import cn.dreampie.resource.user.model.User;
import cn.dreampie.security.AuthenticateService;
import cn.dreampie.security.Principal;
import cn.dreampie.security.credential.Credential;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by ice on 15-1-7.
 */
public class MyAuthenticateService extends AuthenticateService {


  public Principal getPrincipal(String username) {
    User user = User.dao.findFirstBy("username=? AND deleted_at is null", username);
    if (user != null)
      return new Principal<User>(username, user.<String>get("password"), user.getPermissions(), user);
    else
      return null;
  }

  public Set<Credential> getAllCredentials() {
    List<Permission> permissions = Permission.dao.findBy("deleted_at is null");
    Set<Credential> credentials = new HashSet<Credential>();

    for (Permission permission : permissions) {
      credentials.add(new Credential(permission.<String>get("method"), permission.<String>get("url"), permission.<String>get("value")));
    }

    return credentials;
  }
}
