import com.diogonunes.jcolor.Ansi;
import com.diogonunes.jcolor.Attribute;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GameMap implements GameMaps {
    private char[][] map;
    private int size;
    private int enemiesCount;
    private int wallsCount;
    private Random random;
    private String profile;

    // Characters for different entities
    private char playerChar;
    private char enemyChar;
    private char wallChar;
    private char targetChar;
    private char emptyChar;
    // Change color variables to Attribute type
    private Attribute playerColor;
    private Attribute enemyColor;
    private Attribute wallColor;
    private Attribute targetColor;
    private Attribute emptyColor;
    // List to store enemy positions
    private List<int[]> enemyPositions;
    
    public GameMap(CommandLine commandLine) throws IOException, NoSuchFieldException, IllegalAccessException {
        size = commandLine.getSize();
        enemiesCount = commandLine.getEnemiesCount();
        wallsCount = commandLine.getWallsCount();
        profile = commandLine.getProfile();
        map = new char[size][size];
        this.enemyPositions = new ArrayList<>();
        random = new Random();
       
        loadSymbols();
        loadColors();
        initializeMap();
    }

    private void loadSymbols() throws IOException {
        ParseProperties parseProperties = new ParseProperties();
        Map<String, Character> symbols = parseProperties.getSimbol(profile);
        
        playerChar = symbols.get("player.char");
        enemyChar = symbols.get("enemy.char");
        wallChar = symbols.get("wall.char");
        targetChar = symbols.get("goal.char");
        emptyChar = symbols.get("empty.char");
    }

    private void loadColors() throws IOException, NoSuchFieldException, IllegalAccessException {
        ParseProperties parseProperties = new ParseProperties();
        Map<String, Attribute> colors = parseProperties.getColor(profile);
        
        playerColor = colors.get("player.color");
        enemyColor = colors.get("enemy.color");
        wallColor = colors.get("wall.color");
        targetColor = colors.get("goal.color");
        emptyColor = colors.get("empty.color");
    }

    private void initializeMap() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                map[i][j] = emptyChar; 
            }
        }
        
        placeEntity(playerChar);                    
        placeEntity(targetChar);
        for (int i = 0; i < wallsCount; i++) {
            placeEntity(wallChar);
        }
        for (int i = 0; i < enemiesCount; i++) {
            placeEntity(enemyChar);
        }  
         
    }

   private void placeEntity(char entityChar) {
    int x, y;
    while (true) {
        x = random.nextInt(size);
        y = random.nextInt(size);
        
        if (isPositionValid(x, y, entityChar)) {
            map[y][x] = entityChar;  
            if (entityChar == enemyChar) {
                enemyPositions.add(new int[]{x, y}); 
            }
            break; 
        }
    }
}

    private boolean isPositionValid(int x, int y, char entityChar) {
        if (x < 0 || x >= size || y < 0 || y >= size) {
            return false;
        }
        if (map[y][x] != emptyChar) {
            return false;
        }
        
        return true;
    }

    public int[] findEntityPosition(char entityChar) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (map[i][j] == entityChar) {
                    return new int[]{j, i}; 
                }
            }
        }
        return new int[]{-1, -1}; 
    }

    private Attribute colorOfWichChar(char ch) {
        if (ch == playerChar) {
            return playerColor;
        } else if (ch == enemyChar) {
            return  enemyColor;
        } else if (ch == wallChar) {
            return wallColor;
        } else if (ch == targetChar) {
            return targetColor;
        } else {
            return emptyColor;
        }
    }

    public void printMap(String profile) {
        if (profile.equals("production")) {
            System.out.print("\033[H\033[2J\033[3J"); // Добавляем "\033[3J" для полного очищения буфера
            System.out.flush();
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                char ch = map[i][j];
                System.out.print(Ansi.colorize(String.valueOf(ch), colorOfWichChar(ch)));
            }
            System.out.println();
        }
    }

    public void reloadMap() throws IOException, NoSuchFieldException, IllegalAccessException {
        map = new char[size][size]; 
        initializeMap(); 
    }

    public char getPlayerChar() {
        return playerChar;
    }

    public char getTargetChar() {
        return targetChar;
    }

    public char getEmptyChar() {
        return emptyChar;
    }

    public char getEnemyChar() {
        return enemyChar;
    }

    public char getWallChar() {
        return wallChar;
    }

    public char[][] getMap() {
        return map;
    }

    public int getSize(){
        return size;
    }

    public List<int[]> getEnemyPositions() {
        return enemyPositions; 
    }
}

