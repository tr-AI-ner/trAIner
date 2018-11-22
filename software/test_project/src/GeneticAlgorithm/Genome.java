package GeneticAlgorithm;

import java.util.Random;

public class Genome {
/**
 * Class to represent a gene of an individual
 *
 * @author Lugges991: Lucas Mahler
 */
    
    int[][] gene;
    int step;
    int maxNrOfMoves;
//    int[][][] DNA;

    /**
     * default constructor
     *
     * takes maximum number of possible moves until Generation ends 
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
     * returns a array with the new direction
     */
    private int[] getRandomDirection() {
        int[] direction = new int[2];
        Random rand = new Random();
        int random_nr = rand.nextInt(8);
        switch (random_nr) {
            case 0:
                //runter
                direction[0] = 0;
                direction[1] = 1;
            case 1:
                // rauf
                direction[0] = 1;
                direction[1] = 1;
            case 2:
                // links
                direction[0] = 1;
                direction[1] = 0;
            case 3:
                // rechts
                direction[0] = 1;
                direction[1] = -1;
            case 4:
                //runter links
                direction[0] = 0;
                direction[1] = -1;
            case 5:
                // rauf links
                direction[0] = -1;
                direction[1] = -1;
            case 6:
                //runter rechts
                direction[0] = -1;
                direction[1] = 0;
            case 7:
                // rauf rechts
                direction[0] = -1;
                direction[1] = 1;

        }
        return direction;
    }
    
    /**
     * clone a gene
     *
     * returns a new gene, exactly the same as the original
     */
    public Genome clone() {
        Genome cloned_genome = new Genome(this.gene.length);
        for (int i = 0; i < this.gene.length; i++) {
            cloned_genome.gene[i] = this.gene[i];
        }
        return cloned_genome;
    }

    /**
     *
     * mutates a gene according to the mutation rate
     *
     * returns 
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
     * creates a  new gene with combined genetic material of the
     * two parents
     */
    
    public Genome crossover(Genome mate){
        int [][]child = new int[this.maxNrOfMoves][2];
        Random rand = new Random();
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
     */
    public void increaseNumberOfMoves(int nr) {
        this.maxNrOfMoves = nr;
    }

    public int[][] getGenes(){
        return this.gene;
    }
}
