import java.util.ArrayList;

public class Shop {
  private ArrayList<Server> servers;
  private EventQueue globalQueue;
  private double currTime;
  private double totalWait;
  private int servedCustomers;
  private int leftCustomers;

  public Shop(EventQueue queue, int numHumanServer, int numMachineServer, int servMaxCust, int servQueueSize,
      double probRest, ServerRandomizer servRand, ServiceTimeRandomizer servTimeRand) {
    this.globalQueue = queue;
    this.totalWait = 0;
    ServerGenerator serGen = new ServerGenerator(numHumanServer, numMachineServer, this, servMaxCust, servQueueSize,
        probRest, servRand, servTimeRand);
    this.servers = serGen.generate();
  }

  // continuously process events in the global queue
  public void runShop() {
    while (!this.globalQueue.isEmpty()) {
      Event curr = this.globalQueue.popEvent();
      if (curr.getTime() > currTime)
        currTime = curr.getTime();
      switch (curr.getEventType()) {
      case ARRIVES:
        if (this.hasUnusedServers()) {
          serveArrived(curr);
        } else if (this.hasEmptyQueues()) {
          queueArrived(curr);
        } else {
          leaveArrived(curr);
        }
        break;
      case LEAVES:
        leavingShop(curr);
        break;
      case SERVED:
        servingServed(curr);
        break;
      case DONE:
        doneWithTheShop(curr);
        break;
      case REST:
        curr.processEvent(State.BACK, currTime);
        break;
      case BACK:
        HumanServer temp = (HumanServer) curr.getServer();
        temp.stopResting(currTime);
        curr.processEvent(State.UNKNOWN, currTime);
        break;
      default:
        break;
      }
    }
  }

  // modularising the shop running
  // when there are servers available to serve
  private void serveArrived(Event curr) {
    ArrayList<Server> unused = this.unusedServers();
    Server to_use = curr.getCustomerType().findPreferredServer(unused);
    Event newEvent = curr.processEvent(State.SERVED, currTime, to_use);
    to_use.queueCustomer(newEvent);
    this.globalQueue.pushEvent(newEvent);
  }

  // when there are no servers available to serve but with available queues
  private void queueArrived(Event curr) {
    ArrayList<Server> emptyQueues = this.emptyQueueServers();
    Server to_use = curr.getCustomerType().findPreferredQueue(emptyQueues);
    Event newEvent = curr.processEvent(State.WAITS, currTime, to_use);
    to_use.queueCustomer(newEvent);
    if (newEvent.getCustomerType() == CustomerType.ELDERLY) {
      ElderlyCustomer elderlyCurr = (ElderlyCustomer) newEvent.getCustomer();
      Event leaving = new Event(curr.getCustomer(), currTime + elderlyCurr.getPatience(), State.LEAVES, to_use,
          CustomerType.ELDERLY);
      this.globalQueue.pushEvent(leaving);
      elderlyCurr.startWaiting(newEvent, leaving);
    }
  }

  // when no servers are available and the
  private void leaveArrived(Event curr) {
    if (!curr.getCustomer().willReturn()) { // if the customer will not return
      Event newEvent = curr.processEvent(State.LEAVES, currTime);
      this.globalQueue.pushEvent(newEvent);
    } else { // if the customer will return
      GeneralCustomer currCust = (GeneralCustomer) curr.getCustomer();
      Event newEvent = curr.processEvent(State.LEAVES, currTime);
      this.globalQueue.pushEvent(newEvent);
      newEvent = curr.processEvent(State.ARRIVES, currTime + currCust.getWaitTime());
      this.globalQueue.pushEvent(newEvent);
    }
  }

  // serve
  private void servingServed(Event curr) {
    Server to_use = curr.getServer();
    Event newEvent = curr.processEvent(State.DONE, currTime + to_use.getServingTime());
    this.globalQueue.pushEvent(newEvent);
    this.totalWait += curr.getStats();
    if (newEvent.getCustomerType() == CustomerType.ELDERLY) {
      ElderlyCustomer elderlyCurr = (ElderlyCustomer) newEvent.getCustomer();
      this.globalQueue.removeEvent(elderlyCurr.getLeaveEvent());
    }
  }

  // leaving
  private void leavingShop(Event curr) {
    curr.processEvent(State.UNKNOWN, currTime);
    leftCustomers += 1;
    if (curr.getCustomerType() == CustomerType.ELDERLY) {
      ElderlyCustomer elderlyCurr = (ElderlyCustomer) curr.getCustomer();
      if (elderlyCurr.getWaitEvent() != null) {
        Server to_use = curr.getServer();
        to_use.elderlyLeaving(elderlyCurr.getWaitEvent());
      }
    }
  }

  // customer done
  private void doneWithTheShop(Event curr) {
    Server to_use = curr.getServer();
    if (to_use.serverWillRest()) {
      HumanServer temp = (HumanServer) to_use;
      temp.willItRest();
      temp.processCurrentCustomer(currTime);
      curr.processEvent(State.UNKNOWN, currTime);
      this.servedCustomers++;
      temp.checkRest(currTime);
    } else {
      to_use.processCurrentCustomer(currTime);
      curr.processEvent(State.UNKNOWN, currTime);
      this.servedCustomers++;
    }
  }

  // function is for server to push to global queue
  void serverPush(Event next) {
    this.globalQueue.pushEvent(next);
  }

  // result to return to simulator
  String returnResults() {
    String result = String.format("%.3f, %d, %d", (totalWait / servedCustomers), servedCustomers, leftCustomers);
    return result;
  }

  // boolean check on servers that can serve now
  private boolean hasUnusedServers() {
    return this.unusedServers().size() > 0;
  }

  // boolean check on servers with queues with spots
  private boolean hasEmptyQueues() {
    return this.emptyQueueServers().size() > 0;
  }

  // to filter out a list of servers available to serve
  private ArrayList<Server> unusedServers() {
    ArrayList<Server> result = new ArrayList<Server>();
    for (Server x : this.servers) {
      if (!x.gotQueue() && !x.isBusy() && !x.isGoingToRest()) {
        result.add(x);
      }
    }
    return result;
  }

  // to filter out a list of servers that have queues for queuing
  private ArrayList<Server> emptyQueueServers() {
    ArrayList<Server> result = new ArrayList<Server>();
    for (Server x : this.servers) {
      if (!x.queueIsFull()) {
        result.add(x);
      }
    }
    return result;
  }

}