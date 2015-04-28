(function () {
  define(['regularjs', 'rgl!/html/module/order/order.html', '/js/http.js', '/js/plugin/app/pager.js', '/js/plugin/app/datepicker.js', 'table2csv'], function (Regular, tpl, $http, Pager) {
    'use strict';
    return Regular.extend({
      template: tpl,
      data: {
        order_items: ['店铺', '订单号', '用户名', '手机', '金额', '产品', '时间', '是否已对单'],
        o_tab: {
          first: true
        },
        config: {
          format: "yyyymmdd",
          minViewMode: 0,
          language: "zh-CN",
          autoclose: true,
          todayHighlight: true,
          clearBtn: true
        },
        orders: {},
        searchOrder: {
          twice: true,
          loading: false,
          error: false,
          serror: false
        },
        shops: [
          {
            name: '全部',
            id: null
          }, {
            name: '天猫旗舰店',
            id: 11
          }, {
            name: '京东旗舰店',
            id: 4
          }, {
            name: '一号商城',
            id: 3
          }, {
            name: '国美旗舰店',
            id: 9
          }, {
            name: '淘宝集市店',
            id: 10
          }
        ],
        states: [
          {
            name: '全部',
            id: null
          }, {
            name: '未对单',
            id: 0
          }, {
            name: '已对单',
            id: 1
          }
        ]
      },
      init: function () {
        var component;
        component = this;
        return component.searchOrder();
      },
      selectOrderShop: function (shop) {
        var component;
        component = this;
        component.data.orderShopName = shop.name;
        return component.data.orderShopId = shop.id;
      },
      searchOrder: function (date) {
        var component, param;
        component = this;
        component.data.searchOrder.loading = true;
        param = {
          shopId: component.data.orderShopId,
          state: component.data.orderStateId,
          code: component.data.orderCode,
          pageNum: component.data.orderPageNum
        };
        if (date && date.start && date.end) {
          if (date.start <= date.end) {
            component.data.searchOrder.error = false;
            param.start = date.start;
            param.end = date.end;
          } else {
            component.data.searchOrder.error = true;
          }
        }
        return $http.get('/api/v1.0/orders', param, function (rep) {
          if (rep) {
            component.data.orders = rep;
          }
          component.data.searchOrder.loading = false;
          return component.$update();
        });
      },
      refresh: function (page, redirect) {
        var component;
        component = this;
        component.data.orderPageNum = page;
        component.$refs.datepicker.$emit('select', {
          start: component.$refs.datepicker.data.start,
          end: component.$refs.datepicker.data.end
        });
        return false;
      },
      selectOrderState: function (state) {
        var component;
        component = this;
        component.data.orderStateName = state.name;
        return component.data.orderStateId = state.id;
      },
      formatNum: function (number, digit) {
        return String(Number(number / 100).toFixed(digit));
      },
      "export": function (sel, name, $event) {
        return $($event.target).table2csv(sel, name);
      },
      formatProduct: function (product) {
        return product.product_name + " X " + product.product_count;
      },
      formatState: function (state) {
        switch (state) {
          case 0:
            return '未对单';
          case 1:
            return '已对单';
        }
      }
    }).component("pager", Pager);
  });

}).call(this);
