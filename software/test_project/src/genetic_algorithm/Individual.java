package genetic_algorithm;

import java.lang.Math;
import custom_objects.Avatar;
import java.awt.*;
import game.Game;


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
    // objects for the avatar and the genome
    Avatar avatar;
    Genome genome;
    Game game;
   

    // tunable hyper parameters
    // x, y coordinate of the goal field
    int[] goal;
    // reached the goal in number of steps
    int fin = 0;
    // start position
    int[] start_pos;
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
    public Individual(int x, int y, int width, int height, Color color, Game game) {
        super(x, y, width, height, color);
        this.game = game;
        this.maxNrOfMoves = game.getMaxNrOfMoves();
        this.genome = new Genome(this.maxNrOfMoves);
        this.start_pos =new int[] {x,y};
        this.goal = game.getFinishXY();

    }


    /**
     * Calculate the fitness of an Individual
     *
     */
    public void calcFitness() {
        // pre factor determining fluctuation of fitness function
        double preFit = this.maxNrOfMoves * 100;
        // calculate the distance if individual has finished
        if (getFinAI() != 0) {
            this.fitness = (preFit / (getFinAI()));
            this.fitness *= 2;
        }else
        {
            // calculate the distance of the individual to the goal
            double distance = Math.sqrt(Math.abs(Math.pow((this.getX() - goal[0]), 2) + Math.pow((this.getY() - goal[1]), 2)));

            // calculate the fitness
            this.fitness = (preFit / (this.maxNrOfMoves * distance));
            this.fitness = Math.pow(this.fitness, 3);
        }
    }




    /**
     * move the individual according to the next direction in the gene
     * 
     * @param currentMove int
     */
    public void makeMove(int currentMove){
        int direction[];
        direction = this.getGenome().getGenes()[currentMove]; 
        this.move(direction[0], direction[1]);
    }

    /**
     * sets a new gene and updates the maximum number of possible moves per 
     * generation accordingly
     *
     *@param newGenes Genome
     *
     */
    public void updateGenome(Genome newGenes){
        this.genome = newGenes;
        this.maxNrOfMoves = this.game.getMaxNrOfMoves();
    }

    /**
     * getters and setters
     *
     */

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }


    public Genome getGenome() {
        return genome;
    }

    public void setGenome(Genome genome) {
        this.genome = genome;
    }

}
