import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;
import java.util.Comparator;

public class GA_Simulation {

  // Use the instructions to identify the class variables, constructors, and methods you need

  ArrayList<Individual> population = new ArrayList<Individual>();

  int n = 100;
  int k = 15;
  int r = 100;
  int c_0 = 8;
  int c_max = 20;
  double m = 0.01;
  int g = 5;

  /**
   * @param n
   * @param k
   * @param r
   * @param c_0
   * @param c_max
   * @param m
   * @param g
   */
  public GA_Simulation(int n, int k, int r, int c_0, int c_max, double m, int g) {
    this.n = n;
    this.k = k;
    this.r = r;
    this.c_0 = c_0;
    this.c_max = c_max;
    this.m = m;
    this.g = g;
  }

  /**
   * @param n
   */
  public void init (int n) {
    
    for (int i = 0; i < n; i++) {
      Individual individual = new Individual(c_0, c_max);
      population.add(individual);
    }

  }

  /** Sorts population by fitness score, best first 
   * @param pop: ArrayList of Individuals in the current generation
   * @return: ArrayList of Individuals sorted by fitness
  */
    public void rankPopulation(ArrayList<Individual> pop) {
        // sort population by fitness
        Comparator<Individual> ranker = new Comparator<>() {
          // this order will sort higher scores at the front
          public int compare(Individual c1, Individual c2) {
            return (int)Math.signum(c2.getFitness()-c1.getFitness());
          }
        };
        pop.sort(ranker); 
      }

      public void evolve() {

      }

      public void describeGeneration() {

      }

      public void run() {

      }

      public static void main(String[] args) {
        GA_Simulation test = new GA_Simulation(100, 15, 100, 8, 20, 0.01, 5);
        test.init(100);
        test.run();
      }
}