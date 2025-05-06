import com.diogonunes.jcolor.Ansi;
import com.diogonunes.jcolor.Attribute;

public class Player {
    private char playerChar; 
    private int x; 
    private int y; 
    private GameMap gameMap; 

    public Player(GameMap gameMap) {
        this.gameMap = gameMap;
        this.playerChar = gameMap.getPlayerChar();
        this.x = gameMap.findEntityPosition(playerChar)[0];
        this.y = gameMap.findEntityPosition(playerChar)[1]; 
    }

    public void movePlayer(String direction) {
         if (direction.equals("9")) {
            System.out.println("Exiting the game. Thank you for playing!");
            System.exit(0); 
        }
        
        int newX = x; 
        int newY = y; 

        
        switch (direction.toUpperCase()) {
            case "W": 
                newY--;
                break;
            case "S": 
                newY++;
                break;
            case "A": 
                newX--;
                break;
            case "D": 
                newX++;
                break;
            default:
                System.out.println(Ansi.colorize("Invalid move. Use 'w', 'a', 's', or 'd'.", Attribute.BLUE_TEXT()));
                return;
        }

        if (isValidMove(newX, newY)) {
             if (gameMap.getMap()[newY][newX] == gameMap.getTargetChar()) {
            System.out.println(Ansi.colorize("Congratulations! You've reached the target and won the game!", Attribute.RED_TEXT()));
            System.exit(0); 
        }
            gameMap.getMap()[y][x] = gameMap.getEmptyChar();
            x = newX; 
            y = newY; 
            gameMap.getMap()[y][x] = playerChar; 
        } else {
            System.out.println("Invalid move. You hit a wall or went out of bounds.");
        }
        
        if (!canMove()) {
            System.out.println(Ansi.colorize("You are surrounded by obstacles or at the edge of the map. You lose!", Attribute.RED_TEXT()));
            System.exit(0); 
        }
    }

    private boolean isValidMove(int newX, int newY) {
        return newX >= 0 && newX < gameMap.getSize() && newY >= 0 && newY < gameMap.getSize() &&
               (gameMap.getMap()[newY][newX] == gameMap.getEmptyChar() || 
               gameMap.getMap()[newY][newX] == gameMap.getTargetChar());
    }

    public boolean canMove() {
        int[] dx = {1, 0, -1, 0}; 
        int[] dy = {0, 1, 0, -1}; 

        for (int k = 0; k < 4; k++) {
            int newX = x + dx[k];
            int newY = y + dy[k];

            if (isValidMove(newX, newY)) {
                return true; 
            }
        }
        return false; 
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

