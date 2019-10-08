public class ElderlyCustomer extends Customer {
  private double maxWaitTime;
  private Event waitEvent;
  private Event leaveEvent;

  public ElderlyCustomer(int id, double time, double maxWaitTime) {
    super(id, time, false, CustomerType.ELDERLY);
    this.maxWaitTime = maxWaitTime;
  }

  double getPatience() {
    return this.maxWaitTime;
  }

  void startWaiting(Event waiting, Event leaving) {
    this.waitEvent = waiting;
    this.leaveEvent = leaving;
  }

  Event getWaitEvent() {
    return this.waitEvent;
  }

  Event getLeaveEvent() {
    return this.leaveEvent;
  }
}