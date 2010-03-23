package park

/** A utility for profiling code akin to scala.testing.Benchmark */
trait Bench {
  
  /** Module defining an interface for time */
  object Timing {
    
    case class Time(cpu: Long, user: Long, system: Long, real: Long) {
      val tuple = (cpu, user, system, real)
      def - (t: Time) = Time(cpu - t.cpu, user - t.user, system - t.system, real - t.real)
      def + (t: Time) = Time(cpu + t.cpu, user + t.user, system + t.system, real + t.real)
    }

    def cpu = get(bean getCurrentThreadCpuTime)

    def user = get(bean getCurrentThreadUserTime)

    def real = System.nanoTime

    def system = cpu - user

    def now = Time(cpu, user, system, real)

    val empty = Time(0l, 0l, 0l, 0l)
  
    def get(fn: => Long) = if(bean isCurrentThreadCpuTimeSupported) fn else 0l
  
    private val bean = java.lang.management.ManagementFactory.getThreadMXBean
  }

  /** Hook for running Bench as a main application. Args are expected to be
   *  (number of times to execute `run`) (number of observtions) */
  def main(args: Array[String]) = watch(args(0).toInt, args(1).toInt)

 /** @param n number of times per observation
  *  @param o number of observations */
  def watch(n: Int, o: Int) = { 
    headers
    for(i <- Stream.range(0, o)) collect(n)
  }

  /** Collects the results of observtions and renders the results 
   * @param times - the number of times to observe `run` */
  def collect(times: Int) = tell(subject, (Timing.empty /: observe(times))(_+_))

  /** a label for a line of profiling info */
  def subject = getClass getName

  /** generates a list of timings of executions of `run` 
   * @param n - the number of times to observe `run` */
  def observe(n: Int) = for(i <- Stream.range(0, n)) yield time { run }

  /** closure for capturing the time it takes to execute `run` */
  def time(block: => Any) = {
    val before = Timing.now
    block
    val after = Timing.now
    after - before
  }

  /** prints headers to stdout */
  def headers = println((" "*subject.size)+"\t\tcpu\t\tuser\t\tsystem\t\treal")

  /** prints one line summary of profiling info */
  def tell(subject: String, time: Timing.Time) = {
    def sec(n: Long) = n / 1000000000.0
    val (cpu, user, system, real) = time.tuple
    println(
      "%s\t\t%f\t%f\t%f\t%f" format(subject, sec(cpu), sec(user), sec(system), sec(real))
    )
  }

  /** implementation of block to run */
  def run
}