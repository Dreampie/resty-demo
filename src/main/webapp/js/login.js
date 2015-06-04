(function() {
  define(['regularjs', 'rgl!/html/login.html', '/js/http.js'], function(Regular, tpl, $http) {
    'use strict';
    return Regular.extend({
      template: tpl,
      login: function(username, password, rememberMe) {
        var component, data;
        component = this;
        data = component.data;
        if (!username) {
          data.uerror = true;
        } else {
          data.uerror = false;
        }
        if (!password) {
          data.perror = true;
        } else {
          data.perror = false;
        }
        if (username && password) {
          data.loading = true;
          $http.post('/api/v1.0/sessions', {
            username: username,
            password: password,
            rememberMe: rememberMe
          }, function(rep) {
            var e, p, ps, _i, _len, _ref;
            data.ferror = false;
            data.loading = false;
            component.$state.user = rep;
            try {
              localStorage.setItem('username', username);
              ps = [];
              _ref = rep.permissions;
              for (_i = 0, _len = _ref.length; _i < _len; _i++) {
                p = _ref[_i];
                ps.push(p.value);
              }
              localStorage.setItem("permissionValues", ps);
              component.$state.emit('login');
            } catch (_error) {
              e = _error;
            }
            data.password = null;
            component.$update();
            return component.$state.go("app.order");
          }, function(req) {
            data.ferror = true;
            data.loading = false;
            return component.$update();
          });
        }
        return false;
      }
    });
  });

}).call(this);
