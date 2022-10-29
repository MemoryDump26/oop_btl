package components.astar;

import java.util.ArrayList;

public class Node implements Comparable<Node>{

    int row;
    int col;
    boolean obstacle = false;
    boolean visited = false;
    Node parent = null;
    ArrayList<Node> neighbors;
    int distance = Integer.MAX_VALUE;
    int heuristic;

    public Node(int row, int col) {
        this.row = row;
        this.col = col;
        this.obstacle = false;
        this.visited = false;
        this.parent = null;
        this.neighbors = new ArrayList<>();
        this.distance = Integer.MAX_VALUE;
    }

    public Node(Node n) {
        this.row = n.row;
        this.col = n.col;
        this.obstacle = n.obstacle;
        this.visited = n.visited;
        this.parent = n.parent;
        this.neighbors = new ArrayList<>(n.neighbors);
        this.distance = n.distance;
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
