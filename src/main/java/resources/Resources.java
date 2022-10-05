package resources;

import javafx.scene.image.Image;
import sprite.SpriteData;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Resources {
    public static Map<String, SpriteData> spriteDataMap = new HashMap<String, SpriteData>();
    public static ArrayList<File> levelList = new ArrayList<File>();

    public static void getSprites() {
        try {
            ArrayList<String> spriteDirArray = new ArrayList<String>();
            String spriteDirPath = ClassLoader.getSystemClassLoader().getResource("sprites").getPath();
            Files.walk(Path.of(spriteDirPath)).forEach((path) -> {
                String fileName = Path.of(spriteDirPath).relativize(path).toString();
                if (fileName.contains(".")) spriteDirArray.add(fileName);
            });
            spriteDirArray.sort(Comparator.naturalOrder());
            for (String r:spriteDirArray) {
                loadSprite(r);
            }
        } catch (Exception e) {
            System.out.printf("Can't get sprites\n");
        }
    }

    public static void loadSprite(String fileName) {
        try {
            Image i = new Image("sprites" + File.separator + fileName);
            String[] parsed = fileName.split(File.separator + "|(?<=\\D)(?=\\d)|\\.");
            if (!spriteDataMap.containsKey(parsed[0])) {
                spriteDataMap.put(parsed[0], new SpriteData());
            }
            spriteDataMap.get(parsed[0]).addFrame(parsed[1], i);
        }
        catch (Exception e) {
            System.out.printf("Can't load sprite: %s\n", fileName);
        }
    }

    public static void getLevels() {
        try {
            String levelDirPath = ClassLoader.getSystemClassLoader().getResource("levels").getPath();
            Files.walk(Path.of(levelDirPath)).forEach((path) -> {
                String fileName = path.toString();
                if (fileName.contains(".")) {
                    levelList.add(new File(fileName));
                }
            });
            levelList.sort(Comparator.naturalOrder());
        } catch (Exception e) {
            System.out.printf("Can't load levels\n");
        }
    }
}
