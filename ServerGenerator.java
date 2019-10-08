import java.util.ArrayList;

public class ServerGenerator {
  private ArrayList<Server> servers;
  private int serverQueueSize;
  private Shop shop;
  private int servMaxCust;
  private double probRest;
  private ServerRandomizer servRand;
  private ServiceTimeRandomizer servTimeRand;
  private int numHumanServer;
  private int numMachineServer;
  private static int currID = 0;

  // fill in settings for server generation
  public ServerGenerator(int numHumanServer, int numMachineServer, Shop shop, int servMaxCust, int servQueueSize,
      double probRest, ServerRandomizer servRand, ServiceTimeRandomizer servTimeRand) {
    // this.numServers = numHumanServer + numMachineServer;
    this.numHumanServer = numHumanServer;
    this.numMachineServer = numMachineServer;
    servers = new ArrayList<Server>();
    this.shop = shop;
    this.servMaxCust = servMaxCust;
    this.serverQueueSize = servQueueSize;
    this.probRest = probRest;
    this.servRand = servRand;
    this.servTimeRand = servTimeRand;
  }

  // doing the actual generating
  public ArrayList<Server> generate() {
    for (int i = 0; i < numHumanServer; i++) {
      this.servers.add(new HumanServer(currID++, this.serverQueueSize, this.shop, this.servTimeRand.serviceTime(),
          this.servMaxCust, this.probRest, this.servRand));
    }
    for (int i = 0; i < numMachineServer; i++) {
      this.servers.add(new MachineServer(currID++, this.serverQueueSize, this.shop, 1.000, this.servMaxCust));
    }
    return this.servers;
  }

  // possible expansion: function to overwrite settings so
  // as to allow reuse of a single server generator
}