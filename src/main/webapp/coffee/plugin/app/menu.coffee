define ['regularjs', 'rgl!/html/plugin/app/menu.html', '/js/http.js'], (Regular, tpl, $http)->
  Menu = Regular.extend(
    template: tpl

    config: (data)->
      component = this
      data.state.on('end', component.$update.bind(component))

    logout: ->
      data = this.data
      $http.delete('/api/v1.0/sessions', (rep)->
        if(rep)
          data.state.user = null
          try
            localStorage.removeItem('username')
            localStorage.removeItem("permissions")
          catch e
          data.state.go('app.index')
          data.state.emit('logout')
      )

      false
  )

  Regular.component('app-menu', Menu)

  Menu
