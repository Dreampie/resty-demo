(function () {
  define(["regularjs", "rgl!/html/plugin/app/pager.html"], function (Regular, tpl) {
    var Pager;
    Pager = Regular.extend({
      template: tpl,
      config: function (data) {
        var count, show;
        count = 5;
        show = data.show = Math.floor(count / 2);
        data.current = parseInt(data.current || 1);
        data.total = parseInt(data.total || 1);
        return this.$watch(['current', 'total'], function (current, total) {
          data.begin = current - show;
          data.end = current + show;
          if (data.begin < 2) {
            data.begin = 2;
          }
          if (data.end > data.total - 1) {
            data.end = data.total - 1;
          }
          if (current - data.begin <= 1) {
            data.end = data.end + show + data.begin - current;
          }
          if (data.end - current <= 1) {
            return data.begin = data.begin - show - current + data.end;
          }
        });
      },
      nav: function (page) {
        var data, evObj;
        data = this.data;
        if (page < 1) {
          return false;
        }
        if (page > data.total) {
          return false;
        }
        if (page === data.current) {
          return false;
        }
        evObj = {
          page: page
        };
        this.$emit('nav', evObj);
        if (!evObj.stop) {
          data.current = page;
        }
        return false;
      }
    });
    return Pager;
  });

}).call(this);
