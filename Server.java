import java.util.function.Predicate;

public abstract class Server {
  private int id;
  private int queueSize;
  protected Shop shop;
  protected EventQueue currCustomer;
  private EventQueue queue;
  private double servingTime;
  protected int servMaxCust;
  protected boolean toRest;

  public Server(int id, int size, Shop shop, double servingTime, int servMaxCust) {
    this.id = id;
    this.queueSize = size;
    this.queue = new EventQueue(this.queueSize, new ServerEventComp());
    this.shop = shop;
    this.servingTime = servingTime;
    this.servMaxCust = servMaxCust;
    this.currCustomer = new EventQueue(this.servMaxCust, new ServerEventComp());
    this.toRest = false;
  }

  // to check if it will rest
  abstract boolean serverWillRest();

  // process the first customer currently serving
  // and take in first customer in the queue at the same time
  void processCurrentCustomer(double currTime) {
    if (this.gotQueue() && !this.toRest) {
      // System.out.println("Moving from queue");
      Event nextCustomer = this.queue.popEvent();
      nextCustomer = nextCustomer.processEvent(State.SERVED, currTime, this);
      this.currCustomer.pushEvent(nextCustomer);
      this.shop.serverPush(nextCustomer);
    } else {
      this.currCustomer.popEvent();
    }
  }

  // return serverID
  int getID() {
    return this.id;
  }

  // push customer to the appropriate queue
  void queueCustomer(Event e) {
    if (!this.isBusy() && (!this.toRest)) {
      this.currCustomer.pushEvent(e);
    } else {
      this.queue.pushEvent(e);
    }
  }

  // check if there is a queue - for shop to use.
  boolean gotQueue() {
    return !this.queue.isEmpty();
  }

  // check if server is already serving max customers
  boolean isBusy() {
    return this.currCustomer.size() == this.servMaxCust;
  }

  // check if queue is full
  boolean queueIsFull() {
    return this.queue.size() >= this.queueSize;
  }

  double getServingTime() {
    return this.servingTime;
  }

  int getQueueSize() {
    return this.queue.size();
  }

  int getElderly() {
    return this.queue.filterCustomer(new Predicate<Event>() {
      @Override
      public boolean test(Event x) {
        return x.getCustomerType() == CustomerType.ELDERLY;
      }
    });
  }

  boolean isGoingToRest() {
    return this.toRest;
  }

  void elderlyLeaving(Event curr) {
    this.queue.removeEvent(curr);
  }
}