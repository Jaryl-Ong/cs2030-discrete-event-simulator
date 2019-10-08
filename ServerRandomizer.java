import java.util.Random;

public class ServerRandomizer {
  private final long seed;
  private double restRate;
  private Random serverRand;

  public ServerRandomizer(long seed, double restRate) {
    this.seed = seed;
    this.restRate = restRate;
    this.serverRand = new Random(this.seed + 2);
  }

  public double toRest() {
    return serverRand.nextDouble();
  }

  public double restTime() {
    return rng(this.serverRand, this.restRate);
  }

  private double rng(Random rand, double rate) {
    return -Math.log(rand.nextDouble()) / rate;
  }
}