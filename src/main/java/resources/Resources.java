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
    public static ArrayList<String> spriteDirArray = new ArrayList<String>();
    public static Map<String, SpriteData> spriteDataMap = new HashMap<String, SpriteData>();

    public static void getResource(String type) {
        try {
            String spriteDirPath = ClassLoader.getSystemClassLoader().getResource(type).getPath();
            Files.walk(Path.of(spriteDirPath)).forEach((path) -> {
                String fileName = Path.of(spriteDirPath).relativize(path).toString();
                if (fileName.contains(".")) spriteDirArray.add(fileName);
            });
            spriteDirArray.sort(Comparator.naturalOrder());
            for (String r:spriteDirArray) {
                loadResources(r, type);
            }
        } catch (Exception e) {
            System.out.printf("Can't get %s resources\n", type);
        }
    }

    public static void loadResources(String fileName, String type) {
        try {
            Image i = new Image(type + File.separator + fileName);
            String[] parsed = fileName.split(File.separator + "|(?<=\\D)(?=\\d)|\\.");
            if (!spriteDataMap.containsKey(parsed[0])) {
                spriteDataMap.put(parsed[0], new SpriteData(32, 32));
            }
            spriteDataMap.get(parsed[0]).addFrame(parsed[1], i);
        }
        catch (Exception e) {
            System.out.printf("Can't load %s: %s\n", type, fileName);
        }
    }
}
