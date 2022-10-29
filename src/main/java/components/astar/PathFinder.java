package components.astar;

import javafx.scene.canvas.GraphicsContext;
import options.Globals;
import world.World;

import java.util.ArrayList;
import java.util.Collections;

import static javafx.scene.paint.Color.BLACK;

public class PathFinder {
    private GraphicsContext gc;
    private ArrayList<Node> testing = new ArrayList<>();
    private int width;
    private int height;
    private ArrayList<Node> pathToDraw = new ArrayList<>();
    private Node[][] grid;
    private World w;

    public PathFinder(int width, int height, World w, GraphicsContext gc) {
        this.width = width;
        this.height = height;
        this.grid = new Node[height][width];
        this.w = w;
        this.gc = gc;

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                grid[row][col] = new Node(row, col);
            }
        }

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (row > 0) grid[row][col].addNeighbor(grid[row - 1][col]);
                if (row < height - 1) grid[row][col].addNeighbor(grid[row + 1][col]);
                if (col > 0) grid[row][col].addNeighbor(grid[row][col - 1]);
                if (col < width - 1) grid[row][col].addNeighbor(grid[row][col + 1]);
            }
        }
    }

    public World.Direction getDirection(int rowStart, int colStart, int rowEnd, int colEnd) {
        Node startNode = grid[rowStart][colStart];
        Node endNode = getPath(rowStart, colStart, rowEnd, colEnd);
        if (endNode == null) return World.Direction.NA;
        drawPath(endNode);
        Node current = endNode;
        while (current.parent != null) {
            if (current.parent.equals(startNode)) break;
            current = current.parent;
        };

        int cRow = current.row;
        int cCol = current.col;

        if (cRow == rowStart && cCol == colStart) return World.Direction.NA;
        if (cRow == rowStart) {
            if (cCol > colStart) return World.Direction.RIGHT;
            else return World.Direction.LEFT;
        }
        else if (cCol == colStart) {
            if (cRow > rowStart) return World.Direction.DOWN;
            else return World.Direction.UP;
        }
        return World.Direction.NA;
    }

    public Node getPath(int rowStart, int colStart, int rowEnd, int colEnd) {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                grid[row][col].resetKeepNeighbor();
                if (w.isOccupied(row, col, w.getAllStaticEntities())) grid[row][col].obstacle = true;
            }
        }
        Node startNode = grid[rowStart][colStart];
        startNode.distance = 0;
        Node endNode = grid[rowEnd][colEnd];
        testing.add(startNode);

        while (!testing.isEmpty()) {
            Node current = testing.get(0);
            for (Node n : current.neighbors) {
                if (n.obstacle) continue;
                if (current.distance + 1 < n.distance) {
                    n.parent = current;
                    n.distance = current.distance + 1;
                    n.heuristic = getHeuristic(n, endNode);
                    if (!n.visited) testing.add(n);
                }
            }
            current.visited = true;
            testing.remove(0);
            Collections.sort(testing);
        }
        if (endNode.visited) return endNode;
        else return null;
    }

    public int getHeuristic(Node a, Node b) {
        return Math.abs(a.row - b.row) + Math.abs(a.col - b.col);
    }

    public void drawPath(Node start) {
        pathToDraw.add(start);
    }

    public void drawAllPath() {
        double cs = Globals.cellSize;
        for (Node start : pathToDraw) {
            while (start != null) {
                gc.setFill(BLACK);
                gc.strokeRect(start.col * cs, start.row * cs, cs, cs);
                start = start.parent;
            }
        }
        pathToDraw.clear();
    }
}