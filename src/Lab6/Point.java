package Lab6;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class Point {

    private static final int SFMAX = 100000;

    public ArrayList<Point> neighbors;
    public static Integer[] types = {0, 1, 2, 3};
    public int type;
    public int staticField;
    public boolean isPedestrian;

    public boolean moved = false;

    public Point() {
        type = 0;
        staticField = SFMAX;
        neighbors = new ArrayList<Point>();
    }

    public void clear() {
        staticField = SFMAX;

    }

    public boolean calcStaticField() {
        int min = 100001;
        for (Point neighbour : neighbors) {
            if (neighbour.staticField < min) {
                min = neighbour.staticField;
            }
        }

        if (this.staticField > min + 1) {
            this.staticField = min + 1;
            return true;
        }

        return false;
    }

    public void move() {
        if (isPedestrian && !this.moved) {
            int min = 100001;
            Point minPoint = null;
            for (Point neighbour : neighbors) {
                if (neighbour.staticField < min && (neighbour.type == 0 || neighbour.type == 2)) {
                    minPoint = neighbour;
                    min = neighbour.staticField;
                }
            }

            if (minPoint != null && !minPoint.moved) {
                type = 0;
                isPedestrian = false;
                minPoint.moved = true;
                if (minPoint.type == 0) {
                    minPoint.type = 3;
                    minPoint.isPedestrian = true;

                }
            }
        }
    }

    public void addNeighbor(Point nei) {
        neighbors.add(nei);
    }

}