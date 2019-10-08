public class Simulator {
  // initialises a shop object with the appropriate global and customer queues,
  // and let the shop run
  public static String simulate(EventQueue globalQueue, int servMaxCust, int numHumanServer, int numMachineServer,
      int servQueueSize, double probRest, ServerRandomizer servRand, ServiceTimeRandomizer servTimeRand) {
    // numOfServers = numServers;
    Shop newShop = new Shop(globalQueue, numHumanServer, numMachineServer, servMaxCust, servQueueSize, probRest,
        servRand, servTimeRand);
    newShop.runShop();
    return newShop.returnResults();
  }

  // concept of simulator is to simply generate the environments where inputs are
  // passed into and outputs are received.
  // this includes creating a shop, a street of shops, and other possible
  // expansions beyond shops.
}