package geometry;

public class Point {
    private double x;
    private double y;

    public static final Point ZERO = new Point(0, 0);

    public Point() {
        this.x = 0;
        this.y = 0;
    }

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point p) {
        this.x = p.getX();
        this.y = p.getY();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void zero() {
        this.x = 0;
        this.y = 0;
    }

    public Point add(double x, double y) {
        this.x += x;
        this.y += y;
        return this;
    }

    public Point subtract(double x, double y) {
        this.x -= x;
        this.y -= y;
        return this;
    }

    public Point multiply(double x, double y) {
        this.x *= x;
        this.y *= y;
        return this;
    }
    public Point add(Point p) {
        return add(p.getX(), p.getY());
    }

    public Point subtract(Point p) {
        return subtract(p.getX(), p.getY());
    }

    public Point multiply(Point p) {
        return multiply(p.getX(), p.getY());
    }

    // Vector math (should I change the name from Point to Vector2D hmm..)
    public Point normalize() {
        double length = Math.sqrt(x*x + y*y);
        this.x /= length;
        this.y /= length;
        return this;
    }

    public Point rotate(double angleCCW) {
        double s = Math.sin(angleCCW);
        double c = Math.cos(angleCCW);
        double newX = this.x*c + this.y*s;
        double newY = -this.x*s + this.y*c;
        this.x = newX;
        this.y = newY;
        return this;
    }
}