# 拼车小程序
### 什么是拼车？

周末了，我要从学校出发去车站，打车需要40元，我想找一个和我一样要去车站的同学，这样只需要20元。

这，就是**拼车**。一起出行，AA付款。

区别于滴滴的拼车，全程对司机透明，可使乘客利益最大化。

### 使用技术

SpringBoot(SpringMVC+MyBatis)+Redis(GeoHash+缓存)+MySQL+WebSocket

分包结构
clinet:微信小程序端
server:Java服务端
sql:数据库文件

api：应用的入口，接口定义处

domain：业务实体类

infrastructure：基础设施层，与数据源的交互，以及配置文件定义处

service：业务主要逻辑

