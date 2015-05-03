(function() {
  define(['regularjs', 'rgl!/html/module/setting/setting.html', '/js/http.js', '/js/plugin/app/datepicker.js', '/js/plugin/app/dateformat.js'], function(Regular, tpl, $http) {
    return Regular.extend({
      template: tpl,
      data: {
        product_items: ['编号', '名称', '编码', '成本', '时间'],
        user_items: ['编号', '登录名', '姓名', '电话', '权限', '时间'],
        updateP: [],
        pName: [],
        pBarCode: [],
        pCost: [],
        pDate: [],
        cerror: [],
        derror: [],
        s_tab: {
          first: true,
          second: false,
          third: false
        },
        config: {
          format: "yyyymmdd",
          minViewMode: 0,
          language: "zh-CN",
          autoclose: true,
          todayHighlight: true,
          clearBtn: true
        },
        sState: {
          twice: true,
          icon: 'play',
          loading: false,
          error: true
        },
        rep: {
          success: false,
          error: false
        }
      },
      active: function(tab) {
        var component;
        component = this;
        switch (tab) {
          case 'first':
            return component.data.s_tab = {
              first: true,
              second: false,
              third: false
            };
          case 'second':
            return component.data.s_tab = {
              first: false,
              second: true,
              third: false
            };
          case 'third':
            return component.data.s_tab = {
              first: false,
              second: false,
              third: true
            };
        }
      },
      init: function() {
        var component;
        component = this;
        component.product();
        return component.user();
      },
      product: function() {
        var component;
        component = this;
        return $http.get('/api/v1.0/settings/products', function(rep) {
          if (rep) {
            component.data.products = rep;
            return component.$update();
          }
        });
      },
      user: function() {
        var component;
        component = this;
        return $http.get('/api/v1.0/users', function(rep) {
          if (rep) {
            component.data.users = rep;
            return component.$update();
          }
        });
      },
      "new": function() {
        var component, newPBarCode, newPCost, newPDate, newPName, param, s;
        component = this;
        s = false;
        param = {
          product: {}
        };
        newPName = component.data.newPName;
        param.product.name = newPName;
        if (newPName && newPName !== "") {
          s = true;
          component.data.newPNerror = false;
        } else {
          s = false;
          component.data.newPNerror = true;
        }
        newPBarCode = component.data.newPBarCode;
        param.product.bar_code = newPBarCode;
        if (newPBarCode && newPBarCode !== "") {
          s = true;
          component.data.newPBerror = false;
        } else {
          s = false;
          component.data.newPBerror = true;
        }
        newPCost = component.data.newPCost;
        param.product.cost = newPCost;
        if (newPCost && !isNaN(newPCost) && newPCost !== "") {
          s = true;
          component.data.newPCerror = false;
        } else {
          s = false;
          component.data.newPCerror = true;
        }
        newPDate = component.data.newPDate;
        param.product.date = newPDate;
        if (newPDate && newPDate.match(/\d{4}-\d{2}-\d{2} \d{2}:\d{2}/)) {
          s = true;
          component.data.newPDerror = false;
        } else {
          s = false;
          component.data.newPDerror = true;
        }
        if (s) {
          return $http.post('/api/v1.0/settings/products', param, function(rep) {
            if (rep) {
              component.data.products = rep;
              component.data.newP = false;
              return component.$update();
            }
          });
        }
      },
      update: function(index) {
        var barCode, component, cost, date, name, oldP, param, u;
        component = this;
        oldP = component.data.products[index];
        u = false;
        param = {
          product: {}
        };
        name = component.data.pName[index];
        param.product.name = name;
        if (name && oldP.name !== name && name !== "") {
          u = true;
        }
        barCode = component.data.pBarCode[index];
        param.product.bar_code = barCode;
        if (barCode && oldP.bar_code !== barCode && barCode !== "") {
          u = true;
        }
        cost = component.data.pCost[index];
        if (cost && isNaN(cost)) {
          component.data.cerror[index] = true;
          return;
        } else {
          component.data.cerror[index] = false;
          param.product.cost = cost;
          if (oldP.cost !== Number(cost)) {
            u = true;
          }
        }
        date = component.data.pDate[index];
        if (date && date.match(/\d{4}-\d{2}-\d{2} \d{2}:\d{2}/)) {
          component.data.derror[index] = false;
          param.product.date = date;
          if (oldP.date !== date) {
            u = true;
          }
        } else {
          u = false;
        }
        if (u) {
          return $http.post('/api/v1.0/settings/products', param, function(rep) {
            if (rep) {
              component.data.products = rep;
              component.data.updateP[index] = false;
              return component.$update();
            }
          });
        }
      },
      job: function(date) {
        var component, param;
        component = this;
        if (date.start && date.end && date.start <= date.end) {
          component.data.sState.loading = true;
          component.data.sState.error = false;
          param = {
            start: date.start,
            end: date.end,
            edb: component.data.edb,
            order: component.data.order,
            store: component.data.store,
            gift: component.data.gift
          };
          if (param.edb || param.order || param.store || param.gift) {
            component.data.cerror = false;
            return $http.put('/api/v1.0/settings/repick', param, function(rep) {
              if (rep) {
                component.data.rep = {
                  success: true,
                  error: false
                };
              } else {
                component.data.rep = {
                  success: false,
                  error: true
                };
              }
              return component.$update();
            });
          } else {
            return component.data.cerror = true;
          }
        } else {
          component.data.sState.loading = false;
          return component.data.sState.error = true;
        }
      },
      formatNum: function(number, digit) {
        return String(Number(number / 100).toFixed(digit));
      },
      formatDate: function(date) {
        if (!isNaN(date)) {
          return new Date(date).format("yyyy-MM-dd HH:mm");
        } else {
          if (date.length > 16) {
            return date.substr(0, 16);
          } else {
            return date;
          }
        }
      },
      "export": function(sel, name, $event) {
        return $($event.target).table2csv(sel, name);
      }
    });
  });

}).call(this);
