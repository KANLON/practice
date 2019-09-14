# 这个主要是RocketMQ的入门练习

参考： https://guozh.net/rocketmqshiyongyizhiwindowdajianbushurocketmq/

https://blog.csdn.net/javahighness/article/details/79449054


磁盘满报错：

https://bbs.csdn.net/topics/392568834

设置conf目录下的所有`broker-b.properties`添加属性：`diskMaxUsedSpaceRatio=95`，rocketMQ应该是判断每个盘下面的容量是否大于95%，不过还需要看源码。

