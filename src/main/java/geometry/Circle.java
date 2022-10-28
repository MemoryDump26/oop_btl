package geometry;

public class Circle {
    private double x;
    private double y;
    private double r;

    public Circle(double x, double y, double r) {
        this.x = x;
        this.y = y;
        this.r = r;
    }

    public Circle() {
        this(0, 0, 0);
    }

    public Circle(double r) {
        this(0, 0, r);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public Point getCenter() {
        return new Point(x, y);
    }

    public void setCenter(Point p) {
        this.x = p.getX();
        this.y = p.getY();
    }

    public boolean contains(Point p) {
        Point center = getCenter();
        double dX = center.getX() - p.getX();
        double dY = center.getY() - p.getY();
        double distanceSquared = dX*dX + dY*dY;
        double radiusSquared = r*r;
        return !(distanceSquared > radiusSquared);
    }
}
