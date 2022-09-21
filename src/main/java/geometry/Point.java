package geometry;

public class Point {
    private double x;
    private double y;

    public Point() {
        this.x = 0;
        this.y = 0;
    }

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
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

    public static void main(String[] args) {
        Point p1 = new Point(2, 3);
        p1.multiply(new Point(3, 3));
        System.out.printf("%f, %f\n", p1.getX(), p1.getY());
    }
}