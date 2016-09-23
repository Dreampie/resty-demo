
Resty-demo 是以Resty作为服务端接口，regularjs作为前端构建的交互模型
===========
<a href="https://github.com/Dreampie/Resty" target="_blank">Resty</a>   <a href="http://dreampie.gitbooks.io/resty-chs/content/index.html" target="_blank">开发文档</a>    <a target="_blank" href="http://shang.qq.com/wpa/qunwpa?idkey=8fc9498714ebbc3675cc5a5035858004154ef4645ebc9c128dfd76688d32179b"><img border="0" src="http://pub.idqqimg.com/wpa/images/group.png" alt="极简Restful框架 - Resty" title="极简Restful框架 - Resty"></a>

一、界面效果：
-----------------
界面使用semantic ui:
![image](https://raw.githubusercontent.com/Dreampie/resty-demo/master/src/main/webapp/image/demo/index.png)


![image](https://raw.githubusercontent.com/Dreampie/resty-demo/master/src/main/webapp/image/demo/order.png)


![image](https://raw.githubusercontent.com/Dreampie/resty-demo/master/src/main/webapp/image/demo/setting.png)


![image](https://raw.githubusercontent.com/Dreampie/resty-demo/master/src/main/webapp/image/demo/user.png)

二、运行example示例：
-----------------

1.在本地mysql数据库里创建resty-demo数据库，对应application.properties的数据库配置

3.运行resty-demo下的pom.xml->flyway-maven-plugin:clean->flyway-maven-plugin:migration，自动根具resources下db目录下的数据库文件生成数据库表结构和数据

4.运行resty-demo下的pom.xml->tomcat-maven-plugin:run

如果war在tomcat下部署,不要添加contextPath

提醒:推荐idea作为开发ide，maven做项目管理，git做源码管理

License <a href="https://www.apache.org/licenses/LICENSE-2.0" target="_blank">Apache License V2</a>


