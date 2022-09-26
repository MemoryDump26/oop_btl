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

    public boolean intersect(double x, double y, double w, double h) {
        if (this.x >= x + w || this.x + this.w <= x) return false;
        else if (this.y >= y + h || this.y + this.h <= y) return false;
        return true;
    }

    public boolean intersect(Rectangle r) {
        return intersect(r.getX(), r.getY(), r.getW(), r.getH());
    }

    public boolean intersect(Rectangle r, Point offset) {
        double newX = r.getX() + offset.getX();
        double newY = r.getY() + offset.getY();
        return intersect(newX, newY, r.getW(), r.getH());
    }

    public Point resolveCollision(Rectangle r) {
        double xOffset;
        double yOffset;
        if (this.x <= r.x) xOffset = r.x - (this.x + this.w);
        else xOffset = (r.x + r.w) - this.x;
        if (this.y <= r.y) yOffset = r.y - (this.y + this.h);
        else yOffset = (r.y + r.h) - this.y;
        System.out.printf("%f, %f\n", xOffset, yOffset);
        if (Math.abs(xOffset) == Math.abs(yOffset)) return new Point(xOffset, yOffset);
        if (Math.abs(xOffset) > Math.abs(yOffset)) return new Point(0, yOffset);
        else return new Point(xOffset, 0);
    }
}
