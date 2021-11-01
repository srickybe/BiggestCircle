/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biggestcircle;

import java.util.ArrayList;

/**
 *
 * @author john
 */
public class Map {

    private final int length = 1024;
    private final int width = 1024;
    private ArrayList<Circle> circles;

    public Map() {
        circles = null;
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public void fillWithCircles(int nCircles, int minR, int maxR) {
        if (nCircles > 0 && minR > 0 && maxR > 0 && minR <= maxR) {
            circles = new ArrayList<>();

            for (int i = 0; i < nCircles; ++i) {
                Circle circle = randomCircleWithoutIntersection(minR, maxR);
                circles.add(circle);
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }

    private Circle randomCircleWithoutIntersection(int minR, int maxR) {
        while (true) {
            Circle circle = new Circle(randomX(maxR), randomY(maxR), randomRadius(minR, maxR));
            
            if (contains(circle) && intersections(circle) == 0) {
                return circle;
            }
        }
    }

    public Circle randomCircleWithMaximumNumberOfIntersections(int maxIntersections) {
        Circle circle = new Circle(randomX(length / 2), randomY(width / 2), randomRadius());
        
        while (intersections(circle) > maxIntersections) {
            circle = new Circle(randomX(length / 2), randomY(width / 2), randomRadius());
        }
        
        return circle;
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

    public boolean contains(Circle circle) {
        int cx = circle.getCenterX();
        int cy = circle.getCenterY();
        int cr = circle.getRadius();

        return cx - cr >= 0 && cx + cr <= getLength()
                && cy - cr >= 0 && cy + cr <= getWidth();
    }

    public int intersections(Circle circle) {
        int res = 0;

        for (int i = 0; i < circles.size(); ++i) {
            if (circles.get(i).intersects(circle)) {
                ++res;
            }
        }

        return res;
    }

    @Override
    public String toString() {
        String res = "Map{" + "\nlength=" + getLength()
                + ", \nwidth=" + getWidth()
                + ", \ncircles=";

        for (int i = 0; i < circles.size(); ++i) {
            res += '\n' + circles.get(i).toString();
        }

        res += ", \nnCircles=" + circles.size() + '}';

        return res;
    }
}
