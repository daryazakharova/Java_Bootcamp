import java.util.Objects;

public class Node {
    public int x, y; // Node position
    public int gCost; // Cost from start node
    public int hCost; // Heuristic cost to target node
    public Node parent; // Parent node for path tracing

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
        this.gCost = Integer.MAX_VALUE; // Initialize to a high value
        this.hCost = 0; // Heuristic cost will be calculated later
        this.parent = null;
    }

    public int fCost() {
        return gCost + hCost; // Total cost
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Node)) return false;
        Node node = (Node) obj;
        return x == node.x && y == node.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}