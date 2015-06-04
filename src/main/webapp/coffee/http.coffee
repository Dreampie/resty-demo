define ['jquery'], ($)->
  'use strict'

  $ajax = (async, method, url, data, datatype, success, error)->
    if ($.isFunction(data) )
      if($.isFunction(success))
        error = success
      success = data
      data = undefined

    contentType ='application/json; charset=UTF-8'
    if(data && method != 'GET')
      data = JSON.stringify(data)
    else if data
      data = $.param(data)
      contentType = 'application/x-www-form-urlencoded; charset=UTF-8'

    $.ajax({
      async: async
      type: method
      url: url
      data: data
      dataType: datatype
      success: success
      error: error
      contentType: contentType
    })


  get: (url, data, success, error)->
    $ajax(true, 'GET', url, data, 'json', success, error)
  getA: (url, data, success, error)->
    $ajax(false, 'GET', url, data, 'json', success, error)
  post: (url, data, success, error)->
    $ajax(true, 'POST', url, data, 'json', success, error)
  postA: (url, data, success, error)->
    $ajax(false, 'POST', url, data, 'json', success, error)
  delete: (url, data, success, error)->
    $ajax(true, 'DELETE', url, data, 'json', success, error)
  deleteA: (url, data, success, error)->
    $ajax(false, 'DELETE', url, data, 'json', success, error)
  put: (url, data, success, error)->
    $ajax(true, 'PUT', url, data, 'json', success, error)
  putA: (url, data, success, error)->
    $ajax(false, 'PUT', url, data, 'json', success, error)
  patch: (url, data, success, error)->
    $ajax(true, 'PATCH', url, data, 'json', success, error)
  patchA: (url, data, success, error)->
    $ajax(false, 'PATCH', url, data, 'json', success, error)
  head: (url, data, success, error)->
    $ajax(true, 'HEAD', url, data, 'json', success, error)
  headA: (url, data, success, error)->
    $ajax(false, 'HEAD', url, data, 'json', success, error)
  ajax: (async, method, url, data, datatype, success, error)->
    $ajax(async, method, url, data, datatype, success, error)