/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biggestcircle;

import java.util.Arrays;
import java.util.HashSet;
import java.util.TreeMap;

/**
 *
 * @author john
 */
public class Circle implements Chromosome {

    private static Map MAP = null;
    private static final int N_BASES = 30;
    private static final int[] POWERS = {
        512, 256, 128, 64, 32, 16, 8, 4, 2, 1,
        512, 256, 128, 64, 32, 16, 8, 4, 2, 1,
        512, 256, 128, 64, 32, 16, 8, 4, 2, 1
    };
    private boolean[] bases;
    private int centerX;
    private int centerY;
    private int radius;
    private double fitness;

    public Circle() {
        centerX = 0;
        centerY = 0;
        radius = 0;
        initializeBases();
    }

    public Circle(int centerX, int centerY, int radius) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
        this.bases = basesFromCenterAndRadius(centerX, centerY, radius);

    }

    public Circle(Circle rhs) {
        this.centerX = rhs.centerX;
        this.centerY = rhs.centerY;
        this.radius = rhs.radius;
        this.copyBases(rhs.bases);
    }

    public Circle(boolean[] param) {
        if (param.length == N_BASES) {
            this.copyBases(param);
            this.centerX = extractX(param);
            this.centerY = extractY(param);
            this.radius = extractR(param);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public boolean getBase(int i) {
        if (i >= 0 && i < bases.length) {
            return bases[i];
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public int getCenterX() {
        return centerX;
    }

    public int getCenterY() {
        return centerY;
    }

    @Override
    public double getFitness() {
        return fitness;
    }

    public int getRadius() {
        return radius;
    }

    public void setBase(int pos, boolean base) {
        if (pos >= 0 && pos < bases.length) {
            if (getBase(pos) != base) {
                if (pos < 10) {
                    changeBitInCenterX(pos, base);
                } else if (pos < 20) {
                    changeBitInCenterY(pos, base);
                } else if (pos < 30) {
                    changeBitInRadius(pos, base);
                } else {
                    throw new UnsupportedOperationException();
                }
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public void changeBitInRadius(int pos, boolean base) {
        if (getBase(pos)) {
            if (base) {

            } else {
                radius -= POWERS[pos];
            }
        } else {
            if (base) {
                radius += POWERS[pos];
            } else {

            }
        }

        this.bases[pos] = base;
    }

    public void changeBitInCenterY(int pos, boolean base) {
        if (getBase(pos)) {
            if (base) {

            } else {
                centerY -= POWERS[pos];
            }
        } else {
            if (base) {
                centerY += POWERS[pos];
            } else {

            }
        }

        this.bases[pos] = base;
    }

    public void changeBitInCenterX(int pos, boolean base) {
        if (getBase(pos)) {
            if (base) {

            } else {
                centerX -= POWERS[pos];
            }
        } else {
            if (base) {
                centerX += POWERS[pos];
            } else {

            }
        }

        this.bases[pos] = base;
    }

    public static void setMap(Map map) {
        MAP = map;
    }

    public static final boolean[] basesFromCenterAndRadius(int centerX, int centerY, int radius) {
        if (isValidCenterX(centerX) && isValidCenterY(centerY) && isValidRadius(radius)) {
            boolean[] res = new boolean[N_BASES];
            int quotient = centerX;

            for (int i = 9; i >= 0; --i) {
                res[i] = (quotient % 2) == 1;
                quotient /= 2;
            }

            quotient = centerY;

            for (int i = 19; i >= 10; --i) {
                res[i] = (quotient % 2) == 1;
                quotient /= 2;
            }

            quotient = radius;

            for (int i = 29; i >= 20; --i) {
                res[i] = (quotient % 2) == 1;
                quotient /= 2;
            }

            return res;
        } else {
            String message = "centerX = ";
            message += centerX;
            message += "\ncenterY = ";
            message += centerY;
            message += "\nradius = ";
            message += radius;

            throw new IllegalArgumentException(message);
        }
    }

    @Override
    public int compareTo(Chromosome chr) {
        double diff = this.getFitness() - chr.getFitness();

        if (diff > 0) {
            return 1;
        } else if (diff < 0) {
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public void computeFitness() {
        if (!MAP.contains(this)) {
            fitness = 0.0;

            return;
        }

        int nIntersections = MAP.intersections(this);

        if (nIntersections == 0) {
            fitness = this.getRadius();
        } else {
            fitness = 0.9 / nIntersections;
        }
    }

    @Override
    public Chromosome copy() {
        return new Circle(this);
    }

    public final void copyBases(boolean[] param) {
        if (param.length == N_BASES) {
            this.bases = new boolean[param.length];

            for (int i = 0; i < param.length; ++i) {
                this.bases[i] = param[i];
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public boolean crossOver(Chromosome rhs, double crossOverRate) {
        if (Rand.getInstance().nextDouble() < crossOverRate) {
            int n1 = Rand.getInstance().nextInt(bases.length - 2);
            int n2 = Rand.getInstance().nextInt(n1 + 2, bases.length);

            for (int i = 0; i <= n1; ++i) {
                boolean val = getBase(i);
                setBase(i, ((Circle) rhs).getBase(i));
                ((Circle) rhs).setBase(i, val);
            }

            for (int i = n2; i < bases.length; ++i) {
                boolean val = getBase(i);
                setBase(i, ((Circle) rhs).getBase(i));
                ((Circle) rhs).setBase(i, val);
            }

            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Arrays.hashCode(this.bases);
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

        Circle c2 = (Circle) obj;

        if (this.bases.length != c2.bases.length) {
            return false;
        }

        for (int i = 0; i < c2.bases.length; ++i) {
            if (bases[i] != c2.bases[i]) {
                return false;
            }
        }

        return true;
    }

    public final int extractX(boolean[] param) {
        int cx = 0;

        for (int i = 0; i < 10; ++i) {
            cx += param[i] ? 1 * POWERS[i] : 0;
        }

        return cx;
    }

    public final int extractY(boolean[] param) {
        int cy = 0;

        for (int i = 10; i < 20; ++i) {
            cy += param[i] ? 1 * POWERS[i] : 0;
        }

        return cy;
    }

    public final int extractR(boolean[] param) {
        int cr = 0;

        for (int i = 20; i < param.length; ++i) {
            cr += param[i] ? 1 * POWERS[i] : 0;
        }

        return cr;
    }

    @Override
    public boolean isSolution() {
        return false;
    }

    private void initializeBases() {
        bases = new boolean[N_BASES];

        for (int i = 0; i < bases.length; ++i) {
            bases[i] = false;
        }
    }

    public boolean intersects(Circle right) {
        int dx = this.getCenterX() - right.getCenterX();
        int dy = this.getCenterY() - right.getCenterY();
        int sr = this.getRadius() + right.getRadius();

        return dx * dx + dy * dy <= sr * sr;
    }

    private static boolean isValidCenterX(int centerX1) {
        return centerX1 <= 2 * POWERS[0] - 1;
    }

    private static boolean isValidCenterY(int centerY1) {
        return centerY1 <= 2 * POWERS[10] - 1;
    }

    private static boolean isValidRadius(int radius1) {
        return radius1 <= 2 * POWERS[20] - 1;
    }

    @Override
    public boolean mutate(double mutationRate) {
        boolean mutated = false;

        for (int i = 0; i < bases.length; ++i) {
            double r = Rand.getInstance().nextDouble();

            if (r < mutationRate) {
                setBase(i, !getBase(i));

                if (!mutated) {
                    mutated = true;
                }
            }
        }

        return mutated;
    }

    @Override
    public String toString() {
        return "Circle2{" + "centerX=" + centerX + ", centerY=" + centerY + ", radius=" + radius + '}';
    }

    public static void main(String args[]) {
        Circle c1 = new Circle(217, 356, 138);
        boolean bases[] = basesFromCenterAndRadius(c1.getCenterX(), c1.getCenterY(), c1.getRadius());
        Circle c2 = new Circle(bases);
        System.out.println("c2 = " + c2);

        boolean test1 = c1.equals(c2);
        boolean test2 = ((Chromosome) c1).equals((Chromosome) c2);

        System.out.println("test1 == test2 ? " + (test1 == test2));

        TreeMap<Integer, HashSet<Chromosome>> chromosomesByFitness = new TreeMap<>();
        HashSet<Chromosome> set = new HashSet<>();
        set.add(c1);
        chromosomesByFitness.put((int) c1.getFitness(), set);
        set = chromosomesByFitness.get((int) c1.getFitness());
        System.out.println("set.add(c1) ? " + set.add(c1));
        System.out.println("set.add(c1) ? " + set.add(c2));

        System.out.println("set.add((Chromosome)c1) ? " + set.add((Chromosome) c1));
        System.out.println("set.add((Chromosome)c1) ? " + set.add((Chromosome) c2));

        for (int i = 0; i < 10; ++i) {
            int x1 = c1.getCenterX();
            boolean base = c1.getBase(i);
            c1.changeBitInCenterX(i, !base);
            int x2 = c1.getCenterX();

            if (base) {
                if (x1 - x2 != POWERS[i]) {
                    throw new UnsupportedOperationException();
                }
            } else {
                if (x2 - x1 != POWERS[i]) {
                    throw new UnsupportedOperationException();
                }
            }
            
            x1 = x2;
            c1.changeBitInCenterX(i, base);
            x2 = c1.getCenterX();
            
            if (!base) {
                if (x1 - x2 != POWERS[i]) {
                    throw new UnsupportedOperationException();
                }
            } else {
                if (x2 - x1 != POWERS[i]) {
                    throw new UnsupportedOperationException();
                }
            }
        }

        for (int i = 10; i < 30; ++i) {
            int y1 = c1.getCenterY();
            boolean base = c1.getBase(i);
            c1.changeBitInCenterY(i, !base);
            int y2 = c1.getCenterY();

            if (base) {
                if (y1 - y2 != POWERS[i]) {
                    throw new UnsupportedOperationException();
                }
            } else {
                if (y2 - y1 != POWERS[i]) {
                    throw new UnsupportedOperationException();
                }
            }
            
            y1 = y2;
            c1.changeBitInCenterY(i, base);
            y2 = c1.getCenterY();
            
            if (!base) {
                if (y1 - y2 != POWERS[i]) {
                    throw new UnsupportedOperationException();
                }
            } else {
                if (y2 - y1 != POWERS[i]) {
                    throw new UnsupportedOperationException();
                }
            }
        }

        for (int i = 20; i < 30; ++i) {
            int r1 = c1.getRadius();
            boolean base = c1.getBase(i);
            c1.changeBitInRadius(i, !base);
            int r2 = c1.getRadius();

            if (base) {
                if (r1 - r2 != POWERS[i]) {
                    throw new UnsupportedOperationException();
                }
            } else {
                if (r2 - r1 != POWERS[i]) {
                    throw new UnsupportedOperationException();
                }
            }
            
            r1 = r2;
            c1.changeBitInRadius(i, base);
            r2 = c1.getRadius();
            
            if (!base) {
                if (r1 - r2 != POWERS[i]) {
                    throw new UnsupportedOperationException();
                }
            } else {
                if (r2 - r1 != POWERS[i]) {
                    throw new UnsupportedOperationException();
                }
            }
        }
    }
}
