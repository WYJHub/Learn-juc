JDK中提供了12个原子操作类。

# 原子更新基本类型
使用原子的方式更新基本类型，Atomic包提供了以下3个类。
AtomicBoolean: 原子更新布尔类型。
AtomicInteger: 原子更新整型。
AtomicLong: 原子更新长整型。
以上3个类提供的方法几乎一模一样，可以参考上面AtomicInteger中的相关方法。

# 原子更新数组
通过原子的方式更新数组里的某个元素，Atomic包提供了以下的3个类：
AtomicIntegerArray: 原子更新整型数组里的元素。
AtomicLongArray: 原子更新长整型数组里的元素。
AtomicReferenceArray: 原子更新引用类型数组里的元素。
这三个类的最常用的方法是如下两个方法：
get(int index)：获取索引为index的元素值。
compareAndSet(int i,E expect,E update): 如果当前值等于预期值，则以原子方式将数组位置i的元素设置为update值。

# 原子更新引用类型
Atomic包提供了以下三个类：
AtomicReference: 原子更新引用类型。
AtomicStampedReference: 原子更新引用类型, 内部使用Pair来存储元素值及其版本号。
AtomicMarkableReferce: 原子更新带有标记位的引用类型。

这三个类提供的方法都差不多，首先构造一个引用对象，然后把引用对象set进Atomic类，然后调用compareAndSet等一些方法去进行原子操作，原理都是基于Unsafe实现，但AtomicReferenceFieldUpdater略有不同，更新的字段必须用volatile修饰。

# 原子更新字段类
Atomic包提供了三个类进行原子字段更新：
AtomicIntegerFieldUpdater: 原子更新整型的字段的更新器。
AtomicLongFieldUpdater: 原子更新长整型字段的更新器。
AtomicReferenceFieldUpdater: 上面已经说过此处不在赘述。

这三个类的使用方式都差不多，是基于反射的原子更新字段的值。要想原子地更新字段类需要两步:
第一步，因为原子更新字段类都是抽象类，每次使用的时候必须使用静态方法newUpdater()创建一个更新器，并且需要设置想要更新的类和属性。
第二步，更新类的字段必须使用public volatile修饰。
