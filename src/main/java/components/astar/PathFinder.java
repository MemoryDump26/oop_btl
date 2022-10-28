package components.astar;

import javafx.scene.canvas.GraphicsContext;
import options.Globals;
import world.World;

import java.util.ArrayList;
import java.util.Collections;

import static javafx.scene.paint.Color.BLACK;

public class PathFinder {
    private ArrayList<Node> testing = new ArrayList<>();
    private int width;
    private int height;
    private Node[][] grid;
    private World w;

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

    public Node getPath(int rowStart, int colStart, int rowEnd, int colEnd) {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                grid[row][col].resetKeepNeighbor();
                if (w.isOccupied(row, col, w.getAllEntities())) grid[row][col].obstacle = true;
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
        return endNode;
    }

    public int getHeuristic(Node a, Node b) {
        return Math.abs(a.row - b.row) + Math.abs(a.col - b.col);
    }

    public void drawPath(Node start, GraphicsContext gc) {
        double cs = Globals.cellSize;
        while (start != null) {
            gc.setFill(BLACK);
            gc.strokeRect(start.col * cs, start.row * cs, cs, cs);
            start = start.parent;
        }
    }
}