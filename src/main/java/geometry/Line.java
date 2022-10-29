package geometry;

public class Line {
    private Point start = new Point();
    private Point end = new Point();

    public Line() {
    }

    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }

    public Point getStart() {return start;}
    public Point getEnd() {return end;}
    public void setStart(Point start) {this.start = start;}
    public void setEnd(Point end) {this.end = end;}

    public double getLength() {
        return start.getDistance(end);
    }

    public double getDistance(Point p) {
        double l = getLength();
        if (l == 0) return 0;
        double x2 = start.getX();
        double y2 = start.getY();
        double x1 = end.getX();
        double y1 = end.getY();
        double x0 = p.getX();
        double y0 = p.getY();
        return Math.abs((x2 - x1) * (y1 - y0) - (x1 - x0) * (y2 - y1)) / l;
    }

    public void lengthenEnd(double newLength) {
        double oldLength = getLength();
        Point newEnd = new Point(end).subtract(start).normalize().multiply(newLength).add(start);
        end = newEnd;
    }
}
