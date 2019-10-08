import java.util.Random;

public class CustomerGenerator{
  private Random typeRand;
  private Random custRand;
  private double probElderly;
  private double arrivalRate;
  private double rearriveRate;
  private double waitRate;
  private static int currCustID = 0;
  private static double currTime = 0;

  // initialises the queue instantly due to inefficiency of storing the input arrays
  // for later usage, unlike with ServerGenerator with uses default settings for generation


  public CustomerGenerator(long seed, double probElderly, double arrivalRate, double rearriveRate, double waitRate){
    this.typeRand = new Random(seed);
    this.custRand = new Random(seed+1);
    this.probElderly = probElderly;
    this.arrivalRate = arrivalRate;
    this.rearriveRate = rearriveRate;
    this.waitRate = waitRate;
  }

  public Customer nextCustomer(){
    double typeCust = typeRand.nextDouble();
      if (typeCust<=probElderly){
        currTime += rng(custRand, arrivalRate);
        ElderlyCustomer newCust = new ElderlyCustomer(currCustID++, currTime, rng(custRand, waitRate));
        return newCust;
      }else{
        currTime += rng(custRand, arrivalRate);
        GeneralCustomer newCust = new GeneralCustomer(currCustID++, currTime, rng(custRand, rearriveRate));
        return newCust;
      }
  }

  private double rng(Random rand, double rate){
    return - Math.log(rand.nextDouble())/rate;
  }

  // can add additional functions to enable reuse of a single customer
  // generator object.
}