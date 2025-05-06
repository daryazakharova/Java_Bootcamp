import java.io.*;
import java.util.HashMap;
import java.util.Map;

import com.diogonunes.jcolor.Attribute;


public class ParseProperties {
    public String checkProfile(String profile) {
        if (profile.equals("production")) {
            return "application-production.properties";
        } else if (profile.equals("dev")) {
            return "application-dev.properties";
        } else {
            System.out.println("Profile is not valided");
            System.exit(1);
            return null;
        }
    }

    public Map<String, Character> getSimbol(String profile) throws IOException {
        Map<String, Character> simbol = new HashMap<>();
        String path = checkProfile(profile);

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path)) {
            if (inputStream == null) {
                throw new IOException("Properties file not found: " + path);
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] lines = line.split("=");
                if (lines.length == 2) {
                    simbol.put(lines[0].trim(), lines[1].charAt(0)); 
                }
            }

        }
        return simbol;
    }

    public Map<String, Attribute> getColor(String profile) throws IOException, NoSuchFieldException, IllegalAccessException {
        Map<String, Attribute> color = new HashMap<>();
        String path = checkProfile(profile);

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path)) {
            if (inputStream == null) {
                throw new IOException("Properties file not found: " + path);
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] lines = line.split("=");
                if (lines[0].trim().equals("enemy.color") ||
                        lines[0].trim().equals("player.color") ||
                        lines[0].trim().equals("wall.color") ||
                        lines[0].trim().equals("goal.color") ||
                        lines[0].trim().equals("empty.color")) {
                        color.put(lines[0], getColorFromName(lines[1]));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return color;
    }


    private static Attribute getColorFromName(String colorName) {
    switch (colorName.toLowerCase().trim()) {
            case "black":
                return Attribute.BLACK_BACK();
            case "green":
                return Attribute.GREEN_BACK();
            case "blue":
                return Attribute.BLUE_BACK();
            case "red":
                return Attribute.RED_BACK();
            case "yellow":
                return Attribute.YELLOW_BACK();
            case "magenta":
                return Attribute.MAGENTA_BACK();
            case "cyan":
                return Attribute.CYAN_BACK();
            default:
                return Attribute.WHITE_BACK();
        }
    }

}
