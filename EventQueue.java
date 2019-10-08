import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.function.Predicate;

public class EventQueue {
  private PriorityQueue<Event> queue;
  private int maxSize;

  public EventQueue(int size, Comparator<Event> x) {
    this.maxSize = size;
    this.queue = new PriorityQueue<Event>(x);
  }

  // check if queue is clear
  public boolean isEmpty() {
    return this.queue.isEmpty();
  }

  // pop event from the front of the queue
  public Event popEvent() {
    Event result = this.queue.poll();
    return result;
  }

  public void removeEvent(Event specific) {
    this.queue.remove(specific);
  }

  // push event into the back of the queue and sort
  public void pushEvent(Event e) {
    if (this.queue.size() + 1 <= maxSize) {
      this.queue.offer(e);
    }
  }

  // returns event queue size
  public int size() {
    return this.queue.size();
  }

  // filter by type of Customer
  public int filterCustomer(Predicate<Event> pred) {
    return (int) this.queue.stream().filter(x -> pred.test(x)).count();
  }
}