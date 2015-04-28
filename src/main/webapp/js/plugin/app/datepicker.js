(function () {
  define(['regularjs', 'rgl!/html/plugin/app/datepicker.html', 'bootstrap-datepicker.zh-CN'], function (Regular, tpl, s) {
    var Datepicker;
    Datepicker = Regular.extend({
      template: tpl,
      config: function (data) {
        var component;
        component = this;
        return data.state.on('end', component.$update.bind(component));
      },
      init: function () {
        var component;
        component = this;
        $(component.$refs.start).datepicker(component.data.config).on("changeDate", function (e) {
          if (component.data.end < $(e.target).val()) {
            component.data.searchState.error = true;
          } else {
            component.data.searchState.error = false;
          }
          component.data.start = $(e.target).val();
          if (!component.data.searchState.twice) {
            return component.$emit('change', component.data.start);
          }
        });
        if (component.data.searchState.twice) {
          return $(component.$refs.end).datepicker(component.data.config).on("changeDate", function (e) {
            if (component.data.start > $(e.target).val()) {
              component.data.searchState.error = true;
            } else {
              component.data.searchState.error = false;
            }
            component.data.end = $(e.target).val();
            return component.$emit('change', {
              start: component.data.start,
              end: component.data.end
            });
          });
        }
      }
    });
    Regular.component('app-datepicker', Datepicker);
    return Datepicker;
  });

}).call(this);
