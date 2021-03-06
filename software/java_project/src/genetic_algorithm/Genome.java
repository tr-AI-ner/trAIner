package genetic_algorithm;

import game.Game;

import java.util.Arrays;
import java.util.Random;

public class Genome {
/**
 * Class to represent a gene of an individual
 *
 * @author Lugges991: Lucas Mahler
 */
    
    int numberOfSameMoves = 6;
    int[][] gene;
    int step;
    int maxNrOfMoves;
    int speed = 10;
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
        int[] move = this.getRandomDirection(); 
        for(int i=0; i<maxNrOfMoves; i++){
           // move = this.getRandomDirection();
//            if(i % numberOfSameMoves == 0){
//                move = this.getRandomDirection();
//            }
            this.gene[i] = move;
        }

    }


    /**
     * Copy constructor
     */
    public Genome(int[][] gene, int moves){
        this.gene = gene;
        this.maxNrOfMoves = moves;
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
                //DOWN
                direction[0] = 0;
                direction[1] = speed;
                break;
            case 1:
                //UP
                direction[0] = 0;
                direction[1] = -speed;
                break;
            case 2:
                //RIGHT
                direction[0] = speed;
                direction[1] = 0;
                break;
            case 3:
                //LEFT
                direction[0] = -speed;
                direction[1] = 0;
                break;
            case 4:
                //DOWN RIGHT
                direction[0] = speed;
                direction[1] = speed;
                break;
            case 5:
                //DOWN LEFT
                direction[0] = -speed;
                direction[1] = speed;
                break;
            case 6:
                //UP RIGHT 
                direction[0] = speed;
                direction[1] = -speed;
                break;
            case 7:
                //UP LEFT
                direction[0] = -speed;
                direction[1] = -speed;
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
     * exchange genetic material of 2 genes using a 3 way crossover
     *
     * @param mate individuals genes to mate with
     *
     * @return Genome new genome with combined genetic material of mom and dad
     */
    public Genome crossover(Genome mate){
        int [][]child = new int[this.maxNrOfMoves][2];
        Random rand = new Random();
        int crossover = this.maxNrOfMoves / 4;
        for(int i = 0; i<this.maxNrOfMoves;i++){
            if(i< crossover){
                child[i] = this.gene[i];
            }
            else if(i > crossover && i < 2*crossover){
                child[i] = mate.gene[i];
            }else if(i > 2*crossover && i < 3*crossover ){
                child[i] = this.gene[i];
            }else{
                child[i] = mate.gene[i];
            }
        }
        Genome newGenes = new Genome(child, this.maxNrOfMoves);
        return newGenes;
    }
    
    public Genome getExtendedGene(int increase){
        int newLen = this.maxNrOfMoves + increase;
        int[][] newGene = new int[newLen][2];
        int[] direction = getRandomDirection();
        for(int i=0; i < newLen; i++){
            if(i < this.maxNrOfMoves){
                newGene[i] = this.gene[i];
            }else{
                //newGene[i] = getRandomDirection();
                newGene[i] = direction;
            }
        }
        this.maxNrOfMoves = newLen;
        this.gene = newGene;
        return this;
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
