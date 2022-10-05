package geometry;

public class Rectangle {
    private double x;
    private double y;
    private double w;
    private double h;

    public Rectangle() {
        this.x = 0;
        this.y = 0;
        this.w = 0;
        this.h = 0;
    }

    public Rectangle(double x, double y, double w, double h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getW() {
        return w;
    }

    public double getH() {
        return h;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setW(double w) {
        this.w = w;
    }

    public void setH(double h) {
        this.h = h;
    }

    public void move(double x, double y) {
        this.x += x;
        this.y += y;
    }

    public void move(Point p) {
        move(p.getX(), p.getY());
    }

    public boolean intersect(double x, double y, double w, double h) {
        if (this.x > x + w - 1 || this.x + this.w - 1 < x) return false;
        else if (this.y > y + h - 1|| this.y + this.h - 1 < y) return false;
        return true;
    }

    public boolean intersect(Rectangle r) {
        return intersect(r.getX(), r.getY(), r.getW(), r.getH());
    }
}
