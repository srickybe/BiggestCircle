/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biggestcircle;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author ricky
 */
public class RouletteWheel extends Selector {

    private ArrayList<Chromosome> chrs;
    private ArrayList<Double> fitnesses;
    private double sumFits;
    private ArrayList<Double> cumulFits;

    public RouletteWheel() {
    }

    @Override
    public void setPopulation(ArrayList<Chromosome> chrs) {
        this.chrs = chrs;
        this.fitnesses = new ArrayList<>(chrs.size());
        this.cumulFits = new ArrayList<>(chrs.size());
        computeCumulativeFitnesses();
    }

    @Override
    public Chromosome select() {
        double random = Rand.getInstance().nextDouble();

        for (int index = 0; index < cumulFits.size(); ++index) {
            if (cumulFits.get(index) >= random) {
                return chrs.get(index);
            }
        }

        return chrs.get(chrs.size() - 1);
    }

    private void computeCumulativeFitnesses() {
        computeFitnesses();
        
        if (sumFits == 0.0) {
            System.out.println("chrs.size() = " + chrs.size());
            throw new UnsupportedOperationException();
        }
        
        cumulFits.add(fitnesses.get(0) / sumFits);

        for (int i = 1; i < chrs.size(); ++i) {
            cumulFits.add(cumulFits.get(i - 1) + fitnesses.get(i) / sumFits);
        }
    }

    private void computeFitnesses() {
        sumFits = 0.0;

        for (int i = 0; i < chrs.size(); ++i) {
            fitnesses.add(chrs.get(i).getFitness());
            sumFits += fitnesses.get(i);
        }
    }
}

