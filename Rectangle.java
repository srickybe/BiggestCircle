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
public class Rectangle {

    private final int length = 1000;
    private final int width = 1000;
    private ArrayList<Disk1> disks;

    public Rectangle() {
        disks = null;
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public void fillWithCircles(int nDisks, int minR, int maxR) {
        if (nDisks > 0 && minR > 0 && maxR > 0 && minR <= maxR) {
            disks = new ArrayList<>();

            for (int i = 0; i < nDisks; ++i) {
                Disk1 disk = randomCircleWithoutIntersection(minR, maxR);
                disks.add(disk);
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }

    private Disk1 randomCircleWithoutIntersection(int minR, int maxR) {
        while (true) {
            Disk1 disk = new Disk1(randomX(maxR), randomY(maxR), randomRadius(minR, maxR));
            
            if (contains(disk) && intersections(disk) == 0) {
                return disk;
            }
        }
    }

    public Disk1 randomCircleWithMaximumNumberOfIntersections(int maxIntersections) {
        Disk1 disk = new Disk1(randomX(length / 2), randomY(width / 2), randomRadius());
        
        while (intersections(disk) > maxIntersections) {
            disk = new Disk1(randomX(length / 2), randomY(width / 2), randomRadius());
        }
        
        return disk;
    }
    
    private int randomX(int maxR) {
        return Rand.getInstance().nextInt(1 + getLength() - maxR);
    }

    private int randomY(int maxR) {
        return Rand.getInstance().nextInt(1 + getWidth() - maxR);
    }

    private int randomRadius() {
        return 1 + Rand.getInstance().nextInt(1 + Math.min(length, width) / 2);
    }
    
    private int randomRadius(int minR, int maxR) {
        return minR + Rand.getInstance().nextInt(1 + maxR - minR);
    }

    public boolean contains(Disk1 disk) {
        int cx = disk.getCenterX();
        int cy = disk.getCenterY();
        int cr = disk.getRadius();

        return cx - cr >= 0 && cx + cr <= getLength()
                && cy - cr >= 0 && cy + cr <= getWidth();
    }

    public int intersections(Disk1 disk) {
        int res = 0;

        for (int i = 0; i < disks.size(); ++i) {
            if (disks.get(i).intersects(disk)) {
                ++res;
            }
        }

        return res;
    }

    @Override
    public String toString() {
        String res = "Rectangle{" + "\nlength=" + getLength()
                + ", \nwidth=" + getWidth()
                + ", \ndisks=";

        for (int i = 0; i < disks.size(); ++i) {
            res += '\n' + disks.get(i).toString();
        }

        res += ", \nnDisks=" + disks.size() + '}';

        return res;
    }
}
