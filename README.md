# 2017-Test

20180410，重构整个目录

----------

#  20180422 # 分布式
一、分布式架构原理
1、cdn 静态加速 动态加速
https://jingyan.baidu.com/article/54b6b9c086aa3d2d583b47e5.html
2、基本原理
https://blog.csdn.net/chunlongyu/article/details/52525604
https://blog.csdn.net/chancein007/article/details/53730991
https://blog.csdn.net/zjcjava/article/details/78893368

二、分布式架构网络通讯原理

1、传输的标准格式是什么？ 
2、怎么样将请求转化为传输的流？ 
3、怎么接收和处理流？ 
4、传输协议是？

----------

# 20180426 # java-datastructure：数据结构
一、数据结构
1、定义：数据元素之间存在的关系，由n(n >= 0)个数据元素组成的有限集合，数据元素之间具有某种特定的元素。
2、逻辑结构
（1）线性结构。
（2）树结构。
（3）图
3、对数据的操作
初始化、判断是否是空、存取、统计个数、遍历、插入、删除、查找、排序 
4、算法
（1）算法：有穷规则的集合，其规则确定一个解决某一特定类型问题的操作序列。
（2）算法设计依赖数据的逻辑结构，实现依赖数据的存储结构。
（3）算法分析的两个方面：时间代价、空间代价。时间复杂度是指执行算法所需要的计算工作量；而空间复杂度是指执行这个算法所需要的内存空间。

二、线性表
1、顺序存储结构
顺序表（SeqList)使用一维数组一次存放书元素。一维数组占用一块内存空间，每个存储单元的地址是连续的，通过下标识别元素，它的下标就代表了他的存储单元序号，也就表示了它的位置。
例子：ArrayList

2、链式存储结构
线性表的链式存储结构(链表LinkedList)是用若干地址分散的存储单元存储数据元素，逻辑上相邻的数据元素在物理位置上不一定相邻。存储一个数据元素的存储单元成为结点Node，单链表的表示方式：结点（数据域，地址域）。
例子：LinkedList

3、特殊的线性表（栈和队列）
（1）栈
插入和删除操作只允许在表的尾端进行（在栈中成为“栈顶”），满足“FIFO：First In Last Out”；
例子：函数（方法）的调用
（2）队列
只允许在表尾插入数据元素，在表头删除数据元素,满足“First In First Out”。
例子：优先队列
https://blog.csdn.net/bbc955625132551/article/details/72773285
https://jingyan.baidu.com/article/6c67b1d6a09f9a2786bb1e4a.html

三、树和二叉树
1、满二叉树，Full Binary Tree。
（1）国内教程定义：一个二叉树，如果每一个层的结点数都达到最大值，则这个二叉树就是满二叉树。也就是说，如果一个二叉树的层数为K，且结点总数是(2^k) -1 ，则它就是满二叉树。
（2）国外(国际)定义:a binary tree T is full if each node is either a leaf or possesses exactly two childnodes.
大意为：如果一棵二叉树的结点要么是叶子结点，要么它有两个孩子结点，这样的树就是满二叉树。
2、完全二叉树：Complete Binary Tree。
若设二叉树的深度为h，除第 h 层外，其它各层 (1～h-1) 的结点数都达到最大个数，第 h 层所有的结点都连续集中在最左边，这就是完全二叉树。
3、Huffman树：带权外路径长度最小的二叉树，也成最优二叉树；Huffman编码是数据压缩技术中一种无损压缩编码。

4、二叉查找树（二叉排序树）
（1）avl自平衡二叉树：查找、插入和删除在平均和最坏情况下都是O（log n）。
它是一棵空树或它的左右两个子树的高度差的绝对值不超过1，并且左右两个子树都是一棵平衡二叉树。
前序遍历：根结点 ---> 左子树 ---> 右子树。(DLR)
中序遍历：左子树---> 根结点 ---> 右子树	。
后序遍历：左子树 ---> 右子树 ---> 根结点。
层次遍历：只需按层次遍历即可

（2）红-黑二叉树：O(log n)时间内做查找，插入和删除，这里的n是树中元素的数目。在实践中是高效的。
每个节点不是红色就是黑色的；
根节点总是黑色的；
如果节点是红色的，则它的子节点必须是黑色的（反之不一定）；
从根节点到叶节点或空子节点的每条路径，必须包含相同数目的黑色节点（即相同的黑色高度）。  
https://blog.csdn.net/eson_15/article/details/51144079

5、B/B+树
（1）平衡多路查找树，B树。
B树与红黑树最大的不同在于，B树的结点可以有许多子女，从几个到几千个。那为什么又说B树与红黑树很相似呢?因为与红黑树一样，一棵含n个结点的B树的高度也为O（lgn），但可能比一棵红黑树的高度小许多，应为它的分支因子比较大。
a、树中每个结点最多含有m个孩子（m>=2）；
b、除根结点和叶子结点外，其它每个结点至少有[ceil(m / 2)]个孩子（其中ceil(x)是一个取上限的函数）；
c、若根结点不是叶子结点，则至少有2个孩子（特殊情况：没有孩子的根结点，即根结点为叶子结点，整棵树只有一个根节点）；
d、所有叶子结点都出现在同一层，叶子结点不包含任何关键字信息(可以看做是外部接点或查询失败的接点，实际上这些结点不存在，指向这些结点的指针都为null)；（读者反馈@冷岳：这里有错，叶子节点只是没有孩子和指向孩子的指针，这些节点也存在，也有元素。@研究者July：其实，关键是把什么当做叶子结点，因为如红黑树中，每一个NULL指针即当做叶子结点，只是没画出来而已）。
f、每个非终端结点中包含有n个关键字信息： (n，P0，K1，P1，K2，P2，......，Kn，Pn)。其中：
       a)   Ki (i=1...n)为关键字，且关键字按顺序升序排序K(i-1)< Ki。 
       b)   Pi为指向子树根的接点，且指针P(i-1)指向子树种所有结点的关键字均小于Ki，但都大于K(i-1)。 
       c)   关键字的个数n必须满足： [ceil(m / 2)-1]<= n <= m-1。
       
（2）B+-tree：是应文件系统所需而产生的一种B-tree的变形树。
一棵m阶的B+树和m阶的B树的异同点在于：
a、有n棵子树的结点中含有n-1 个关键字； 
b、所有的叶子结点中包含了全部关键字的信息，及指向含有这些关键字记录的指针，且叶子结点本身依关键字的大小自小而大的顺序链接。 (而B 树的叶子节点并没有包括全部需要查找的信息)
c、所有的非终端结点可以看成是索引部分，结点中仅含有其子树根结点中最大（或最小）关键字。 (而B 树的非终节点也包含需要查找的有效信息)

为什么说B+-tree比B 树更适合实际应用中操作系统的文件索引和数据库索引？
1) B+-tree的磁盘读写代价更低
B+-tree的内部结点并没有指向关键字具体信息的指针。因此其内部结点相对B 树更小。如果把所有同一内部结点的关键字存放在同一盘块中，那么盘块所能容纳的关键字数量也越多。一次性读入内存中的需要查找的关键字也就越多。相对来说IO读写次数也就降低了。
举个例子，假设磁盘中的一个盘块容纳16bytes，而一个关键字2bytes，一个关键字具体信息指针2bytes。一棵9阶B-tree(一个结点最多8个关键字)的内部结点需要2个盘快。而B+ 树内部结点只需要1个盘快。当需要把内部结点读入内存中的时候，B 树就比B+ 树多一次盘块查找时间(在磁盘中就是盘片旋转的时间)。
2) B+-tree的查询效率更加稳定
由于非终结点并不是最终指向文件内容的结点，而只是叶子结点中关键字的索引。所以任何关键字的查找必须走一条从根结点到叶子结点的路。所有关键字查询的路径长度相同，导致每一个数据的查询效率相当。

B+ 树通常用于数据库和操作系统的文件系统中。B+ 树的特点是能够保持数据稳定有序，其插入与修改拥有较稳定的对数时间复杂度。
----------

# 20180502 #

数据优化：
https://blog.csdn.net/yzsind/article/details/6059209

一、减少数据访问（减少磁盘访问）
1、创建并使用正确的索引
SQL什么条件会使用索引？
当字段上建有索引时，通常以下情况会使用索引：
INDEX_COLUMN = ?
INDEX_COLUMN > ?
INDEX_COLUMN >= ?
INDEX_COLUMN < ?
INDEX_COLUMN <= ?
INDEX_COLUMN between ? and ?
INDEX_COLUMN in (?,?,...,?)
INDEX_COLUMN like ?||'%'（后导模糊查询）
T1. INDEX_COLUMN=T2. COLUMN1（两个表通过索引字段关联）
2、只通过索引访问数据
3、优化SQL执行计划

二、返回更少数据（减少网络传输或磁盘访问）
1、数据分页处理：
（1）客户端(应用程序或浏览器)分页。
（2）应用服务器分页。
（3）数据库SQL分页，直接通过rownum分页。采用rowid分页语法分页。
2、只返回需要的字段
（1）减少数据在网络上传输开销
（2）减少服务器数据处理开销
（3）减少客户端内存占用
（4）字段变更时提前发现问题，减少程序BUG
（5）如果访问的所有字段刚好在一个索引里面，则可以使用纯索引访问提高性能。

三、减少交互次数（减少网络传输）
fetchsize：如果查询数据量大，可以使用流数据；batch:批量。
1、batch DML，批处理。
2、In List，一般IN里面的值个数不应该超过20个。
3、Fetch Size，设置从服务器端抓取数据量的大小。适当加大fetch_size，减少结果数据传输的交互次数及服务器数据准备时间，提高性能。
4、使用存储过程
5、优化业务逻辑
6、jdbc原始的resultset游标处理记录，游标的打开方式设置为FORWARD_READONLY模式(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY)。否则会把结果缓存在JVM里，造成JVM Out of memory问题。

四、减少服务器CPU及内存开销
1、使用绑定变量(bind variable)。
为了在这种情况下避免硬解析,需要使用绑定变量，绑定参数，防SQL注入。
https://blog.csdn.net/tianlesoftware/article/details/5458896
（1）优点。
Java中Preparestatement就是为处理绑定变量提供的对像，绑定变量有以下优点：
防止SQL注入；提高SQL可读性；提高SQL解析性能，不使用绑定变更我们一般称为硬解析，使用绑定变量我们称为软解析。
（2）一些不使用绑定变量的场景：
a、数据仓库应用，这种应用一般并发不高，但是每个SQL执行时间很长，SQL解析的时间相比SQL执行时间比较小，绑定变量对性能提高不明显。数据仓库一般都是内部分析应用，所以也不太会发生SQL注入的安全问题。
b、数据分布不均匀的特殊逻辑，如产品表，记录有1亿，有一产品状态字段，上面建有索引，有审核中，审核通过，审核未通过3种状态，其中审核通过9500万，审核中1万，审核不通过499万。

2、合理使用排序
（1）sort：排序,索引排序。
（2）文件排序，优化Filesort。
这个 filesort 并不是说通过磁盘文件进行排序，而只是告诉我们进行了一个排序操作。
即在MySQL Query Optimizer 所给出的执行计划(通过 EXPLAIN 命令查看)中被称为文件排序（filesort）
a、加大 max_length_for_sort_data 参数的设置。
b、去掉不必要的返回字段。
c、增大 sort_buffer_size 参数设置。
https://blog.csdn.net/shine0181/article/details/17807345

以下列出了可能会发生排序操作的SQL语法：
Order by，Group by，Distinct，Exists子查询，Not Exists子查询，In子查询，Not In子查询
Union（并集），Union All也是一种并集操作，但是不会发生排序，如果你确认两个数据集不需要执行去除重复数据操作，那请使用Union All 代替Union。
Minus（差集），Intersect（交集）
Create Index
Merge Join，这是一种两个表连接的内部算法，执行时会把两个表先排序好再连接，应用于两个大表连接的操作。如果你的两个表连接的条件都是等值运算，那可以采用Hash Join来提高性能，因为Hash Join使用Hash 运算来代替排序的操作。具体原理及设置参考SQL执行计划优化专题。

3、减少比较操作
我们SQL的业务逻辑经常会包含一些比较操作，如a=b，a<b之类的操作，对于这些比较操作数据库都体现得很好。
但是如果有以下操作，我们需要保持警惕：
（1）Like模糊查询，如下所示：a like ‘%abc%’
Like模糊查询对于数据库来说不是很擅长，特别是你需要模糊检查的记录有上万条以上时，性能比较糟糕，
这种情况一般可以采用专用Search或者采用全文索引方案来提高性能。
（2）不能使用索引定位的大量In List

4、大量复杂运算在客户端处理
什么是复杂运算，一般我认为是一秒钟CPU只能做10万次以内的运算。如含小数的对数及指数运算、三角函数、3DES及BASE64数据加密算法等等。
如果有大量这类函数运算，尽量放在客户端处理，一般CPU每秒中也只能处理1万-10万次这样的函数运算，放在数据库内不利于高并发处理。

五、利用更多资源（增加资源）
parallel(多核技术，多线程)

1、客户端多进程并行访问
多进程并行访问是指在客户端创建多个进程(线程)，每个进程建立一个与数据库的连接，然后同时向数据库提交访问请求。
当数据库主机资源有空闲时，我们可以采用客户端多进程并行访问的方法来提高性能。如果数据库主机已经很忙时，
采用多进程并行访问性能不会提高，反而可能会更慢。所以使用这种方式最好与DBA或系统管理员进行沟通后再决定是否采用。

以下是一些如何设置并行数的基本建议：
如果瓶颈在服务器主机，但是主机还有空闲资源，那么最大并行数取主机CPU核数和主机提供数据服务的磁盘数两个参数中的最小值，同时要保证主机有资源做其它任务。
如果瓶颈在客户端处理，但是客户端还有空闲资源，那建议不要增加SQL的并行，而是用一个进程取回数据后在客户端起多个进程处理即可，进程数根据客户端CPU核数计算。
如果瓶颈在客户端网络，那建议做数据压缩或者增加多个客户端，采用map reduce的架构处理。
如果瓶颈在服务器网络，那需要增加服务器的网络带宽或者在服务端将数据压缩后再处理了。

2、数据库并行处理
并不是所有的SQL都可以使用并行处理，一般只有对表或索引进行全部访问时才可以使用并行。
数据库表默认是不打开并行访问，所以需要指定SQL并行的提示，如下所示：
select /*+parallel(a,4)*/ * from employee;

并行的优点：
使用多进程处理，充分利用数据库主机资源（CPU,IO），提高性能。
并行的缺点：
（1）单个会话占用大量资源，影响其它会话，所以只适合在主机负载低时期使用；
（2）只能采用直接IO访问，不能利用缓存数据，所以执行前会触发将脏缓存数据写入磁盘操作。
1、并行处理在OLTP类系统中慎用，使用不当会导致一个会话把主机资源全部占用，而正常事务得不到及时响应，所以一般只是用于数据仓库平台。
2、一般对于百万级记录以下的小表采用并行访问性能并不能提高，反而可能会让性能更差。

----------

# 20180504 #

http://www.importnew.com/19275.html

一、JVM调优工具
Jconsole，jProfile，VisualVM
Jconsole : jdk自带，功能简单，但是可以在系统有一定负荷的情况下使用。对垃圾回收算法有很详细的跟踪。
JProfiler：商业软件，需要付费。功能强大。详细说明参考这里
VisualVM：JDK自带，功能强大，与JProfiler类似。推荐。

二、如何调优
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
一、事务
1、 什么叫事务
构成单一逻辑单元的操作集合
2、事务的特性
（1）原子性，事务是不可再分的，事务中的所有操作在数据库中要么全部正确反映出来，要么完全不反映。
（2）隔离性，尽管多个事务并发，但系统保证，对于任何一对事务Ti和Tj，在Ti看来，Tj或者在Ti开始之前已经完成执行，或者在Tj完成之后开始执行。因此，每个事务都感觉不到系统中有其他的事务在并发的执行。
（3）持久性，一个事务成功完成后，他对数据库的改变是永久的，即使出现系统故障。
（4）一致性，隔离执行

----------

# 20180507 #

一、并发编程时的特性
1、共享性，保证数据的一致性。
如果所有的数据只是在线程内有效，那就不存在线程安全性问题。
2、互斥性，是指同时只允许一个访问者对其进行访问，具有唯一性和排它性。
我们通常允许多个线程同时对数据进行读操作，但同一时间内只允许一个线程对数据进行写操作。
所以我们通常将锁分为共享锁和排它锁，也叫做读锁和写锁。
3、原子性，是指对数据的操作是一个独立的、不可分割的整体。
换句话说，就是一次操作，是一个连续不可中断的过程，数据不会执行到一半的时候被其他线程所修改。
4、可见性，每个线程修改的共享变量，对其他线程是否及时可见。
每个线程都有一个自己的工作内存（相当于CPU高级缓冲区，这么做的目的还是在于进一步缩小存储系统与CPU之间速度的差异，提高性能），
对于共享变量，线程每次读取的是工作内存中共享变量的副本，写入的时候也直接修改工作内存中副本的值，然后在某个时间点上再将工作内存与主内存中的值进行同步。
这样导致的问题是，如果线程1对某个变量进行了修改，线程2却有可能看不到线程1对共享变量所做的修改。
5、有序性，为了提高性能，编译器和处理器可能会对指令做重排序。

二、重排序可以分为三种：
1、编译器优化的重排序。编译器在不改变单线程程序语义的前提下，可以重新安排语句的执行顺序。
2、指令级并行的重排序。现代处理器采用了指令级并行技术（Instruction-Level Parallelism， ILP）来将多条指令重叠执行。
如果不存在数据依赖性，处理器可以改变语句对应机器指令的执行顺序。
3、内存系统的重排序。由于处理器使用缓存和读/写缓冲区，这使得加载和存储操作看上去可能是在乱序执行。

三、Synchronized
1、作用
（1）确保线程互斥的访问同步代码。
（2）保证共享变量的修改能够及时可见。
（3）有效解决重排序问题。

2、用法
（1）修饰普通方法
（2）修饰静态方法
（3）修饰代码块

3、原理
在java语言中存在两种内建的synchronized语法：1、synchronized语句；2、synchronized方法。
（1）对于synchronized语句，当Java源代码被javac编译成bytecode的时候，会在同步块的入口位置和退出位置分别插入monitorenter和monitorexit字节码指令。
（2）而synchronized方法则会被翻译成普通的方法调用和返回指令如:invokevirtual、areturn指令，在VM字节码层面并没有任何特别的指令来实现被synchronized修饰的方法，而是在Class文件的方法表中将该方法的access_flags字段中的synchronized标志位置1，表示该方法是同步方法并使用调用该方法的对象或该方法所属的Class在JVM的内部对象表示Klass做为锁对象。
简单来说在JVM中monitorenter和monitorexit字节码依赖于底层的操作系统的Mutex Lock来实现的，但是由于使用Mutex Lock需要将当前线程挂起并从用户态切换到内核态来执行，这个状态之间的转换需要相对比较长的时间，时间成本相对较高。

4、优化
jdk1.6中对锁的实现引入了大量的优化，如锁粗化（Lock Coarsening）、锁消除（Lock Elimination）、轻量级锁（Lightweight Locking）、偏向锁（Biased Locking）、适应性自旋（Adaptive Spinning）等技术来减少锁操作的开销。
（1）锁粗化（Lock Coarsening）：将多个连续的锁扩展成一个范围更大的锁，用以减少频繁互斥同步导致的性能损耗。
（2）锁消除（Lock Elimination）：JVM及时编译器在运行时，通过逃逸分析，如果判断一段代码中，堆上的所有数据不会逃逸出去被其他线程访问到，就可以去除这些锁。
（3）轻量级锁（Lightweight Locking）：JDK1.6引入。在没有多线程竞争的情况下避免重量级互斥锁，只需要依靠一条CAS原子指令就可以完成锁的获取及释放。
（4）偏向锁（Biased Locking）：JDK1.6引入。目的是消除数据在无竞争情况下的同步原语。使用CAS记录获取它的线程。下一次同一个线程进入则偏向该线程，无需任何同步操作。
（5）适应性自旋（Adaptive Spinning）：为了避免线程频繁挂起、恢复的状态切换消耗。产生了忙循环（循环时间固定），即自旋。JDK1.6引入了自适应自旋。自旋时间根据之前锁自旋时间和线程状态，动态变化，用以期望能减少阻塞的时间。

5、过程
第一步，检查MarkWord里面是不是放的自己的ThreadId ,如果是，表示当前线程是处于 “偏向锁”.跳过轻量级锁直接执行同步体。
第二步，如果MarkWord不是自己的ThreadId,锁升级，这时候，用CAS来执行切换，新的线程根据MarkWord里面现有的ThreadId，通知之前线程暂停，之前线程将Markword的内容置为空。
第三步，两个线程都把对象的HashCode复制到自己新建的用于存储锁的记录空间，接着开始通过CAS操作，把共享对象的MarKword的内容修改为自己新建的记录空间的地址的方式竞争MarkWord.
第四步，第三步中成功执行CAS的获得资源，失败的则进入自旋.
第五步，自旋的线程在自旋过程中，成功获得资源(即之前获的资源的线程执行完成并释放了共享资源)，则整个状态依然处于轻量级锁的状态，如果自旋失败 第六步，进入重量级锁的状态，这个时候，自旋的线程进行阻塞，等待之前线程执行完成并唤醒自己.

# 20180509 #
一、 volatile关键字作用
一旦一个共享变量（类的成员变量、类的静态成员变量）被volatile修饰之后，那么就具备了两层语义：
1、保证了不同线程对这个变量进行操作时的可见性，即一个线程修改了某个变量的值，这新值对其他线程来说是立即可见的。
2、禁止进行指令重排序。
atomic是利用CAS来实现原子性操作的（Compare And Swap），CAS实际上是利用处理器提供的CMPXCHG指令实现的，
而处理器执行CMPXCHG指令是一个原子性操作。volatile没办法保证对变量的操作的原子性。
volatile关键字禁止指令重排序有两层意思：
（1）当程序执行到volatile变量的读操作或者写操作时，在其前面的操作的更改肯定全部已经进行，
且结果已经对后面的操作可见；在其后面的操作肯定还没有进行；
（2）在进行指令优化时，不能将在对volatile变量访问的语句放在其后面执行，也不能把volatile变量后面的语句放到其前面执行。

二、 volatile的原理和实现机制。
观察加入volatile关键字和没有加入volatile关键字时所生成的汇编代码发现，加入volatile关键字时，会多出一个lock前缀指令。
lock前缀指令实际上相当于一个内存屏障（也成内存栅栏），内存屏障会提供3个功能：
1、它确保指令重排序时不会把其后面的指令排到内存屏障之前的位置，也不会把前面的指令排到内存屏障的后面；
即在执行到内存屏障这句指令时，在它前面的操作已经全部完成；
2、它会强制将对缓存的修改操作立即写入主存；
3、如果是写操作，它会导致其他CPU中对应的缓存行无效。

# 2180510 #
一、JMM：Java Memory Model(Java内存模型)，围绕着在并发过程中如何处理可见性、原子性、有序性这三个特性而建立的模型。
1、可见性。
JMM提供了volatile变量定义、final、synchronized块来保证可见性。
例如：线程a在将共享变量x=1写入主内存的时候，如何保证线程b读取共享变量x的值为1，这就是JMM做的事情。JMM通过控制主内存与每个线程的本地内存之间的交互，来为java程序员提供内存可见性保证。
2、原子性。
JMM提供保证了访问基本数据类型的原子性（其实在写一个工作内存变量到主内存是分主要两步：store、write），但是实际业务处理场景往往是需要更大的范围的原子性保证，所以模型也提供了synchronized块来保证。
3、有序性。
这个概念是相对而言的，如果在本线程内，所有的操作都是有序的，如果在一个线程观察另一个线程，所有的操作都是无序的，前句是“线程内表现为串行行为”，后句是“指令的重排序”和“工作内存和主内存同步延迟”现象，模型提供了volatile和synchronized来保证线程之间操作的有序性。

二、重排序：
在执行程序时为了提高性能，编译器和处理器常常会对指令做重排序(编译器、处理器)，就是因为这些重排序，所以可能会导致多线程程序出现内存可见性问题(数据安全问题)和有序性问题。
JMM是如何处理的呢？
对于编译器，JMM的编译器重排序规则会禁止特定类型的编译器重排序
对于处理器重排序，JMM的处理器重排序规则会要求java编译器在生成指令序列时，插入特定类型的内存屏障（memory barriers，intel称之为memory fence）指令，通过内存屏障指令来禁止特定类型的处理器重排序
总之一句话，JMM是通过禁止特定类型的编译器重排序和处理器重排序来为程序员提供一致的内存可见性保证。

源代码——》 编译器优化重排序——》 指令级并行重排序——》 内存系统重排序——》 最终执行的指令序列

由于重排序会导致多线程程序出现内存可见性问题(数据安全问题)和有序性问题。
happens-before就是“什么什么一定在什么什么之前运行”，也就是保证顺序性。 

三、happens-before的定义
JSR-133使用happens-before的概念来指定两个操作之间的执行顺序。
JMM可以通过happens-before关系向程序员提供跨线程的内存可见性保证（如果A线程的写操作a与B线程的读操作b之间存在happensbefore关系，尽管a操作和b操作在不同的线程中执行，但JMM向程序员保证a操作将对b操作可见）。
在JMM中，如果一个操作执行的结果需要对另一个操作可见，那么这两个操作之间必须存在happens-before关系。
happens-before原则非常重要，它是判断数据是否存在竞争、线程是否安全的主要依据，依靠这个原则，我们解决在并发环境下两操作之间是否可能存在冲突的所有问题。

《JSR-133:Java Memory Model and Thread Specification》对happens-before关系的定义如下：
1、如果一个操作happens-before另一个操作，那么第一个操作的执行结果将对第二个操作可见，
而且第一个操作的执行顺序排在第二个操作之前。
2、两个操作之间存在happens-before关系，并不意味着Java平台的具体实现必须要按照happens-before关系指定的顺序来执行。
如果重排序之后的执行结果，与按happens-before关系来执行的结果一致，那么这种重排序并不非法。
上面1是JMM对程序员的承诺。从程序员的角度来说，可以这样理解happens-before关系：如果A happens-before B，那么Java内存模型将向程序员保证——A操作的结果将对B可见，且A的执行顺序排在B之前。注意，这只是Java内存模型向程序员做出的保证！
上面2是JMM对编译器和处理器冲排序的约束。MM其实是在遵循一个基本原则：只要不改变程序的执行结果，编译器和处理器怎么优化都行。happens-before这么做的目的，都是为了在不改变程序执行结果的前提下，尽可能地提高程序执行的并行度。

三、规则
1、程序顺序规则：一个线程内，按照代码顺序，书写在前面的操作先行发生于书写在后面的操作；
2、监视器锁规则：一个unLock操作先行发生于后面对同一个锁的lock操作；
3、volatile变量规则：对一个变量的写操作先行发生于后面对这个变量的读操作；
4、传递性：如果操作A先行发生于操作B，而操作B又先行发生于操作C，则可以得出操作A先行发生于操作C；
5、start规则：Thread对象的start()方法先行发生于此线程的每个一个动作；
6、join规则：线程中所有的操作都先行发生于线程的终止检测，我们可以通过Thread.join()方法结束、Thread.isAlive()的返回值手段检测到线程已经终止执行；
7、中断法则：对线程interrupt()方法的调用先行发生于被中断线程的代码检测到中断事件的发生；
8、终结法则：一个对象的构造函数结束一定发生在对象的finalizer之前；




