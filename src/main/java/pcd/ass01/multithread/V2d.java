package pcd.ass01.multithread;

public class V2d {
    private final double x, y;

    public V2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public V2d sum(V2d v) {
        return new V2d(x + v.getX(), y + v.getY());
    }

    public V2d mul(double scalar) {
        return new V2d(x * scalar, y * scalar);
    }

    public V2d normalize() {
        double magnitude = Math.sqrt(x * x + y * y);
        return magnitude > 0 ? new V2d(x / magnitude, y / magnitude) : new V2d(0, 0);
    }

    public double magnitude() {
        return Math.sqrt(x * x + y * y);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
