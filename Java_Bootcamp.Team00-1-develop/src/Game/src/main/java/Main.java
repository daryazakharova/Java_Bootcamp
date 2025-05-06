import com.beust.jcommander.JCommander;
import com.diogonunes.jcolor.Ansi;
import com.diogonunes.jcolor.Attribute;

import java.io.IOException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
       try {
            CommandLine commandLine = new CommandLine();
            JCommander.newBuilder()
                    .addObject(commandLine)
                    .build()
                    .parse(args);

            if (!commandLine.checkParameters()) {
                throw new IllegalParametersException("Change parameters - map can't be created");
            }

            GameMap gameMap = new GameMap(commandLine);
            Player player = new Player(gameMap); 

            List<Enemy> enemies = new ArrayList<>();
            for (int[] position : gameMap.getEnemyPositions()) {
                enemies.add(new Enemy(gameMap, gameMap.getEnemyChar(), position[0], position[1]));
            }
            gameMap.printMap(commandLine.getProfile());
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.print(Ansi.colorize("Enter move (w/a/s/d): ", Attribute.BLUE_TEXT()));
                String move = scanner.nextLine();
                player.movePlayer(move);
                for (Enemy enemy : enemies) {
                    enemy.moveEnemy();
                }
                gameMap.printMap(commandLine.getProfile());
            }
        } catch (IllegalParametersException | IOException | NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.err.println("A null pointer exception occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

