package genetic_algorithm;

import java.util.Random;

/**
 * Class to represent a gene of an individual
 *
 * @author Lugges991: Lucas Mahler
 */
public class Genome {
/**
 * Class to represent a gene of an individual
 *
 * @author Lugges991: Lucas Mahler
 */
    
    int[][] gene;
    int step;
    int maxNrOfMoves;
    int speed = 5;
    /**
     * default constructor
     *
     * @param maxNrOfMoves maximum number of possible moves until Generation ends 
     * as input
     */
    public Genome(int maxNrOfMoves) {
        this.step = 0;
        this.maxNrOfMoves = maxNrOfMoves;
        this.gene = new int[maxNrOfMoves][2];
        for(int i=0; i<maxNrOfMoves; i++){
            this.gene[i] = this.getRandomDirection();
        }

    }
    
    /**
     * Copy constructor
     */
    public Genome(int[][] gene){
        this.gene = gene;
    }
    
    /**
     * function to fully randomize the gene
     *
     * ---obsolete--
     */
    private void randomizeGene(int population_size) {
        for (int i = 0; i < population_size; i++) {
            this.gene[i] = getRandomDirection();
        }

    }
    
    /**
     * randomly pick a direction
     *
     * @return int[] array with the new direction x,y
     */
    private int[] getRandomDirection() {
        int[] direction = new int[2];
        Random rand = new Random();
        int random_nr = rand.nextInt(8);
        switch (random_nr) {
            case 0:
                //runter
                direction[0] = 0;
                direction[1] = speed;
                break;
            case 1:
                // rauf
                direction[0] = speed;
                direction[1] = speed;
                break;
            case 2:
                // links
                direction[0] = speed;
                direction[1] = 0;
                break;
            case 3:
                // rechts
                direction[0] = speed;
                direction[1] = -speed;
                break;
            case 4:
                //runter links
                direction[0] = speed;
                direction[1] = -speed;
                break;
            case 5:
                // rauf links
                direction[0] = -speed;
                direction[1] = -speed;
                break;
            case 6:
                //runter rechts
                direction[0] = -speed;
                direction[1] = 0;
                break;
            case 7:
                // rauf rechts
                direction[0] = -speed;
                direction[1] = speed;
                break;

        }
        return direction;
    }
    
    /**
     * clone a gene
     *
     * @return Genome a new gene, exactly the same as the original
     */
    public Genome clone() {
        Genome cloned_genome = new Genome(this.gene.length);
        for (int i = 0; i < this.gene.length; i++) {
            cloned_genome.gene[i] = this.gene[i];
        }
        return cloned_genome;
    }

    /**
     * mutates a gene according to the mutation rate
     *
     * @param mutationRate probability of a gene to get mutated
     */
    public void mutateGene(float mutationRate) {
        Random rand = new Random();


        for (int i = 0; i < this.gene.length; i++) {
            if(rand.nextFloat() < mutationRate){
                this.gene[i] = this.getRandomDirection();
                
            }
       }
    }

    /**
     * exchange genetic material of 2 genes
     *
     * @param mate individuals genes to mate with
     *
     * @return Genome new genome with combined genetic material of mom and dad
     */
    public Genome crossover(Genome mate){
        int [][]child = new int[this.maxNrOfMoves][2];
        Random rand = new Random();
        System.out.println("max moves: "+this.maxNrOfMoves);
        int crossover = rand.nextInt(this.maxNrOfMoves);
        for(int i = 0; i<this.maxNrOfMoves;i++){
            if(i > crossover){
                child[i] = this.gene[i];
            }
            else{
                child[i] = mate.gene[i];
            }
        }
        Genome newGenes = new Genome(child);
        return newGenes;
    }

    /**
     * increase the possible number of moves until generation ends
     *
     * @param nr new maxNrOfMoves
     */
    public void increaseNumberOfMoves(int nr) {
        this.maxNrOfMoves = nr;
    }

    /**
     * getters and setters
     *
     */
    public int[][] getGenes(){
        return this.gene;
    }
    public int getMaxNrOfMoves(){
        return this.maxNrOfMoves;
    }
}
