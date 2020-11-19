# 设计目的
  - 现有公司采用随机数生成流水号,看不出有意思的信息,且搜索极其费时
  - 于是花费大半天写了一个id生成器(基于号段),可动态根据业务添加生成策略.(后续支持分布式.雪花.)
# 设计草图
![](https://img-blog.csdnimg.cn/2020111720083246.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L051YW5fRmVuZw==,size_16,color_FFFFFF,t_70)
# 执行结果
- 100个线程同时创建1000个id,步长设置为100,方便测试极端情况
![](https://img-blog.csdnimg.cn/2020111720081077.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L051YW5fRmVuZw==,size_16,color_FFFFFF,t_70)
# 核心代码
![](https://img-blog.csdnimg.cn/20201117201158581.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L051YW5fRmVuZw==,size_16,color_FFFFFF,t_70)


![](https://img-blog.csdnimg.cn/20201117200857811.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L051YW5fRmVuZw==,size_16,color_FFFFFF,t_70)

# 具体可查看本人博客
[[手写分布式id生成器]][id]
[id]: https://blog.csdn.net/Nuan_Feng/article/details/109751744
