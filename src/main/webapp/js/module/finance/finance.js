(function () {
  define(['regularjs', 'rgl!/html/module/finance/finance.html', '/js/http.js', '/js/plugin/app/pager.js', '/js/plugin/app/datepicker.js', 'table2csv', 'jquery-file-upload'], function (Regular, tpl, $http, Pager) {
    return Regular.extend({
      template: tpl,
      data: {
        product_items: ['产品名称', '产品编码', '产品单价', '总数量', '总支付'],
        gift_items: ['产品', '编码', '出库单数', '出库数量', '成本价', '货值'],
        bill_items: ['店铺名', '订单号', '结算金额', '差异金额', '备注', '状态'],
        file_items: ['店铺名', '文件路径', '文件类型', '时间'],
        f_tab: {
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
        fileState: {
          loading: false,
          error: false,
          serror: false
        },
        uploadState: {
          noSearch: true
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
          }
        ],
        states: [
          {
            name: '全部',
            id: null
          }, {
            name: '完全匹配',
            id: 0
          }, {
            name: '订单号匹配',
            id: 1
          }, {
            name: '不匹配',
            id: 2
          }
        ],
        billState: {
          loading: false,
          error: false,
          serror: false,
          sterror: false
        },
        productState: {
          loading: false,
          error: false
        },
        giftState: {
          loading: false,
          error: false
        },
        rep: {
          success: false,
          error: false
        }
      },
      init: function () {
        var component;
        component = this;
        component.product();
        component.gift();
        component.bill();
        component.file();
        return $(component.$refs.fileupload).fileupload({
          url: "/api/v1.0/finances/files",
          sequentialUploads: true,
          submit: function (e, data) {
            data.formData = {
              month: component.data.fileMonth,
              shopId: component.data.fileShopId,
              shopName: component.data.fileShopName
            };
            component.data.fileState.loading = true;
            return component.$update();
          },
          done: function (e, result) {
          },
          success: function (result, textStatus, jqXHR) {
            component.data.fileState.loading = false;
            component.data.rep = {
              success: true,
              error: false
            };
            return component.$update();
          },
          error: function (jqXHR, textStatus, errorThrown) {
            component.data.fileState.loading = false;
            component.data.rep = {
              success: false,
              error: true
            };
            return component.$update();
          }
        });
      },
      active: function (tab) {
        var component;
        component = this;
        switch (tab) {
          case 'first':
            return component.data.f_tab = {
              first: true,
              second: false,
              third: false,
              four: false
            };
          case 'second':
            return component.data.f_tab = {
              first: false,
              second: true,
              third: false,
              four: false
            };
          case 'third':
            return component.data.f_tab = {
              first: false,
              second: false,
              third: true,
              four: false
            };
          case 'four':
            return component.data.f_tab = {
              first: false,
              second: false,
              third: false,
              four: true
            };
        }
      },
      product: function (month) {
        var component, param;
        component = this;
        if (month) {
          param = {
            month: month
          };
          component.data.productState = {
            error: false,
            loading: true
          };
        } else {
          component.data.productState.error = true;
        }
        return $http.get('/api/v1.0/orders/products', param, function (rep) {
          component.data.products = rep;
          component.data.productState.loading = false;
          return component.$update();
        });
      },
      gift: function (month) {
        var component, param;
        component = this;
        if (month) {
          param = {
            month: month
          };
          component.data.giftState = {
            error: false,
            loading: true
          };
        } else {
          component.data.giftState.error = true;
        }
        return $http.get('/api/v1.0/gifts/monthlys', param, function (rep) {
          component.data.gifts = rep;
          component.data.giftState.loading = false;
          return component.$update();
        });
      },
      bill: function (month) {
        var component, param;
        component = this;
        param = {
          shopId: component.data.billShopId,
          state: component.data.billStateId,
          pageNum: component.data.billPageNum
        };
        if (month && component.data.billShopId) {
          param.month = month;
          component.data.billState = {
            error: false,
            serror: false,
            loading: true
          };
        } else {
          if (!month) {
            component.data.billState.error = true;
          }
          if (!component.data.billShopId) {
            component.data.billState.serror = true;
          }
        }
        return $http.get('/api/v1.0/finances/bills', param, function (rep) {
          component.data.bills = rep;
          component.data.billState.loading = false;
          return component.$update();
        });
      },
      refresh: function (page, redirect) {
        var component;
        component = this;
        component.data.billPageNum = page;
        component.$refs.billDatepicker.$emit('select', component.$refs.billDatepicker.data.start);
        return false;
      },
      file: function (month) {
        var component, param;
        component = this;
        if (month) {
          param = {
            month: month
          };
          component.data.fileState = {
            error: false,
            loading: true
          };
        } else {
          component.data.fileState.error = true;
        }
        return $http.get('/api/v1.0/finances/files', param, function (rep) {
          component.data.files = rep;
          component.data.fileState.loading = false;
          return component.$update();
        });
      },
      selectBillShop: function (shop) {
        var component;
        component = this;
        component.data.billShopName = shop.name;
        return component.data.billShopId = shop.id;
      },
      selectBillState: function (state) {
        var component;
        component = this;
        component.data.billStateName = state.name;
        return component.data.billStateId = state.id;
      },
      selectBillMonth: function (month) {
        var component;
        component = this;
        return component.data.billMonth = month;
      },
      selectFileShop: function (shop) {
        var component;
        component = this;
        component.data.fileShopName = shop.name;
        return component.data.fileShopId = shop.id;
      },
      selectFileMonth: function (month) {
        var component;
        component = this;
        return component.data.fileMonth = month;
      },
      upload: function () {
        var component, result;
        component = this;
        result = false;
        if (!component.data.fileMonth) {
          component.data.fileState.error = true;
        } else {
          component.data.fileState.error = false;
          result = true;
        }
        if (!component.data.fileShopId) {
          component.data.fileState.serror = true;
          result = false;
        } else {
          component.data.fileState.serror = false;
        }
        return result;
      },
      formatNum: function (number, digit) {
        return String(Number(number / 100).toFixed(digit));
      },
      formatDiff: function (number, digit) {
        if (number) {
          return String(Number(number / 100).toFixed(digit));
        } else {
          return "";
        }
      },
      formatState: function (state) {
        switch (state) {
          case 0:
            return '完全匹配';
          case 1:
            return '订单号匹配';
          case 2:
            return '不匹配';
        }
      },
      "export": function (sel, name, $event) {
        return $($event.target).table2csv(sel, name);
      }
    }).component("pager", Pager);
  });

}).call(this);
