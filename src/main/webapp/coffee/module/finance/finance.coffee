define ['regularjs', 'rgl!/html/module/finance/finance.html', '/js/http.js', '/js/plugin/app/pager.js',
        '/js/plugin/app/datepicker.js', 'table2csv',
        'jquery-file-upload'], (Regular, tpl, $http, Pager)->
  Regular.extend(
    template: tpl
    data:
      product_items: ['产品名称', '产品编码', '产品单价', '总数量', '总支付']
      gift_items: ['产品', '编码', '出库单数', '出库数量', '成本价', '货值']
      bill_items: ['店铺名', '订单号', '结算金额', '差异金额', '备注', '状态']
      file_items: ['店铺名', '文件路径', '文件类型', '时间']
      f_tab:
        first: true, second: false, third: false, four: false
      config:
        format: "yyyymm", minViewMode: 1, language: "zh-CN", autoclose: true
      fileState:
        loading: false, error: false, serror: false
      uploadState:
        noSearch: true
      shops:
        [{name: '全部', id: null}, {name: '天猫旗舰店', id: 11}, {name: '京东旗舰店', id: 4}, {name: '一号商城', id: 3}]
      states:
        [{name: '全部', id: null}, {name: '完全匹配', id: 0}, {name: '订单号匹配', id: 1}, {name: '不匹配', id: 2}]
      billState:
        loading: false, error: false, serror: false, sterror: false
      productState:
        loading: false, error: false
      giftState:
        loading: false, error: false
      rep:
        success: false, error: false

    init: ->
      component = this
      component.product()
      component.gift()
      component.bill()
      component.file()
      $(component.$refs.fileupload).fileupload(
        url: "/api/v1.0/finances/files" #文件上传地址，当然也可以直接写在input的data-url属性内
        sequentialUploads: true
        submit: (e, data)->
          data.formData =
            month: component.data.fileMonth, shopId: component.data.fileShopId, shopName: component.data.fileShopName

          component.data.fileState.loading = true
          component.$update()
        done: (e, result) ->
        success: (result, textStatus, jqXHR)->
        component.data.fileState.loading = false
          component.data.rep =
            success: true, error: false
          component.$update()
        error: (jqXHR, textStatus, errorThrown)->
          component.data.fileState.loading = false
          component.data.rep =
            success: false, error: true
          component.$update()
      )

    active: (tab)->
      component = this
      switch tab
        when 'first' then component.data.f_tab =
          first: true, second: false, third: false, four: false
        when 'second' then component.data.f_tab =
          first: false, second: true, third: false, four: false
        when 'third' then component.data.f_tab =
          first: false, second: false, third: true, four: false
        when 'four' then component.data.f_tab =
          first: false, second: false, third: false, four: true

    product: (month)->
      component = this
      if month
        param =
          month: month
        component.data.productState =
          error: false, loading: true
      else
        component.data.productState.error = true

      $http.get('/api/v1.0/orders/products', param, (rep)->
        component.data.products = rep
        component.data.productState.loading = false
        component.$update()
      )

    gift: (month)->
      component = this
      if month
        param =
          month: month
        component.data.giftState =
          error: false, loading: true
      else
        component.data.giftState.error = true

      $http.get('/api/v1.0/gifts/monthlys', param, (rep)->
        component.data.gifts = rep
        component.data.giftState.loading = false
        component.$update()
      )

    bill: (month)->
      component = this
      param = shopId: component.data.billShopId, state: component.data.billStateId, pageNum: component.data.billPageNum
      if month && component.data.billShopId
        param.month = month
        component.data.billState =
          error: false, serror: false, loading: true
      else
        if !month
          component.data.billState.error = true
        if !component.data.billShopId
          component.data.billState.serror = true

      $http.get('/api/v1.0/finances/bills', param, (rep)->
        component.data.bills = rep
        component.data.billState.loading = false
        component.$update()
      )

    refresh: (page, redirect)->
      component=this
      component.data.billPageNum = page;
      component.$refs.billDatepicker.$emit('select', component.$refs.billDatepicker.data.start)
      false


    file: (month)->
      component = this
      if month
        param =
          month: month
        component.data.fileState =
          error: false, loading: true
      else
        component.data.fileState.error = true

      $http.get('/api/v1.0/finances/files', param, (rep)->
        component.data.files = rep
        component.data.fileState.loading = false
        component.$update()
      )

    selectBillShop: (shop)->
      component = this
      component.data.billShopName = shop.name
      component.data.billShopId = shop.id
#      component.data.billState.serror = false

    selectBillState: (state)->
      component = this
      component.data.billStateName = state.name
      component.data.billStateId = state.id

    selectBillMonth: (month)->
      component = this
      component.data.billMonth = month
#      component.data.billState.error = false

    selectFileShop: (shop)->
      component = this
      component.data.fileShopName = shop.name
      component.data.fileShopId = shop.id
#      component.data.fileState.serror = false

    selectFileMonth: (month)->
      component = this
      component.data.fileMonth = month
#      component.data.fileState.error = false

    upload: ->
      component = this
      result=false
      if !component.data.fileMonth
        component.data.fileState.error = true
      else
        component.data.fileState.error = false
        result = true

      if !component.data.fileShopId
        component.data.fileState.serror = true
        result = false
      else
        component.data.fileState.serror = false
      result

    formatNum: (number, digit)->
      String(Number(number / 100).toFixed(digit))

    formatDiff: (number, digit)->
      if number
        String(Number(number / 100).toFixed(digit))
      else
        ""

    formatState: (state)->
      switch state
        when 0 then '完全匹配'
        when 1 then '订单号匹配'
        when 2 then '不匹配'

    export: (sel, name, $event)->
      $($event.target).table2csv(sel, name)
  ).component("pager", Pager)
