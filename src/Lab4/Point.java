package Lab3;

public class Point {

    public Point nNeighbor;
    public Point wNeighbor;
    public Point eNeighbor;
    public Point sNeighbor;
    public float nVel;
    public float eVel;
    public float wVel;
    public float sVel;
    public float pressure;
    public static Integer[] types = {0, 1, 2};
    public int type;
    public int sinInput = 0;

    public double freq;

    public double ampl;

    public Point() {
        this.type = 0;
        clear();
        ampl = 0.5;
        freq = 5;
    }


    public void clicked() {
        pressure = 1;
    }

    public void clear() {
        // TODO: clear velocity and pressure
        nVel = eVel = wVel = sVel = pressure = 0;
    }

    public void updateVelocity() {

        // TODO: velocity update
        nVel = nVel - (nNeighbor.pressure - pressure);
        eVel = eVel - (eNeighbor.pressure - pressure);
        sVel = sVel - (sNeighbor.pressure - pressure);
        wVel = wVel - (wNeighbor.pressure - pressure);

    }

    public void updatePresure() {
        // TODO: pressure update
        if (type == 0) {
            pressure = pressure - 0.5f * (nVel + eVel + sVel + wVel);
        }

        if (type == 2) {
            double radians = Math.toRadians(sinInput);
            pressure = (float)( (Math.sin(radians * 2 * Math.PI * freq)) * ampl);
            sinInput++;
        }
    }

    public float getPressure() {
        return pressure;
    }
}