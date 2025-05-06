import java.util.List;

public class Enemy {
    private char enemyChar;
    private int x;
    private int y;
    private GameMap gameMap;

    public Enemy(GameMap gameMap, char enemyChar, int startX, int startY) {
        this.gameMap = gameMap;
        this.enemyChar = enemyChar;
        this.x = startX;
        this.y = startY;
    }

    public void moveEnemy() {
        int[] playerPosition = gameMap.findEntityPosition(gameMap.getPlayerChar());
        if (playerPosition[0] == -1) {
            return; // Player not found
        }

        StarPathfinder pathfinder = new StarPathfinder(gameMap);
        List<Node> path = pathfinder.findPath(x, y, playerPosition[0], playerPosition[1]);

        if (!path.isEmpty()) {
            // Move to the next node in the path
            Node nextNode = path.get(1);
            // System.out.println("Enemy attempting to move to: (" + nextNode.x + ", " + nextNode.y + ")");

            // Check if the enemy is already at the next node
            if (x == nextNode.x && y == nextNode.y) {
                System.out.println("Enemy is already at the target position. No move needed.");
                return;
            }

            // Check if the move is valid
            if (isValidMove(nextNode.x, nextNode.y)) {
                gameMap.getMap()[y][x] = gameMap.getEmptyChar();
                x = nextNode.x;
                y = nextNode.y;
                gameMap.getMap()[y][x] = enemyChar;
                // System.out.println("Enemy moved to: (" + x + ", " + y + ")");

                // Check if the enemy has reached the player
                if (x == playerPosition[0] && y == playerPosition[1]) {
                    System.out.println("The enemy has reached the player! Game Over!");
                    System.exit(0);
                }
            } else {
                System.out.println("Invalid move. Skipping turn.");
            }
        } else {
            System.out.println("No valid path to player. Skipping turn.");
        }

    }

    private boolean isValidMove(int newX, int newY) {
        return newX >= 0 && newX < gameMap.getSize() && newY >= 0 && newY < gameMap.getSize() &&
               (gameMap.getMap()[newY][newX] == gameMap.getEmptyChar() || 
               gameMap.getMap()[newY][newX] == gameMap.getPlayerChar());
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

