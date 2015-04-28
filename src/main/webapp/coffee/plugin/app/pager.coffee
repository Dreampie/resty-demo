define ["regularjs", "rgl!/html/plugin/app/pager.html"], (Regular, tpl)->
  Pager = Regular.extend(
    template: tpl,
  # is called before compile. 一般用来处理数据
    config: (data)->
      count = 5;
      show = data.show = Math.floor(count / 2);
      data.current = parseInt(data.current || 1);
      data.total = parseInt(data.total || 1);

      this.$watch(['current', 'total'], (current, total)->
        data.begin = current - show
        data.end = current + show
        if(data.begin < 2)
          data.begin = 2
        if(data.end > data.total - 1)
          data.end = data.total - 1
        if(current - data.begin <= 1)
          data.end = data.end + show + data.begin - current
        if(data.end - current <= 1)
          data.begin = data.begin - show - current + data.end
      );
    ,
    nav: (page)->
      data = this.data;
      if(page < 1)
        return false
      if(page > data.total)
        return false
      if(page == data.current)
        return false
      evObj = {page: page}
      this.$emit('nav', evObj);

      if(!evObj.stop)
        data.current = page

      # preventDefault
      false
  )

  Pager

