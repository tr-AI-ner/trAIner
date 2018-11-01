package GeneticAlgorithm;

import java.util.ArrayList;

public class Population {

    int population_size;
    float mutation_rate;
    Individual[] population;
    ArrayList<Individual> gene_pool;
    int fittest_index = 0;
    int generations;

    public void initializePopulation(int population_size, float mutation_rate) {
        this.population_size = population_size;
        this.mutation_rate = mutation_rate;
        this.population = new Individual[population_size];
        this.generations = 0;
        for (int i = 0; i < population.length; i++) {
            // TO DO !!
            //population[i] = new Individual();
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
