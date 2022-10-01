package MapLoader;

public class Position {
    public Position() {
    }

    public static int pixelToTile(double i) {
        return (int)(i / 16.0);
    }

    public static int tileToPixel(int i) {
        return i * 16;
    }

    public static int tileToPixel(double i) {
        return (int)(i * 16.0);
    }
}