abstract class Customer {
  private int id;
  private double arrTime;
  protected boolean willReturn;
  protected CustomerType type;

  // constructor with id and arrTime
  public Customer(int id, double time, boolean willReturn, CustomerType type) {
    this.id = id;
    this.arrTime = time;
    this.willReturn = willReturn;
    this.type = type;
  }

  // insert checking condition here later
  int getID() {
    return this.id;
  }

  // insert checking condition here later
  double getArrTime() {
    return this.arrTime;
  }

  // to check if customer will return after leaving
  boolean willReturn() {
    return this.willReturn;
  }

  void willBeBack(double newArrTime) {
    if (newArrTime > this.arrTime) {
      this.arrTime = newArrTime;
    }
  }

  CustomerType getType() {
    return this.type;
  }
}
