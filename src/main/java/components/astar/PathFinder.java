package components.astar;

import javafx.scene.canvas.GraphicsContext;
import options.Globals;
import world.World;

import java.util.ArrayList;
import java.util.Collections;

import static javafx.scene.paint.Color.BLACK;

public class PathFinder {
    private int width;
    private int height;
    private Node[][] grid;
    private World w;

    public PathFinder(World w) {
        this(w.getWidth(), w.getHeight(), w);
    }

    public PathFinder(int width, int height, World w) {
        this.width = width;
        this.height = height;
        this.grid = new Node[height][width];
        this.w = w;

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

    public Node getStartNode(Node pathEnd) {
        Node current = pathEnd;
        while (current.parent != null) {
            current = current.parent;
        }
        return current;
    }

    public World.Direction getDirection(int rowStart, int colStart, int rowEnd, int colEnd) {
        Node pathEnd = getPath(rowStart, colStart, rowEnd, colEnd);
        return getDirection(pathEnd);
    }

    public World.Direction getDirection(Node pathEnd) {
        Node endNode = pathEnd;
        if (endNode == null) return World.Direction.NA;

        Node startNode = getStartNode(pathEnd);
        int rowStart = startNode.row;
        int colStart = startNode.col;

        // Get the node next to start node.
        Node nearest = endNode;
        while (nearest.parent != null) {
            if (nearest.parent.equals(startNode)) break;
            nearest = nearest.parent;
        };

        int nearestRow = nearest.row;
        int nearestCol = nearest.col;

        if (nearestRow == rowStart && nearestCol == colStart) return World.Direction.NA;
        if (nearestRow == rowStart) {
            if (nearestCol > colStart) return World.Direction.RIGHT;
            else return World.Direction.LEFT;
        }
        else if (nearestCol == colStart) {
            if (nearestRow > rowStart) return World.Direction.DOWN;
            else return World.Direction.UP;
        }
        return World.Direction.NA;
    }

    public Node getPath(int rowStart, int colStart, int rowEnd, int colEnd) {
        ArrayList<Node> testing = new ArrayList<>();

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

    public static void drawPath(Node start, GraphicsContext gc) {
        double cs = Globals.cellSize;
        while (start != null) {
            gc.setFill(BLACK);
            gc.strokeRect(start.col * cs, start.row * cs, cs, cs);
            start = start.parent;
        }
    }
}