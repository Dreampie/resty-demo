package cn.dreampie.resource.setting;

import cn.dreampie.resource.ApiResource;
import cn.dreampie.resource.setting.model.Product;
import cn.dreampie.route.core.annotation.API;
import cn.dreampie.route.core.annotation.GET;
import cn.dreampie.route.core.annotation.POST;
import cn.dreampie.route.core.annotation.PUT;

import java.util.List;

/**
 * Created by wangrenhui on 15/4/8.
 */
@API("/settings")
public class SettingResource extends ApiResource {

  @GET("/products")
  public List<Product> products() {
    return Product.dao.findProducts();
  }

  @POST("/products")
  public List<Product> updateProduct(Product product) {
    if (product.save()) {
      return products();
    }
    return null;
  }

  @PUT("/repick")
  public boolean repick(String start, String end, boolean edb, boolean order, boolean store, boolean gift) {
    if (start != null && !"".equals(start) && end != null && !"".equals(end)) {
      //not completed
      return true;
    }
    return false;
  }

}
