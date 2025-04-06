package pcd.ass01.Jmultithread;

public class P2d {
    private final double x, y;

    public P2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public P2d sum(V2d v) {
        return new P2d(x + v.getX(), y + v.getY());
    }

    public V2d sub(P2d p) {
        return new V2d(x - p.getX(), y - p.getY());
    }

    public double distanceTo(P2d p) {
        return Math.sqrt(Math.pow(x - p.getX(), 2) + Math.pow(y - p.getY(), 2));
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}