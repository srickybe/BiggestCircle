/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biggestcircle;

/**
 *
 * @author ricky
 */
public interface Chromosome extends Comparable<Chromosome> {

    public void computeFitness();
    
    public Chromosome copy();

    public boolean crossOver(Chromosome chr, double crossOverRate);
    
    public double getFitness();
    
    public boolean isSolution();

    public boolean mutate(double mutationRate);
}
