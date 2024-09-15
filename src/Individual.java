import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

public class Individual {

    /**
     * Chromosome stores the individual's genetic data as an ArrayList of characters
     * Each character represents a gene
     */
    ArrayList<Character> chromosome;
    
    /**
     * Chooses a letter at random, in the range from A to the number of states indicated
     * @param num_letters The number of letters available to choose from (number of possible states)
     * @return The letter as a Character
     */
    private Character randomLetter(int num_letters) {
        return Character.valueOf((char)(65+ThreadLocalRandom.current().nextInt(num_letters)));
      }
    
    /** 
     * Method to determine whether a given gene will mutate based on the parameter ***m***
     * @param m the mutation rate
     * @return true if a number randomly chosen between 0 and 1 is less than ***m***, else false
    */
    private Boolean doesMutate(float m){
        float randomNum = ThreadLocalRandom.current().nextInt(0, 1);
        return randomNum < m;
    }

    /**
     * Expresses the individual's chromosome as a String, for display purposes
     * @return The chromosome as a String
     */
    public String toString() {
        StringBuilder builder = new StringBuilder(chromosome.size());
        for(Character ch: chromosome) {
          builder.append(ch);
        }
        return builder.toString();
      }

    /** 
     * Inital constructor to generate initial population members
     * @param c_0 The initial chromosome size
     * @param num_letters The number of letters available to choose from
     */
    public Individual(int c_0, int num_letters) {
      // fill in

      this.chromosome = new ArrayList<Character>();

      for (int i = 0; i < c_0; i++){
        
        char randomizedLetter = randomLetter(num_letters);
        chromosome.add(randomizedLetter);
      }

      System.out.println("Your chromosome is: " + chromosome);
    }

     /**
      * Second constructor to create parents and offspring chromosomes
      * @param parent1 The first parent chromosome
      * @param parent2 The second parent chromosome
      * @param c_max The maximum chromosome size
      * @param m The chances per round of mutation in each gene
      */
    public Individual(Individual parent1, Individual parent2, int c_max, double m, int num_letters) {
      // fill in

      ArrayList<Character> prefix = new  ArrayList<>();
      ArrayList<Character> suffix = new  ArrayList<>();

      int prefixLength = ThreadLocalRandom.current().nextInt(1, parent1.chromosome.size());

      for (int i = 0; i < prefixLength; i++) {
        prefix.add(parent1.chromosome.get(i));
      }

      System.out.println("Your prefix is: " + prefix);


      int suffixLength = ThreadLocalRandom.current().nextInt(1, parent2.chromosome.size());

      int j = parent2.chromosome.size() - 1;

      for (int i = 0; i < suffixLength; i++) {
        suffix.add(parent2.chromosome.get(j));
        j--;
      }

      Collections.reverse(suffix);


      System.out.println("Your suffix is: " + suffix);

      chromosome = new  ArrayList<>(prefixLength + suffixLength);

      for (int i = 0; i < prefixLength; i++) {
        chromosome.add(prefix.get(i));
      }

      for (int i = 0; i < suffixLength; i++) {
        chromosome.add(suffix.get(i));
      }

      while (this.chromosome.size() > c_max) {

        for (int i = c_max; i < this.chromosome.size(); i++) {

          this.chromosome.remove(chromosome.size()-1);

        }
      }

      System.out.println("Your chromosome is: " + chromosome);

      for (int i = 0; i < this.chromosome.size(); i++) {
        boolean chance = doesMutate(i);
        if (chance) {
          chromosome.set(i, randomLetter(num_letters));
        }
      }

      System.out.println("Your mutated chromosome is: " + chromosome);
    }

    /**
     * Calculates the fitness score of each chromosome
     * @return The fitness score as an int
     */
    public int getFitness() {
        // fill in
        // remove the return below and write your own

        int fitnessScore = 0;

        Iterator <Character> iterator = this.chromosome.iterator();

        while (iterator.hasNext()){

          if(this.chromosome.get(0) == this.chromosome.get(1)) {
            fitnessScore = fitnessScore ++;
          }
          else {
            fitnessScore = fitnessScore--;
          }
          System.out.print(iterator.next());
        }
        System.out.println("Your fitness score is: " + fitnessScore);
        return fitnessScore;

    }

    public static void main(String[] args) {

      Individual parent1 = new Individual(8, 5);
      System.out.println();

      Individual parent2 = new Individual(8, 5);
      System.out.println();

      Individual child = new Individual(parent1, parent2, 8, .5, 5);

      child.getFitness();

    }
    
}
