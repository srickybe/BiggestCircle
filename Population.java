/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biggestcircle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;

/**
 *
 * @author ricky
 */
public class Population implements Iterable<Chromosome> {

    private final ArrayList<Chromosome> chrs;

    public Population(ArrayList<Chromosome> chrs) {
        this.chrs = chrs;
    }

    public void addAll(ArrayList<Chromosome> chromosomes) {
        chrs.addAll(chromosomes);
    }

    public double averageFitness() {
        double res = 0.0;

        for (Chromosome chr : chrs) {
            res += chr.getFitness();
        }

        res /= size();

        return res;
    }

    public void clear() {
        chrs.clear();
    }

    public void computeFitnesses() {
        for (int i = 0; i < chrs.size(); ++i) {
            chrs.get(i).computeFitness();
        }
    }

    public Chromosome first() {
        return chrs.get(0);
    }

    private Chromosome get(int i) {
        return chrs.get(i);
    }

    /*The ArrayList chrs must be ordered in descending order of fitness
    Returns the N first different best fits
     */
    public ArrayList<Chromosome> getNBestFit(int nChrs) {
        HashSet<Chromosome> tmp = new HashSet<>();
        int count = 0;
        
        for (int i = 0; i < this.size() && count < nChrs; ++i) {
            if (tmp.add(this.get(i))) {
                ++count;
            }
        }
        
        ArrayList<Chromosome> res = new ArrayList<>();
        res.addAll(tmp);
        
        return res;
    }

    public boolean isEmpty() {
        return chrs.isEmpty();
    }

    @Override
    public Iterator<Chromosome> iterator() {
        return chrs.iterator();
    }

    public void set(int i, Chromosome chr) {
        chrs.set(i, chr);
    }

    public void shuffle() {
        for (int i = 0; i < this.size(); ++i) {
            int j = Rand.getInstance().nextInt(this.size());
            Chromosome tmp = this.get(i);
            this.set(i, this.get(j));
            this.set(j, tmp);
        }
    }

    public int size() {
        return chrs.size();
    }

    public void sortInDescendingOrder() {
        Collections.sort(chrs, Collections.reverseOrder());
    }

    public ArrayList<Chromosome> toArrayList() {
        return chrs;
    }
}
