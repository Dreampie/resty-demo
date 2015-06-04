(function() {
  define(['regularjs', 'rgl!/html/app.html', '/js/plugin/app/menu.js'], function(Regular, tpl) {
    'use strict';
    return Regular.extend({
      template: tpl,
      config: function(data) {
        var component;
        component = this;
        data.menus = [
          {
            url: '/',
            name: '首页',
            icon: 'home',
            state: 'app.index'
          }
        ];
        this.$state.on('login', function() {
          var e, permission, permissions, _i, _len;
          try {
            permissions = localStorage.getItem("permissionValues");
          } catch (_error) {
            e = _error;
          }
          if (permissions) {
            component.data.menus = [
              {
                url: '/',
                name: '首页',
                icon: 'home',
                state: 'app.index'
              }
            ];
            permissions = permissions.split(",");
            for (_i = 0, _len = permissions.length; _i < _len; _i++) {
              permission = permissions[_i];
              switch (permission) {
                case 'P_ORDER':
                  component.data.menus.push({
                    url: '/order',
                    name: '订单',
                    icon: 'table',
                    state: 'app.order'
                  });
                  break;
                case 'P_SALE':
                  component.data.menus.push({
                    url: '/sale',
                    name: '销售',
                    icon: 'strikethrough',
                    state: 'app.sale'
                  });
                  break;
                case 'P_FINANCE':
                  component.data.menus.push({
                    url: '/finance',
                    name: '财务',
                    icon: 'yen',
                    state: 'app.finance'
                  });
                  break;
                case 'P_SETTING':
                  component.data.menus.push({
                    url: '/setting',
                    name: '设置',
                    icon: 'configure',
                    state: 'app.setting'
                  });
              }
            }
            return component.$update();
          }
        });
        this.$state.on('logout', function() {
          component.data.menus = [
            {
              url: '/',
              name: '首页',
              icon: 'home',
              state: 'app.index'
            }
          ];
          return component.$update();
        });
        return component.$state.emit('login');
      }
    });
  });

}).call(this);
