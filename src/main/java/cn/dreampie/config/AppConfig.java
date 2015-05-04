package cn.dreampie.config;

import cn.dreampie.orm.ActiveRecordPlugin;
import cn.dreampie.orm.provider.druid.DruidDataSourceProvider;
import cn.dreampie.quartz.QuartzPlugin;
import cn.dreampie.route.config.*;
import cn.dreampie.route.handler.cors.CORSHandler;
import cn.dreampie.route.interceptor.security.SecurityInterceptor;
import cn.dreampie.route.interceptor.transaction.TransactionInterceptor;

/**
 * Created by ice on 15-1-16.
 */
public class AppConfig extends Config {
  public void configConstant(ConstantLoader constantLoader) {
    //单页应用 避免被resty解析路径
    constantLoader.setDefaultForward("/");
  }

  public void configResource(ResourceLoader resourceLoader) {
//设置resource的目录  减少启动扫描目录
    resourceLoader.addIncludePackages("cn.dreampie.resource");
  }

  public void configPlugin(PluginLoader pluginLoader) {
    DruidDataSourceProvider ddsp = new DruidDataSourceProvider("default");
    ActiveRecordPlugin activeRecordPlugin = new ActiveRecordPlugin(ddsp);
    activeRecordPlugin.addIncludePackages("cn.dreampie.resource");

    pluginLoader.add(activeRecordPlugin);
    pluginLoader.add(new QuartzPlugin());
  }

  public void configInterceptor(InterceptorLoader interceptorLoader) {
    //权限拦截器 limit 为最大登录session数
    interceptorLoader.add(new SecurityInterceptor(2, new MyAuthenticateService()));
    //事务的拦截器 @Transaction
    interceptorLoader.add(new TransactionInterceptor());
  }

  public void configHandler(HandlerLoader handlerLoader) {
    //跨域
    handlerLoader.add(new CORSHandler());
  }
}
