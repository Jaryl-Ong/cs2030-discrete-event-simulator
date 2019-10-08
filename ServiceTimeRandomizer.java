import java.util.Random;

public class ServiceTimeRandomizer {
  private double servRate;
  private Random timeRand;

  public ServiceTimeRandomizer(long seed, double servRate) {
    this.servRate = servRate;
    this.timeRand = new Random(seed + 3);
  }

  public double serviceTime() {
    return rng(this.timeRand, this.servRate);
  }

  private double rng(Random rand, double rate) {
    return -Math.log(rand.nextDouble()) / rate;
  }
}