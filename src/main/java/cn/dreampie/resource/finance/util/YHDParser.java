package cn.dreampie.resource.finance.util;


import cn.dreampie.resource.finance.model.OrderBill;
import cn.dreampie.resource.order.model.Order;
import org.apache.commons.csv.CSVRecord;

import java.io.File;


/**
 * Created by wangrenhui on 15/4/2.
 */
public class YHDParser extends BaseParser {

  public YHDParser(String filePath) {
    super(filePath);
  }

  public YHDParser(File file) {
    super(file);
  }

  public void process(Long fileId, String shopId, String shopName, String month) {
    Iterable<CSVRecord> orderBills = parse();

    String code_column = "订单号";
    String sku_column = "商品编号";
    String settle_column = "商品款";

    int settle = 0;
    OrderBill bill = null;
    for (CSVRecord orderBill : orderBills) {
      if (orderBill.get(settle_column) != null && !"".equals(orderBill.get(sku_column).trim())) {
        settle = (int) Double.parseDouble(orderBill.get(settle_column).trim()) * 100;
        if (bill != null && bill.getStr("code").equals(orderBill.get(code_column).trim())) {
          bill.set("settle", bill.getInt("settle") + settle).set("note", bill.getStr("note") + ",产品-" + orderBill.get(sku_column).trim() + ":价格-" + orderBill.get(settle_column).trim());
          check(shopId, bill);
          bill.update();
        } else {
          bill = new OrderBill().set("file_id", fileId).set("shop_id", shopId).set("shop_name", shopName).set("date", month)
              .set("code", orderBill.get(code_column).trim()).set("settle", settle)
              .set("note", "产品-" + orderBill.get(sku_column).trim() + ":价格-" + orderBill.get(settle_column));

          check(shopId, bill);
          bill.save();
        }
      }
    }
  }

  private void check(String shopId, OrderBill bill) {
    Order order;
    int diff_settle = 0;
    order = Order.dao.findFirstBy("shop_id=? AND code=?", shopId, bill.getStr("code"));
    if (order == null) {
      bill.set("state", 2);
    } else {
      diff_settle = bill.getInt("settle") - order.getInt("total_pay");
      bill.set("diff_pay", diff_settle);
      if (order.getInt("state") != 1) {
        order.set("state", 1).update();
      }
      if (diff_settle == 0) {
        bill.set("state", 0);
      } else {
        bill.set("state", 1);
      }
    }
  }
}


