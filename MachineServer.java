public class MachineServer extends Server {
  public MachineServer(int id, int size, Shop shop, double servingTime, int servMaxCust) {
    super(id, size, shop, servingTime, servMaxCust);
  }

  @Override
  boolean serverWillRest() {
    return false;
  }
}