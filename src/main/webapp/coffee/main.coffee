'use strict'

libsources =
#id like webjars dir
  version:
    'require-css': '0.1.8-1'
    'jquery': '2.1.3'
    'Semantic-UI': '1.12.3'
    'bootstrap-datepicker': '1.4.0'
    'jquery-file-upload': '9.8.1'
#  cdn:
#    'bootstrap': '#cdn.bootcss.com/'

  jarpath: (requireid, webjarsname)->
    'webjars/' + requireid + '/' + libsources.version[requireid] + '/' + webjarsname
#when cdnname  not equal webjarsname  please insert param cdnname
  jarpaths: (requireid, webjarsname, cdnname)->
    webjarspath = libsources.jarpath(requireid, webjarsname)
    if libsources.cdn[requireid]
      #cdnpath = webjars.cdn[requireid] + webjars.version[requireid] + '/' + (cdnname || webjarsname)
      [libsources.cdnpath(requireid, cdnname || webjarsname), webjarspath]
    else
      webjarspath
  localjs: (name)->
    'js/' + name
  localcss: (name)->
    'css/' + name
  localpaths: (requireid, localname, cdnname, type = 'js')->
    localpath = if type == 'css' then libsources.localcss(localname) else libsources.localjs(localname)
    if libsources.cdn[requireid]
      #cdnpath = webjars.cdn[requireid] + webjars.version[requireid] + '/' + (cdnname || localname)
      [libsources.cdnpath(requireid, cdnname || localname), localpath]
    else
      localpath
  cdnpath: (requireid, cdnname)->
    libsources.cdn[requireid] + requireid + '/' + libsources.version[requireid] + '/' + cdnname

  getTime:->
    #日期
    now = new Date(); #获取系统日期
    yy = now.getYear(); #截取年
    mm = now.getMonth(); #截取月
    dd = now.getDay(); #截取日
    #取时间
    hh = now.getHours(); #截取小时
    mm = now.getMinutes(); #截取分钟
    ""+ yy+ dd+ hh+ parseInt(mm/3)

#requirejs  config
requirejs.config
  baseUrl: '/'
  urlArgs: 'v='+libsources.getTime()
#all of the webjar configs from their webjars-requirejs.js files
  paths:
    'jquery': libsources.jarpath('jquery', 'jquery.min')
    'semantic-ui': libsources.jarpath('Semantic-UI', 'semantic.min')
    'bootstrap-datepicker': libsources.jarpath('bootstrap-datepicker', 'js/bootstrap-datepicker.min')
    'jquery.ui.widget': libsources.localjs('plugin/jquery.ui.widget')
    'jquery-file-upload': libsources.jarpath('jquery-file-upload', 'js/jquery.fileupload')
    'bootstrap-datepicker.zh-CN': libsources.localjs('plugin/app/bootstrap-datepicker.zh-CN')
    'nanobar': libsources.localjs('plugin/nanobar.min')
    'rgl': libsources.localjs('plugin/regularjs/rgl.min')
    'regularjs': libsources.localjs('plugin/regularjs/regular.min')
    'restate': libsources.localjs('plugin/regularjs/restate.min')
    'stateman': libsources.localjs('plugin/regularjs/stateman.min')
    'table2csv': libsources.localjs('plugin/export/table2csv')

  shim:
    'semantic-ui': 'jquery'
    'table2csv': 'jquery'
    'bootstrap-datepicker': ['jquery', 'css!' + libsources.jarpath('bootstrap-datepicker', 'css/bootstrap-datepicker3.standalone.min')]
    'bootstrap-datepicker.zh-CN': 'bootstrap-datepicker'
    'jquery.ui.widget': 'jquery'
    'jquery-file-upload': ['jquery.ui.widget', 'css!' + libsources.jarpath('jquery-file-upload', 'css/jquery.fileupload')]
  map:
    '*':
      'css': libsources.jarpath('require-css', 'css') #'webjars/require-css/0.1.4/css' #or whatever the path to require-css is
  rgl:
    BEGIN: '{'
    END: '}'

#  waitSeconds: 1

require ['jquery', 'restate', 'regularjs', '/js/http.js', '/js/app.js', '/js/login.js',
         '/js/module/order/order.js', '/js/module/sale/sale.js', '/js/module/finance/finance.js', '/js/module/setting/setting.js'],
  ($, restate, Regular, $http, App, Login, Order, Sale, Finance, Setting)->
    'use strict'

    # Start Stateman.

    stateman = restate({
      view: document.getElementById("#app")
      Component: Regular
    })

    # store infomation in local
    try
      username = localStorage.getItem("username");

      if(username)
        $http.getA('/api/v1.0/sessions', (rep)->
          if(rep)
            stateman.user = rep
          else
            stateman.user = null
            localStorage.removeItem("permissions")
        )
      else localStorage.removeItem("permissions")

    catch e


    stateman
    # application core
    .state("app", App, "")
    # home page
    .state("app.index", Login, "")
    # order page
    .state("app.order", Order, "order")
    .state("app.sale", Sale, "sale")
    .state("app.finance", Finance, "finance")
    .state("app.setting", Setting, "setting")
    # redirect when notfound
    .on("notfound", (option) ->
      component = this
      if !option.current || option.current.name != "app.index"
        component.go("app.index", replace: true)
    )
    # authen, need login first
    .on("begin", (option)->
      component = this
      if !option.current || option.current.name != "app.index" && !component.user
        option.stop()
        component.go("app.index", replace: true)
    )

    # start the routing prefix: "!"
    .start({html5: true})

    window.Regular = Regular

    Regular.filter('replace', (val, rep)->
      val.toString().replace(rep, '')
    )
    $ ->
      $(document).ajaxError (event, xhr, options, exc) ->
        switch xhr.status
          when 401 then stateman.go('app.index', replace: true)

      #scrollup
      $(".back-top").click(->
        $('html, body').animate({scrollTop: '0px'}, 400, 'linear')
      )
      $(window).scroll(->
        if ($(window).scrollTop() > 200)
          $(".back-top").fadeIn(200)
        else
          $(".back-top").fadeOut(200)
      )
