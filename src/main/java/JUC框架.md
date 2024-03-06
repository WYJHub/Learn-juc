# JUC

![image-20240306105854904](https://yingjun-typora.oss-cn-hangzhou.aliyuncs.com/img/202403061059079.png)



## 线程存在的六种状态

new

runnable、blocked、waiting、time_waiting

terminated

## 三种让线程等待和唤醒的机制

1. synchornized、 wait、notify
2. ReentrantLock、condition、await、signal
3. locksupport、park阻塞、unpark发放凭证唤醒

详情见locksupport中笔记