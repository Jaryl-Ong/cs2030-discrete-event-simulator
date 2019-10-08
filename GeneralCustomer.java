public class GeneralCustomer extends Customer {
  private double waitTime;

  public GeneralCustomer(int id, double arrTime, double waitTime) {
    super(id, arrTime, true, CustomerType.GENERAL);
    this.waitTime = waitTime;
  }

  double getWaitTime() {
    return this.waitTime;
  }
}