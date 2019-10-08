public class HumanServer extends Server {
  private ServerRandomizer servRand;
  protected double probRest;

  public HumanServer(int id, int size, Shop shop, double servingTime, int servMaxCust, double probRest,
      ServerRandomizer servRand) {
    super(id, size, shop, servingTime, servMaxCust);
    this.servRand = servRand;
    this.probRest = probRest;
  }

  void checkRest(double currTime) {
    if (this.toRest && this.currCustomer.isEmpty()) {
      double restTime = this.servRand.restTime();
      this.shop.serverPush(new Event(null, currTime, State.REST, this, null));
      this.shop.serverPush(new Event(null, currTime + restTime, State.BACK, this, null));
    }
  }

  void willItRest() {
    if (!this.toRest) {
      double restingChance = this.servRand.toRest();
      if (restingChance <= this.probRest)
        this.toRest = true;
    }
  }

  void stopResting(double currTime) {
    this.toRest = false;
    // this.servMaxCust = 1;
    double tempTime = currTime;
    while (this.gotQueue() && this.currCustomer.size() < this.servMaxCust) {
      this.processCurrentCustomer(tempTime);
      tempTime += 0.001;
    }
  }

  @Override
  boolean serverWillRest() {
    return true;
  }
}