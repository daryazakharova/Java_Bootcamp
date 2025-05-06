import java.util.*;


public class StarPathfinder {
    private GameMaps gameMap;

    public StarPathfinder(GameMaps gameMap) {
        this.gameMap = gameMap;
    }

    public List<Node> findPath(int startX, int startY, int targetX, int targetY) {
        Node startNode = new Node(startX, startY);
        Node targetNode = new Node(targetX, targetY);

        List<Node> openList = new ArrayList<>();
        Set<Node> closedList = new HashSet<>();
        openList.add(startNode);

        while (!openList.isEmpty()) {
            Node currentNode = getLowestFCostNode(openList);

            if (currentNode.equals(targetNode)) {
                return retracePath(currentNode);
            }

            openList.remove(currentNode);
            closedList.add(currentNode);

            for (Node neighbor : getNeighbors(currentNode)) {
                if (closedList.contains(neighbor) || !isValidMove(neighbor)) {
                    continue; // Skip if already evaluated or invalid
                }

                int newCostToNeighbor = currentNode.gCost + 1; // Assuming cost of 1 for each move
                if (newCostToNeighbor < neighbor.gCost || !openList.contains(neighbor)) {
                    neighbor.gCost = newCostToNeighbor;
                    neighbor.hCost = calculateHeuristic(neighbor, targetNode);
                    neighbor.parent = currentNode;

                    if (!openList.contains(neighbor)) {
                        openList.add(neighbor);
                    }
                }
            }
        }

        return Collections.emptyList(); // No path found
    }

    private Node getLowestFCostNode(List<Node> openList) {
        return openList.stream().min(Comparator.comparingInt(Node::fCost)).orElse(null);
    }

    private List<Node> getNeighbors(Node node) {
        List<Node> neighbors = new ArrayList<>();
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}}; // Up, Right, Down, Left

        for (int[] direction : directions) {
            int newX = node.x + direction[0];
            int newY = node.y + direction[1];
            Node neighbor = new Node(newX, newY);

            // Only add valid neighbors
            if (isValidMove(neighbor)) {
                neighbors.add(neighbor);
            }
        }
        return neighbors;
    }

    private boolean isValidMove(Node node) {
        return node.x >= 0 && node.x < gameMap.getSize() && node.y >= 0 && node.y < gameMap.getSize() &&
                (gameMap.getMap()[node.y][node.x] == gameMap.getEmptyChar() ||
                        gameMap.getMap()[node.y][node.x] == gameMap.getPlayerChar());
    }

    private int calculateHeuristic(Node a, Node b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y); // Manhattan distance
    }

    private List<Node> retracePath(Node targetNode) {
        List<Node> path = new ArrayList<>();
        Node currentNode = targetNode;

        while (currentNode != null) {
            path.add(currentNode);
            currentNode = currentNode.parent;
        }

        Collections.reverse(path); // Reverse the path to get it from start to target
        return path;
    }
}