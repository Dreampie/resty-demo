define ['regularjs', 'rgl!/html/plugin/app/datepicker.html', 'bootstrap-datepicker.zh-CN'], (Regular, tpl, s)->
  Datepicker = Regular.extend
    template: tpl
    config: (data)->
      component = this
      data.state.on('end', component.$update.bind(component))

    init: ->
      component = this
      $(component.$refs.start).datepicker(
        component.data.config
      ).on("changeDate", (e)->
        if(component.data.end < $(e.target).val())
          component.data.searchState.error = true
        else
          component.data.searchState.error = false
        component.data.start = $(e.target).val()
        if(!component.data.searchState.twice)
          component.$emit('change', component.data.start)
      )

      if(component.data.searchState.twice)
        $(component.$refs.end).datepicker(
          component.data.config
        ).on("changeDate", (e)->
          if(component.data.start > $(e.target).val())
            component.data.searchState.error = true
          else
            component.data.searchState.error = false
          component.data.end = $(e.target).val()
          component.$emit('change', {start: component.data.start, end: component.data.end})
        )
  Regular.component('app-datepicker', Datepicker)

  Datepicker
