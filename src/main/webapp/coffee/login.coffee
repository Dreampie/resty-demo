define ['regularjs', 'rgl!/html/login.html', '/js/http.js'], (Regular, tpl, $http)->
  'use strict'

  Regular.extend({
    template: tpl

    login: (username, password, rememberMe)->
      component = this
      data = component.data
      if(!username)
        data.uerror = true
      else
        data.uerror = false

      if(!password)
        data.perror = true
      else
        data.perror = false

      if(username && password)
        data.loading = true
        $http.post('/api/v1.0/sessions', {username: username, password: password, rememberMe: rememberMe}, (rep)->
          data.ferror = false
          data.loading = false
          #返回的user数据
          component.$state.user = rep
          try
            localStorage.setItem('username', username)
            localStorage.setItem("permissions", rep.permissions)
            #执行登录处理
            component.$state.emit('login')
          catch e

          data.password = null
          component.$update()

          #跳转
          component.$state.go("app.order");

        , (req)->
          data.ferror = true
          data.loading = false
          component.$update()
        )


      false
  })