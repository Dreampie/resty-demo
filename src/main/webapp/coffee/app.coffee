define ['regularjs', 'rgl!/html/app.html', '/js/plugin/app/menu.js'], (Regular, tpl)->
  'use strict'

  Regular.extend({
    template: tpl

    config: (data)->
      component = this
      data.menus = [
        {url: '/', name: '首页', icon: 'home', state: 'app.index'}
      ]

      this.$state.on('login', ->
        try
          permissions = localStorage.getItem("permissions")
        catch e

        if permissions
          component.data.menus = [
            {url: '/', name: '首页', icon: 'home', state: 'app.index'}
          ]
          permissions=permissions.split(",")
          for permission in permissions
            switch permission
              when 'P_ORDER' then component.data.menus.push({
                url: '/order',
                name: '订单',
                icon: 'table',
                state: 'app.order'
              })
              when 'P_SALE' then component.data.menus.push({
                url: '/sale',
                name: '销售',
                icon: 'strikethrough',
                state: 'app.sale'
              })
              when 'P_FINANCE' then component.data.menus.push({
                url: '/finance',
                name: '财务',
                icon: 'yen',
                state: 'app.finance'
              })
              when 'P_SETTING' then component.data.menus.push({
                url: '/setting',
                name: '设置',
                icon: 'configure',
                state: 'app.setting'
              })
          component.$update()
      )

      this.$state.on('logout', ->
        component.data.menus = [
          {url: '/', name: '首页', icon: 'home', state: 'app.index'}
        ]
        component.$update()
      )
      component.$state.emit('login')
  })
