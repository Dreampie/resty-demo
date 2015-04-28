(function () {
  'use strict';
  var libsources;

  libsources = {
    version: {
      'require-css': '0.1.8-1',
      'jquery': '2.1.3',
      'Semantic-UI': '1.8.1',
      'bootstrap-datepicker': '1.4.0',
      'jquery-file-upload': '9.8.1'
    },
    jarpath: function (requireid, webjarsname) {
      return 'webjars/' + requireid + '/' + libsources.version[requireid] + '/' + webjarsname;
    },
    jarpaths: function (requireid, webjarsname, cdnname) {
      var webjarspath;
      webjarspath = libsources.jarpath(requireid, webjarsname);
      if (libsources.cdn[requireid]) {
        return [libsources.cdnpath(requireid, cdnname || webjarsname), webjarspath];
      } else {
        return webjarspath;
      }
    },
    localjs: function (name) {
      return 'js/' + name;
    },
    localcss: function (name) {
      return 'css/' + name;
    },
    localpaths: function (requireid, localname, cdnname, type) {
      var localpath;
      if (type == null) {
        type = 'js';
      }
      localpath = type === 'css' ? libsources.localcss(localname) : libsources.localjs(localname);
      if (libsources.cdn[requireid]) {
        return [libsources.cdnpath(requireid, cdnname || localname), localpath];
      } else {
        return localpath;
      }
    },
    cdnpath: function (requireid, cdnname) {
      return libsources.cdn[requireid] + requireid + '/' + libsources.version[requireid] + '/' + cdnname;
    },
    getTime: function () {
      var dd, hh, mm, now, yy;
      now = new Date();
      yy = now.getYear();
      mm = now.getMonth();
      dd = now.getDay();
      hh = now.getHours();
      mm = now.getMinutes();
      return "" + yy + dd + hh + parseInt(mm / 3);
    }
  };

  requirejs.config({
    baseUrl: '/',
    urlArgs: 'v=' + libsources.getTime(),
    paths: {
      'jquery': libsources.jarpath('jquery', 'jquery.min'),
      'semantic-ui': libsources.jarpath('Semantic-UI', 'semantic.min'),
      'bootstrap-datepicker': libsources.jarpath('bootstrap-datepicker', 'js/bootstrap-datepicker.min'),
      'jquery.ui.widget': libsources.localjs('plugin/jquery.ui.widget'),
      'jquery-file-upload': libsources.jarpath('jquery-file-upload', 'js/jquery.fileupload'),
      'bootstrap-datepicker.zh-CN': libsources.localjs('plugin/app/bootstrap-datepicker.zh-CN'),
      'nanobar': libsources.localjs('plugin/nanobar.min'),
      'rgl': libsources.localjs('plugin/regularjs/rgl.min'),
      'regularjs': libsources.localjs('plugin/regularjs/regular.min'),
      'restate': libsources.localjs('plugin/regularjs/restate.min'),
      'stateman': libsources.localjs('plugin/regularjs/stateman.min'),
      'table2csv': libsources.localjs('plugin/export/table2csv')
    },
    shim: {
      'semantic-ui': 'jquery',
      'table2csv': 'jquery',
      'bootstrap-datepicker': ['jquery', 'css!' + libsources.jarpath('bootstrap-datepicker', 'css/bootstrap-datepicker3.standalone.min')],
      'bootstrap-datepicker.zh-CN': 'bootstrap-datepicker',
      'jquery.ui.widget': 'jquery',
      'jquery-file-upload': ['jquery.ui.widget', 'css!' + libsources.jarpath('jquery-file-upload', 'css/jquery.fileupload')]
    },
    map: {
      '*': {
        'css': libsources.jarpath('require-css', 'css')
      }
    },
    rgl: {
      BEGIN: '{',
      END: '}'
    }
  });

  require(['jquery', 'restate', 'regularjs', '/js/http.js', '/js/app.js', '/js/login.js', '/js/module/order/order.js', '/js/module/sale/sale.js', '/js/module/finance/finance.js', '/js/module/setting/setting.js'], function ($, restate, Regular, $http, App, Login, Order, Sale, Finance, Setting) {
    'use strict';
    var e, stateman, username;
    stateman = restate({
      view: document.getElementById("#app"),
      Component: Regular
    });
    try {
      username = localStorage.getItem("username");
      if (username) {
        $http.getA('/api/v1.0/sessions', function (rep) {
          if (rep) {
            return stateman.user = rep;
          } else {
            stateman.user = null;
            return localStorage.removeItem("permissions");
          }
        });
      } else {
        localStorage.removeItem("permissions");
      }
    } catch (_error) {
      e = _error;
    }
    stateman.state("app", App, "").state("app.index", Login, "").state("app.order", Order, "order").state("app.sale", Sale, "sale").state("app.finance", Finance, "finance").state("app.setting", Setting, "setting").on("notfound", function (option) {
      var component;
      component = this;
      if (!option.current || option.current.name !== "app.index") {
        return component.go("app.index", {
          replace: true
        });
      }
    }).on("begin", function (option) {
      var component;
      component = this;
      if (!option.current || option.current.name !== "app.index" && !component.user) {
        option.stop();
        return component.go("app.index", {
          replace: true
        });
      }
    }).start({
      html5: true
    });
    window.Regular = Regular;
    Regular.filter('replace', function (val, rep) {
      return val.toString().replace(rep, '');
    });
    return $(function () {
      $(document).ajaxError(function (event, xhr, options, exc) {
        switch (xhr.status) {
          case 401:
            return stateman.go('app.index', {
              replace: true
            });
        }
      });
      $(".back-top").click(function () {
        return $('html, body').animate({
          scrollTop: '0px'
        }, 400, 'linear');
      });
      return $(window).scroll(function () {
        if ($(window).scrollTop() > 200) {
          return $(".back-top").fadeIn(200);
        } else {
          return $(".back-top").fadeOut(200);
        }
      });
    });
  });

}).call(this);
