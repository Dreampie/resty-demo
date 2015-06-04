(function() {
  define(['regularjs', 'rgl!/html/plugin/app/menu.html', '/js/http.js'], function(Regular, tpl, $http) {
    var Menu;
    Menu = Regular.extend({
      template: tpl,
      config: function(data) {
        var component;
        component = this;
        return data.state.on('end', component.$update.bind(component));
      },
      logout: function() {
        var data;
        data = this.data;
        $http["delete"]('/api/v1.0/sessions', function(rep) {
          var e;
          if (rep) {
            data.state.user = null;
            try {
              localStorage.removeItem('username');
              localStorage.removeItem("permissionValues");
            } catch (_error) {
              e = _error;
            }
            data.state.go('app.index');
            return data.state.emit('logout');
          }
        });
        return false;
      }
    });
    Regular.component('app-menu', Menu);
    return Menu;
  });

}).call(this);
