/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biggestcircle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.TreeMap;

/**
 *
 * @author ricky
 */
public class Reserve {

    private final TreeMap<Integer, HashSet<Chromosome>> chromosomesByFitness;

    public Reserve() {
        chromosomesByFitness = new TreeMap<>(Collections.reverseOrder());
    }

    public boolean addChromosome(Chromosome chr) {
        HashSet<Chromosome> set = chromosomesByFitness.get((int) chr.getFitness());

        if (set != null) {
            return set.add(chr);
        } else {
            set = new HashSet<>();

            if (set.add(chr)) {
                chromosomesByFitness.put((int) chr.getFitness(), set);

                return true;
            } else {
                return false;
            }
        }
    }

    public boolean addAllChromosomes(Collection<Chromosome> collection) {
        boolean allAdded = true;

        for (Chromosome chr : collection) {
            if (!this.addChromosome(chr)) {
                if (allAdded) {
                    allAdded = false;
                }
            }
        }

        return allAdded;
    }

    public ArrayList<Chromosome> chromosomesWithFitnessEqualTo(int fitness) {
        HashSet<Chromosome> tmp = chromosomesByFitness.get(fitness);
        
        return new ArrayList<>(tmp);
    }
    
    public void clear() {
        chromosomesByFitness.clear();
    }

    public ArrayList<Chromosome> toArrayList() {
        ArrayList<Chromosome> res = new ArrayList<>();

        chromosomesByFitness.entrySet().forEach(entry -> {
            res.addAll(entry.getValue());
        });

        return res;
    }

    public int getNumberOfChromosomes() {
        int res = 0;

        for (var entry : chromosomesByFitness.entrySet()) {
            res += entry.getValue().size();
        }
        
        return res;
    }

    public ArrayList<Chromosome> getNBestFitChromosomes(int nChrs) {
        ArrayList<Chromosome> bestFit = new ArrayList<>(nChrs);
        int count = 0;

        for (var entry : chromosomesByFitness.entrySet()) {
            for (var chr : entry.getValue()) {
                if (count < nChrs) {
                    if (bestFit.add(chr)) {
                        ++count;
                    } else {
                        throw new UnsupportedOperationException();
                    }
                } else {
                    return bestFit;
                }
            }
        }

        return bestFit;
    }

    @Override
    public String toString() {
        return "Reserve{"
                + "chromosomesByFitness=" + chromosomesByFitness
                //+ ", numberOfChromosomes=" + numberOfChromosomes 
                + '}';
    }

    public static void main(String args[]) {
        Rand.getInstance().setSeed(0);
        Reserve reserve = new Reserve();
        Chromosome[] chromos = new TestChromo[10000];

        for (int i = 0; i < chromos.length; ++i) {
            chromos[i] = new TestChromo(5);
            reserve.addChromosome(chromos[i]);
            System.out.println(i + ": " + chromos[i]);
        }

        /*System.out.println("reserve = " + reserve);
        System.out.println("reserve.addChromosome(tmp[0]) ?" + reserve.addChromosome(chromos[0]));
        System.out.println("+++++reserve = " + reserve);
        var chrs = reserve.toArrayList();

        for (var chr : chrs) {
            System.out.println("chr =\n" + chr);
            System.out.println("chr.getFitness() = " + chr.getFitness());
        }*/

        System.out.println("reserve.getNumberOfChromosomes() = " + reserve.getNumberOfChromosomes());
        var nBestFits = reserve.getNBestFitChromosomes(25);

        for (var chr : nBestFits) {
            System.out.println("#####chr = " + chr);
            System.out.println("*****chr.getFitness() = " + chr.getFitness());
        }
        
        int maxFitness = 3125;
        var bestFits = reserve.chromosomesWithFitnessEqualTo(maxFitness);
        System.out.println("bestFits = " + bestFits);
        
        int fitness = 2500;
        var fits = reserve.chromosomesWithFitnessEqualTo(fitness);
        System.out.println("fits = " + fits);
    }
}
