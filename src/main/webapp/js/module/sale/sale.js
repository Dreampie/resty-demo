(function () {
  define(['regularjs', 'rgl!/html/module/sale/sale.html', '/js/http.js', '/js/plugin/app/datepicker.js', 'table2csv'], function (Regular, tpl, $http) {
    'use strict';
    return Regular.extend({
      template: tpl,
      data: {
        order_items: ['日期', '订单数量', '销售数量', '销售额', '成本', '毛利', '毛利率', '运费', '用户运费', '包材(1.8/提)', '平台扣点', '销售毛利', '销售毛利率', '税费', '经营费用摊销10%', '净利润', '净利率', '客单价'],
        store_items: ['产品', '编码', '入库单数', '出库单数', '月初库存', '入库', '出库', '月末库存', '成本价', '库存货值'],
        o_tab: {
          first: true,
          second: false,
          third: false,
          four: false
        },
        config: {
          format: "yyyymm",
          minViewMode: 1,
          language: "zh-CN",
          autoclose: true
        },
        dailys: {},
        shops: {},
        monthlys: {},
        stores: {},
        searchDaily: {
          loading: false,
          error: false
        },
        searchShop: {
          loading: false,
          error: false
        },
        searchMonthly: {
          loading: false,
          error: false
        },
        searchStore: {
          loading: false,
          error: false
        }
      },
      init: function () {
        var component;
        component = this;
        component.searchDaily();
        component.searchShop();
        component.searchMonthly();
        return component.searchStore();
      },
      active: function (tab) {
        var component;
        component = this;
        switch (tab) {
          case 'first':
            return component.data.o_tab = {
              first: true,
              second: false,
              third: false,
              four: false
            };
          case 'second':
            return component.data.o_tab = {
              first: false,
              second: true,
              third: false,
              four: false
            };
          case 'third':
            return component.data.o_tab = {
              first: false,
              second: false,
              third: true,
              four: false
            };
          case 'four':
            return component.data.o_tab = {
              first: false,
              second: false,
              third: false,
              four: true
            };
        }
      },
      orderTotal: function (datas) {
        var data, total, _i, _len;
        if (datas && datas.length > 0) {
          total = {
            date: '合计',
            order_count: 0,
            product_count: 0,
            sales: 0,
            cost: 0,
            profit: 0,
            profit_rate: 0,
            freight: 0,
            in_freight: 0,
            packing: 0,
            point: 0,
            tax: 0,
            sales_profit: 0,
            amortization: 0,
            net_profit: 0,
            order_price: 0
          };
          for (_i = 0, _len = datas.length; _i < _len; _i++) {
            data = datas[_i];
            total.order_count += data.order_count;
            total.product_count += data.product_count;
            total.sales += data.sales;
            total.cost += data.cost;
            total.profit += data.profit;
            total.freight += data.freight;
            total.in_freight += data.in_freight;
            total.packing += data.packing;
            total.point += data.point;
            total.tax += data.tax;
            total.sales_profit += data.sales_profit;
            total.amortization += data.amortization;
            total.net_profit += data.net_profit;
          }
          total.order_price = total.sales / total.order_count;
          total.profit_rate = total.profit / total.sales * 10000;
          total.sales_profit_rate = total.sales_profit / total.sales * 10000;
          total.net_profit_rate = total.net_profit / total.sales * 10000;
          datas.push(total);
        }
        return datas;
      },
      searchDaily: function (month) {
        var component, param;
        component = this;
        if (month) {
          param = {
            month: month
          };
          component.data.searchDaily = {
            error: false,
            loading: true
          };
        } else {
          component.data.searchDaily.error = true;
        }
        return $http.get('/api/v1.0/sales/dailys', param, function (rep) {
          if (rep) {
            rep.data = component.orderTotal(rep.data);
          }
          component.data.dailys = rep;
          component.data.searchDaily.loading = false;
          return component.$update();
        });
      },
      searchShop: function (month) {
        var component, param;
        component = this;
        if (month) {
          param = {
            month: month
          };
          component.data.searchShop = {
            error: false,
            loading: true
          };
        } else {
          component.data.searchShop.error = true;
        }
        return $http.get('/api/v1.0/sales/shops', param, function (rep) {
          var hasShop, o, shop, _i, _j, _k, _len, _len1, _len2, _ref, _ref1, _ref2;
          if (rep && rep.data.length > 0) {
            component.data.shops.month = rep.month;
            component.data.shops.data = [];
            _ref = rep.data;
            for (_i = 0, _len = _ref.length; _i < _len; _i++) {
              o = _ref[_i];
              hasShop = false;
              if (component.data.shops.data) {
                _ref1 = component.data.shops.data;
                for (_j = 0, _len1 = _ref1.length; _j < _len1; _j++) {
                  shop = _ref1[_j];
                  if (shop.name === o.shop_name) {
                    shop.datas.push(o);
                    hasShop = true;
                    break;
                  }
                }
              }
              if (!hasShop) {
                component.data.shops.data.push({
                  name: o.shop_name,
                  datas: [o]
                });
              }
            }
            _ref2 = component.data.shops.data;
            for (_k = 0, _len2 = _ref2.length; _k < _len2; _k++) {
              shop = _ref2[_k];
              shop.datas = component.orderTotal(shop.datas);
            }
          } else {
            component.data.shops = rep;
          }
          component.data.searchShop.loading = false;
          return component.$update();
        });
      },
      searchMonthly: function (month) {
        var component, param;
        component = this;
        if (month) {
          param = {
            month: month
          };
          component.data.searchMonthly = {
            error: false,
            loading: true
          };
        } else {
          component.data.searchMonthly.error = true;
        }
        return $http.get('/api/v1.0/sales/monthlys', param, function (rep) {
          component.data.monthlys = rep;
          component.data.searchMonthly.loading = false;
          return component.$update();
        });
      },
      searchStore: function (month) {
        var component, param;
        component = this;
        if (month) {
          param = {
            month: month
          };
          component.data.searchStore = {
            error: false,
            loading: true
          };
        } else {
          component.data.searchStore.error = true;
        }
        return $http.get('/api/v1.0/stores/monthlys', param, function (rep) {
          component.data.stores = rep;
          component.data.searchStore.loading = false;
          return component.$update();
        });
      },
      formatNum: function (number, digit) {
        return String(Number(number / 100).toFixed(digit));
      },
      "export": function (sel, name, $event) {
        return $($event.target).table2csv(sel, name);
      }
    });
  });

}).call(this);
