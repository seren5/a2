import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

public class Individual {
    //Note: A lot of the print statements are commented out since I used them to test certain methods as I was creating them to ensure everything worked properly.
    // If you want to check certain methods, just uncomment the print statements and run that method on a single individual object

    /**
     * Chromosome stores the individual's genetic data as an ArrayList of characters
     * Each character represents a gene
     */
    ArrayList < Character > chromosome;

    /**
     * Chooses a letter at random, in the range from A to the number of states indicated
     * @param numLetters The number of letters available to choose from (number of possible states)
     * @return The letter as a Character
     */
    private Character randomLetter(int numLetters) {
        return Character.valueOf((char)(65 + ThreadLocalRandom.current().nextInt(numLetters)));
    }

    /** 
     * Method to determine whether a given gene will mutate based on the parameter ***m***
     * @param m the mutation rate
     * @return true if a number randomly chosen between 0 and 1 is less than ***m***, else false
     */
    private Boolean doesMutate(double m) {
        double randomNum = ThreadLocalRandom.current().nextInt(0, 1);
        return randomNum < m;
    }

    /**
     * Expresses the individual's chromosome as a String, for display purposes
     * @return The chromosome as a String
     */
    public String toString() {
        StringBuilder builder = new StringBuilder(chromosome.size());
        for (Character ch: chromosome) {
            builder.append(ch);
        }
        return builder.toString();
    }

    /** 
     * Inital constructor to generate initial population members
     * @param c0 The initial chromosome size
     * @param numLetters The number of letters available to choose from
     */
    public Individual(int c0, int numLetters) {

        this.chromosome = new ArrayList < Character > ();

        for (int i = 0; i < c0; i++) {

            char randomizedLetter = randomLetter(numLetters);
            chromosome.add(randomizedLetter);
        }

        //System.out.println("Your chromosome is: " + chromosome);
    }

    /**
     * Second constructor to create parents and offspring chromosomes
     * @param parent1 The first parent chromosome
     * @param parent2 The second parent chromosome
     * @param cMax The maximum chromosome size
     * @param m The chances per round of mutation in each gene
     * @param numLetters The number of letters available to choose from
     */
    public Individual(Individual parent1, Individual parent2, int cMax, double m, int numLetters) {

        ArrayList < Character > prefix = new ArrayList < > ();
        ArrayList < Character > suffix = new ArrayList < > ();

        //Creates a prefix of a random length from the first parent
        int prefixLength = ThreadLocalRandom.current().nextInt(1, parent1.chromosome.size());

        for (int i = 0; i < prefixLength; i++) {
            prefix.add(parent1.chromosome.get(i));
        }

        //System.out.println("Your prefix is: " + prefix);

        //Creates a suffix of a random length from the second parent
        int suffixLength = ThreadLocalRandom.current().nextInt(1, parent2.chromosome.size());

        int j = parent2.chromosome.size() - 1;

        for (int i = 0; i < suffixLength; i++) {
            suffix.add(parent2.chromosome.get(j));
            j--;
        }

        Collections.reverse(suffix);


        //System.out.println("Your suffix is: " + suffix);

        //Creates a new chromosome using the prefix and suffix
        chromosome = new ArrayList < > (prefixLength + suffixLength);

        for (int i = 0; i < prefixLength; i++) {
            chromosome.add(prefix.get(i));
        }

        for (int i = 0; i < suffixLength; i++) {
            chromosome.add(suffix.get(i));
        }

        //Removes letters from the end of the chromosome if the size of the chromosome is greater than the maximum chromosome size
        while (this.chromosome.size() > cMax) {

            for (int i = cMax; i < this.chromosome.size(); i++) {

                this.chromosome.remove(chromosome.size() - 1);
            }
        }

        //System.out.println("Your chromosome is: " + chromosome);

        //Handles mutations
        for (int i = 0; i < this.chromosome.size(); i++) {
            boolean chance = doesMutate(i);
            if (chance) {
                chromosome.set(i, randomLetter(numLetters));
            }
        }

        //System.out.println("Your mutated chromosome is: " + chromosome);
    }

    /**
     * Calculates the fitness score of each chromosome
     * @return The fitness score as an int
     */
    public int getFitness() {
        //System.out.println("Your chromosome is: " + chromosome);

        //Handles mirror partner comparision
        int mirrorPartnerFitnessScore = 0;

        //System.out.println();
        //System.out.println("Comparing mirror partner genes:");

        int j = this.chromosome.size();

        for (int i = 0; i <= (j - 1) / 2; i++) {

            if (this.chromosome.get(i).equals(this.chromosome.get(j - 1))) {
                mirrorPartnerFitnessScore++;
                j--;
                //System.out.println(this.chromosome.get(i).toString() + " X " + this.chromosome.get(j).toString() + " --> +1 ");
            } else {
                mirrorPartnerFitnessScore--;
                j--;
                //System.out.println(this.chromosome.get(i).toString() + " X " + this.chromosome.get(j).toString() +" --> -1 ");
            }
        }

        //System.out.println("Subtotal: " + mirrorPartnerFitnessScore);
        //System.out.println();

        //Handles adjacent comparision
        int adjacentFitnessScore = 0;

        //System.out.println("Comparing adjacent genes:");
        for (int i = 0; i < this.chromosome.size() - 1; i++) {
            if (this.chromosome.get(i).equals(this.chromosome.get(i + 1))) {
                adjacentFitnessScore--;
                //System.out.println(this.chromosome.get(i).toString() + this.chromosome.get(i+1).toString() +" --> -1 ");
            } else {
                //System.out.println(this.chromosome.get(i).toString() + this.chromosome.get(i+1).toString() +" --> 0 ");
            }
        }

        //System.out.println("Subtotal: " + adjacentFitnessScore);
        //System.out.println();

        //Combines mirror partner score and adjacent score
        int fitnessScore = adjacentFitnessScore + mirrorPartnerFitnessScore;
        //System.out.println("Total Fitness: " + fitnessScore);

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