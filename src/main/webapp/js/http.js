(function() {
  define(['jquery'], function($) {
    'use strict';
    var $ajax;
    $ajax = function(async, method, url, data, datatype, success, error) {
      var contentType;
      if ($.isFunction(data)) {
        if ($.isFunction(success)) {
          error = success;
        }
        success = data;
        data = void 0;
      }
      contentType = 'application/json; charset=UTF-8';
      if (data && method !== 'GET') {
        data = JSON.stringify(data);
      } else if (data) {
        data = $.param(data);
        contentType = 'application/x-www-form-urlencoded; charset=UTF-8';
      }
      return $.ajax({
        async: async,
        type: method,
        url: url,
        data: data,
        dataType: datatype,
        success: success,
        error: error,
        contentType: contentType
      });
    };
    return {
      get: function(url, data, success, error) {
        return $ajax(true, 'GET', url, data, 'json', success, error);
      },
      getA: function(url, data, success, error) {
        return $ajax(false, 'GET', url, data, 'json', success, error);
      },
      post: function(url, data, success, error) {
        return $ajax(true, 'POST', url, data, 'json', success, error);
      },
      postA: function(url, data, success, error) {
        return $ajax(false, 'POST', url, data, 'json', success, error);
      },
      "delete": function(url, data, success, error) {
        return $ajax(true, 'DELETE', url, data, 'json', success, error);
      },
      deleteA: function(url, data, success, error) {
        return $ajax(false, 'DELETE', url, data, 'json', success, error);
      },
      put: function(url, data, success, error) {
        return $ajax(true, 'PUT', url, data, 'json', success, error);
      },
      putA: function(url, data, success, error) {
        return $ajax(false, 'PUT', url, data, 'json', success, error);
      },
      patch: function(url, data, success, error) {
        return $ajax(true, 'PATCH', url, data, 'json', success, error);
      },
      patchA: function(url, data, success, error) {
        return $ajax(false, 'PATCH', url, data, 'json', success, error);
      },
      head: function(url, data, success, error) {
        return $ajax(true, 'HEAD', url, data, 'json', success, error);
      },
      headA: function(url, data, success, error) {
        return $ajax(false, 'HEAD', url, data, 'json', success, error);
      },
      ajax: function(async, method, url, data, datatype, success, error) {
        return $ajax(async, method, url, data, datatype, success, error);
      }
    };
  });

}).call(this);
