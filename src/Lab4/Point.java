package Lab4;

public class Point {

    public Point under = null;
    public int velocity;
    public int type = 0; //0 road, 1 car
    public boolean moved;
    public Point next;
    private static int maxVelocity = 5;
    private static float slowDownVariable = 0.3f; //p variable


    public Point() {
        velocity = 1;
    }

    public void allType() {
        acceleration();
        slowingDown();
        randomize();
        move();
    }

    public int getState() {
        return type;
    }

    public void acceleration() {
        if (type == 1 && velocity < maxVelocity) {
            velocity += 1;
        }
    }

    public void slowingDown() {
        if (type == 1) {
            Point nextPoint = this;
            int dis = 1;

            while (dis <= maxVelocity) {
                nextPoint = nextPoint.next;
                if (nextPoint.type == 1) {
                    break;
                }
                dis++;
            }

            if (dis <= velocity) {
                velocity = Math.max(dis - 1,0);
            }
        }
    }

    public void randomize() {
        if (type == 1 && velocity >= 1) {
            if (Math.random() < slowDownVariable) {
                velocity -= 1;
            }
        }
    }

    public void move() {
        if (type == 1) {
            Point nextPoint = this;
            int i = 0;
            while (i < velocity) {
                nextPoint = nextPoint.next;
                i++;
            }
            if (!moved && nextPoint.type == 0) {
                type = 0;
                velocity = 0;
                nextPoint.type = 1;
                nextPoint.moved = true;
                nextPoint.velocity = velocity;
            }
        }
    }


    public void clicked(int x, int y) {
        type = 1;
    }

    public void setState() {
        type = 0;
    }


}