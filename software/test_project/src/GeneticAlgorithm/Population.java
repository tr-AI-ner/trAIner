package GeneticAlgorithm;

import functionality.Constants;

import java.awt.*;
import java.util.ArrayList;

import static functionality.Constants.COLOR_AVATAR_RED;

public class Population {

    int population_size;
    float mutation_rate;
    Individual[] population;
    ArrayList<Individual> gene_pool;
    int fittest_index = 0;
    int generations;

    // defaults for the individual
    // still hard coded... needs to be added to constants
    int def_width = 20;
    int def_height = 20;
    int def_x = ((Constants.WINDOW_MAP_X0+Constants.WINDOW_MAP_WIDTH - (def_width/2)) / 2);
    int def_y = ((Constants.WINDOW_MAP_HEIGHT+Constants.WINDOW_MAP_Y0 - (def_height/2)) / 2);

    Color color = COLOR_AVATAR_RED;

    public Population(int population_size, float mutation_rate) {
        this.population_size = population_size;
        this.mutation_rate = mutation_rate;
        this.population = new Individual[population_size];
        this.generations = 0;
        for (int i = 0; i < population.length; i++) {
            // TO DO !!
            population[i] = new Individual(def_x, def_y, def_width, def_height, color);
        }
    }

    public Individual getFittest() {
        double most_fit = Integer.MIN_VALUE;
        int most_fit_index = 0;

        for (int i = 0; i > population.length; i++) {
            if (most_fit >= population[i].fitness) {
                most_fit = population[i].fitness;
                most_fit_index = i;
            }
        }
        return population[most_fit_index];

    }

    public void calculateFitness() {

        for (int i = 0; i < population.length; i++) {
            population[i].calcFitness();
        }

        getFittest();
    }

    public boolean reachedGoal() {
        for (int i = 0; i < population.length; i++) {
            if (population[i].fin) return true;
        }
        return false;
    }

    public double getMaxFitness() {
        double fittest = 0;
        for (int i = 0; i < population.length; i++) {
            if (population[i].fitness > fittest) {
                fittest = population[i].fitness;
            }
        }
        return fittest;
    }

    public void selection() {
        // flush the gene pool
        this.gene_pool.clear();

        double max_fit = getMaxFitness();
        for (int i = 0; i < population.length; i++) {
            // TO DO
        }

    }

    public void crossover() {
        // TO DO
    }


}
