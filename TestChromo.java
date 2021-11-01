/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biggestcircle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 *
 * @author ricky
 */
class TestChromo implements Chromosome {

    private final ArrayList<Integer> coefficients;

    public TestChromo(int numberOfCoefficients) {
        this.coefficients = new ArrayList<>(numberOfCoefficients);

        for (int i = 0; i < numberOfCoefficients; ++i) {
            this.coefficients.add(
                    1 + Rand.getInstance().nextInt(numberOfCoefficients)
            );
        }
    }

    @Override
    public Chromosome copy() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean mutate(double mutationRate) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean crossOver(Chromosome chr, double crossOverRate) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getFitness() {
        int res = 1;

        for (int coefficient : coefficients) {
            res *= coefficient;
        }

        return (double) res;
    }

    @Override
    public void computeFitness() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isSolution() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int compareTo(Chromosome arg0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        return "MyChromo{" + "coefficients=" + coefficients + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.coefficients);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        
        if (obj == null) {
            return false;
        }
        
        if (getClass() != obj.getClass()) {
            return false;
        }
        
        TestChromo rhs = (TestChromo)obj;
        
        if (this.coefficients.size() != rhs.coefficients.size()) {
            return false;
        }
        
        for (int i = 0; i < this.coefficients.size(); ++i) {
            if (!Objects.equals(this.coefficients.get(i), rhs.coefficients.get(i))) {
                return false;
            }
        }
        
        return true;
    }
}
