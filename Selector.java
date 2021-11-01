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
public abstract class Selector {
    public abstract void setPopulation(ArrayList<Chromosome> chrs);
    public abstract Chromosome select();
}
