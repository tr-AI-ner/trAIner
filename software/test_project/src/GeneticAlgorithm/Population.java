package GeneticAlgorithm;

import functionality.Constants;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

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
            population[i] = new Individual(def_x+i, def_y+i, def_width, def_height, color);
        }
    }



    public Individual getFittest() {
        double most_fit = Integer.MIN_VALUE;
        int most_fit_index = 0;

        for (int i = 0; i > this.population.length; i++) {
            if (most_fit >= this.population[i].fitness) {
                most_fit = this.population[i].fitness;
                most_fit_index = i;
            }
        }
        return population[most_fit_index];

    }

    public void live(){
        for(int i = 0; i < population.length; i++){
            if(this.population[i].getX == this.population[i].goal[0] && this.population[i].getY == this.population[i].goal[1]){
                this.population[i].fin = true;
            }

        }
    }

    public void calculateFitness() {

        for (int i = 0; i < population.length; i++) {
            this.population[i].calcFitness();
        }

        getFittest();
    }

    public boolean reachedGoal() {
        for (int i = 0; i < this.population.length; i++) {
            if (this.population[i].fin) return true;
        }
        return false;
    }

    public double getMaxFitness() {
        double fittest = 0;
        for (int i = 0; i < this.population.length; i++) {
            if (this.population[i].fitness > fittest) {
                fittest = this.population[i].fitness;
            }
        }
        return fittest;
    }

    public void selection() {
        // flush the gene pool
        this.gene_pool.clear();

        double maxFit = getMaxFitness();
        for (int i = 0; i < this.population.length; i++) {
            double scaledFitness = scaleMinMax(this.population[i].fitness, 0, maxFit);
            int multiplier = (int)(scaledFitness * 100);
            for(int j = 0; j < multiplier; j++){
                gene_pool.add(this.population[i]);
            }
        }

    }

    public void reproduction() {
        Random rand = new Random();
        for(int i = 0; i < this.population.length; i++){
            Individual father = this.gene_pool.get(rand.nextInt(this.gene_pool.size()));
            Individual mother  =  this.gene_pool.get(rand.nextInt(this.gene_pool.size()));
            Genome dadsGenes = father.getGenome();
            Genome momsGenes = mother.getGenome();

            Genome child = momsGenes.crossover(dadsGenes);

            child.mutate(this.mutation_rate);
            this.population[i] = new Individual(this.def_x, this.def_y, this.def_width, this.def_height, this.color, child);

        }
        this.generations++;
    }

    public Individual getIndividual(int index){
        return this.population[index];
    }
    
    public double scaleMinMax(double val, double min, double  max){
        double xScaled = (val - min) / (max - min);
        return xScaled;
    }


}
