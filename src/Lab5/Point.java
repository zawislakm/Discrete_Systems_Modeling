package Lab5;

public class Point {

    public int x;
    public int y;
    public Point upPoint;
    public Point downPoint;
    public int velocity;
    public int type = 0; //0 road, 1 car, 2 wall
    public boolean moved;
    public Point prev;
    public Point next;
    private static int maxVelocity = 5;

    private static int l_ahead = 5;
    private static int l_back = 5;
    private static int l_ahead_here = 5;
    private static float overtakeVariable = 0.2f;
    private static float slowDownVariable = 0.5f; //p variable


    public boolean changedLane = false;

    public Point() {
        velocity = 1;
    }

    public void allType() {

        acceleration();
        overtake();
        slowingDown();
        randomize();
        move();
        if (this.downPoint != null) {
            comeback();
        }
    }

    public void comeback() {
        if (type == 1 && !changedLane && velocity > 0) {
            Point prevPoint = this.downPoint;
            int distance_back = 0;
            while (prevPoint != null && distance_back < l_back && prevPoint.type != 1) {
                prevPoint = prevPoint.prev;
                distance_back++;
            }

            if (distance_back >= l_back) {
                this.downPoint.velocity = velocity;
                this.downPoint.type = 1;
                this.downPoint.changedLane = true;
                this.downPoint.move();
                changedLane = false;
                velocity = 0;
                type = 0;
            }
        }
    }

    public void acceleration() {
        if (type == 1 && velocity < maxVelocity) {
            velocity++;
        }
    }

    public void slowingDown() {
        if (type == 1) {
            Point nextPoint = this;
            int distance = 1;

            while (distance <= maxVelocity && nextPoint.next != null) {
                nextPoint = nextPoint.next;
                if (nextPoint.type == 1) {
                    break;
                }
                distance++;
            }

            if (distance <= velocity) {
                velocity = Math.max(distance - 1, 0);
            }
        }
    }

    public void randomize() {
        if (type == 1 && velocity >= 1) {
            if (Math.random() < slowDownVariable) {
                velocity--;
            }
        }
    }

    public void overtake() {
        if (type == 1 && this.upPoint != null) {

            Point nextPointhere = this.next;
            int distance_here = 1;
            while (nextPointhere != null && distance_here < l_ahead_here && nextPointhere.type != 1) {
                nextPointhere = nextPointhere.next;
                distance_here++;
            }

            Point nextPoint = this.upPoint;
            int distance_up = 0;
            while (nextPoint != null && distance_up < l_ahead && nextPoint.type != 1) {
                nextPoint = nextPoint.next;
                distance_up++;
            }


            Point prevPoint = this.upPoint;
            int distance_back = 0;
            while (prevPoint != null && distance_back < l_back && prevPoint.type != 1) {
                prevPoint = prevPoint.prev;
                distance_back++;
            }

            boolean flag = Math.random() < overtakeVariable;

            if (nextPointhere != null && distance_here < l_ahead_here && distance_up >= l_ahead && distance_back >= l_back && flag) {
                this.upPoint.type = 3;
                this.upPoint.velocity = this.velocity;
                this.upPoint.changedLane = changedLane;

                changedLane = false;
                type = 0;
                velocity = 0;
                this.upPoint.move();
            }
        }
    }

    public void move() {
        if (type == 1 || type == 3) {
            Point nextPoint = this;
            int i = 0;
            while (i < velocity && nextPoint.next != null) {
                nextPoint = nextPoint.next;
                i++;
            }
            if (!moved && nextPoint.type == 0) {
                nextPoint.changedLane = changedLane;
                nextPoint.type = type;
                nextPoint.moved = true;
                nextPoint.velocity = velocity;

                type = 0;
                velocity = 0;
                changedLane = false;
            }
        }
    }


    public int getState() {
        return type;
    }

    public void clicked(int x, int y) {
        if (type == 0)
            type = 1;
    }

    public void setState() {
        type = 0;
    }


}
