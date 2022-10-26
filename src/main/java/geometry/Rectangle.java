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

    public Point getTopLeft() {
        return new Point(x, y);
    }

    public Point getTopRight() {
        return new Point(x + w - 1, y);
    }

    public Point getBotLeft() {
        return new Point(x, y + h - 1);
    }

    public Point getBotRight() {
        return new Point(x + w - 1, y + h - 1);
    }

    public Point getCenter() {
        int centerX = (int)x + (int)w / 2;
        int centerY = (int)y + (int)h / 2;
        return new Point(centerX, centerY);
    }

    public void move(double x, double y) {
        this.x += x;
        this.y += y;
    }

    public void move(Point p) {
        move(p.getX(), p.getY());
    }

    public boolean contains(Point p) {
        if (this.x > p.getX() || this.x + this.w - 1 < p.getX()) return false;
        else if (this.y > p.getY() || this.y + this.h - 1 < p.getY()) return false;
        return true;
    }

    public boolean intersect(double x, double y, double w, double h) {
        if (this.x > x + w - 1 || this.x + this.w - 1 < x) return false;
        else if (this.y > y + h - 1|| this.y + this.h - 1 < y) return false;
        return true;
    }

    public boolean intersect(Rectangle r) {
        return intersect(r.getX(), r.getY(), r.getW(), r.getH());
    }

    public boolean intersect(Rectangle r, double xOffset, double yOffset) {
        return intersect(r.getX() + xOffset, r.getY() + yOffset, r.getW(), r.getH());
    }
}
