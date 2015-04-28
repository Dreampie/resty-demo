Date.prototype.format = (fmt) ->
  o =
    "M+": this.getMonth() + 1, #月份
    "d+": this.getDate(), #日
    "h+": if this.getHours() % 12 == 0 then 12 else this.getHours() % 12, #小时
    "H+": this.getHours(), #小时
    "m+": this.getMinutes(), #分
    "s+": this.getSeconds(), #秒
    "q+": Math.floor((this.getMonth() + 3) / 3), #季度
    "S": this.getMilliseconds() #毫秒
  week =
    "0": "/u65e5",
    "1": "/u4e00",
    "2": "/u4e8c",
    "3": "/u4e09",
    "4": "/u56db",
    "5": "/u4e94",
    "6": "/u516d"

  if /(y+)/.test(fmt)
    fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));

  if /(E+)/.test(fmt)
    fmt = fmt.replace(RegExp.$1,
      (if (RegExp.$1.length > 1) then (if RegExp.$1.length > 2 then "/u661f/u671f" else "/u5468") else "") + week[this.getDay() + ""])

  for k,v of o
    if new RegExp("(" + k + ")").test(fmt)
      fmt = fmt.replace(RegExp.$1, if (RegExp.$1.length == 1) then (v) else (("00" + v).substr(("" + v).length)))
  fmt