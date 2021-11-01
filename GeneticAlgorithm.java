/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biggestcircle;

import java.util.ArrayList;
import java.util.TreeSet;

/**
 *
 * @author ricky
 */
public class GeneticAlgorithm {

    private final ArrayList<ArrayList<Chromosome>> populations;
    private final Settings settings;
    private final Solver[] solvers;
    private final Thread[] threads;
    private Chromosome fittest;
    private double bestFitness;
    private final long startTime;

    public GeneticAlgorithm(ArrayList<ArrayList<Chromosome>> populations, Settings settings) {
        this.populations = populations;
        this.settings = settings;
        this.solvers = new Solver[this.settings.getNumberOfThreads()];
        this.threads = new Thread[this.solvers.length];
        this.initializeSolvers();
        this.initializeThreads();
        this.fittest = null;
        this.bestFitness = 0;
        this.startTime = System.currentTimeMillis();
    }

    public final void initializeSolvers() {
        for (int i = 0; i < solvers.length; ++i) {
            solvers[i] = new Solver(
                    this.populations.get(i),
                    this.settings.getSelector(i),
                    this.settings,
                    Integer.toString(i)
            );
            solvers[i].setVerbose(false);
        }
    }

    public final void initializeThreads() {
        for (int i = 0; i < threads.length; ++i) {
            threads[i] = new Thread(solvers[i]);
        }
    }
    
    public void solve() {
        Rand.getInstance().setSeed(settings.getSeed());
        TreeSet<Integer> indexesOfRunningSolvers
                = initializeIndexesOfRunningSolvers();
        startThreads();
        boolean on = true && threads.length > 0;
        
        while (on) {
            for (int i = 0; i < threads.length && on; ++i) {
                if (solvers[i].hasSolution()) {
                    fittest = solvers[i].getBestFit();
                    System.out.println("solution =\n" + fittest);
                    on = false;
                } else if (solvers[i].hasEnded()) {
                    indexesOfRunningSolvers.remove((Integer) i);

                    if (indexesOfRunningSolvers.isEmpty()) {
                        on = false;
                        System.out.println("***All populations have reached" +
                                " the maximum number of generations");
                        System.out.println("***fittest = " + fittest);
                        System.out.println("***bestFitness = " + bestFitness);
                    }
                } else {
                    Chromosome candidate = solvers[i].getBestFit();

                    if (candidate != null && candidate.getFitness() > bestFitness) {
                        fittest = candidate;
                        bestFitness = candidate.getFitness();
                        System.out.println("best fitness = " + bestFitness);
                        System.out.println("best solution =\n" + fittest);
                        System.out.println("The program has been running for "
                                + elapsedTime() + " secs");
                    }
                }
            }
        }
    }

    public void startThreads() {
        for (int i = 0; i < threads.length; ++i) {
            threads[i].start();
        }
    }

    public TreeSet<Integer> initializeIndexesOfRunningSolvers() {
        TreeSet<Integer> indexesOfSolversStillRunning = new TreeSet<>();

        for (int i = 0; i < threads.length; ++i) {
            indexesOfSolversStillRunning.add(i);
        }

        return indexesOfSolversStillRunning;
    }

    public double elapsedTime() {
        double res = (double) (System.currentTimeMillis() - startTime) / 1000.0;

        return res;
    }

    public double getBestFitness() {
        return bestFitness;
    }

    public Chromosome getFittest() {
        return fittest;
    }

    public static void main(String agrs[]) {

    }
}
