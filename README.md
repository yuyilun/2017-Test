# 2017-Test

20180410，重构整个目录

----------


#  20180422#
- 一、分布式架构原理

（1）cdn 静态加速 动态加速
https://jingyan.baidu.com/article/54b6b9c086aa3d2d583b47e5.html
（2）基本原理
https://blog.csdn.net/chunlongyu/article/details/52525604
https://blog.csdn.net/chancein007/article/details/53730991
https://blog.csdn.net/zjcjava/article/details/78893368

- 二、分布式架构网络通讯原理

1、传输的标准格式是什么？ 
2、怎么样将请求转化为传输的流？ 
3、 怎么接收和处理流？ 
4、传输协议是？


----------


# 20180426 #

java-datastructure  



- 数据结构分析
线性表（栈、队列），树（二叉、哈夫曼），图主要就这三类。
其他附带算法应该还有排序、查找、遍历之类的。


线性表 ->
栈的插入和删除操作只允许在表的尾端进行（在栈中成为“栈顶”），满足“FIFO：First In Last Out”；
队列只允许在表尾插入数据元素，在表头删除数据元素,满足“First In First Out”。
https://blog.csdn.net/bbc955625132551/article/details/72773285
https://jingyan.baidu.com/article/6c67b1d6a09f9a2786bb1e4a.html


二叉树 ->
满二叉树：Full Binary Tree，国内教程定义：一个二叉树，如果每一个层的结点数都达到最大值，则这个二叉树就是满二叉树。也就是说，如果一个二叉树的层数为K，且结点总数是(2^k) -1 ，则它就是满二叉树。
国外(国际)定义:a binary tree T is full if each node is either a leaf or possesses exactly two childnodes.
大意为：如果一棵二叉树的结点要么是叶子结点，要么它有两个孩子结点，这样的树就是满二叉树。

完全二叉树：Complete Binary Tree，若设二叉树的深度为h，除第 h 层外，其它各层 (1～h-1) 的结点数都达到最大个数，第 h 层所有的结点都连续集中在最左边，这就是完全二叉树。

 二叉查找树（二叉排序树）
 avl自平衡二叉树
 redblack二叉树
 

----------

 
# 20180502 #

数据优化：
https://blog.csdn.net/yzsind/article/details/6059209

1、  减少数据访问（减少磁盘访问）
index:索引
sql plan：执行计划

（1）创建并使用正确的索引
（2）只通过索引访问数据
（3）优化SQL执行计划

2、  返回更少数据（减少网络传输或磁盘访问）
pagination：分页
result:结果集

（1）数据分页处理：客户端(应用程序或浏览器)分页，应用服务器分页，数据库SQL分页
（2）只返回需要的字段：优点：
1、减少数据在网络上传输开销
2、减少服务器数据处理开销
3、减少客户端内存占用
4、字段变更时提前发现问题，减少程序BUG
5、如果访问的所有字段刚好在一个索引里面，则可以使用纯索引访问提高性能。
缺点：增加编码工作量


3、  减少交互次数（减少网络传输）
fetchsize：如果查询数据量大，可以使用流数据
batch:批量

（1）batch DML
（2）In List，ORACLE的IN里就不允许超过1000个值。
综合考虑，一般IN里面的值个数超过20个以后性能基本没什么太大变化，也特别说明不要超过100，
超过后可能会引起执行计划的不稳定性及增加数据库CPU及内存成本，这个需要专业DBA评估。

（3）设置Fetch Size
当我们采用select从数据库查询数据时，数据默认并不是一条一条返回给客户端的，也不是一次全部返回客户端的，
而是根据客户端fetch_size参数处理，每次只返回fetch_size条记录，当客户端游标遍历到尾部时再从服务端取数据，
直到最后全部传送完成。所以如果我们要从服务端一次取大量数据时，可以加大fetch_size，
这样可以减少结果数据传输的交互次数及服务器数据准备时间，提高性能。

（4）使用存储过程
（5）优化业务逻辑
（6）使用ResultSet游标处理记录

4、  减少服务器CPU开销（减少CPU及内存开销）
sort：排序,索引排序；文件排序
优化Filesort，1. 加大 max_length_for_sort_data 参数的设置；2. 去掉不必要的返回字段；3. 增大 sort_buffer_size 参数设置
https://blog.csdn.net/shine0181/article/details/17807345

bind var:为了在这种情况下避免硬解析,需要使用绑定变量(bind variable)，绑定参数，防SQL注入
https://blog.csdn.net/tianlesoftware/article/details/5458896

（1）使用绑定变量
Java中Preparestatement就是为处理绑定变量提供的对像，绑定变量有以下优点：
1、防止SQL注入
2、提高SQL可读性
3、提高SQL解析性能，不使用绑定变更我们一般称为硬解析，使用绑定变量我们称为软解析。

一些不使用绑定变量的场景：
a、数据仓库应用，这种应用一般并发不高，但是每个SQL执行时间很长，SQL解析的时间相比SQL执行时间比较小，绑定变量对性能提高不明显。数据仓库一般都是内部分析应用，所以也不太会发生SQL注入的安全问题。
b、数据分布不均匀的特殊逻辑，如产品表，记录有1亿，有一产品状态字段，上面建有索引，有审核中，审核通过，审核未通过3种状态，其中审核通过9500万，审核中1万，审核不通过499万。

（2）合理使用排序
以下列出了可能会发生排序操作的SQL语法：
Order by
Group by
Distinct
Exists子查询
Not Exists子查询
In子查询
Not In子查询
Union（并集），Union All也是一种并集操作，但是不会发生排序，如果你确认两个数据集不需要执行去除重复数据操作，那请使用Union All 代替Union。
Minus（差集）
Intersect（交集）
Create Index
Merge Join，这是一种两个表连接的内部算法，执行时会把两个表先排序好再连接，应用于两个大表连接的操作。如果你的两个表连接的条件都是等值运算，那可以采用Hash Join来提高性能，因为Hash Join使用Hash 运算来代替排序的操作。具体原理及设置参考SQL执行计划优化专题。

（3）减少比较操作
我们SQL的业务逻辑经常会包含一些比较操作，如a=b，a<b之类的操作，
对于这些比较操作数据库都体现得很好，但是如果有以下操作，我们需要保持警惕：
Like模糊查询，如下所示：
a like ‘%abc%’
 
Like模糊查询对于数据库来说不是很擅长，特别是你需要模糊检查的记录有上万条以上时，性能比较糟糕，
这种情况一般可以采用专用Search或者采用全文索引方案来提高性能。

（4）大量复杂运算在客户端处理
什么是复杂运算，一般我认为是一秒钟CPU只能做10万次以内的运算。如含小数的对数及指数运算、三角函数、3DES及BASE64数据加密算法等等。
如果有大量这类函数运算，尽量放在客户端处理，一般CPU每秒中也只能处理1万-10万次这样的函数运算，放在数据库内不利于高并发处理。

5、  利用更多资源（增加资源）
parallel(多核技术，多线程)

（1）客户端多进程并行访问
多进程并行访问是指在客户端创建多个进程(线程)，每个进程建立一个与数据库的连接，然后同时向数据库提交访问请求。
当数据库主机资源有空闲时，我们可以采用客户端多进程并行访问的方法来提高性能。如果数据库主机已经很忙时，
采用多进程并行访问性能不会提高，反而可能会更慢。所以使用这种方式最好与DBA或系统管理员进行沟通后再决定是否采用。

以下是一些如何设置并行数的基本建议：
如果瓶颈在服务器主机，但是主机还有空闲资源，那么最大并行数取主机CPU核数和主机提供数据服务的磁盘数两个参数中的最小值，同时要保证主机有资源做其它任务。
如果瓶颈在客户端处理，但是客户端还有空闲资源，那建议不要增加SQL的并行，而是用一个进程取回数据后在客户端起多个进程处理即可，进程数根据客户端CPU核数计算。
如果瓶颈在客户端网络，那建议做数据压缩或者增加多个客户端，采用map reduce的架构处理。
如果瓶颈在服务器网络，那需要增加服务器的网络带宽或者在服务端将数据压缩后再处理了。

（2）数据库并行处理
并不是所有的SQL都可以使用并行处理，一般只有对表或索引进行全部访问时才可以使用并行。
数据库表默认是不打开并行访问，所以需要指定SQL并行的提示，如下所示：
select /*+parallel(a,4)*/ * from employee;

并行的优点：
使用多进程处理，充分利用数据库主机资源（CPU,IO），提高性能。
并行的缺点：
1、单个会话占用大量资源，影响其它会话，所以只适合在主机负载低时期使用；
2、只能采用直接IO访问，不能利用缓存数据，所以执行前会触发将脏缓存数据写入磁盘操作。

1、并行处理在OLTP类系统中慎用，使用不当会导致一个会话把主机资源全部占用，而正常事务得不到及时响应，所以一般只是用于数据仓库平台。
2、一般对于百万级记录以下的小表采用并行访问性能并不能提高，反而可能会让性能更差。



----------


# 20180504 #

http://www.importnew.com/19275.html

（1）JVM调优工具
Jconsole，jProfile，VisualVM

Jconsole : jdk自带，功能简单，但是可以在系统有一定负荷的情况下使用。对垃圾回收算法有很详细的跟踪。详细说明参考这里

JProfiler：商业软件，需要付费。功能强大。详细说明参考这里

VisualVM：JDK自带，功能强大，与JProfiler类似。推荐。

（2）如何调优
1、观察内存释放情况、集合类检查、对象树
（1）堆信息查看
可查看堆空间大小分配（年轻代、年老代、持久代分配）
提供即时的垃圾回收功能
垃圾监控（长时间监控回收情况）
（2）查看堆内类、对象信息查看：数量、类型等
（3）对象引用情况查看 

有了堆信息查看方面的功能，我们一般可以顺利解决以下问题：

–年老代年轻代大小划分是否合理

–内存泄漏

–垃圾回收算法设置是否合理

2、线程监控
线程信息监控：系统线程数量。

线程状态监控：各个线程都处在什么样的状态下

Dump线程详细信息：查看线程内部运行情况

死锁检查 

热点分析



----------


# 20180505 #
- 一、事务
> 1、 什么叫事务

构成单一逻辑单元的操作集合

> 2、事务的特性

1. 原子性，事务是不可再分的，事务中的所有操作在数据库中要么全部正确反映出来，要么完全不反映。
2. 隔离性，尽管多个事务并发，但系统保证，对于任何一对事务Ti和Tj，在Ti看来，Tj或者在Ti开始之前已经完成执行，或者在Tj完成之后开始执行。因此，每个事务都感觉不到系统中有其他的事务在并发的执行。
3. 持久性，一个事务成功完成后，他对数据库的改变是永久的，即使出现系统故障。
4. 一致性，隔离执行



----------


# 20180507 #

1、 volatile关键字作用
一旦一个共享变量（类的成员变量、类的静态成员变量）被volatile修饰之后，那么就具备了两层语义：
1）保证了不同线程对这个变量进行操作时的可见性，即一个线程修改了某个变量的值，这新值对其他线程来说是立即可见的。
2）禁止进行指令重排序。

atomic是利用CAS来实现原子性操作的（Compare And Swap），CAS实际上是利用处理器提供的CMPXCHG指令实现的，
而处理器执行CMPXCHG指令是一个原子性操作。volatile没办法保证对变量的操作的原子性。

volatile关键字禁止指令重排序有两层意思：
1）当程序执行到volatile变量的读写操作时，在其前面的操作的更改肯定已经进行，
且结果对后面的操作可见；在其后面的操作肯定还没有进行。

2）在进行指令优化时，不能把volatile变量前面的语句放在其后面执行，也不能把volatile变量后面的语句放到其前面执行。


2、 volatile的原理和实现机制

观察加入volatile关键字和没有加入volatile关键字时所生成的汇编代码发现，加入volatile关键字时，会多出一个lock前缀指令。
lock前缀指令实际上相当于一个内存屏障（也成内存栅栏），内存屏障会提供2个功能：
1）它确保指令重排序时不会把其后面的指令排到内存屏障之前的位置，也不会把前面的指令排到内存屏障的后面；即在执行到内存屏障这句指令时，在它前面的操作已经全部完成；

2）如果对声明了volatile变量进行写操作时，JVM会向处理器发送一条LOCK前缀指令，将这个变量所在缓存行的数据写回到系统内存。其他处理器缓存还是旧的，为了保证各个处理器缓存一致，每个处理器会通过嗅探在总线上传播的数据来检查自己的缓存是否过期，当处理器发现自己缓存行对应的内存地址被修改了，就会将当前处理器的缓存行设置成无效状态，当处理器要对这个数据进行修改操作时，会强制重新从系统内存中把数据读到处理器缓存。




3、并发编程时的特性

（1）共享性，保证数据的一致性。

如果所有的数据只是在线程内有效，那就不存在线程安全性问题。

（2）互斥性，是指同时只允许一个访问者对其进行访问，具有唯一性和排它性。

我们通常允许多个线程同时对数据进行读操作，但同一时间内只允许一个线程对数据进行写操作。
所以我们通常将锁分为共享锁和排它锁，也叫做读锁和写锁。

（3）原子性，是指对数据的操作是一个独立的、不可分割的整体。

换句话说，就是一次操作，是一个连续不可中断的过程，数据不会执行到一半的时候被其他线程所修改。

（4）可见性，每个线程修改的共享变量，对其他线程是否及时可见。

每个线程都有一个自己的工作内存（相当于CPU高级缓冲区，这么做的目的还是在于进一步缩小存储系统与CPU之间速度的差异，提高性能），
对于共享变量，线程每次读取的是工作内存中共享变量的副本，写入的时候也直接修改工作内存中副本的值，
然后在某个时间点上再将工作内存与主内存中的值进行同步。这样导致的问题是，如果线程1对某个变量进行了修改，
线程2却有可能看不到线程1对共享变量所做的修改。

（5）有序性，为了提高性能，编译器和处理器可能会对指令做重排序。

重排序可以分为三种：
（1）编译器优化的重排序。编译器在不改变单线程程序语义的前提下，可以重新安排语句的执行顺序。
（2）指令级并行的重排序。现代处理器采用了指令级并行技术（Instruction-Level Parallelism， ILP）来将多条指令重叠执行。
 如果不存在数据依赖性，处理器可以改变语句对应机器指令的执行顺序。
（3）内存系统的重排序。由于处理器使用缓存和读/写缓冲区，这使得加载和存储操作看上去可能是在乱序执行。


4、Synchronized

（1）作用

	（1）确保线程互斥的访问同步代码。
	（2）保证共享变量的修改能够及时可见。
	（3）有效解决重排序问题。

（2）用法

    （1）修饰普通方法
    （2）修饰静态方法
    （3）修饰代码块

（3）原理

在java语言中存在两种内建的synchronized语法：1、synchronized语句；2、synchronized方法。

对于synchronized语句，当Java源代码被javac编译成bytecode的时候，会在同步块的入口位置和退出位置分别插入monitorenter和monitorexit字节码指令。

而synchronized方法则会被翻译成普通的方法调用和返回指令如:invokevirtual、areturn指令，在VM字节码层面并没有任何特别的指令来实现被synchronized修饰的方法，而是在Class文件的方法表中将该方法的access_flags字段中的synchronized标志位置1，表示该方法是同步方法并使用调用该方法的对象或该方法所属的Class在JVM的内部对象表示Klass做为锁对象。

简单来说在JVM中monitorenter和monitorexit字节码依赖于底层的操作系统的Mutex Lock来实现的，但是由于使用Mutex Lock需要将当前线程挂起并从用户态切换到内核态来执行，这个状态之间的转换需要相对比较长的时间，时间成本相对较高。

（4）优化
jdk1.6中对锁的实现引入了大量的优化，如锁粗化（Lock Coarsening）、锁消除（Lock Elimination）、轻量级锁（Lightweight Locking）、偏向锁（Biased Locking）、适应性自旋（Adaptive Spinning）等技术来减少锁操作的开销。

锁粗化（Lock Coarsening）：将多个连续的锁扩展成一个范围更大的锁，用以减少频繁互斥同步导致的性能损耗。

锁消除（Lock Elimination）：JVM及时编译器在运行时，通过逃逸分析，如果判断一段代码中，堆上的所有数据不会逃逸出去被其他线程访问到，就可以去除这些锁。

轻量级锁（Lightweight Locking）：JDK1.6引入。在没有多线程竞争的情况下避免重量级互斥锁，只需要依靠一条CAS原子指令就可以完成锁的获取及释放。

偏向锁（Biased Locking）：JDK1.6引入。目的是消除数据在无竞争情况下的同步原语。使用CAS记录获取它的线程。下一次同一个线程进入则偏向该线程，无需任何同步操作。

适应性自旋（Adaptive Spinning）：为了避免线程频繁挂起、恢复的状态切换消耗。产生了忙循环（循环时间固定），即自旋。JDK1.6引入了自适应自旋。自旋时间根据之前锁自旋时间和线程状态，动态变化，用以期望能减少阻塞的时间。







（5）过程

第一步，检查MarkWord里面是不是放的自己的ThreadId ,如果是，表示当前线程是处于 “偏向锁”.跳过轻量级锁直接执行同步体。
第二步，如果MarkWord不是自己的ThreadId,锁升级，这时候，用CAS来执行切换，新的线程根据MarkWord里面现有的ThreadId，通知之前线程暂停，之前线程将Markword的内容置为空。
第三步，两个线程都把对象的HashCode复制到自己新建的用于存储锁的记录空间，接着开始通过CAS操作，把共享对象的MarKword的内容修改为自己新建的记录空间的地址的方式竞争MarkWord.
第四步，第三步中成功执行CAS的获得资源，失败的则进入自旋.
第五步，自旋的线程在自旋过程中，成功获得资源(即之前获的资源的线程执行完成并释放了共享资源)，则整个状态依然处于轻量级锁的状态，如果自旋失败 第六步，进入重量级锁的状态，这个时候，自旋的线程进行阻塞，等待之前线程执行完成并唤醒自己.


