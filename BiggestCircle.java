package biggestcircle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class BiggestCircle {

    private Map map;
    private final Settings settings;
    
    BiggestCircle(String mapSettingsFilePath, String solverSettingsFilePath) throws FileNotFoundException {
        this.settings = new Settings(solverSettingsFilePath);
        initializeMap(mapSettingsFilePath);
        Circle.setMap(map);  
    }
    
    public final void initializeMap(String mapSettingsFilePath) throws FileNotFoundException, IllegalArgumentException {
        File file = new File(mapSettingsFilePath);
        Scanner scanner = new Scanner(file);
        String tmp = scanner.next();
        
        if (!"number_of_circles".equals(tmp)) {
            throw new IllegalArgumentException();
        }
        
        int nCircles = scanner.nextInt();
        tmp = scanner.next();
        
        if (!"minimum_radius".equals(tmp)) {
            throw new IllegalArgumentException();
        }
        
        int minimumRadius = scanner.nextInt();
        tmp = scanner.next();
        
        if (!"maximum_radius".equals(tmp)) {
            throw new IllegalArgumentException();
        }
        
        int maximumRadius = scanner.nextInt();
        map = new Map();
        map.fillWithCircles(nCircles, minimumRadius, maximumRadius);
        System.out.println("map = " + map);
    }
    
    public void findBiggestCircle() {
        ArrayList<ArrayList<Chromosome>> populations = new ArrayList<>();
        int maxIntersections = 0;
        
        for (int i = 0; i < settings.getNumberOfThreads(); ++i) {
            populations.add(randomPopulation(maxIntersections));
        }
        
        GeneticAlgorithm ga = new GeneticAlgorithm(populations, settings);
        ga.solve();
        System.out.println("fittest = " + ga.getFittest());
    }
    
    private ArrayList<Chromosome> randomPopulation(int maxIntersections) {
        ArrayList<Chromosome> chrs = new ArrayList<>();
        Chromosome bestFit = null;
        
        for (int i = 0; i < settings.getPopulationSize(); ++i) {
            Chromosome chr = map.randomCircleWithMaximumNumberOfIntersections(maxIntersections);
            chr.computeFitness();
            chrs.add(chr);
            
            if (bestFit != null) {
                if (chr.getFitness() > bestFit.getFitness()) {
                    bestFit = chr;
                }
            } else {
                bestFit = chr;
            }
        }
        
        /*System.out.println("%%%%%bestFit = " + bestFit);
        
        if (bestFit != null) {
            System.out.println("best fitness = " + bestFit.getFitness());
        }*/
        
        return chrs;
    }
    
    public static void main(String[] args) throws FileNotFoundException {
        String mapSettingsFilePath = 
                "map_settings.txt";
        String solverSettingsFilePath = 
                "solver_settings.txt";
    
        BiggestCircle bc = new BiggestCircle(mapSettingsFilePath, solverSettingsFilePath);
        bc.findBiggestCircle();
    }
}
