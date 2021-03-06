package com.alun.springtest;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Optional;
import java.util.Random;

import org.junit.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;
import reactor.test.publisher.TestPublisher;
/**
 * 测试Flux类的方法
 * @author xyd-yuyilun
 * 详情见：https://blog.csdn.net/simple_chao/article/details/73648238
 * 原文：https://www.ibm.com/developerworks/cn/java/j-cn-with-reactor-response-encode/index.html?lnk=hmhm
 *
 */
public class ReactAPITest {
	
	@Test
	public void staticMethodTest() {
		//可以指定序列中包含的全部元素。创建出来的 Flux 序列在发布这些元素之后会自动结束。
		Flux.just("Hello","world").subscribe(System.out::println);
		//fromArray()，fromIterable()和 fromStream()：可以从一个数组、Iterable 对象或 Stream 对象中创建 Flux 对象。
		Flux.fromArray(new Integer[] {1,2,3}).subscribe(System.out::println);
		//empty()：创建一个不包含任何元素，只发布结束消息的序列。
		Flux.empty().subscribe(System.out::println);
		//创建包含从 start 起始的 count 个数量的 Integer 对象的序列。
		Flux.range(1, 10).subscribe(System.out::println);
		//interval(Duration period)和 interval(Duration delay, Duration period)：创建一个包含了从 0 开始递增的 Long 对象的序列。其中包含的元素按照指定的间隔来发布。除了间隔时间之外，还可以指定起始元素发布之前的延迟时间。
		Flux.interval(Duration.of(10,ChronoUnit.SECONDS)).subscribe(System.out::println);
		
	}
	
	@Test
	public void generateTest() {
		final Random random = new Random();
		//	generate()方法通过同步和逐一的方式来产生 Flux 序列。序列的产生是通过调用所提供的 SynchronousSink 对象的 next()，complete()和 error(Throwable)方法来完成的。逐一生成的含义是在具体的生成逻辑中，next()方法只能最多被调用一次。在有些情况下，序列的生成可能是有状态的，需要用到某些状态对象。
		//此时可以使用 generate()方法的另外一种形式 generate(Callable<S> stateSupplier, BiFunction<S,SynchronousSink<T>,S> generator)，其中 stateSupplier 用来提供初始的状态对象。在进行序列生成时，状态对象会作为 generator 使用的第一个参数传入，可以在对应的逻辑中对该状态对象进行修改以供下一次生成时使用。	
		//第一个序列的生成逻辑中通过 next()方法产生一个简单的值，然后通过 complete()方法来结束该序列。如果不调用 complete()方法，所产生的是一个无限序列
		Flux.generate(sink ->{
			String value = random.nextInt(100)+":SSS";
			sink.next(value);
			if(value.length() == 5) {
				sink.complete();
			}
		}).subscribe(System.out::println);
		
		//第二个序列的生成逻辑中的状态对象是一个 ArrayList 对象。实际产生的值是一个随机数。产生的随机数被添加到 ArrayList 中。当产生了 10 个数时，通过 complete()方法来结束序列。
		Flux.generate(HashMap::new,(map,sink) -> {
			String value = random.nextInt(100)+":AAA";
			//list.add(value);
			sink.next(value);
			if(value.length() == 5) {
				sink.complete();
			}
			return map;
		}).subscribe(System.out::println);
	}
	
	@Test
	public void createTest() {
		//create()方法与 generate()方法的不同之处在于所使用的是 FluxSink 对象。FluxSink 支持同步和异步的消息产生，并且可以在一次调用中产生多个元素。在代码清单 3 中，在一次调用中就产生了全部的 10 个元素。
		Flux.create(sink -> {
			for(int i=0;i<10;i++) {
				sink.next(i);
			}
			sink.complete();
		}).subscribe(System.out::println);
		
	}
	
	@Test
	public void monoStaticMethodTest() {
		//fromCallable()、fromCompletionStage()、fromFuture()、fromRunnable()和 fromSupplier()：分别从 Callable、CompletionStage、CompletableFuture、Runnable 和 Supplier 中创建 Mono。
		Mono.fromSupplier(()-> "Hello World").subscribe(System.out::println);
		//justOrEmpty(Optional<? extends T> data)和 justOrEmpty(T data)：从一个 Optional 对象或可能为 null 的对象中创建 Mono。只有 Optional 对象中包含值或对象不为 null 时，Mono 序列才产生对应的元素。
		Mono.justOrEmpty(Optional.of("Hello")).subscribe(System.out::println);
		
		Mono.create(sink -> sink.success("Hello")).subscribe(System.out::println);
		
		
	}
	
	@Test
	public void bufferTest() {
		//buffer 和 bufferTimeout
		//这两个操作符的作用是把当前流中的元素收集到集合中，并把集合对象作为流中的新元素。
		//在进行收集时可以指定不同的条件：所包含的元素的最大数量或收集的时间间隔。
		//方法 buffer()仅使用一个条件，而 bufferTimeout()可以同时指定两个条件。
		//指定时间间隔时可以使用 Duration 对象或毫秒数，
		
		//从1到100，每隔20，当前流中的元素收集到集合中，并把集合对象作为流中的新元素
		Flux.range(1, 100).buffer(20).subscribe(System.out::println);
		//从0开始每隔100毫秒产生一个数。每1001毫秒将流中元素集合成新的元素，抓取2次，转为stream，循环处理
		Flux.interval(Duration.of(100, ChronoUnit.MILLIS)).buffer(Duration.ofMillis(1001)).take(2).toStream().forEach(System.out::println);
		//从1到10，bufferUntil（），i -> i % 2 == 0  直到是偶数，就开始重新收集流中元素
		//bufferUntil 会一直收集直到 Predicate 返回为 true。使得 Predicate 返回 true 的那个元素可以选择添加到当前集合或下一个集合中
		Flux.range(1, 10).bufferUntil(i -> i % 2 == 0).subscribe(System.out::println);
		//bufferWhile 则只有当 Predicate 返回 true 时才会收集。一旦值为 false，会立即开始下一次收集。
		Flux.range(1, 10).bufferWhile(i -> i % 2 == 0).subscribe(System.out::println);
		
		
		//TODO:question：如何使用bufferwhen
		Flux.range(1, 200).delayElements(Duration.ofMillis(25))
		.bufferWhen(Flux.interval(Duration.ZERO, Duration.ofMillis(200)), 
				open -> Mono.delay(Duration.ofMillis(100)))
		.subscribe(System.out::println);
	}
	
	@Test
	public void filterTest() {
		//对流中包含的元素进行过滤，只留下满足 Predicate 指定条件的元素。代码清单 6 中的语句输出的是 1 到 10 中的所有偶数。
		Flux.range(1, 100).filter(i -> i % 2 == 0 ).subscribe(System.out::println);
	}
	
	@Test
	public void windowTest() {
		//window 操作符的作用类似于 buffer，所不同的是 window 操作符是把当前流中的元素收集到另外的 Flux 序列中，因此返回值类型是 Flux<Flux<T>>。
		//在代码清单 7 中，两行语句的输出结果分别是 5 个和 2 个 UnicastProcessor 字符。这是因为 window 操作符所产生的流中包含的是 UnicastProcessor 类的对象，而 UnicastProcessor 类的 toString 方法输出的就是 UnicastProcessor 字符。
		Flux.range(1, 100).window(20).subscribe(System.out::println);
		
		//Flux<Flux<Integer>> window = Flux.range(1, 100).window(20);
		
		//TODO:如何处理UnicastProcessor
		Flux.interval(Duration.ofMillis(100)).window(Duration.ofMillis(1100)).take(2).toStream().forEach(System.out::println);
	}
	
	@Test
	public void zipWithTest() {
		//zipWith 操作符把当前流中的元素与另外一个流中的元素按照一对一的方式进行合并。在合并时可以不做任何处理，由此得到的是一个元素类型为 Tuple2 的流；
		//也可以通过一个 BiFunction 函数对合并的元素进行处理，所得到的流的元素类型为该函数的返回值。
		
		Flux.just("1","2").zipWith(Flux.just("c","d"))
		.subscribe(System.out::println);
		
		Flux.just("3","4").zipWith(Flux.just("a","b"),(s1,s2) -> String.format("%s-%s",s1, s2))
		.subscribe(System.out::println);
		
	}
	
	@Test
	public void takeTest() {
		//take 系列操作符用来从当前流中提取元素。提取的方式可以有很多种。
		
		Flux.range(1, 1000).take(10).subscribe(System.out::println);
		Flux.range(1, 1000).takeLast(10).subscribe(System.out::println);
		Flux.range(1, 1000).takeWhile(i -> i < 10).subscribe(System.out::println);
		Flux.range(1, 1000).takeUntil(i -> i == 10).subscribe(System.out::println);
		
		//TODO:如何使用takeUntilOther
		Flux.range(1, 7).takeUntilOther(Flux.range(1, 6)).subscribe(System.out::println);
	}
	
	@Test
	public void reduceTest() {
		//reduce 和 reduceWith 操作符对流中包含的所有元素进行累积操作，得到一个包含计算结果的 Mono 序列。
		//累积操作是通过一个 BiFunction 来表示的。
		//在操作时可以指定一个初始值。如果没有初始值，则序列的第一个元素作为初始值。
		Flux.range(1, 100).reduce((x,y) -> x+y).subscribe(System.out::println);
		
		Flux.range(1, 100).reduceWith(() -> 100, (x, y) -> x + y).subscribe(System.out::println);
	}
	
	@Test
	public void mergeTest() {
		//merge 和 mergeSequential 操作符用来把多个流合并成一个 Flux 序列。
		//不同之处在于 merge 按照所有流中元素的实际产生顺序来合并，
		//而 mergeSequential 则按照所有流被订阅的顺序，以流为单位进行合并。
		//代码清单 11 中分别使用了 merge 和 mergeSequential 操作符。
		//进行合并的流都是每隔 100 毫秒产生一个元素，不过第二个流中的每个元素的产生都比第一个流要延迟 50 毫秒。
		//在使用 merge 的结果流中，来自两个流的元素是按照时间顺序交织在一起；
		//而使用 mergeSequential 的结果流则是首先产生第一个流中的全部元素，再产生第二个流中的全部元素。
		Flux.merge(Flux.interval(Duration.ZERO, Duration.ofMillis(100)).take(5), Flux.interval(Duration.ofMillis(100), Duration.ofMillis(100)).take(5))
		.toStream()
		.forEach(System.out::println);
		
		Flux.mergeSequential(Flux.interval(Duration.ZERO, Duration.ofMillis(100)).take(5), Flux.interval(Duration.ofMillis(100), Duration.ofMillis(100)).take(5))
		.toStream()
		.forEach(System.out::println);
		
	}
	
	@Test
	public void flatMapTest() {
		//flatMap 和 flatMapSequential 操作符把流中的每个元素转换成一个流，再把所有流中的元素进行合并。
		//flatMapSequential 和 flatMap 之间的区别与 mergeSequential 和 merge 之间的区别是一样的。
		//在代码清单 12 中，流中的元素被转换成每隔 100 毫秒产生的数量不同的流，再进行合并。
		//由于第一个流中包含的元素数量较少，所以在结果流中一开始是两个流的元素交织在一起，然后就只有第二个流中的元素。
		Flux.just(5,10,15)
        	.flatMap(x -> Flux.interval(Duration.ofMillis(x*10), Duration.ofMillis(100)).take(x))
        	.toStream()
        	.forEach(x->{System.out.println(x.toString()+"a----");});
		
		Flux.just(5,10)
		.flatMapSequential(x -> Flux.interval(Duration.ofMillis(x*10), Duration.ofMillis(100)).take(x))
		.toStream()
		.forEach(System.out::println);
		
	}
	
	@Test
	public void concatMapTest() {
		//concatMap 操作符的作用也是把流中的每个元素转换成一个流，再把所有流进行合并。
		//与 flatMap 不同的是，concatMap 会根据原始流中的元素顺序依次把转换之后的流进行合并；
		//与 flatMapSequential 不同的是，concatMap 对转换之后的流的订阅是动态进行的，
		//而 flatMapSequential 在合并之前就已经订阅了所有的流。
		//代码清单 13 与代码清单 12 类似，只不过把 flatMap 换成了 concatMap，
		//结果流中依次包含了第一个流和第二个流中的全部元素。
		Flux.just(5,10).concatMap(x -> Flux.interval(Duration.ofMillis(x*10),Duration.ofMillis(100)).take(x))
		.toStream()
		.forEach(System.out::println);
		
	}
	
	
	@Test
	public void combineLatest() {
		//combineLatest 操作符把所有流中的最新产生的元素合并成一个新的元素，作为返回结果流中的元素。
		//只要其中任何一个流中产生了新的元素，合并操作就会被执行一次，结果流中就会产生新的元素。
		//在 代码清单 14 中，流中最新产生的元素会被收集到一个数组中，通过 Arrays.toString 方法来把数组转换成 String。
		
		Flux.combineLatest(Arrays::toString,
				Flux.interval(Duration.ofMillis(100)).take(5), 
				Flux.interval(Duration.ofMillis(50),Duration.ofMillis(100)).take(5),
				Flux.interval(Duration.ofMillis(100),Duration.ofMillis(100)).take(5))
		.toStream().forEach(System.out::println);
		
	}
	
	@Test
	public void subscribeTest() {
		//当需要处理 Flux 或 Mono 中的消息时，如之前的代码清单所示，可以通过 subscribe 方法来添加相应的订阅逻辑。
		//在调用 subscribe 方法时可以指定需要处理的消息类型。
		//可以只处理其中包含的正常消息，也可以同时处理错误消息和完成消息。
		//代码清单 15 中通过 subscribe()方法同时处理了正常消息和错误消息。
		
		Flux.just(1,2).concatWith(Mono.error(new IllegalStateException()))
		.subscribe(System.out::println, System.err::println);
		
		//第一种策略是通过 onErrorReturn()方法返回一个默认值。
		Flux.just(3,4).concatWith(Mono.error(new IllegalStateException()))
		.onErrorReturn(0)
		.subscribe(System.out::println);
		
		//第三种策略是通过 onErrorResume()方法来根据不同的异常类型来选择要使用的产生元素的流
		Flux.just(11,12).concatWith(Mono.error(new IllegalStateException()))
		.onErrorResume(e -> {
			if(e instanceof IllegalStateException) {
				return Mono.just(0);
			}else if(e instanceof IllegalArgumentException) {
				return Mono.just(-1);
			}
			return Mono.empty();
		}).subscribe(System.out::println);
		
		//当出现错误时，还可以通过 retry 操作符来进行重试。
		//重试的动作是通过重新订阅序列来实现的。在使用 retry 操作符时可以指定重试的次数。
		//代码清单 19 中指定了重试次数为 1，所输出的结果是 1，2，1，2 和错误信息。
		Flux.just(7,8)
		.concatWith(Mono.error(new IllegalStateException()))
		.retry(1)
		.subscribe(System.out::println);
		
	}
	
	
	
	//*********************************************************************************
	//调度器Scheduler
	@Test
	public void schedulerTest() {
		//前面介绍了反应式流和在其上可以进行的各种操作，通过调度器（Scheduler）可以指定这些操作执行的方式和所在的线程。有下面几种不同的调度器实现。
		//当前线程，通过 Schedulers.immediate()方法来创建。
		//单一的可复用的线程，通过 Schedulers.single()方法来创建。
		//使用弹性的线程池，通过 Schedulers.elastic()方法来创建。线程池中的线程是可以复用的。当所需要时，新的线程会被创建。如果一个线程闲置太长时间，则会被销毁。该调度器适用于 I/O 操作相关的流的处理。
		//使用对并行操作优化的线程池，通过 Schedulers.parallel()方法来创建。其中的线程数量取决于 CPU 的核的数量。该调度器适用于计算密集型的流的处理。
		//使用支持任务调度的调度器，通过 Schedulers.timer()方法来创建。
		//从已有的 ExecutorService 对象中创建调度器，通过 Schedulers.fromExecutorService()方法来创建。
		
		//在代码清单 20 中，使用 create()方法创建一个新的 Flux 对象，其中包含唯一的元素是当前线程的名称。
		//接着是两对 publishOn()和 map()方法，其作用是先切换执行时的调度器，再把当前的线程名称作为前缀添加。
		//最后通过 subscribeOn()方法来改变流产生时的执行方式。
		//运行之后的结果是[elastic-2] [single-1] parallel-1。
		//最内层的线程名字 parallel-1 来自产生流中元素时使用的 Schedulers.parallel()调度器，
		//中间的线程名称 single-1 来自第一个 map 操作之前的 Schedulers.single()调度器，
		//最外层的线程名字 elastic-2 来自第二个 map 操作之前的 Schedulers.elastic()调度器。
		Flux.create(sink ->{
			sink.next(Thread.currentThread().getName());
			sink.complete();
		})
		.publishOn(Schedulers.single())//单一的可复用的线程
		.map(x -> String.format("[%s] %s", Thread.currentThread().getName(),x))
		.publishOn(Schedulers.elastic())//使用弹性的线程池，通过 Schedulers.elastic()方法来创建。线程池中的线程是可以复用的。当所需要时，新的线程会被创建。如果一个线程闲置太长时间，则会被销毁。该调度器适用于 I/O 操作相关的流的处理。
		.map(x -> String.format("[%s] %s", Thread.currentThread().getName(), x))
		.subscribeOn(Schedulers.parallel())//使用对并行操作优化的线程池，通过 Schedulers.parallel()方法来创建。其中的线程数量取决于 CPU 的核的数量。该调度器适用于计算密集型的流的处理。
		.toStream()
		.forEach(System.out::println);
		
	}
	
	
	//*****************************************************************************
	//测试
	@Test
	public void stepVerifierTest() {
		//在对使用 Reactor 的代码进行测试时，需要用到 io.projectreactor.addons:reactor-test 库。
		
		//进行测试时的一个典型的场景是对于一个序列，验证其中所包含的元素是否符合预期。StepVerifier 的作用是可以对序列中包含的元素进行逐一验证。
		//在代码清单 21 中，需要验证的流中包含 a 和 b 两个元素。
		//通过 StepVerifier.create()方法对一个流进行包装之后再进行验证。
		//expectNext()方法用来声明测试时所期待的流中的下一个元素的值，
		//而 verifyComplete()方法则验证流是否正常结束。类似的方法还有 verifyError()来验证流由于错误而终止。
		StepVerifier.create(Flux.just("a","b"))
		.expectNext("a")
		.expectNext("b")
		.verifyComplete();
		
		//有些序列的生成是有时间要求的，比如每隔 1 分钟才产生一个新的元素。在进行测试中，不可能花费实际的时间来等待每个元素的生成。此时需要用到 StepVerifier 提供的虚拟时间功能。通过 StepVerifier.withVirtualTime()方法可以创建出使用虚拟时钟的 StepVerifier。通过 thenAwait(Duration)方法可以让虚拟时钟前进。
		//在代码清单 22 中，需要验证的流中包含两个产生间隔为一天的元素，并且第一个元素的产生延迟是 4 个小时。在通过 StepVerifier.withVirtualTime()方法包装流之后，expectNoEvent()方法用来验证在 4 个小时之内没有任何消息产生，然后验证第一个元素 0 产生；接着 thenAwait()方法来让虚拟时钟前进一天，然后验证第二个元素 1 产生；最后验证流正常结束。
		StepVerifier.withVirtualTime(() -> Flux.interval(Duration.ofHours(4), Duration.ofDays(1)).take(2))
		.expectSubscription()
		.expectNoEvent(Duration.ofHours(4))
		.expectNext(0L)
		.thenAwait(Duration.ofHours(1))
		.expectNext(1L)
		.expectComplete();
		
	}
	
	@Test
	public void testPublisherTest() {
		//TestPublisher 的作用在于可以控制流中元素的产生，甚至是违反反应流规范的情况。
		//在代码清单 23 中，通过 create()方法创建一个新的 TestPublisher 对象，然后使用 next()方法来产生元素，使用 complete()方法来结束流。
		//TestPublisher 主要用来测试开发人员自己创建的操作符。
		
		
		TestPublisher<Object> create = TestPublisher.create();
		
		create.next("a");
		create.next("b");
		create.complete();
		
		StepVerifier.create(create)
		.expectNext("a")
		.expectNext("b")
		.expectComplete();
		
		
	} 
	
	
	//*********************************************************************************
	//调试
	@Test
	public void testHook() {
		//另外一种做法是通过 checkpoint 操作符来对特定的流处理链来启用调试模式。代码清单 25 中，在 map 操作符之后添加了一个名为 test 的检查点。当出现错误时，检查点名称会出现在异常堆栈信息中。对于程序中重要或者复杂的流处理链，可以在关键的位置上启用检查点来帮助定位可能存在的问题。
		Hooks.onOperatorDebug();
		Flux.range(1, 10).subscribe(System.out::println);
		
		Flux.just(1, 0).map(x -> 1 / x).checkpoint("test").subscribe(System.out::println);
		
	}
	
	@Test
	public void logTest() {
		//在开发和调试中的另外一项实用功能是把流相关的事件记录在日志中。这可以通过添加 log 操作符来实现。
		//在代码清单 26 中，添加了 log 操作符并指定了日志分类的名称。
		Flux.range(1, 2).log(String.format("[%s]: %s", "2018-04-09","logging")).subscribe(System.out::println);
	}
	
	
	//****************************************************
	//hot and cold
	@Test
	public void hotAndColdTest() throws Exception {
		//在代码清单 28 中，原始的序列中包含 10 个间隔为 1 秒的元素。通过 publish()方法把一个 Flux 对象转换成 ConnectableFlux 对象。方法 autoConnect()的作用是当 ConnectableFlux 对象有一个订阅者时就开始产生消息。代码 source.subscribe()的作用是订阅该 ConnectableFlux 对象，让其开始产生数据。
		//接着当前线程睡眠 5 秒钟，第二个订阅者此时只能获得到该序列中的后 5 个元素，因此所输出的是数字 5 到 9。
		Flux<Long> source = Flux.interval(Duration.ofMillis(1000))
		.take(10)
		.publish()
		.autoConnect();
		
		source.subscribe();
		
		Thread.sleep(5000);
		source.toStream()
		.forEach(System.out::println);
		
	}
	
	//***************************
	/**
	 * 2018-4-10 暂时函数式编程结束
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	
	
}
