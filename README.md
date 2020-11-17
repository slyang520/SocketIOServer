SocketIO Server base [Netty-socketio](https://github.com/mrniko/netty-socketio)
===
cluster base on redis

```
env jdk8, gradle4 

```


#  启动2个服务

![image-app01.png](doc/image-app01.png)、

![image-app02.png](doc/image-app02.png)

```
app1  im端口 9202
app2  im端口 9203
依赖redis 自行修改.
```

#   打开聊天窗口

```
link1: port_9092_user_159.html   连接上app1，用户为159
link2: port_9093_user_160.html   连接上app2, 用户为160

2个连接在不同服务的用户相互发送消息，用户能正常接收。
```
