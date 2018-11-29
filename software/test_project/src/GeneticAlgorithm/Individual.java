package GeneticAlgorithm;

import custom_objects.Avatar;
import java.util.Random;
import java.awt.*;

public class Individual extends Avatar {

    // fixed parameters

    // starting fitness
    double fitness = 0;
    // starting best distance (closest distance to finish)
    double best_dist = 20000;
    // objects for the avatar and the genome
    Avatar avatar;
    Genome genome;
    int speed = 3;
    int lifecycles;

    // tunable hyper parameters

    // number of moves until current generation terminates
    int nr_of_moves = 10;
    // x, y coordinate of the goal field
    int[] goal = {1, 1};
    // reached the goal
    boolean fin = false;
    // start position
    int[] start_pos = {475, 265};




    // constructor for the individual inherits from Avatar
    public Individual(int x, int y, int width, int height, Color color, int lifecycles) {
        super(x, y, width, height, color);
        this.lifecycles = lifecycles;
        this.genome = new Genome(lifecycles);

    }

    public Individual(int x, int y, int width, int height, Color color, Genome genome){
        super(x, y, width, height, color);
        this.genome = genome;
    }

    // calculate the fitness of the individual
    public void calcFitness() {
        fitness = Math.pow((1 / best_dist * nr_of_moves), 3);
        if (fin) {
            fitness = fitness * 2;
        }
    }

    // calculate how far the individual is away from the goal and check if he has
    // already reached the goal
    public void calcDistance() {
        // calculate the distance of the vector d
        double d = Math.pow((this.getX() - goal[0]), 2) - Math.pow((this.getX() - goal[0]), 2);

        if (d < best_dist) {
            best_dist = d;
        } else if (d == 0) {
            fin = true;
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
 

    public void makeMove(int lifecycleStep){
        int direction[] = new int[2];
        direction = this.getGenome().getGenes()[lifecycleStep]; 
        this.move(direction[0], direction[1]);
    }

    // getters and setters
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
}
