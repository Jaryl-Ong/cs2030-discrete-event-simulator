import java.util.*;

public class Main {
  public static final int MAX_CUSTOMERS = 1000;
  public static final int MAX_EVENTS = 1100;

  public static void main(String[] args) {
    // INPUT
    Scanner scanner = new Scanner(System.in);
    // First line
    int numCustomer = scanner.nextInt();
    // Second line
    int servMaxCust = scanner.nextInt();
    int numHumanServer = scanner.nextInt();
    int servQueueSize = scanner.nextInt();
    int numMachineServer = scanner.nextInt();
    // Third line
    long seed = scanner.nextLong();
    double probElderly = scanner.nextDouble();
    double arrivalRate = scanner.nextDouble();
    double rearriveRate = scanner.nextDouble();
    double probRest = scanner.nextDouble();
    double restRate = scanner.nextDouble();
    double servRate = scanner.nextDouble();
    double waitRate = scanner.nextDouble();
    // Creation of 'global' randomizers
    ServerRandomizer servRand = new ServerRandomizer(seed, restRate);
    ServiceTimeRandomizer servTimeRand = new ServiceTimeRandomizer(seed, servRate);
    // Creation of 'global' generators
    EventQueue inputQueue = new EventQueue(MAX_EVENTS, new EventComp());
    CustomerGenerator custGen = new CustomerGenerator(seed, probElderly, arrivalRate, rearriveRate, waitRate);
    for (int i = 0; i < numCustomer; i++) {
      Customer newCust = custGen.nextCustomer();
      inputQueue.pushEvent(new Event(newCust, State.ARRIVES));
    }

    // Passing starting input into simulator
    // PROCESS
    String stats = Simulator.simulate(inputQueue, servMaxCust, numHumanServer, numMachineServer, servQueueSize,
        probRest, servRand, servTimeRand);

    // OUTPUT
    System.out.println(stats);
    scanner.close();
  }
}