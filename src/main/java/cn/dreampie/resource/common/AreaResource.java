package cn.dreampie.resource.common;

import cn.dreampie.resource.ApiResource;
import cn.dreampie.resource.common.model.Area;
import cn.dreampie.route.annotation.API;
import cn.dreampie.route.annotation.GET;

import java.util.List;

/**
 * Created by ice on 15-1-22.
 */
@API("/areas")
public class AreaResource extends ApiResource {

  @GET("/:pid")
  public List<Area> find(int pid) {
    return Area.dao.findBy("area.pid=?", pid);
  }
}
