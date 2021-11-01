/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biggestcircle;

import java.util.ArrayList;

/**
 *
 * @author ricky
 */
public class Tournament extends Selector {

    private ArrayList<Chromosome> population;
    private ArrayList<Chromosome> copy;
    private final int size;

    public Tournament(int size) {
        population = null;
        copy = null;
        this.size = size;
    }

    @Override
    public Chromosome select() {
        int nChoices = Math.min(size, population.size());
        
        if (population.isEmpty()) {
            throw new UnsupportedOperationException();
        }
        
        int choice = Rand.getInstance().nextInt(population.size());

        Chromosome bestFit = population.get(choice);
        Double bestFitness = population.get(choice).getFitness();

        for (int j = 1; j < nChoices; ++j) {
            int index = Rand.getInstance().nextInt(population.size());
            Chromosome chr = population.get(index);
            Double eval = population.get(index).getFitness();

            if (eval > bestFitness) {
                bestFitness = eval;
                bestFit = chr;
                choice = index;
            }
        }

        population.remove(choice);
        replete();

        return bestFit;
    }

    @Override
    public final void setPopulation(ArrayList<Chromosome> chromos) {
        if (!chromos.isEmpty()) {
            this.population = new ArrayList<>(chromos.size());
            this.copy = new ArrayList<>(chromos.size());

            chromos.forEach(chromo -> {
                this.population.add(chromo);
                this.copy.add(chromo);
            });
        } else {
            throw new UnsupportedOperationException();
        }
    }

    private void replete() {
        if (population.isEmpty()) {
            this.copy.forEach(chromo -> {
                this.population.add(chromo);
            });
        }
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        return "Tournament{" + "size=" + size + '}';
    }
}
