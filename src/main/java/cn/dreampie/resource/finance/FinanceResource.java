package cn.dreampie.resource.finance;

import cn.dreampie.common.http.UploadedFile;
import cn.dreampie.common.util.Maper;
import cn.dreampie.resource.ApiResource;
import cn.dreampie.resource.finance.model.OrderBill;
import cn.dreampie.resource.finance.model.OrderFile;
import cn.dreampie.resource.finance.util.JDParser;
import cn.dreampie.resource.finance.util.TMParser;
import cn.dreampie.resource.finance.util.YHDParser;
import cn.dreampie.route.core.annotation.API;
import cn.dreampie.route.core.annotation.GET;
import cn.dreampie.route.core.annotation.POST;
import cn.dreampie.route.core.multipart.FILE;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by wangrenhui on 15/4/2.
 */
@API("/finances")
public class FinanceResource extends ApiResource {

  @GET("/files")
  public Map files(String month) {
    if (month == null || "".equals(month)) {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
      month = sdf.format(new Date());
    }
    return Maper.of("month", month, "data", OrderFile.dao.findBy("date=?", month));
  }

  @GET("/bills")
  public Map bills(String month, Integer pageNum, String shopId, Integer state) {
    if (month == null || "".equals(month)) {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
      month = sdf.format(new Date());
    }
    List<Object> paras = new ArrayList<Object>();
    String where = "date=?";
    paras.add(month);
    if (shopId != null) {
      where += " AND shop_id=?";
      paras.add(shopId);
    }
    if (state != null) {
      where += " AND state=?";
      paras.add(state);
    }

    if (pageNum == null) {
      pageNum = 1;
    }
    if (shopId != null) {
      return Maper.of("month", month, "data", OrderBill.dao.paginateBy(pageNum, 100, where, paras.toArray()));
    } else {
      return Maper.of("month", month, "data", OrderBill.dao.paginateBy(pageNum, 100, where, paras.toArray()));
    }
  }


  @POST("/files")
  @FILE(dir = "/upload/finance/", allows = {"text/csv"})
  public OrderFile upload(UploadedFile file, String month, String shopId, String shopName) {
    if (file != null && month != null && !"".equals(month) && shopId != null && !"".equals(shopId)) {
      OrderFile orderFile = new OrderFile().set("path", "/upload/finance/" + file.getFileName()).set("type", file.getContentType())
          .set("date", month).set("shop_id", shopId).set("shop_name", shopName).set("created_at", new Date());
      OrderFile.dao.deleteBy("shop_id=? AND date=?", shopId, month);
      OrderBill.dao.deleteBy("shop_id=? AND date=?", shopId, month);
      if (orderFile.save()) {
        String path = getRequest().getRealPath("/") + orderFile.get("path");
        if (shopName.contains("天猫")) {
          TMParser tmParser = new TMParser(path);
          tmParser.process(orderFile.getLong("id"), shopId, shopName, month);
        } else if (shopName.contains("京东")) {
          JDParser jdParser = new JDParser(path);
          jdParser.process(orderFile.getLong("id"), shopId, shopName, month);
        } else if (shopName.contains("一号")) {
          YHDParser yhdParser = new YHDParser(path);
          yhdParser.process(orderFile.getLong("id"), shopId, shopName, month);
        }

        return orderFile;
      }
    }
    return null;
  }

}
