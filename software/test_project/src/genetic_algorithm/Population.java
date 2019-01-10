package genetic_algorithm;

import functionality.Constants;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import functionality.Constants;
import game.Game;
import java.lang.Math;
/**
 * Class representing the population of the genetic algorithm, holding an array of 
 * individuals; population also performs selection and reproduction
 *
 * @author Lugges991 Lucas Mahler
 */

public class Population {
    
    // tunable hyperparameters
    //
    // size of the population
    int populationSize;
    // probability of mutation of the genome
    float mutationRate;
    // count of the current generation
    int currentGeneration;
    // maximum number of possible moves for one individual to make in
    // one generation
    int maxNrOfMoves;

    //defaults
    //
    Individual[] population;
    ArrayList<Individual> gene_pool;
    // index of the fittest individual
    int fittest_index = 0;
    // width and height of the avatar
    int def_width = Constants.AVATAR_WIDTH;
    int def_height = Constants.AVATAR_HEIGHT;
    // default x/y of the avatar
//    int def_x = ((Constants.WINDOW_MAP_X0+Constants.WINDOW_MAP_WIDTH - (def_width/2)) / 2);
//    int def_y = ((Constants.WINDOW_MAP_HEIGHT+Constants.WINDOW_MAP_Y0 - (def_height/2)) / 2);
    int def_x;
    int def_y;
    Color color = Constants.COLOR_AVATAR_RED;

    // current game
    Game game;

    /**
     * Constructor for the population object
     *
     * @param populationSize    int     size of the population
     * @param mutationRate      float   probability of mutation of a gene
     * @param maxNrOfMoves      int     maximum number of possible moves of an individual in one generation
     */
    public Population(int populationSize, float mutationRate, Game game) {
        this.populationSize = populationSize;
        this.mutationRate = mutationRate;
        this.population = new Individual[populationSize];
        this.currentGeneration = 0;
        this.maxNrOfMoves = game.getMaxNrOfMoves();
        this.gene_pool = new ArrayList<>();
        this.game = game;
        this.def_x = game.getStartXY()[0];
        this.def_y = game.getStartXY()[1];
        for (int i = 0; i < population.length; i++) {
            population[i] = new Individual(def_x, def_y, def_width, def_height, color, game);
            population[i].setGame(game);
        }
    }
    
    /**
     * search the fittest individual of the population and return it
     * 
     * @return Individual
     */
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
    
    /**
     * check if individuals of the population have reached the goal, otherwise move the individual
     * 
     * @param currentCycle int count of the current iteration of the generation
     */
    public void live(int currentCycle){
        for(int i = 0; i < this.population.length; i++){
            this.population[i].makeMove(currentCycle);
        }
    }

    /**
     * calculate the fitness for each individual of the population
     *
     */
    public void calculateFitness() {

        for (int i = 0; i < population.length; i++) {
            this.population[i].calcFitness();
        }
    }
    
    /**
     * checks if the individuals of the population reached the goal
     * 
     * @return boolean
     */
    public boolean reachedGoal() {
        for (int i = 0; i < this.population.length; i++) {
            if (this.population[i].fin != 0) return true;
        }
        return false;
    }
    
    /**
     * search the fittest individual of the population
     *
     * @return double fitness of the fittest individual
     */
    public double getMaxFitness() {
        double fittest = 0;
        for (int i = 0; i < this.population.length; i++) {
            if (this.population[i].fitness > fittest) {
                fittest = this.population[i].fitness;
            }
        }
        return fittest;
    }
    
    /**
     * select individuals out of the population to add to the gene pool
     *
     */
    public void selection() {
        // flush the gene pool
        this.gene_pool.clear();

        double maxFit = getMaxFitness();
        System.out.println("maxFit " + maxFit);

        for (int i = 0; i < this.population.length; i++) {
            double scaledFitness = scaleMinMax(this.population[i].fitness, 0, maxFit);
            int multiplier = (int)(scaledFitness * 100);

            for(int j = 0; j < multiplier; j++){
                gene_pool.add(this.population[i]);
            }
        }
    }
    
    /**
     * select a father and a mother from the gene pool, create a child via crossover and then
     * mutate the childs genes with probability mutationRate and add the child to the new 
     * population, after that start a new generation
     *
     */
    public void reproduction(Game game) {
        Random rand = new Random();
        for(int i = 0; i < this.population.length; i++){
            System.out.println("fitness of " + i + this.population[i].getFitness());

            int randomMommy = rand.nextInt(this.gene_pool.size());
            int randomDaddy = rand.nextInt(this.gene_pool.size());

            Individual father = this.gene_pool.get(randomDaddy);
            Individual mother = this.gene_pool.get(randomMommy);
            Genome dadsGenes = father.getGenome();
            Genome momsGenes = mother.getGenome();
            Genome child = momsGenes.crossover(dadsGenes);

            child.mutateGene(this.mutationRate);
            this.population[i].setGenome(child);
            game.getEntities().add(this.population[i]);
        }
        this.currentGeneration++;
    }
    
    public void extendGenes(int increase){
        for(int i=0; i<this.population.length; i++){
            Genome oldGene = this.population[i].getGenome();
            Genome newGene = oldGene.getExtendedGene(increase);
            this.population[i].updateGenome(newGene);

        }
    }
   /**
    * min max scale the values
    *
    * @return double scaled value
    */
    public double scaleMinMax(double val, double min, double  max){
        double xScaled = (val - min) / (max - min);
        return xScaled;
    }

    public void resetDaShiat(Game game){
        for(int i = 0; i < this.population.length; i++){
            this.population[i].reset();
        }
    }
    
    /**
     * getters and setters
     *
     */
    public Individual getIndividual(int index){
        return this.population[index];
    }
 

}
