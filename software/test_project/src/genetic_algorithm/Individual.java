package genetic_algorithm;

import java.lang.Math;
import custom_objects.Avatar;
import java.util.Random;
import java.awt.*;

/**
 * Class that represents an Individual of the Population of the genetic algorithm
 * each individual can move independently and has a fitness function as well as a
 * best distance
 *
 * @author Lugges991 Lucas Mahler
 */
public class Individual extends Avatar {

    /**
     *fixed parameters for each individual of a population
     *
     */

    // starting fitness
    double fitness = 0;
    // starting best distance (closest distance to finish)
    double best_dist = 20000;
    // objects for the avatar and the genome
    Avatar avatar;
    Genome genome;
   

    // tunable hyper parameters

    // number of moves until current generation terminates
    int nr_of_moves = 10;
    // x, y coordinate of the goal field
    int[] goal = {1, 1};
    // reached the goal
    boolean fin = false;
    // start position
    int[] start_pos = {475, 265};
    // speed of the individuals movement
    int speed = 5;
    // length of the gene, that is number of directions in the gene array i.e maximum
    // number of possible moves
    int maxNrOfMoves;


    /**
     * Constructor of the individual, inheriting from Avatar
     *
     *@param x      int   x-coordinate of the starting point of the individual
     *@param y      int   y-coordinate of the starting point of the individual
     *@param width  int   width of the individual
     *@param height int   height of the individual
     *@param color  Color color of the individual
     *@param maxNrOfMoves int maximum number of possible moves
     */
    public Individual(int x, int y, int width, int height, Color color, int maxNrOfMoves) {
        super(x, y, width, height, color);
        this.maxNrOfMoves = maxNrOfMoves;
        this.genome = new Genome(maxNrOfMoves);

    }
    
    /**
     * Constructor of the individual, inheriting from Avatar
     *
     */
    public Individual(int x, int y, int width, int height, Color color, Genome genome){
        super(x, y, width, height, color);
        this.genome = genome;
        this.maxNrOfMoves = genome.getMaxNrOfMoves();
    }

    /**
     * Calculate the fitness of an Individual
     *
     *
     */
    public void calcFitness() {
        this.fitness = Math.pow((1 / best_dist * nr_of_moves), 3);
        if (this.fin) {
            this.fitness = this.fitness * 2;
        }
    }

    /**
     * calculate how far the individual is away from the goal and check if he has
     * already reached the goal
     */
    public void calcDistance() {
        // calculate the distance of the vector d
        double d = Math.pow((this.getX() - goal[0]), 2) - Math.pow((this.getX() - goal[0]), 2);

        if (d < this.best_dist) {
            this.best_dist = d;
        } else if (d == 0) {
            this.fin = true;
        }

    }

    /**
     * randomly pick a direction
     *
     * returns a array with the new direction as (x,y) 
     */
    private int[] getRandomDirection() {
        int[] direction = new int[2];
        // Random rand = new Random();
        //int random_nr = rand.nextInt(8);
        int random_nr = (int)(Math.random() * 8 + 1);

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
                direction[0] = 0;
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
     * move the individual according to the next direction in the gene
     *
     */
    public void makeMove(int maxNrOfMovestep){
        int direction[] = new int[2];
        direction = this.getGenome().getGenes()[maxNrOfMovestep]; 
        this.move(direction[0], direction[1]);
    }

    /**
     * getters and setters
     *
     */
    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public double getBest_dist() {
        return best_dist;
    }

    public void setBest_dist(double best_dist) {
        this.best_dist = best_dist;
    }

    public int getNr_of_moves() {
        return nr_of_moves;
    }

    public void setNr_of_moves(int nr_of_moves) {
        this.nr_of_moves = nr_of_moves;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public int[] getGoal() {
        return goal;
    }

    public void setGoal(int[] goal) {
        this.goal = goal;
    }

    public boolean isFin() {
        return fin;
    }

    public void setFin(boolean fin) {
        this.fin = fin;
    }

    public int[] getStart_pos() {
        return start_pos;
    }

    public void setStart_pos(int[] start_pos) {
        this.start_pos = start_pos;
    }

    public Genome getGenome() {
        return genome;
    }

    public void setGenome(Genome genome) {
        this.genome = genome;
    }
    
    public int getMaxNrOfMoves() {
        return maxNrOfMoves;
    }

    public void setMaxNrOfMoves(int num){
        this.maxNrOfMoves = num;
    }
}
