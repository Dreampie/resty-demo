package cn.dreampie.resource.finance.util;


import cn.dreampie.resource.finance.model.OrderBill;
import cn.dreampie.resource.order.model.Order;
import org.apache.commons.csv.CSVRecord;

import java.io.File;


/**
 * Created by wangrenhui on 15/4/2.
 */
public class JDParser extends BaseParser {

  public JDParser(String filePath) {
    super(filePath);
  }

  public JDParser(File file) {
    super(file);
  }

  public void process(Long fileId, String shopId, String shopName, String month) {
    Iterable<CSVRecord> orderBills = parse();

    String code_column = "订单编号";
    String sku_column = "SKU编号";
    String settle_column = "商品应结金额";
    String product_settle_column = "SKU单价";

    int settle = 0;
    int diff_settle = 0;
    OrderBill bill = null;
    Order order;
    for (CSVRecord orderBill : orderBills) {
      if (orderBill.get(sku_column) != null && !"".equals(orderBill.get(sku_column).trim())) {
        if ("".equals(orderBill.get(code_column).trim())) {
          bill.set("note", bill.getStr("note") + ",产品-" + orderBill.get(sku_column).trim() + ":价格-" + orderBill.get(product_settle_column).trim());
          bill.update();
        } else {
          settle = (int) Double.parseDouble(orderBill.get(settle_column).trim()) * 100;
          bill = new OrderBill().set("file_id", fileId).set("shop_id", shopId).set("shop_name", shopName).set("date", month)
              .set("code", orderBill.get(code_column).trim()).set("settle", settle).set("note", "产品-" + orderBill.get(sku_column).trim() + ":价格-" + orderBill.get(product_settle_column).trim());

          order = Order.dao.findFirstBy("shop_id=? AND code=?", shopId, orderBill.get(code_column).trim());
          if (order == null) {
            bill.set("state", 2);
          } else {
            diff_settle = settle - order.getInt("total_pay");
            bill.set("diff_pay", diff_settle);
            order.set("state", 1).update();
            if (diff_settle == 0) {
              bill.set("state", 0);
            } else {
              bill.set("state", 1);
            }
          }
          bill.save();
        }

      }
    }
  }
}


