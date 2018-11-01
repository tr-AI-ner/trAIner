package GeneticAlgorithm;

import custom_objects.Avatar;

import java.awt.*;

public class Individual extends Avatar {

    double fitness = 0;
    double best_dist = 20000;
    int nr_of_moves = 10;
    Avatar avatar;
    // x, y coordinate of the goal field
    int[] goal = {1, 1};
    // reached the goal
    boolean fin = false;
    // start position
    int[] start_pos = {475, 265};

    Genome genome;

    // constructor for the individual inherits from Avatar
    public Individual(Genome genome, int x, int y, int width, int height, Color color) {
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
