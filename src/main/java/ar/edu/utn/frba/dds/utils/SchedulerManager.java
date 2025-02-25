package ar.edu.utn.frba.dds.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Single thread pool for all the scheduled tasks.
 */
public class SchedulerManager {
  private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(10);

  /**
   * Schedule a task to run after a delay.
   *
   * @param task  the task to run
   * @param delay the delay before the task is executed
   * @param unit  the time unit of the delay parameter
   * @return a ScheduledFuture representing pending completion of the task
   */
  public static ScheduledFuture<?> scheduleTask(Runnable task, long delay, TimeUnit unit) {
    return scheduler.schedule(task, delay, unit);
  }

  /**
   * Shutdown the scheduler.
   */
  public static void shutdown() {
    scheduler.shutdownNow();
  }
}
