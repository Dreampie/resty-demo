package cn.dreampie.resource.finance.util;


import cn.dreampie.resource.finance.model.OrderBill;
import cn.dreampie.resource.order.model.Order;
import org.apache.commons.csv.CSVRecord;

import java.io.File;


/**
 * Created by wangrenhui on 15/4/2.
 */
public class TMParser extends BaseParser {

  public TMParser(String filePath) {
    super(filePath);
  }

  public TMParser(File file) {
    super(file);
  }

  public void process(Long fileId, String shopId, String shopName, String month) {
    Iterable<CSVRecord> orderBills = parse();

    String code_column = "平台订单号";
    String pay_type_column = "业务类型";
    String settle_column = "收入金额（+元）";

    int settle = 0;
    int diff_settle = 0;
    OrderBill bill = null;
    Order order;
    for (CSVRecord orderBill : orderBills) {
      if (orderBill.get(pay_type_column) != null && "交易付款".equals(orderBill.get(pay_type_column).trim())) {
        settle = (int) Double.parseDouble(orderBill.get(settle_column).trim()) * 100;

        bill = new OrderBill().set("file_id", fileId).set("shop_id", shopId).set("shop_name", shopName).set("date", month)
            .set("code", orderBill.get(code_column).trim()).set("settle", settle);

        order = Order.dao.findFirstBy("shop_id=? AND code=?", shopId, orderBill.get(code_column).trim());
        if (order == null) {
          bill.set("state", 2);
        } else {
          diff_settle = settle - order.<Integer>get("total_pay");
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


