public class Event {
  private State eventType;
  private Customer cust;
  private double eventTime;
  private Server server;
  private CustomerType customerType;

  // creates Event with customer, eventTime and eventType
  public Event(Customer c, double time, State eventType, Server server, CustomerType custType) {
    this.eventType = eventType;
    this.cust = c;
    this.eventTime = time;
    this.server = server;
    this.customerType = custType;
  }

  // with no server
  public Event(Customer c, State eventType) {
    if (eventType == State.ARRIVES) {
      this.eventType = eventType;
      this.cust = c;
      this.server = null;
      this.eventTime = c.getArrTime();
      this.customerType = c.getType();
    } else {
      throw new IllegalArgumentException("Wrong constructor - need to provide more parameters");
    }
  }

  // this function helps to push events to the next state and
  // also log the events to the console
  Event processEvent(State nextState, double time) {
    return this.processEvent(nextState, time, null);
  }

  Event processEvent(State nextState, double time, Server newServer) {
    if (newServer != null) {
      this.server = newServer;
    }
    Event result = transferState(nextState, time);
    return this.eventType.processEvent.apply(this, result);
  }

  static Event processArrives(Event curr, Event next) {
    switch (next.getEventType()) {
    case WAITS:
      Logger.logEvent(curr);
      Logger.logEvent(next);
      return next;
    case ARRIVES:
      next.getCustomer().willBeBack(next.getTime());
      return next;
    default:
      Logger.logEvent(curr);
      return next;
    }
  }

  static Event processWaits(Event curr, Event next) {
    switch (next.getEventType()) {
    case SERVED:
      return next;
    default:
      return null;
    }
  }

  static Event processServed(Event curr, Event next) {
    Logger.logEvent(curr);
    switch (next.getEventType()) {
    case DONE:
      return next;
    default:
      return null;
    }
  }

  // The following methods below are identical, but have been kept as separate
  // methods
  // to allow easier expansion, if required.

  static Event processRest(Event curr, Event next) {
    Logger.logEvent(curr);
    return null;
  }

  static Event processBack(Event curr, Event next) {
    Logger.logEvent(curr);
    return null;
  }

  static Event processDone(Event curr, Event next) {
    Logger.logEvent(curr);
    return null;
  }

  static Event processLeaves(Event curr, Event next) {
    Logger.logEvent(curr);
    return null;
  }

  static Event processUnknown(Event curr, Event next) {
    Logger.logEvent(curr);
    return null;
  }

  // for retrieval of customer when creating follow-up events
  // need to insert checking condition
  Customer getCustomer() {
    return this.cust;
  }

  // to identify event type
  // need to insert checking condition
  State getEventType() {
    return this.eventType;
  }

  // to fetch event time
  // need to insert checking condition
  double getTime() {
    return this.eventTime;
  }

  // to calculate time waited for the customer
  double getStats() {
    return this.eventTime - this.cust.getArrTime();
  }

  // fetch the server that is/will be serving the customer
  Server getServer() {
    return this.server;
  }

  private Event transferState(State nextState, double time) {
    Event result = new Event(this.cust, time, nextState, this.server, this.customerType);
    return result;
  }

  CustomerType getCustomerType() {
    return this.customerType;
  }
}
