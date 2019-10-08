public class Logger {
  public static void logEvent(Event curr) {
    if (curr.getEventType() == State.ARRIVES) {
      System.out.printf("%.3f %d arrives\n", curr.getTime(), curr.getCustomer().getID());
    } else if (curr.getEventType() == State.WAITS) {
      System.out.printf("%.3f %d waits for %d\n", curr.getTime(), curr.getCustomer().getID(), curr.getServer().getID());
    } else if (curr.getEventType() == State.SERVED) {
      System.out.printf("%.3f %d served by %d\n", curr.getTime(), curr.getCustomer().getID(), curr.getServer().getID());
    } else if (curr.getEventType() == State.LEAVES) {
      System.out.printf("%.3f %d leaves\n", curr.getTime(), curr.getCustomer().getID());
    } else if (curr.getEventType() == State.DONE) {
      System.out.printf("%.3f %d done with %d\n", curr.getTime(), curr.getCustomer().getID(), curr.getServer().getID());
    } else if (curr.getEventType() == State.REST) {
      System.out.printf("%.3f %d is resting\n", curr.getTime(), curr.getServer().getID());
    } else if (curr.getEventType() == State.BACK) {
      System.out.printf("%.3f %d is back\n", curr.getTime(), curr.getServer().getID());
    }
  }
}