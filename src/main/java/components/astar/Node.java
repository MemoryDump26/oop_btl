package components.astar;

import java.util.ArrayList;

public class Node implements Comparable<Node>{
    ArrayList<Node> neighbors = new ArrayList<>();
    boolean obstacle = false;
    boolean visited = false;

    int row;
    int col;
    Node parent = null;
    int heuristic;
    int distance = Integer.MAX_VALUE;

    public Node(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public int compareTo(Node n) {
        return Integer.compare(this.heuristic, n.heuristic);
    }

    public void resetKeepNeighbor() {
        obstacle = false;
        visited = false;
        parent = null;
        distance = Integer.MAX_VALUE;
    }

    public void addNeighbor(Node n) {
        neighbors.add(n);
    }
}
