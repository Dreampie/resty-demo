DROP TABLE IF EXISTS fun_order;
CREATE TABLE fun_order (
  id               BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY
  COMMENT '订单id',
  code             VARCHAR(20)     DEFAULT NULL
  COMMENT '订单号',
  shop_id          INT             DEFAULT NULL
  COMMENT '店铺id',
  storage_id       INT             DEFAULT NULL
  COMMENT '仓库id',
  buyer_id         VARCHAR(100)    DEFAULT NULL
  COMMENT '买家id',
  name             VARCHAR(50)     DEFAULT NULL
  COMMENT '收货人',
  province_code    VARCHAR(100)    DEFAULT NULL
  COMMENT '省编码',
  city_code        VARCHAR(100)    DEFAULT NULL
  COMMENT '市编码',
  area_code        VARCHAR(100)    DEFAULT NULL
  COMMENT '区编码',
  province         VARCHAR(100)    DEFAULT NULL
  COMMENT '省',
  city             VARCHAR(100)    DEFAULT NULL
  COMMENT '市',
  area             VARCHAR(100)    DEFAULT NULL
  COMMENT '区',
  address          VARCHAR(500)    DEFAULT NULL
  COMMENT '地址',
  post_code        VARCHAR(10)     DEFAULT NULL
  COMMENT '邮编',
  tel_phone        VARCHAR(30)     DEFAULT NULL
  COMMENT '座机',
  mobil_phone      VARCHAR(20)     DEFAULT NULL
  COMMENT '手机',
  express          VARCHAR(50)     DEFAULT NULL
  COMMENT '快递名称',
  express_code     VARCHAR(50)     DEFAULT NULL
  COMMENT '快递',
  freight          INT             DEFAULT NULL
  COMMENT '运费',
  in_freight       INT             DEFAULT NULL
  COMMENT '收取运费',
  total_weight     DOUBLE          DEFAULT NULL
  COMMENT '总重量',
  pay_status       VARCHAR(10)     DEFAULT NULL
  COMMENT '支付状态',
  count            INT             DEFAULT NULL
  COMMENT '产品数量',
  product_count    INT             DEFAULT NULL
  COMMENT '产品提数',
  total_pay        INT             DEFAULT NULL
  COMMENT '总支付',
  actual_pay       INT             DEFAULT NULL
  COMMENT '实际支付',
  orig_pay         INT             DEFAULT NULL
  COMMENT '总原价',
  pay_method       VARCHAR(20)     DEFAULT NULL
  COMMENT '支付方式',
  favourable_money INT             DEFAULT NULL
  COMMENT '优惠金额',
  note             TEXT COMMENT '备注',
  create_time      DATETIME        DEFAULT NULL
  COMMENT '创建时间',
  pay_time         DATETIME        DEFAULT NULL
  COMMENT '支付时间',
  #   merge_status VARCHAR(100) DEFAULT NULL COMMENT '合并状态',
  #  plat_type  VARCHAR(20) DEFAULT NULL COMMENT '平台类型',#店铺名 店铺id
  shop_name        VARCHAR(100)    DEFAULT NULL
  COMMENT '店铺名',
  state            INT             DEFAULT 0
  COMMENT '对单状态,0未匹配，1已匹配',
  date             INT             DEFAULT NULL
  COMMENT '统计日期(天)'
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT = '订单数据';

DROP TABLE IF EXISTS fun_order_product;
CREATE TABLE fun_order_product (
  id            BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY
  COMMENT '主键',
  order_id      BIGINT NOT NULL
  COMMENT '订单id',
  product_name  VARCHAR(100)    DEFAULT NULL
  COMMENT '产品名称',
  bar_code      VARCHAR(100)    DEFAULT NULL
  COMMENT '条形码',
  product_count INT             DEFAULT NULL
  COMMENT '产品数量',
  product_price INT             DEFAULT NULL
  COMMENT '产品价格',
  total_pay     INT             DEFAULT NULL
  COMMENT '总支付',
  date          INT             DEFAULT NULL
  COMMENT '统计日期(天)'
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT = '订单产品数据';

DROP TABLE IF EXISTS fun_order_shop;
CREATE TABLE fun_order_shop (
  id                BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY
  COMMENT '主键',
  order_count       INT    NOT NULL
  COMMENT '订单数量',
  count             INT    NOT NULL
  COMMENT '销售产品数量',
  product_count     INT    NOT NULL
  COMMENT '销售产品提数',
  sales             INT    NOT NULL
  COMMENT '销售额',
  cost              INT    NOT NULL
  COMMENT '成本',
  profit            INT    NOT NULL
  COMMENT '毛利',
  profit_rate       INT    NOT NULL
  COMMENT '毛利率',
  freight           INT    NOT NULL
  COMMENT '运费',
  in_freight        INT    NOT NULL
  COMMENT '收入运费',
  packing           INT    NOT NULL
  COMMENT '包装',
  point             INT    NOT NULL
  COMMENT '扣点',
  tax               INT    NOT NULL
  COMMENT '税费',
  sales_profit      INT    NOT NULL
  COMMENT '销售毛利',
  sales_profit_rate INT    NOT NULL
  COMMENT '销售毛利率',
  amortization      INT    NOT NULL
  COMMENT '经营费用摊销10%',
  net_profit        INT    NOT NULL
  COMMENT '净利润',
  net_profit_rate   INT    NOT NULL
  COMMENT '净利率',
  shop_id           INT             DEFAULT NULL
  COMMENT '店铺id',
  shop_name         VARCHAR(100)    DEFAULT NULL
  COMMENT '店铺名',
  order_price       INT             DEFAULT NULL
  COMMENT '客单价',
  date              INT             DEFAULT NULL
  COMMENT '统计日期(天)'
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT = '订单每天统计数量';

DROP TABLE IF EXISTS fun_order_shop_product;
CREATE TABLE fun_order_shop_product (
  id            BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY
  COMMENT '主键',
  order_shop_id BIGINT NOT NULL
  COMMENT '订单平台统计id',
  product_name  VARCHAR(100)    DEFAULT NULL
  COMMENT '产品名称',
  bar_code      VARCHAR(100)    DEFAULT NULL
  COMMENT '条形码',
  product_count INT             DEFAULT NULL
  COMMENT '产品数量',
  product_price INT             DEFAULT NULL
  COMMENT '产品价格',
  total_pay     INT             DEFAULT NULL
  COMMENT '总支付',
  date          INT             DEFAULT NULL
  COMMENT '统计日期(天)'
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT = '订单产品数据';


DROP TABLE IF EXISTS fun_order_daily;
CREATE TABLE fun_order_daily (
  id                BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY
  COMMENT '主键',
  order_count       INT    NOT NULL
  COMMENT '订单数量',
  count             INT    NOT NULL
  COMMENT '销售产品数量',
  product_count     INT    NOT NULL
  COMMENT '销售产品提数',
  sales             INT    NOT NULL
  COMMENT '销售额',
  cost              INT    NOT NULL
  COMMENT '成本',
  profit            INT    NOT NULL
  COMMENT '毛利',
  profit_rate       INT    NOT NULL
  COMMENT '毛利率',
  freight           INT    NOT NULL
  COMMENT '运费',
  in_freight        INT    NOT NULL
  COMMENT '收入运费',
  packing           INT    NOT NULL
  COMMENT '包装',
  point             INT    NOT NULL
  COMMENT '扣点',
  tax               INT    NOT NULL
  COMMENT '税费',
  sales_profit      INT    NOT NULL
  COMMENT '销售毛利',
  sales_profit_rate INT    NOT NULL
  COMMENT '销售毛利率',
  amortization      INT    NOT NULL
  COMMENT '经营费用摊销10%',
  net_profit        INT    NOT NULL
  COMMENT '净利润',
  net_profit_rate   INT    NOT NULL
  COMMENT '净利率',
  order_price       INT             DEFAULT NULL
  COMMENT '客单价',
  date              INT             DEFAULT NULL
  COMMENT '统计日期(天)'
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT = '订单每天统计数量';

DROP TABLE IF EXISTS fun_order_daily_product;
CREATE TABLE fun_order_daily_product (
  id             BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY
  COMMENT '主键',
  order_daily_id BIGINT NOT NULL
  COMMENT '订单每天统计id',
  product_name   VARCHAR(100)    DEFAULT NULL
  COMMENT '产品名称',
  bar_code       VARCHAR(100)    DEFAULT NULL
  COMMENT '条形码',
  product_count  INT             DEFAULT NULL
  COMMENT '产品数量',
  product_price  INT             DEFAULT NULL
  COMMENT '产品价格',
  total_pay      INT             DEFAULT NULL
  COMMENT '总支付',
  date           INT             DEFAULT NULL
  COMMENT '统计日期(天)'
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT = '订单产品数据';


#产品表
DROP TABLE IF EXISTS fun_product;
CREATE TABLE fun_product (
  id       BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY
  COMMENT '主键',
  name     VARCHAR(100)    DEFAULT NULL
  COMMENT '产品名字',
  cost     INT             DEFAULT NULL
  COMMENT '产品成本',
  bar_code VARCHAR(100)    DEFAULT NULL
  COMMENT '产品条码',
  date     DATETIME        DEFAULT NULL
  COMMENT '时间'
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT = '订单每天统计数量';

#扣点表
DROP TABLE IF EXISTS fun_point;
CREATE TABLE fun_point (
  id        BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY
  COMMENT '主键',
  shop_id   INT             DEFAULT NULL
  COMMENT '店铺id',
  shop_name VARCHAR(100)    DEFAULT NULL
  COMMENT '店铺名',
  point     INT             DEFAULT NULL
  COMMENT '扣点',
  date      DATETIME        DEFAULT NULL
  COMMENT '时间'
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT = '订单每天统计数量';

#数据类型表
DROP TABLE IF EXISTS fun_type;
CREATE TABLE fun_type (
  id    BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY
  COMMENT '主键',
  name  VARCHAR(50)     DEFAULT NULL
  COMMENT '类型名',
  value VARCHAR(100)    DEFAULT NULL
  COMMENT '类型值',
  note  VARCHAR(200)    DEFAULT NULL
  COMMENT '备注',
  date  DATETIME        DEFAULT NULL
  COMMENT '时间'
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT = '订单每天统计数量';

#出入库表
DROP TABLE IF EXISTS fun_store;
CREATE TABLE fun_store (
  id           BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY
  COMMENT '主键',
  oid          VARCHAR(50)     DEFAULT NULL
  COMMENT '单号',
  storage_id   INT             DEFAULT NULL
  COMMENT '仓库编号',
  storage_name VARCHAR(50)     DEFAULT NULL
  COMMENT '仓库名',
  product_name VARCHAR(100)    DEFAULT NULL
  COMMENT '产品名',
  bar_code     VARCHAR(100)    DEFAULT NULL
  COMMENT '产品条码',
  count        INT             DEFAULT NULL
  COMMENT '数量',
  cost         INT             DEFAULT NULL
  COMMENT '成本',
  type         VARCHAR(20)     DEFAULT NULL
  COMMENT '类型',
  note         VARCHAR(200)    DEFAULT NULL
  COMMENT '备注',
  date         INT             DEFAULT NULL
  COMMENT '日期(天)'
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT = '库存';

#库存初期表
DROP TABLE IF EXISTS fun_store_init;
CREATE TABLE fun_store_init (
  id           BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY
  COMMENT '主键',
  product_name VARCHAR(100)    DEFAULT NULL
  COMMENT '产品名',
  bar_code     VARCHAR(100)    DEFAULT NULL
  COMMENT '产品条码',
  stock        INT             DEFAULT NULL
  COMMENT '库存数量',
  date         DATETIME        DEFAULT NULL
  COMMENT '日期(天)'
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT = '库存初始';

#库存每天统计表
DROP TABLE IF EXISTS fun_store_daily;
CREATE TABLE fun_store_daily (
  id           BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY
  COMMENT '主键',
  product_name VARCHAR(100)    DEFAULT NULL
  COMMENT '产品名',
  bar_code     VARCHAR(100)    DEFAULT NULL
  COMMENT '产品条码',
  in_count     INT             DEFAULT NULL
  COMMENT '入库单数',
  out_count    INT             DEFAULT NULL
  COMMENT '出库单数',
  in_stock     INT             DEFAULT NULL
  COMMENT '入库数量',
  out_stock    INT             DEFAULT NULL
  COMMENT '出库数量',
  cost         INT             DEFAULT NULL
  COMMENT '成本',
  date         INT             DEFAULT NULL
  COMMENT '日期(天)'
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT = '库存每天统计';

#库存每月统计
DROP TABLE IF EXISTS fun_store_monthly;
CREATE TABLE fun_store_monthly (
  id           BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY
  COMMENT '主键',
  product_name VARCHAR(100)    DEFAULT NULL
  COMMENT '产品名',
  bar_code     VARCHAR(100)    DEFAULT NULL
  COMMENT '产品条码',
  in_count     INT             DEFAULT NULL
  COMMENT '入库单数',
  out_count    INT             DEFAULT NULL
  COMMENT '出库单数',
  in_stock     INT             DEFAULT NULL
  COMMENT '入库数量',
  out_stock    INT             DEFAULT NULL
  COMMENT '出库数量',
  begin_stock  INT             DEFAULT NULL
  COMMENT '月初库存数量',
  end_stock    INT             DEFAULT NULL
  COMMENT '月末库存数量',
  cost         INT             DEFAULT NULL
  COMMENT '成本',
  date         INT             DEFAULT NULL
  COMMENT '日期(年月)'
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  COMMENT = '库存每月统计';
