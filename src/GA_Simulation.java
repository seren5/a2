import java.util.concurrent.ThreadLocalRandom;
import java.util.ArrayList;
import java.util.Comparator;

public class GA_Simulation {

    // Use the instructions to identify the class variables, constructors, and methods you need

    ArrayList < Individual > population = new ArrayList < Individual > ();
    ArrayList < Individual > fittestIndividuals = new ArrayList < > ();
    int currentGeneration = 1;

    //Initializes the following varaibles, which are used as parameters for GA Simulation
    int n = 100;
    int k = 15;
    int r = 100;
    int c0 = 8;
    int cMax = 20;
    double m = 0.01;
    int g = 5;

    /**
     * Creates a new GA Simulation
     * @param n The number of individuals in each generation
     * @param k The number of winners (individuals allowed to reproduce) in each generation
     * @param r The number of rounds of evolution to run
     * @param c0 The initial chromosome size
     * @param cMax The maximum chromosome size
     * @param m Chance per round of a mutation in each gene
     * @param g Number of states possible per gene
     */
    public GA_Simulation(int n, int k, int r, int c0, int cMax, double m, int g) {
        this.n = n;
        this.k = k;
        this.r = r;
        this.c0 = c0;
        this.cMax = cMax;
        this.m = m;
        this.g = g;
    }

    /**
     * Initializes a population of size n
     * @param n The number of individuals in each generation
     */
    public void init(int n) {

        for (int i = 0; i < n; i++) {
            Individual individual = new Individual(c0, cMax);
            population.add(individual);
        }
        System.out.println();
        System.out.println("Gen " + currentGeneration + ": " + population);

    }

    /** Sorts population by fitness score, best first 
     * @param pop: ArrayList of Individuals in the current generation
     * @return: ArrayList of Individuals sorted by fitness
     */
    public void rankPopulation(ArrayList < Individual > pop) {
        // sort population by fitness
        Comparator < Individual > ranker = new Comparator < > () {
            // this order will sort higher scores at the front
            public int compare(Individual c1, Individual c2) {
                return (int) Math.signum(c2.getFitness() - c1.getFitness());
            }
        };
        pop.sort(ranker);
    }


    /**
     * Evolves population creating a new generation from 2 random parents of the top k (The number of winners (individuals allowed to reproduce) in each generation) individuals
     */
    public void evolve() {

        currentGeneration++;

        this.rankPopulation(population);

        ArrayList < Individual > winners = new ArrayList < > ();
        ArrayList < Individual > offspring = new ArrayList < > ();

        //Gets the top k individuals and adds them to an array list of winners
        for (int i = 0; i < k; i++) {
            winners.add(this.population.get(i));
        }
        //System.out.println("Ranked population:" + winners);

        //Generates a new generation of n individuals using 2 random parents in the array list winners
        for (int i = 0; i < n; i++) {

            int randomNumber1 = ThreadLocalRandom.current().nextInt(0, k);
            Individual parent1 = winners.get(randomNumber1);

            int randomNumber2 = ThreadLocalRandom.current().nextInt(0, k);
            Individual parent2 = winners.get(randomNumber2);

            Individual child = new Individual(parent1, parent2, cMax, m, g);
            offspring.add(child);
        }
        System.out.println();
        population = offspring;
        System.out.println("Gen " + currentGeneration + ": " + population);
    }

    /**
     * Describes the generation giving specified statistics:
     * Fittest individual's fitness score,
     * Least fit individual's fitness score,
     * kth individual's fitness score,
     * The chromosome of the fittest/best individual
     */
    public void describeGeneration() {

        Individual fittestIndividual = population.get(0);
        Individual kth = population.get(k);
        Individual leastFit = population.getLast();

        //Adds the current generations fittest individual to an array list of the fittest individual from every generation
        fittestIndividuals.add(fittestIndividual);

        System.out.println();
        System.out.println("The fitness of the best individual is " + fittestIndividual.getFitness());
        System.out.println("The fitness of the " + k + "th individual is " + kth.getFitness());
        System.out.println("The fitness of the least fit individual is " + leastFit.getFitness());
        System.out.println("The chromosome of the best individual is " + fittestIndividual);
        System.out.println();
    }

    /**
     * Runs the GA simulation
     */
    public void run() {

        //Initializes a first generation, ranks it, and then describes it
        this.init(100);
        this.rankPopulation(population);
        this.describeGeneration();

        //For r rounds, evolves the population, ranks it, and desribes it
        for (int i = 0; i < r - 1; i++) {
            this.evolve();
            this.rankPopulation(population);
            this.describeGeneration();
        }

        //Prints out the solution, which is an array list of the fittest individual from every generation
        System.out.println();
        System.out.println("Solution: " + fittestIndividuals);
        System.out.println();
    }

    public static void main(String[] args) {
        GA_Simulation test = new GA_Simulation(100, 15, 100, 8, 20, 0.01, 5);
        test.run();
    }
}